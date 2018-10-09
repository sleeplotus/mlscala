package com.vrv.pinpoint.example.common.server.bo.serializer.trace.v2;

import com.vrv.pinpoint.example.common.server.bo.SpanBo;
import com.vrv.pinpoint.example.common.server.bo.SpanChunkBo;

import java.nio.ByteBuffer;

/**
 * @author Woonduk Kang(emeroad)
 */
public interface SpanEncoder {

    byte TYPE_SPAN = 0;
    byte TYPE_SPAN_CHUNK = 1;

    // reserved
    byte TYPE_PASSIVE_SPAN = 4;
    byte TYPE_INDEX = 7;

    ByteBuffer encodeSpanQualifier(SpanEncodingContext<SpanBo> encodingContext);

    ByteBuffer encodeSpanColumnValue(SpanEncodingContext<SpanBo> encodingContext);


    ByteBuffer encodeSpanChunkQualifier(SpanEncodingContext<SpanChunkBo> encodingContext);

    ByteBuffer encodeSpanChunkColumnValue(SpanEncodingContext<SpanChunkBo> encodingContext);
}
