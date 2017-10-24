package com.vrv.ml.classcompostionwithmixins

object StringIteratorTest extends App {

  class Iter() extends StringIterator("ABCDEFG") with RichIterator

  val iter = new Iter()
  iter foreach println
}
