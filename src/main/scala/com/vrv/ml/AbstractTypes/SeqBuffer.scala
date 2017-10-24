package com.vrv.ml.AbstractTypes

abstract class SeqBuffer extends Buffer {
  type U
  type T <: Seq[U]

  def length = element.length
}
