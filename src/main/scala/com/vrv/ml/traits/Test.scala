package com.vrv.ml.traits

abstract class TestAC(var a:String = "123") {

}

class TestA extends TestAC{
}

trait Test {
  def print: Unit = {
    println("Trait Test")
  }

  def print1
}

object test1 extends App with Test{

  override def print1: Unit ={
    println("Trait Test1")

  }

  val testa = new TestA
  testa.a

  print1
  print



}
