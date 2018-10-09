package com.vrv.pinpoint.example.schema

import com.vrv.pinpoint.example.schema.util.SchemaUtils
import org.apache.hadoop.hbase.CellUtil
import org.apache.hadoop.hbase.client.Result
import org.apache.hadoop.hbase.util.Bytes
import org.junit.Test

class ApplicationIndex {
  /**
    * ApplicationIndex
    */
  @Test
  def applicationIndex(): Unit = {
    SchemaUtils.findTableByOriginalRowMapper("ApplicationIndex", applicationIndexRowMapper)
  }

  /**
    * Map ApplicationIndex row
    *
    * @param result HBase查询结果
    */
  def applicationIndexRowMapper(result: Result): Unit = {
    val cells = result.rawCells()
    println(s"ApplicationName：${Bytes.toString(result.getRow)}")
    for (cell <- cells) {
      val col_name = Bytes.toString(CellUtil.cloneQualifier(cell))
      val serviceTypeCode: Short = Bytes.toShort(CellUtil.cloneValue(cell))
      println(s"AgentId：$col_name")
      println(s"ServiceTypeCode：$serviceTypeCode")
    }
    println("=============================================================================")
  }
}
