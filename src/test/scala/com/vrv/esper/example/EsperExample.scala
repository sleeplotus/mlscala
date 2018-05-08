package com.vrv.esper.example

import com.espertech.esper.client._
import org.junit._

case class PersonEvent(name: String, age: Int) {
  def getName: String = {
    name
  }

  def getAge: Int = {
    age
  }
}

class EsperExample {

  @Test
  def javaObjectEvent(): Unit = {
    // Step One: Obtain Engine Instance
    val engine: EPServiceProvider = EPServiceProviderManager.getDefaultProvider
    // Step Two: Provide Information on Input Events
    engine.getEPAdministrator.getConfiguration.addEventType(classOf[PersonEvent])
    // Step Three: Create EPL Statements and Attach Callbacks
    val epl: String = "select name, age from PersonEvent"
    val statement: EPStatement = engine.getEPAdministrator.createEPL(epl)
    statement.addListener(
      new UpdateListener() {
        override def update(newEvents: Array[EventBean], oldEvents: Array[EventBean]): Unit = {
          val name: String = newEvents(0).get("name").asInstanceOf[String]
          val age: Int = newEvents(0).get("age").asInstanceOf[Int]
          println(s"String.format(Name: $name, Age: $age)")
        }
      }
    )
    // Step Four: Send Events
    engine.getEPRuntime.sendEvent(PersonEvent("Peter", 10))
  }

  @Test
  def createEventTypeByJavaObjectEplTest(): Unit = {
    // Step One: Obtain Engine Instance
    val engine: EPServiceProvider = EPServiceProviderManager.getDefaultProvider
    // Step Two: Provide Information on Input Events
    val schema: String = "create schema PersonEvent as (name string, age int)"
    engine.getEPAdministrator.createEPL(schema)
    // Step Three: Create EPL Statements and Attach Callbacks
    val epl: String = "select name, age from PersonEvent"
    val statement: EPStatement = engine.getEPAdministrator.createEPL(epl)
    statement.addListener(
      new UpdateListener() {
        override def update(newEvents: Array[EventBean], oldEvents: Array[EventBean]): Unit = {
          val name: String = newEvents(0).get("name").asInstanceOf[String]
          val age: Int = newEvents(0).get("age").asInstanceOf[Int]
          println(s"String.format(Name: $name, Age: $age)")
        }
      }
    )
    // Step Four: Send Events
    engine.getEPRuntime.sendEvent(PersonEvent("Peter", 10))
  }

  @Test
  def createEventTypeByObjectArrayEplTest(): Unit = {
    // Step One: Obtain Engine Instance
    val engine: EPServiceProvider = EPServiceProviderManager.getDefaultProvider
    // Step Two: Provide Information on Input Events
    val schema: String = "create objectarray schema PersonEvent as (name string, age int)"
    engine.getEPAdministrator.createEPL(schema)
    // Step Three: Create EPL Statements and Attach Callbacks
    val epl: String = "select name, age from PersonEvent"
    val statement: EPStatement = engine.getEPAdministrator.createEPL(epl)
    statement.addListener(
      new UpdateListener() {
        override def update(newEvents: Array[EventBean], oldEvents: Array[EventBean]): Unit = {
          val name: String = newEvents(0).get("name").asInstanceOf[String]
          val age: Int = newEvents(0).get("age").asInstanceOf[Int]
          println(s"String.format(Name: $name, Age: $age)")
        }
      }
    )
    // Step Four: Send Events
    engine.getEPRuntime.sendEvent(Array[AnyRef]("Peter", Integer.valueOf(10)), "PersonEvent")
  }

}


