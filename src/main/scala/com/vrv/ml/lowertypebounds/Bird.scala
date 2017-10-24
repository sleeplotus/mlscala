package com.vrv.ml.lowertypebounds


trait Bird
case class AfricanSwallow() extends Bird
case class EuropeanSwallow() extends Bird

object test extends App{
  val africanSwallowList= ListNode[AfricanSwallow](AfricanSwallow(), Nil())
  val birdList: Node[Bird] = africanSwallowList
  birdList.prepend(new EuropeanSwallow)
  println(1)
}