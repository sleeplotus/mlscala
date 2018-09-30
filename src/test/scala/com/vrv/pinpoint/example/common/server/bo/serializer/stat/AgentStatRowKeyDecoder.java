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

package com.vrv.pinpoint.example.common.server.bo.serializer.stat;


import com.vrv.pinpoint.example.common.server.bo.serializer.RowKeyDecoder;
import com.vrv.pinpoint.example.common.server.bo.stat.AgentStatType;
import com.vrv.pinpoint.example.common.util.BytesUtils;
import com.vrv.pinpoint.example.common.util.TimeUtils;
import static com.vrv.pinpoint.example.common.hbase.HBaseTables.AGENT_NAME_MAX_LEN;
import static com.vrv.pinpoint.example.common.server.bo.stat.AgentStatType.TYPE_CODE_BYTE_LENGTH;


/**
 * @author HyunGil Jeong
 */
public class AgentStatRowKeyDecoder implements RowKeyDecoder<AgentStatRowKeyComponent> {

    public AgentStatRowKeyComponent decodeRowKey(byte[] rowkey) {
        final String agentId = BytesUtils.safeTrim(BytesUtils.toString(rowkey, 0, AGENT_NAME_MAX_LEN));
        final AgentStatType agentStatType = AgentStatType.fromTypeCode(rowkey[AGENT_NAME_MAX_LEN]);
        final long reversedBaseTimestamp = BytesUtils.bytesToLong(rowkey, AGENT_NAME_MAX_LEN + TYPE_CODE_BYTE_LENGTH);
        final long baseTimestamp = TimeUtils.recoveryTimeMillis(reversedBaseTimestamp);
        return new AgentStatRowKeyComponent(agentId, agentStatType, baseTimestamp);
    }
}
