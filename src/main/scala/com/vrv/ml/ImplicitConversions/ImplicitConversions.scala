package com.vrv.ml.ImplicitConversions

object ImplicitConversions extends App {
  /*implicit def list2ordered[A](x: List[A])
                              (implicit elem2ordered: A => Ordered[A]): Ordered[List[A]] =
    new Ordered[List[A]] {
      /* .. */
        override def compare(that: List[A]) = ???
    }

  implicit def int2ordered(x: Int): Ordered[Int] =
    new Ordered[Int] {
      /* .. */
        override def compare(that: Int) = ???
    }*/
  implicit def doubleToInt(x:Double) = x.toInt;

  val a:Int = 3.5


}
