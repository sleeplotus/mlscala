package com.vrv.pinpoint.example.common.server.bo.serializer;

/**
 * @author Woonduk Kang(emeroad)
 */
public interface RowKeyDecoder<V> {

    V decodeRowKey(byte[] rowkey);

}
