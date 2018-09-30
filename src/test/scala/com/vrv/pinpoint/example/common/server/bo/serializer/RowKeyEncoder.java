package com.vrv.pinpoint.example.common.server.bo.serializer;

/**
 * @author Woonduk Kang(emeroad)
 */
public interface RowKeyEncoder<V> {

    byte[] encodeRowKey(V value);

}
