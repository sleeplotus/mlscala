package com.vrv.esper.example

import org.junit._


class JsonTest {

  @Test
  def jsonTest = {
    var jsonStr: String = """{"body":\\\\\"{\"assetGuid\":\"587fa983027d40598c34e1823d96c7bf\",\"ifEntry\":\"[{\"ifIndex\":\"1\",\"installedSoft\":\\\\\\"Lenovo Power Management Driver\\\""""
    jsonStr = jsonStr.replaceAll("(\\\\)+\"", "\"")
    println(jsonStr)
    jsonStr = jsonStr.replaceAll("(\\\\)*\"\\{", "\\{")
    println(jsonStr)
    jsonStr = jsonStr.replaceAll("(\\\\)*\"\\[", "\\[")
    println(jsonStr)
  }

}
