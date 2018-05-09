package com.vrv.esper.example

import java.util

import com.espertech.esper.client._
import org.junit._

import scala.util.matching.Regex
import scala.util.matching.Regex.Match

class EsperExample {

  case class PersonEvent(name: String, age: Int) {
    def getName: String = {
      name
    }

    def getAge: Int = {
      age
    }
  }

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

  @Test
  def createEventTypeByMapEplTest(): Unit = {
    // Step One: Obtain Engine Instance
    val engine: EPServiceProvider = EPServiceProviderManager.getDefaultProvider
    // Step Two: Provide Information on Input Events
    val schema: String = "create map schema PersonEvent as (name string, age int)"
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
    val event: util.Map[String, Any] = new util.HashMap[String, Any]()
    event.put("name", "Peter")
    event.put("age", 10)
    engine.getEPRuntime.sendEvent(event, "PersonEvent")
  }

  @Test
  def objectArrayNestedPropertiesTest(): Unit = {
    // Step One: Obtain Engine Instance
    val engine: EPServiceProvider = EPServiceProviderManager.getDefaultProvider
    // Step Two: Provide Information on Input Events
    val fields: String = "create objectarray schema extraInfoFields as (address string, phone string, uid int)"
    val schema: String = "create objectarray schema PersonEvent as (name string, age int, extraInfo extraInfoFields)"
    engine.getEPAdministrator.createEPL(fields)
    engine.getEPAdministrator.createEPL(schema)
    // Step Three: Create EPL Statements and Attach Callbacks
    val epl: String = "select name, age, extraInfo.address, extraInfo.phone as phone, extraInfo.uid from PersonEvent"
    val statement: EPStatement = engine.getEPAdministrator.createEPL(epl)
    statement.addListener(
      new UpdateListener() {
        override def update(newEvents: Array[EventBean], oldEvents: Array[EventBean]): Unit = {
          val name: String = newEvents(0).get("name").asInstanceOf[String]
          val age: Int = newEvents(0).get("age").asInstanceOf[Int]
          val address: String = newEvents(0).get("extraInfo.address").asInstanceOf[String]
          val phone: String = newEvents(0).get("phone").asInstanceOf[String]
          val uid: Int = newEvents(0).get("extraInfo.uid").asInstanceOf[Int]
          println(s"String.format(Name: $name, Age: $age, Address: $address, Phone: $phone, Uid: $uid)")
        }
      }
    )
    // Step Four: Send Events
    engine.getEPRuntime.sendEvent(Array[AnyRef]("Peter", Integer.valueOf(10), Array[AnyRef]("南京市", "13888888888", Integer.valueOf(2018))), "PersonEvent")
  }

  @Test
  def objectArrayNestedPropertiesArrayTest(): Unit = {
    // Step One: Obtain Engine Instance
    val engine: EPServiceProvider = EPServiceProviderManager.getDefaultProvider
    // Step Two: Provide Information on Input Events
    val schemas: String =
      """create objectarray schema extraInfoFields as (address string, phone string, uid int);
        |create objectarray schema PersonEvent as (name string, age int, extraInfo extraInfoFields)""".stripMargin
    schemas.split(";").foreach(schema => {
      engine.getEPAdministrator.createEPL(schema)
    })
    // Step Three: Create EPL Statements and Attach Callbacks
    val epl: String = "select name, age, extraInfo.address, extraInfo.phone as phone, extraInfo.uid from PersonEvent"
    val statement: EPStatement = engine.getEPAdministrator.createEPL(epl)
    statement.addListener(
      new UpdateListener() {
        override def update(newEvents: Array[EventBean], oldEvents: Array[EventBean]): Unit = {
          val name: String = newEvents(0).get("name").asInstanceOf[String]
          val age: Int = newEvents(0).get("age").asInstanceOf[Int]
          val address: String = newEvents(0).get("extraInfo.address").asInstanceOf[String]
          val phone: String = newEvents(0).get("phone").asInstanceOf[String]
          val uid: Int = newEvents(0).get("extraInfo.uid").asInstanceOf[Int]
          println(s"String.format(Name: $name, Age: $age, Address: $address, Phone: $phone, Uid: $uid)")
        }
      }
    )
    // Step Four: Send Events
    engine.getEPRuntime.sendEvent(Array[AnyRef]("Peter", Integer.valueOf(10), Array[AnyRef]("南京市", "13888888888", Integer.valueOf(2018))), "PersonEvent")
  }


  @Test
  def objectArrayNestedPropertiesArrayOverlapTest(): Unit = {
    // Step One: Obtain Engine Instance
    val engine: EPServiceProvider = EPServiceProviderManager.getDefaultProvider
    // Step Two: Provide Information on Input Events
    val schemas: String =
      """create objectarray schema extraInfoFields as (address string, phone string, uid int);
        |create objectarray schema PersonEvent as (name string, age int, extraInfo extraInfoFields);
        |create objectarray schema PersonEvent as (name string, age int, address string)""".stripMargin
    val eventTypeNameRegex: Regex = "schema\\s+(\\w+)\\s+as".r
    var eventTypeNameMatch: Option[Match] = null
    schemas.split(";").foreach(schema => {
      eventTypeNameMatch = eventTypeNameRegex.findFirstMatchIn(schema)
      if (eventTypeNameMatch.isDefined) {
        engine.getEPAdministrator.getConfiguration.removeEventType(eventTypeNameMatch.get.group(1), true)
        engine.getEPAdministrator.createEPL(schema)
      }
    })
    // Step Three: Create EPL Statements and Attach Callbacks
    val epl: String = "select address, age, name from PersonEvent"
    val statement: EPStatement = engine.getEPAdministrator.createEPL(epl)
    statement.addListener(
      new UpdateListener() {
        override def update(newEvents: Array[EventBean], oldEvents: Array[EventBean]): Unit = {
          val name = newEvents(0).get("name")
          val age = newEvents(0).get("age")
          val address = newEvents(0).get("address")
          println(s"String.format(Name: $name, age: $age, address: $address)")
        }
      }
    )
    // Step Four: Send Events
    engine.getEPRuntime.sendEvent(Array[AnyRef]("Peter", Integer.valueOf(10), "南京市", Array[AnyRef]("南京市", "13888888888", Integer.valueOf(2018)), "13888888888"), "PersonEvent")
  }
}


