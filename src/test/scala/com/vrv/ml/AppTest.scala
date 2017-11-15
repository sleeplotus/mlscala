package com.vrv.ml

import org.junit._
import Assert._
import com.vrv.ml.caseclasses.Book
import com.vrv.ml.traits.{Cat, Dog, Pet}

import scala.collection.mutable.ArrayBuffer

@Test
class AppTest {

  val args = Array("WordCount")

  @Test
  def testOK() = assertTrue(true)

  //    @Test
  //    def testKO() = assertTrue(false)
  @Test
  def testWordCountClass = {
    val wordCount = new WordCount()
    wordCount.main(args)
  }

  @Test
  def testWordCountObject = {
    /* App.main(args);*/
    App.test
  }

  @Test
  def traits = {
    val dog = new Dog("Harry")
    val cat = new Cat("Sally")
    val test1 = new Cat("test1")
    val animals = ArrayBuffer.empty[Pet]
    val test2 = new Cat("test2")
    animals.append(dog)
    val test3 = new Cat("test3")
    animals.append(cat)
    val test5 = new Cat("test5")
    animals.foreach(pet => println(pet.name))
  }

  @Test
  def caseclass = {
    val book = new Book("978-0486282114");

  }

}


