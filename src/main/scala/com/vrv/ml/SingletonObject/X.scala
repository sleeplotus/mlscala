package com.vrv.ml.SingletonObject

class X(name: String) {
  //  import X._
  private val a = 22

  def blah = X.foo
}

object X {
  private def foo = 42

  def apply(name: String): X = {
    println("apply")
    new X(name)
  }

  def main(args: Array[String]): Unit = {
    val a = new X("a")
    val b = X("a")
    //    val c = b.apply


  }
}
