package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.MapStatisticsCallerMapper
import org.junit.Test

class ApplicationMapStatisticsCallee_Ver2 {
  /**
    * AgentInfo
    */
  @Test
  def mapRow(): Unit = {
    SchemaUtils.findTable("ApplicationMapStatisticsCallee_Ver2", new MapStatisticsCallerMapper())
  }
}
