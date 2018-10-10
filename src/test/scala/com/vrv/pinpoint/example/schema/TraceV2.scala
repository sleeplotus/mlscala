package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.SpanMapperV2
import org.junit.Test

class TraceV2 {

  /**
    * TraceV2
    */
  @Test
  def mapRow(): Unit = {
    SchemaUtils.findTable("TraceV2", new SpanMapperV2())
  }

}
