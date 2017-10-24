package com.vrv.ml.AbstractTypes

abstract class IntSeqBuffer extends SeqBuffer {
  type U = Int
}

object IntSeqBuffer extends App{
  def newIntSeqBuf(elem1: Int, elem2: Int): IntSeqBuffer =
    new IntSeqBuffer {
      type T = List[U]
      val element = List(elem1, elem2)
    }
  val buf = newIntSeqBuf(7, 8)
  println("length = " + buf.length)
  println("content = " + buf.element)
}
