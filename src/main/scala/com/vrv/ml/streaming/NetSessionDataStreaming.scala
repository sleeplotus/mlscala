package com.vrv.ml.streaming

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.flume._
import org.apache.spark.streaming.{Milliseconds, StreamingContext}


object NetSessionDataStreaming {

  def main(args: Array[String]): Unit = {
    sparkFlumeStreaming(args(0), args(1), args(2), args(3), args(4), args(5), args(6), args(7), args(8), args(9))
  }

  def sparkFlumeStreaming(appName: String, master: String, batchInterval: String, sparkSinkHost: String, sparkSinkPort: String, esNode: String, esPort: String, esIndex: String, esType: String, separator: String): Unit = {
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
    val netSessionStream = FlumeUtils.createPollingStream(ssc, sparkSinkHost, sparkSinkPort.toInt, StorageLevel.MEMORY_AND_DISK)
    //    flumeStream.foreachRDD(rdd => rdd.foreach(println))
    //    flumeStream.print()
    netSessionStream.count().map(cnt => "Received " + cnt + " flume events.").print()
    // Start
    ssc.start()
    ssc.awaitTermination()
  }

}
