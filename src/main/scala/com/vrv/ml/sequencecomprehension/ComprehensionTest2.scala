package com.vrv.ml.sequencecomprehension

object ComprehensionTest2 extends App {
  def foo(n: Int, v: Int) =
    for (i <- 0 until n;
         j <- i until n if i + j == v) yield
      (i, j);
  foo(20, 32) foreach {
    case (i, j) =>
      println(s"($i, $j)")
  }
}
