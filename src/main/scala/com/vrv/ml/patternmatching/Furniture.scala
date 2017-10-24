package com.vrv.ml.patternmatching

sealed abstract class Furniture{

}

case class Couch() extends Furniture
case class Chair() extends Furniture

