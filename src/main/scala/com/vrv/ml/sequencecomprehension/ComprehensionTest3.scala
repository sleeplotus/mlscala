package com.vrv.ml.sequencecomprehension

object ComprehensionTest3 extends App {
  for (i <- Iterator.range(0, 20);
       j <- Iterator.range(i, 20) if i + j == 32)
    println(s"($i, $j)")
}
