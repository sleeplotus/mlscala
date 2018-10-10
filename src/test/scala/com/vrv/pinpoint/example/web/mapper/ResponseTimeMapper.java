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
import com.vrv.pinpoint.example.common.hbase.HBaseTables;
import com.vrv.pinpoint.example.common.hbase.RowMapper;
import com.vrv.pinpoint.example.common.hbase.distributor.RangeOneByteSimpleHash;
import com.vrv.pinpoint.example.common.server.util.Slf4jCommonLoggerFactory;
import com.vrv.pinpoint.example.common.service.DefaultServiceTypeRegistryService;
import com.vrv.pinpoint.example.common.service.DefaultTraceMetadataLoaderService;
import com.vrv.pinpoint.example.common.service.ServiceTypeRegistryService;
import com.vrv.pinpoint.example.common.trace.ServiceType;
import com.vrv.pinpoint.example.common.util.BytesUtils;
import com.vrv.pinpoint.example.common.util.TimeUtils;
import com.vrv.pinpoint.example.web.vo.ResponseTime;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Arrays;

/**
 * @author emeroad
 */
public class ResponseTimeMapper implements RowMapper<ResponseTime> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private ServiceTypeRegistryService registry = new DefaultServiceTypeRegistryService(new DefaultTraceMetadataLoaderService(), new Slf4jCommonLoggerFactory());
    ;

    private RowKeyDistributorByHashPrefix rowKeyDistributorByHashPrefix = new RowKeyDistributorByHashPrefix(new RangeOneByteSimpleHash(0, 32, 8));

    @Override
    public ResponseTime mapRow(Result result, int rowNum) throws Exception {
        if (result.isEmpty()) {
            return null;
        }

        final byte[] rowKey = getOriginalKey(result.getRow());

        ResponseTime responseTime = createResponseTime(rowKey);
        for (Cell cell : result.rawCells()) {
            if (CellUtil.matchingFamily(cell, HBaseTables.MAP_STATISTICS_SELF_VER2_CF_COUNTER)) {
                recordColumn(responseTime, cell);
            }

            if (logger.isDebugEnabled()) {
                logger.debug("unknown column family:{}", Arrays.toString(CellUtil.cloneFamily(cell)));
            }
        }
        return responseTime;
    }

    void recordColumn(ResponseTime responseTime, Cell cell) {

        final byte[] qArray = cell.getQualifierArray();
        final int qOffset = cell.getQualifierOffset();
        short slotNumber = Bytes.toShort(qArray, qOffset);

        // agentId should be added as data.
        String agentId = Bytes.toString(qArray, qOffset + BytesUtils.SHORT_BYTE_LENGTH, cell.getQualifierLength() - BytesUtils.SHORT_BYTE_LENGTH);
        long count = Bytes.toLong(cell.getValueArray(), cell.getValueOffset());
        responseTime.addResponseTime(agentId, slotNumber, count);
    }

    private ResponseTime createResponseTime(byte[] rowKey) {
        final Buffer row = new FixedBuffer(rowKey);
        String applicationName = row.read2PrefixedString();
        short serviceTypeCode = row.readShort();
        final long timestamp = TimeUtils.recoveryTimeMillis(row.readLong());
        ServiceType serviceType = registry.findServiceType(serviceTypeCode);
        return new ResponseTime(applicationName, serviceType, timestamp);
    }

    private byte[] getOriginalKey(byte[] rowKey) {
        return rowKeyDistributorByHashPrefix.getOriginalKey(rowKey);
    }
}
