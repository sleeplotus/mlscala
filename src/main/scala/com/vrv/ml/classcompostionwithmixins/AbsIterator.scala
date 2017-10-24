package com.vrv.ml.classcompostionwithmixins

abstract class AbsIterator {
  type T

  def hasNext: Boolean

  def next(): T
}
