package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.AgentInfoMapper
import org.junit.Test

class AgentInfo {
  /**
    * AgentInfo
    */
  @Test
  def agentInfo(): Unit = {
    SchemaUtils.findTable("AgentInfo", new AgentInfoMapper())
  }
}
