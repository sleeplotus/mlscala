package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.MapStatisticsCalleeMapper
import org.junit.Test

class ApplicationMapStatisticsCaller_Ver2 {
  /**
    * ApplicationMapStatisticsCaller_Ver2
    */
  @Test
  def mapRow(): Unit = {
    SchemaUtils.findTable("ApplicationMapStatisticsCaller_Ver2", new MapStatisticsCalleeMapper())
  }
}
