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
  def mapRow(): Unit = {
    SchemaUtils.findTableByOriginalRowMapper("ApplicationIndex", applicationIndexRowMapper)
  }

  /**
    * Map ApplicationIndex row
    *
    * @param result HBase查询结果
    */
  def applicationIndexRowMapper(result: Result): Unit = {
    val cells = result.rawCells()
    val sb:StringBuilder = new StringBuilder
    sb.append(s"{applicationName:'${Bytes.toString(result.getRow)}',agents:[")
    for (cell <- cells) {
      val col_name = Bytes.toString(CellUtil.cloneQualifier(cell))
      val serviceTypeCode: Short = Bytes.toShort(CellUtil.cloneValue(cell))
      sb.append(s"{agentId:'$col_name',")
      sb.append(s"serviceTypeCode:$serviceTypeCode},")
    }
    sb.append("]}")
    println(sb)
  }
}
