package com.vrv.ml.spark

import org.apache.spark.{SparkConf, SparkContext}


object SparkTest extends App {
  val conf = new SparkConf().setAppName("SparkTest").setMaster("local[2]")
  val sc = new SparkContext(conf)
  //  val data = Array(1, 2, 3, 4, 5)
  //  val distData = sc.parallelize(data)
  val lines = sc.textFile("src/main/scala/com/vrv/resources/data.txt")
  //  lines.foreach(println)
  //  lines.collect().foreach(println)
  lines.take(5).foreach(println)
  val lineLengths = lines.map(s => s.length).persist()
  //  val rdd = lineLengths.collect()
  //  lineLengths.foreach(println)
  lineLengths.collect().foreach(println)
  lineLengths.take(5).foreach(println)

  val totalLength = lineLengths.reduce((a, b) => a + b)
  println(totalLength)
}
