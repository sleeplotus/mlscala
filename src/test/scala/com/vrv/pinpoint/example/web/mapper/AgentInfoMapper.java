/*
 * Copyright 2016 NAVER Corp.
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


import com.vrv.pinpoint.example.common.PinpointConstants;
import com.vrv.pinpoint.example.common.buffer.Buffer;
import com.vrv.pinpoint.example.common.buffer.FixedBuffer;
import com.vrv.pinpoint.example.common.hbase.HBaseTables;
import com.vrv.pinpoint.example.common.hbase.RowMapper;
import com.vrv.pinpoint.example.common.server.bo.AgentInfoBo;
import com.vrv.pinpoint.example.common.server.bo.JvmInfoBo;
import com.vrv.pinpoint.example.common.server.bo.ServerMetaDataBo;
import com.vrv.pinpoint.example.common.util.BytesUtils;
import com.vrv.pinpoint.example.common.util.TimeUtils;
import com.vrv.pinpoint.example.web.vo.AgentInfo;
import org.apache.hadoop.hbase.client.Result;

/**
 * @author HyunGil Jeong
 */
public class AgentInfoMapper implements RowMapper<AgentInfo> {

    public AgentInfo mapRow(Result result, int rowNum) throws Exception {

        byte[] rowKey = result.getRow();
        String agentId = BytesUtils.safeTrim(BytesUtils.toString(rowKey, 0, PinpointConstants.AGENT_NAME_MAX_LEN));
        long reverseStartTime = BytesUtils.bytesToLong(rowKey, HBaseTables.AGENT_NAME_MAX_LEN);
        long startTime = TimeUtils.recoveryTimeMillis(reverseStartTime);

        byte[] serializedAgentInfo = result.getValue(HBaseTables.AGENTINFO_CF_INFO, HBaseTables.AGENTINFO_CF_INFO_IDENTIFIER);
        byte[] serializedServerMetaData = result.getValue(HBaseTables.AGENTINFO_CF_INFO, HBaseTables.AGENTINFO_CF_INFO_SERVER_META_DATA);
        byte[] serializedJvmInfo = result.getValue(HBaseTables.AGENTINFO_CF_INFO, HBaseTables.AGENTINFO_CF_INFO_JVM);

        final AgentInfoBo.Builder agentInfoBoBuilder = createBuilderFromValue(serializedAgentInfo);
        agentInfoBoBuilder.setAgentId(agentId);
        agentInfoBoBuilder.setStartTime(startTime);

        if (serializedServerMetaData != null) {
            agentInfoBoBuilder.setServerMetaData(new ServerMetaDataBo.Builder(serializedServerMetaData).build());
        }
        if (serializedJvmInfo != null) {
            agentInfoBoBuilder.setJvmInfo(new JvmInfoBo(serializedJvmInfo));
        }
        return new AgentInfo(agentInfoBoBuilder.build());
    }

    private AgentInfoBo.Builder createBuilderFromValue(byte[] serializedAgentInfo) {
        final Buffer buffer = new FixedBuffer(serializedAgentInfo);
        final AgentInfoBo.Builder builder = new AgentInfoBo.Builder();
        builder.setHostName(buffer.readPrefixedString());
        builder.setIp(buffer.readPrefixedString());
        builder.setPorts(buffer.readPrefixedString());
        builder.setApplicationName(buffer.readPrefixedString());
        builder.setServiceTypeCode(buffer.readShort());
        builder.setPid(buffer.readInt());
        builder.setAgentVersion(buffer.readPrefixedString());
        builder.setStartTime(buffer.readLong());
        builder.setEndTimeStamp(buffer.readLong());
        builder.setEndStatus(buffer.readInt());
        // FIXME - 2015.09 v1.5.0 added vmVersion (check for compatibility)
        if (buffer.hasRemaining()) {
            builder.setVmVersion(buffer.readPrefixedString());
        }
        return builder;
    }
}
