package com.vrv.ml.ExtractorObjects

object Test extends App {
  val customer1ID = CustomerID("Sukyoung") // Sukyoung--23098234908
  customer1ID match {
    case CustomerID(name) => println(name) // prints Sukyoung
    case _ => println("Could not extract a CustomerID")
  }

  val customer2ID = CustomerID("Nico")
  val CustomerID(name) = customer2ID
  println(name) // prints Nico

}
