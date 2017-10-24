package com.vrv.ml.forcomprehensions

object Test extends App {
  val userBase = List(new User("Travis", 28),
    new User("Kelly", 33),
    new User("Jennifer", 44),
    new User("Dennis", 23))

  val twentySomethings = for (user <- userBase if (user.age >= 20 && user.age < 30))
    yield user.name // i.e. add this to a list

  twentySomethings.foreach(name => println(name)) // prints Travis Dennis

  def foo(n: Int, v: Int) =
    for (i <- 0 until n;
         j <- i until n if i + j == v)
      yield (i, j)

  foo(10, 10) foreach {
    case (i, j) =>
      print(s"($i, $j) ") // prints (1, 9) (2, 8) (3, 7) (4, 6) (5, 5)
  }


}
