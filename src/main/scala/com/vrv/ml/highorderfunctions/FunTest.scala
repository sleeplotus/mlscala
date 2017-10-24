package com.vrv.ml.highorderfunctions

object FunTest extends App {
  def apply(f: Int => String, v: Int) = f

  def apply1(f: Int => String, v: Int) = f(v)

  val decorator = new Decorator("[", "]")
  println(apply1(apply(decorator.layout, 7), 7))
}
