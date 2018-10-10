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
import com.vrv.pinpoint.example.common.hbase.HBaseTables;
import com.vrv.pinpoint.example.common.hbase.RowMapper;
import com.vrv.pinpoint.example.common.hbase.distributor.RangeOneByteSimpleHash;
import com.vrv.pinpoint.example.common.server.bo.StringMetaDataBo;
import org.apache.hadoop.hbase.Cell;
import org.apache.hadoop.hbase.client.Result;
import org.apache.hadoop.hbase.util.Bytes;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * @author emeroad
 * @author minwoo.jung
 */
public class StringMetaDataMapper implements RowMapper<List<StringMetaDataBo>> {

    private RowKeyDistributorByHashPrefix rowKeyDistributorByHashPrefix = new RowKeyDistributorByHashPrefix(new RangeOneByteSimpleHash(0,32,8));
    
    private final static String STRING_METADATA_CF_STR_QUALI_STRING = Bytes.toString(HBaseTables.STRING_METADATA_CF_STR_QUALI_STRING);

    @Override
    public List<StringMetaDataBo> mapRow(Result result, int rowNum) throws Exception {
        if (result.isEmpty()) {
            return Collections.emptyList();
        }
        final byte[] rowKey = getOriginalKey(result.getRow());

        List<StringMetaDataBo> stringMetaDataList = new ArrayList<>();
        Cell[] rawCells = result.rawCells();
        for (Cell cell : rawCells) {
            StringMetaDataBo sqlMetaDataBo = new StringMetaDataBo();
            sqlMetaDataBo.readRowKey(rowKey);
            String stringValue = Bytes.toString(cell.getQualifierArray(), cell.getQualifierOffset(), cell.getQualifierLength());
            
            if (STRING_METADATA_CF_STR_QUALI_STRING.equals(stringValue)) {
                stringValue = Bytes.toString(cell.getValueArray(), cell.getValueOffset(), cell.getValueLength());
            }
            
            sqlMetaDataBo.setStringValue(stringValue);
            stringMetaDataList.add(sqlMetaDataBo);
        }
        return stringMetaDataList;
    }

    private byte[] getOriginalKey(byte[] rowKey) {
        return rowKeyDistributorByHashPrefix.getOriginalKey(rowKey);
    }
}
