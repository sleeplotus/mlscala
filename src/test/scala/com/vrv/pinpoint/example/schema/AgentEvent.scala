package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.AgentEventMapper
import org.junit.Test

class AgentEvent {
  /**
    * AgentInfo
    */
  @Test
  def decodeData(): Unit = {
    SchemaUtils.findTable("AgentEvent", new AgentEventMapper())
  }
}
