package com.vrv.ml.streaming

import org.apache.spark.SparkConf
import org.apache.spark.storage.StorageLevel
import org.apache.spark.streaming.flume._
import org.apache.spark.streaming.{Seconds, StreamingContext}


object SparkFlumeStreaming {

  def sparkFlumeStreaming(): Unit = {
    val conf = new SparkConf().setAppName("SparkFlumeTest").setMaster("local[3]")
    val ssc = new StreamingContext(conf, Seconds(5))
    val flumeStream = FlumeUtils.createPollingStream(ssc, "192.168.2.118", 9090, StorageLevel.MEMORY_ONLY)
    //    flumeStream.foreachRDD(rdd => rdd.foreach(println))
    //    flumeStream.print()
    flumeStream.count().map(cnt => "Received " + cnt + " flume events.").print()
    // Start
    ssc.start()
    ssc.awaitTermination()
  }

  def main(args: Array[String]): Unit = {
    sparkFlumeStreaming()
  }

}
