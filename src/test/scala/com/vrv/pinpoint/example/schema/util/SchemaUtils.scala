package com.vrv.pinpoint.example.schema.util

import com.vrv.pinpoint.example.common.hbase.RowMapper
import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.hbase.client.{ConnectionFactory, Result, ResultScanner, Scan}
import org.apache.hadoop.hbase.util.Bytes
import org.apache.hadoop.hbase.{HBaseConfiguration, TableName}

object SchemaUtils {

  /**
    * Finds table
    *
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
    var rowNum: Int = 0
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
    * Finds table
    *
    * @param tableName Table Name
    * @param rowMapper Row Mapper
    */
  def findTableByOriginalRowMapper(tableName: String, rowMapper: Result => Unit): Unit = {
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
    var rowNum: Int = 0
    while (results.hasNext) {
      rowNum += rowNum
      rowMapper(results.next)
    }

    // Releases resources
    resultScanner.close()
    table.close()
    connection.close()
  }

}
