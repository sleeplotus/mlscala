/*
 * Copyright 2014 NAVER Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vrv.pinpoint.example.web.mapper;

import com.sematext.hbase.wd.RowKeyDistributorByHashPrefix;
import com.vrv.pinpoint.example.common.buffer.Buffer;
import com.vrv.pinpoint.example.common.buffer.FixedBuffer;
import com.vrv.pinpoint.example.common.hbase.RowMapper;
import com.vrv.pinpoint.example.common.hbase.distributor.RangeOneByteSimpleHash;
import com.vrv.pinpoint.example.common.server.util.Slf4jCommonLoggerFactory;
import com.vrv.pinpoint.example.common.service.DefaultServiceTypeRegistryService;
import com.vrv.pinpoint.example.common.service.DefaultTraceMetadataLoaderService;
import com.vrv.pinpoint.example.common.service.ServiceTypeRegistryService;
import com.vrv.pinpoint.example.common.trace.ServiceType;
import com.vrv.pinpoint.example.common.util.ApplicationMapStatisticsUtils;
import com.vrv.pinpoint.example.common.util.TimeUtils;
import com.vrv.pinpoint.example.web.applicationmap.rawdata.LinkDataMap;
import com.vrv.pinpoint.example.web.service.ApplicationFactory;
import com.vrv.pinpoint.example.web.service.DefaultApplicationFactory;
import com.vrv.pinpoint.example.web.vo.Application;
import org.apache.commons.lang3.StringUtils;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * @author netspider
 * 
 */
public class MapStatisticsCalleeMapper implements RowMapper<LinkDataMap> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private final LinkFilter filter;

    private ServiceTypeRegistryService registry = new DefaultServiceTypeRegistryService(new DefaultTraceMetadataLoaderService(), new Slf4jCommonLoggerFactory());

    private ApplicationFactory applicationFactory = new DefaultApplicationFactory();

    private RowKeyDistributorByHashPrefix rowKeyDistributorByHashPrefix = new RowKeyDistributorByHashPrefix(new RangeOneByteSimpleHash(0,36,32));

    public MapStatisticsCalleeMapper() {
        this(SkipLinkFilter.FILTER);
    }

    public MapStatisticsCalleeMapper(LinkFilter filter) {
        if (filter == null) {
            throw new NullPointerException("filter must not be null");
        }
        this.filter = filter;
    }

    @Override
    public LinkDataMap mapRow(Result result, int rowNum) throws Exception {
        if (result.isEmpty()) {
            return new LinkDataMap();
        }
        logger.debug("mapRow:{}", rowNum);

        final byte[] rowKey = getOriginalKey(result.getRow());

        final Buffer row = new FixedBuffer(rowKey);
        final Application calleeApplication = readCalleeApplication(row);
        final long timestamp = TimeUtils.recoveryTimeMillis(row.readLong());

        final LinkDataMap linkDataMap = new LinkDataMap();
        for (Cell cell : result.rawCells()) {

            final byte[] qualifier = CellUtil.cloneQualifier(cell);
            final Application callerApplication = readCallerApplication(qualifier, calleeApplication.getServiceType());
            if (filter.filter(callerApplication)) {
                continue;
            }

            long requestCount = Bytes.toLong(cell.getValueArray(), cell.getValueOffset());
            short histogramSlot = ApplicationMapStatisticsUtils.getHistogramSlotFromColumnName(qualifier);

            String callerHost = ApplicationMapStatisticsUtils.getHost(qualifier);
            // There may be no callerHost for virtual queue nodes from user-defined entry points.
            // Terminal nodes, such as httpclient will not have callerHost set as well, but since they're terminal
            // nodes, they would not have reached here in the first place.
            if (calleeApplication.getServiceType().isQueue()) {
                callerHost = StringUtils.defaultString(callerHost);
            }
            boolean isError = histogramSlot == (short) -1;

            if (logger.isDebugEnabled()) {
                logger.debug("    Fetched Callee. {} callerHost:{} -> {} (slot:{}/{}),  ", callerApplication, callerHost, calleeApplication, histogramSlot, requestCount);
            }

            final short slotTime = (isError) ? (short) -1 : histogramSlot;
            linkDataMap.addLinkData(callerApplication, callerApplication.getName(), calleeApplication, callerHost, timestamp, slotTime, requestCount);

            if (logger.isDebugEnabled()) {
                logger.debug("    Fetched Callee. statistics:{}", linkDataMap);
            }
        }

        return linkDataMap;
    }

    private Application readCallerApplication(byte[] qualifier, ServiceType calleeServiceType) {
        short callerServiceType = ApplicationMapStatisticsUtils.getDestServiceTypeFromColumnName(qualifier);
        // Caller may be a user node, and user nodes may call nodes with the same application name but different service type.
        // To distinguish between these user nodes, append callee's service type to the application name.
        String callerApplicationName;
        if (registry.findServiceType(callerServiceType).isUser()) {
            callerApplicationName = ApplicationMapStatisticsUtils.getDestApplicationNameFromColumnNameForUser(qualifier, calleeServiceType);
        } else {
            callerApplicationName = ApplicationMapStatisticsUtils.getDestApplicationNameFromColumnName(qualifier);
        }
        return this.applicationFactory.createApplication(callerApplicationName, callerServiceType);
    }

    private Application readCalleeApplication(Buffer row) {
        String calleeApplicationName = row.read2PrefixedString();
        short calleeServiceType = row.readShort();

        return this.applicationFactory.createApplication(calleeApplicationName, calleeServiceType);
    }

    private byte[] getOriginalKey(byte[] rowKey) {
        return rowKeyDistributorByHashPrefix.getOriginalKey(rowKey);
    }
}
