package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.HostApplicationMapperVer2
import org.junit.Test

class HostApplicationMap_Ver2 {
  /**
    * AgentInfo
    */
  @Test
  def mapRow(): Unit = {
    SchemaUtils.findTable("HostApplicationMap_Ver2", new HostApplicationMapperVer2())
  }
}
