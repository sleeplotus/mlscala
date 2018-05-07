package com.vrv.ml.streaming

import java.text.SimpleDateFormat
import java.util.Date

import com.vrv.ml.streaming.data.structure.NetSession

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming._
import org.apache.spark.streaming.flume.FlumeUtils
import org.elasticsearch.spark._

import scala.collection.mutable.ListBuffer



object NetSessionDataStreaming {

  def main(args: Array[String]): Unit = {
    netSessionDataSteaming(args(0), args(1), args(2), args(3), args(4), args(5), args(6), args(7), args(8), args(9))
  }

  def netSessionDataSteaming(appName: String, master: String, batchInterval: String, sparkSinkHost: String, sparkSinkPort: String, esNode: String, esPort: String, esIndex: String, esType: String, separator: String): Unit = {
    // Spark Configuration
    val conf = new SparkConf().setAppName(appName)
    if (master != null && master.contains("local")) {
      conf.setMaster(master)
    }
    conf.set("es.nodes", esNode)
    conf.set("es.port", esPort)
    conf.set("es.index.auto.create", "true")
    // Create the context
    val ssc = new StreamingContext(conf, Milliseconds(batchInterval.toInt))
    // Create a flume stream that polls the Spark Sink running in a Flume agent
    val netSessionStream = FlumeUtils.createPollingStream(ssc, sparkSinkHost, sparkSinkPort.toInt, StorageLevel.MEMORY_AND_DISK)
    // Date formatting
    val df: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd")
    val dfOfDateTime: SimpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS")
    // Data processing
    netSessionStream.mapPartitions(events => {
      val ipRegex = "^(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." +
        "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." +
        "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)\\." +
        "(1\\d{2}|2[0-4]\\d|25[0-5]|[1-9]\\d|\\d)$"
      val ipExclusive0 = "^0.0.0.0$"
      val ipExclusive255 = "^255.255.255.255$"
      val portRegex = "^([0-9]|[1-9]\\d|[1-9]\\d{2}|[1-9]\\d{3}|[1-5]\\d{4}|6[0-4]\\d{3}|65[0-4]\\d{2}|655[0-2]\\d|6553[0-5])$"
      val eventsList: ListBuffer[NetSession] = ListBuffer[NetSession]()
      var data: Array[String] = null
      while (events.hasNext) {
        data = new String(events.next().event.getBody.array()).split(separator)
        if (data != null && data.length == 12 && data(0).matches(ipRegex) && !data(0).matches(ipExclusive0) && !data(0).matches(ipExclusive255) && data(1).matches(portRegex) && data(2).matches(ipRegex) && !data(2).matches(ipExclusive0) && !data(2).matches(ipExclusive255) && data(3).matches(portRegex)) {
          if ("SYN,ACK".equals(data(5))) {
            // 如果是TCP的"SYN,ACK"帧，则调换（源IP、源端口）和（目的IP、端口）
            eventsList.append(NetSession(data(2), data(3), data(0), data(1), data(4), data(5), data(6), data(7), data(8), data(9), data(10), dfOfDateTime.parse(data(11).substring(0, data(11).length - 3))))
          } else {
            eventsList.append(NetSession(data(0), data(1), data(2), data(3), data(4), data(5), data(6), data(7), data(8), data(9), data(10), dfOfDateTime.parse(data(11).substring(0, data(11).length - 3))))
          }
        }
      }
      eventsList.toIterator
    }
    ).foreachRDD(rdd => rdd.saveToEs(esIndex + "-" + df.format(new Date()) + "/" + esType))
    ssc.start()
    ssc.awaitTermination()
  }
}
