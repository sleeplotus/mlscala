package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.StringMetaDataMapper
import org.junit.Test

class StringMetaData {

  /**
    * StringMetaData
    */
  @Test
  def decodeData(): Unit = {
    SchemaUtils.findTable("StringMetaData", new StringMetaDataMapper())
  }
}
