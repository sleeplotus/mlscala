package com.vrv.ml.highorderfunctions

class Decorator(left: String, right: String)  {

  def layout[A](x: A) = left + x.toString() + right
}
