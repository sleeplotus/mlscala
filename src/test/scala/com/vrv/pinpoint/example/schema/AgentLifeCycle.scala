package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.AgentLifeCycleMapper
import org.junit.Test

class AgentLifeCycle {

  /**
    * AgentLifeCycle
    */
  @Test
  def agentLifeCycle(): Unit = {
    SchemaUtils.findTable("AgentLifeCycle", new AgentLifeCycleMapper())
  }

}
