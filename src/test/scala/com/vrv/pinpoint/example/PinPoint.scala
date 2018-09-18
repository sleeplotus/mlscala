package com.vrv.pinpoint.example

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client._
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{CellUtil, HBaseConfiguration, TableName}
import org.junit._

class PinPoint {

  @Test
  def agentInfo(): Unit = {
    // Configuration
    val conf: Configuration = HBaseConfiguration.create()
    val ZOOKEEPER_QUORUM = "192.168.2.16"
    conf.set("hbase.zookeeper.quorum", ZOOKEEPER_QUORUM)
    val connection = ConnectionFactory.createConnection(conf)

    // TableName
    val table = connection.getTable(TableName.valueOf(Bytes.toBytes("default:AgentInfo")))

    // Scan example
    println("Scan Example:")
    val scan = table.getScanner(new Scan())
    val results = scan.iterator()
    while (results.hasNext) {
      printRow(results.next())
    }
    table.close()
    connection.close()
  }

  /**
    * Prints row
    *
    * @param result HBase查询结果
    */
  def printRow(result: Result): Unit = {
    val cells = result.rawCells()
    println(Bytes.toString(result.getRow))
    for (cell <- cells) {
      val col_name = Bytes.toString(CellUtil.cloneQualifier(cell))
      val col_value = Bytes.toString(CellUtil.cloneValue(cell))
      println(s"$col_name")
      println(s"$col_value")
    }
    println("=============================================================================")
  }

}
