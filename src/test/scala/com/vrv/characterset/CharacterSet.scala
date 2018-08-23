package com.vrv.characterset


import java.io.{File, FileInputStream}
import java.nio.charset.Charset

import org.junit._

import scala.io.Source

class CharacterSet {

  @Test
  def characterSetTest0(): Unit = {
    val file = new File("D:/access.log")
    val in = new FileInputStream(file)
    val bytes = new Array[Byte](file.length().toInt)
    in.read(bytes)
    in.close()
    val src = Source.fromBytes(bytes, "GB2312")
    val iter = src.getLines()
    while (iter.hasNext) {
      println(iter.next())
    }
  }

  @Test
  def characterSetTest1(): Unit = {
    val file = new File("D:/access.log")
    val in = new FileInputStream(file)
    val bytes = new Array[Byte](file.length().toInt)
    in.read(bytes)
    in.close()
    val str: String = new String(bytes, "GB2312")
    println(str)
  }

  @Test
  def characterSetTest2(): Unit = {

    val str:String = new String("八月")
    if(str.equals(new String(str.getBytes(), "UTF-8"))){
      println(true)
    }
    str.getBytes()
    println(new String(str.getBytes(), "GB2312"))
    println(new String(str.getBytes("GB2312"), "GB2312"))


    val file = new File("D:/access.log")
    println(System.getProperty("file.encoding"))
    println(Charset.defaultCharset().name())
    val in = new FileInputStream(file)
    val bytes = new Array[Byte](file.length().toInt)
    in.read(bytes)
    in.close()
    val src = Source.fromBytes(bytes)
    val iter = src.getLines()
    var line:String = ""
    while (iter.hasNext) {
      line = iter.next()
      println(line)
      if(line.equals(new String(line.getBytes(), "UTF-8"))){
        println(true)
      }
      println(new String(line.getBytes("UTF-8"), "UTF-8"))
      println(new String(line.getBytes("GB2312"), "GB2312"))
    }
  }

  @Test
  def gb2312(): Unit = {
    val str:String = new String("八月")
    if(str.equals(new String(str.getBytes(), "UTF-8"))){
      println(true)
    }
    str.getBytes()
    println(new String(str.getBytes("GB2312"), "GB2312"))
  }

  @Test
  def characterSetTest3(): Unit = {
    val str: String = "A"
    println(str.getBytes())
    println(str.getBytes("GB2312"))
  }


}
