package com.vrv.ml.singletonobjects

class X {
  import X._

  def blah = foo
}

object X {
  private def foo = 42
}

