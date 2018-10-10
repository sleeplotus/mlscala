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
import com.vrv.pinpoint.example.common.server.bo.ApiMetaDataBo;
import com.vrv.pinpoint.example.common.server.bo.MethodTypeEnum;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.CellUtil;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author emeroad
 * @author minwoo.jung
 */
public class ApiMetaDataMapper implements RowMapper<List<ApiMetaDataBo>> {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    private RowKeyDistributorByHashPrefix rowKeyDistributorByHashPrefix = new RowKeyDistributorByHashPrefix(new RangeOneByteSimpleHash(0,32,8));
    
    private final static String API_METADATA_CF_API_QUALI_SIGNATURE  = Bytes.toString(HBaseTables.API_METADATA_CF_API_QUALI_SIGNATURE);

    @Override
    public List<ApiMetaDataBo> mapRow(Result result, int rowNum) throws Exception {
        if (result.isEmpty()) {
            return Collections.emptyList();
        }
        final byte[] rowKey = getOriginalKey(result.getRow());

        List<ApiMetaDataBo> apiMetaDataList = new ArrayList<>();
        for (Cell cell : result.rawCells()) {
            ApiMetaDataBo apiMetaDataBo = new ApiMetaDataBo();
            apiMetaDataBo.readRowKey(rowKey);

            byte[] qualifier = CellUtil.cloneQualifier(cell);
            byte[] value = null;
            
            if (API_METADATA_CF_API_QUALI_SIGNATURE.equals(Bytes.toString(qualifier))) {
                value = CellUtil.cloneValue(cell);
            } else {
                value = qualifier;
            }
            
            Buffer buffer = new FixedBuffer(value);
            String apiInfo = buffer.readPrefixedString();
            int lineNumber = buffer.readInt();
            MethodTypeEnum methodTypeEnum = MethodTypeEnum.DEFAULT;
            if (buffer.hasRemaining()) {
                methodTypeEnum = MethodTypeEnum.valueOf(buffer.readInt());
            }
            apiMetaDataBo.setApiInfo(apiInfo);
            apiMetaDataBo.setLineNumber(lineNumber);
            apiMetaDataBo.setMethodTypeEnum(methodTypeEnum);
            apiMetaDataList.add(apiMetaDataBo);
            if (logger.isDebugEnabled()) {
                logger.debug("read apiAnnotation:{}", apiMetaDataBo);
            }
        }
        return apiMetaDataList;
    }

    private byte[] getOriginalKey(byte[] rowKey) {
        return rowKeyDistributorByHashPrefix.getOriginalKey(rowKey);
    }


}

