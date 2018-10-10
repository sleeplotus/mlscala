/*
 *  Copyright 2016 NAVER Corp.
 *  Licensed under the Apache License, Version 2.0 (the "License");
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an "AS IS" BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */

package com.vrv.pinpoint.example.common.server.bo.serializer.trace.v2;

import com.sematext.hbase.wd.AbstractRowKeyDistributor;
import com.sematext.hbase.wd.RowKeyDistributorByHashPrefix;
import com.vrv.pinpoint.example.common.PinpointConstants;
import com.vrv.pinpoint.example.common.hbase.distributor.RangeOneByteSimpleHash;
import com.vrv.pinpoint.example.common.server.bo.serializer.RowKeyEncoder;
import com.vrv.pinpoint.example.common.util.BytesUtils;
import com.vrv.pinpoint.example.common.util.TransactionId;

/**
 * @author Woonduk Kang(emeroad)
 */
public class TraceRowKeyEncoderV2 implements RowKeyEncoder<TransactionId> {

    public static final int AGENT_NAME_MAX_LEN = PinpointConstants.AGENT_NAME_MAX_LEN;
    public static final int DISTRIBUTE_HASH_SIZE = 1;

    private final AbstractRowKeyDistributor rowKeyDistributor = new RowKeyDistributorByHashPrefix(new RangeOneByteSimpleHash(32,40,256));

    public byte[] encodeRowKey(TransactionId transactionId) {
        if (transactionId == null) {
            throw new NullPointerException("basicSpan must not be null");
        }

        byte[] rowKey = BytesUtils.stringLongLongToBytes(transactionId.getAgentId(), AGENT_NAME_MAX_LEN, transactionId.getAgentStartTime(), transactionId.getTransactionSequence());
        return wrapDistributedRowKey(rowKey);
    }

    private byte[] wrapDistributedRowKey(byte[] rowKey) {
        return rowKeyDistributor.getDistributedKey(rowKey);
    }
}
