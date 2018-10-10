package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.AgentInfoMapper
import org.junit.Test

class AgentInfo {
  /**
    * AgentInfo
    */
  @Test
  def mapRow(): Unit = {
    SchemaUtils.findTable("AgentInfo", new AgentInfoMapper())
  }
}
