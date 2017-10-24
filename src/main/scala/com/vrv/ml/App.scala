package com.vrv.ml

import org.apache.spark.{SparkConf, SparkContext}

/**
  * Hello world!
  *
  */
object App {
  val a= 1;

  def main(args: Array[String]): Unit = {
    /*val conf = new SparkConf().setAppName("WordCount").setMaster("local[2]")
    val sc = new SparkContext(conf)
    val data = Array(1, 2, 3, 4, 5)
    val distData = sc.parallelize(data)*/
    test;
  }

  def test = {
    val addOne = (x: Int) => x + 1
    println(addOne(1))

    val add = (x: Int, y: Int) => x + y
    println(add(1, 2))

    val getTheAnswer = () => 42
    println(getTheAnswer())

    println(add(1, 2))

    println(addThenMultiply(1, 2)(3))
  }

  def add(x: Int, y: Int): Int = x + y

  def addThenMultiply(x: Int, y: Int)(multiplier: Int): Int = (x + y) * multiplier


  println("Hello World!")
}
