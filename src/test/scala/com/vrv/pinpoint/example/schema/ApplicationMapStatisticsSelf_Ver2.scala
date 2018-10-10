package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.ResponseTimeMapper
import org.junit.Test

class ApplicationMapStatisticsSelf_Ver2 {
  /**
    * ApplicationMapStatisticsSelf_Ver2
    */
  @Test
  def mapRow(): Unit = {
    SchemaUtils.findTable("ApplicationMapStatisticsSelf_Ver2", new ResponseTimeMapper())
  }
}
