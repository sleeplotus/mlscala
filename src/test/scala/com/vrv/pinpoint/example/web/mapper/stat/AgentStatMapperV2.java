/*
 * Copyright 2016 Naver Corp.
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

package com.vrv.pinpoint.example.web.mapper.stat;


import com.vrv.pinpoint.example.common.buffer.Buffer;
import com.vrv.pinpoint.example.common.buffer.OffsetFixedBuffer;
import com.vrv.pinpoint.example.common.hbase.HBaseTables;
import com.vrv.pinpoint.example.common.server.bo.codec.stat.AgentStatDecoder;
import com.vrv.pinpoint.example.common.server.bo.serializer.stat.AgentStatDecodingContext;
import com.vrv.pinpoint.example.common.server.bo.serializer.stat.AgentStatHbaseOperationFactory;
import com.vrv.pinpoint.example.common.server.bo.stat.AgentStatDataPoint;
import com.vrv.pinpoint.example.common.server.bo.stat.AgentStatDataPointList;
import com.vrv.pinpoint.example.web.mapper.TimestampFilter;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

/**
 * @author HyunGil Jeong
 */
public class AgentStatMapperV2<T extends AgentStatDataPoint> implements AgentStatMapper<T> {

    public final static Comparator<AgentStatDataPoint> REVERSE_TIMESTAMP_COMPARATOR = new Comparator<AgentStatDataPoint>() {
        public int compare(AgentStatDataPoint o1, AgentStatDataPoint o2) {
            long x = o2.getTimestamp();
            long y = o1.getTimestamp();
            return (x < y) ? -1 : ((x == y) ? 0 : 1);
        }
    };

    private final AgentStatHbaseOperationFactory hbaseOperationFactory;
    private final AgentStatDecoder<T> decoder;
    private final TimestampFilter filter;

    public AgentStatMapperV2(AgentStatHbaseOperationFactory hbaseOperationFactory, AgentStatDecoder<T> decoder, TimestampFilter filter) {
        this.hbaseOperationFactory = hbaseOperationFactory;
        this.decoder = decoder;
        this.filter = filter;
    }

    @Override
    public List<T> mapRow(Result result, int rowNum) throws Exception {
        if (result.isEmpty()) {
            return Collections.emptyList();
        }
        final byte[] distributedRowKey = result.getRow();
        final String agentId = this.hbaseOperationFactory.getAgentId(distributedRowKey);
        final long baseTimestamp = this.hbaseOperationFactory.getBaseTimestamp(distributedRowKey);

        List<T> dataPoints = new ArrayList<>();

        for (Cell cell : result.rawCells()) {
            if (CellUtil.matchingFamily(cell, HBaseTables.AGENT_STAT_CF_STATISTICS)) {
                Buffer qualifierBuffer = new OffsetFixedBuffer(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
                Buffer valueBuffer = new OffsetFixedBuffer(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());

                long timestampDelta = this.decoder.decodeQualifier(qualifierBuffer);

                AgentStatDecodingContext decodingContext = new AgentStatDecodingContext();
                decodingContext.setAgentId(agentId);
                decodingContext.setBaseTimestamp(baseTimestamp);
                decodingContext.setTimestampDelta(timestampDelta);
                try{
                    List<T> candidates = this.decoder.decodeValue(valueBuffer, decodingContext);
                    for (T candidate : candidates) {
                        if (filter(candidate)) {
                            continue;
                        }
                        dataPoints.add(candidate);
                    }
                }catch (Throwable e){
//                    e.printStackTrace();
                }

            }
        }
        // Reverse sort as timestamp is stored in a reversed order.
        dataPoints.sort(REVERSE_TIMESTAMP_COMPARATOR);
        return dataPoints;
    }

    private boolean filter(T candidate) {
        if (candidate instanceof AgentStatDataPointList) {
            List<AgentStatDataPointList> list = ((AgentStatDataPointList) candidate).getList();
            for (AgentStatDataPoint agentStatDataPoint : list) {
                long timestamp = agentStatDataPoint.getTimestamp();
                if (!this.filter.filter(timestamp)) {
                    return false;
                }
            }
            return true;
        } else {
            long timestamp = candidate.getTimestamp();
            return this.filter.filter(timestamp);
        }
    }

}
