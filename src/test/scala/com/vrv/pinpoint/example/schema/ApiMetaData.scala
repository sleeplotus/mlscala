package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.ApiMetaDataMapper
import org.junit.Test

class ApiMetaData {
  /**
    * ApiMetaData
    */
  @Test
  def apiMetaData(): Unit = {
    SchemaUtils.findTable("ApiMetaData", new ApiMetaDataMapper())
  }
}
