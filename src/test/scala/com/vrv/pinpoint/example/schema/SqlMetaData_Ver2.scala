package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.SqlMetaDataMapper
import org.junit.Test

class SqlMetaData_Ver2 {
  /**
    * AgentInfo
    */
  @Test
  def decodeData(): Unit = {
    SchemaUtils.findTable("SqlMetaData_Ver2", new SqlMetaDataMapper())
  }
}
