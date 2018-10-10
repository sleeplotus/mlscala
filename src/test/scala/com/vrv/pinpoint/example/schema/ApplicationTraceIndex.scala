package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.TransactionIdMapper
import org.junit.Test

class ApplicationTraceIndex {
  /**
    * ApplicationTraceIndex
    */
  @Test
  def mapRow(): Unit = {
    SchemaUtils.findTable("ApplicationTraceIndex", new TransactionIdMapper())
  }
}
