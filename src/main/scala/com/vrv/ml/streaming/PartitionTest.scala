package com.vrv.ml.streaming

import org.apache.spark.{SparkConf, SparkContext}

object PartitionTest {

  def main(args: Array[String]): Unit = {
    val conf = new SparkConf().setAppName("partition").setMaster("local[*]")
    val sc = new SparkContext(conf)
    val data = Array(1, 2, 3, 4, 5)
    val rdd = sc.parallelize(data)
    sc.getExecutorMemoryStatus
    sc.getExecutorStorageStatus
    println(rdd.getNumPartitions)
  }

}
