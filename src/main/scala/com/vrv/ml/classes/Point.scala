package com.vrv.ml.classes

class Point(val value: Double, val unit: String) {

  private var _x = 0
  private var _y = 0
  private val bound = 100

  def x = _x

  def x_=(newValue: Int): Unit = {
    if (newValue < bound) _x = newValue else printWarning
  }

  def y = _y

  def y_=(newValue: Int): Unit = {
    if (newValue < bound) _y = newValue else printWarning
  }

  private def printWarning = println("WARNING: Out of bounds")

  def apply(value: Double, unit: String): Point = {
    println("class")
    new Point(value, unit)
  }

}

object Point {
  def apply(value: Double, unit: String): Point = {
    println("object")
    new Point(value, unit)
  }

  def unapply(point: Point): Option[(Double, String)] = {
    if (point == null) {
      None
    }
    else {
      Some(point.value, point.unit)
    }
  }


  def main(args: Array[String]): Unit = {
    val aa = new Test1()
    val aa1 = Test1()

    val point = Point(30.2, "USD")
    Point.unapply(point).get
    val Point(amount, unit) = point
    println(amount)
    println(unit)

    point match {
      case Point(amount, "USD") => println("$" + amount)
      case _ => println("No match.")
    }

  }
}

case class Test1() {

}