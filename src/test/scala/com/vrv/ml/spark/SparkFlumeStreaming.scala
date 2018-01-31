package com.vrv.ml.spark

import org.apache.spark.SparkConf
import org.apache.spark.streaming.flume._
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.junit._


class SparkFlumeStreaming {

  @Test
  def sparkFlumeStreaming(): Unit = {
    val conf = new SparkConf().setAppName("SparkFlumeTest").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(10))
//    val flumeStream = FlumeUtils.createPollingStream(ssc, "hostname", "")


  }

}
