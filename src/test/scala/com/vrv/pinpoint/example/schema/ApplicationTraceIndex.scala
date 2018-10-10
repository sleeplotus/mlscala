package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.{TraceIndexScatterMapper2, TraceIndexScatterMapper3, TransactionIdMapper}
import org.junit.Test

class ApplicationTraceIndex {
  /**
    * ApplicationTraceIndex
    */
  @Test
  def scanTraceIndex(): Unit = {
    SchemaUtils.findTable("ApplicationTraceIndex", new TransactionIdMapper())
  }

  /**
    * ApplicationTraceIndex
    */
  @Test
  def scanTraceScatter(): Unit = {
    SchemaUtils.findTable("ApplicationTraceIndex", new TraceIndexScatterMapper2(1, 99999))
  }

  /**
    * ApplicationTraceIndex
    */
  @Test
  def scanTraceScatterData(): Unit = {
    SchemaUtils.findTable("ApplicationTraceIndex", new TraceIndexScatterMapper3(1, 99999, 100, 100))
  }
}
