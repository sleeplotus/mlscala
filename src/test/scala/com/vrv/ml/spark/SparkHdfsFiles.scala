package com.vrv.ml.spark

import org.apache.hadoop.conf.Configuration
import org.apache.hadoop.fs.{FileSystem, Path}
import org.apache.spark.{SparkConf, SparkContext}
import org.junit._
import java.net.URI


class SparkHdfsFiles {

  @Test
  def sparkHdfsFiles(): Unit = {
    val conf = new SparkConf().setAppName("SparkHdfsFiles").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val hadoopConf: Configuration = sc.hadoopConfiguration
    val fs: FileSystem = FileSystem.get(URI.create("hdfs://vmp002:8020"),hadoopConf)
    var exists = fs.exists(new Path("/tmp/spark_word_count/input/sparkwordcountinput.txt"))
    println("================="+exists)
    exists = fs.exists(new Path("/tmp/spark_word_count/input/sparkwordcountinput1.txt"))
    println("================="+exists)
    //  sc.textFile("hdfs://vmp002:8020/tmp/spark_word_count/input/sparkwordcountinput.txt,hdfs://vmp002:8020/tmp/spark_word_count/input/sparkwordcountinput1.txt").collect().foreach(println)
  }

}
