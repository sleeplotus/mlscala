package com.vrv.ml.Constructors

class Animals(var name: String, var species: Int) {
  /*静态代码块，实例化的时候运行*/
//  {
//    println(s"staticcode:$name:$species")
//    name = "Animals"
//  }

  var test = ""

  def this(name: String, species1: Int, test: String) = {
    this(name, species1)
    //    println(s"this:$test")
    //    this.test = test
  }

  def this() = {
    this("1", 2, "3")
  }

  def this(name: String) = {
    this
    //    println(s"this:$test")
    //    this.test = test
  }


  def getName = this.test

}

class Animals1(var species: Int) {
  def this(name: String) = {
    this(1)
  }

}

object test extends App {
  //  val a = new Animals(species = 1)
  //  println(a.name)
  //  val a1 = new Animals("1",2,"3")
  //  println(a1.species)
  val a1 = new Animals("11")
  println(a1.name)
  //  val a2 = new Animals1("1")
  //  println(a2.species)
  //  val a1 = new Animals("1")

}
