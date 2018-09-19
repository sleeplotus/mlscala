package com.vrv.pinpoint.example

import com.vrv.pinpoint.example.common.hbase.RowMapper
import com.vrv.pinpoint.example.web.mapper.AgentInfoMapper
import com.vrv.pinpoint.example.web.vo.AgentInfo
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration, TableName}
import org.junit._

class PinPoint {

  /**
    * Finds table
    * @param tableName Table Name
    * @param rowMapper Row Mapper
    * @tparam T Generic Type
    */
  def findTable[T](tableName: String, rowMapper: RowMapper[T]): Unit = {
    // Configuration
    val conf: Configuration = HBaseConfiguration.create()
    val zookeeperQuorum = "192.168.2.16"
    conf.set("hbase.zookeeper.quorum", zookeeperQuorum)
    val connection = ConnectionFactory.createConnection(conf)

    // TableName
    val table = connection.getTable(TableName.valueOf(Bytes.toBytes("default:" + tableName)))

    // Scan example
    println("Scan Example:")
    val resultScanner: ResultScanner = table.getScanner(new Scan())
    val results = resultScanner.iterator()
    var rowNum:Int = 0
    while (results.hasNext) {
      rowNum += rowNum
      println(rowMapper.mapRow(results.next(), rowNum).toString)
    }

    // Releases resources
    resultScanner.close()
    table.close()
    connection.close()
  }

  /**
    * AgentInfo
    */
  @Test
  def agentInfo(): Unit = {
    findTable[AgentInfo]("AgentInfo", new AgentInfoMapper())
  }

  /**
    * ApplicationIndex
    */
  @Test
  def applicationIndex(): Unit = {

  }


  /**
    * Map ApplicationIndex row
    *
    * @param result HBase查询结果
    */
  def applicationIndexMapRow(result: Result): Unit = {
    val cells = result.rawCells()
    println(s"RowKey：${Bytes.toString(result.getRow)}")
    for (cell <- cells) {
      val col_name = Bytes.toString(CellUtil.cloneQualifier(cell))
      val serviceTypeCode: Short = Bytes.toShort(CellUtil.cloneValue(cell));
      println(s"ColumnName：$col_name")
      println(s"ColumnValue：$serviceTypeCode")
    }
    println("=============================================================================")
  }

}
