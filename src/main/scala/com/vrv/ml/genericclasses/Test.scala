package com.vrv.ml.genericclasses

object Test extends App{
/*  val stack = new Stack[Int]
  stack.push(1)
  stack.push(2)
  println(stack.pop)  // prints 2
  println(stack.pop)  // prints 1*/

  val stack = new Stack[Fruit]
  val apple = new Apple
  val banana = new Banana

  stack.push(apple)
  stack.push(banana)
  println(stack.elements)
}
