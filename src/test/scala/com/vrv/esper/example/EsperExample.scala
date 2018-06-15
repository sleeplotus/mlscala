package com.vrv.esper.example

import java.util

import com.espertech.esper.client._
import com.google.gson.internal.LinkedTreeMap
import com.google.gson.{Gson, GsonBuilder}
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
    var epl: String = "select address, age, name from PersonEvent"
    var statement: EPStatement = engine.getEPAdministrator.createEPL(epl, "epl1")
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
    // Destroy EPL Statement
    engine.getEPAdministrator.getStatement("epl1").destroy()
    epl = "select address from PersonEvent"
    statement = engine.getEPAdministrator.createEPL(epl, "epl2")
    statement.addListener(
      new UpdateListener() {
        override def update(newEvents: Array[EventBean], oldEvents: Array[EventBean]): Unit = {
          val address = newEvents(0).get("address")
          println(s"String.format(address: $address)")
        }
      }
    )
    // Step Four: Send Events
    engine.getEPRuntime.sendEvent(Array[AnyRef]("Peter", "16", "南京市", Array[AnyRef]("南京市", "13888888888", Integer.valueOf(2018)), "13888888888"), "PersonEvent")
    // Step Five: Destroy engine
    engine.destroy()
  }

  @Test
  def mapNestedPropertiesArrayOverlapTest(): Unit = {
    // Step One: Obtain Engine Instance
    val engine: EPServiceProvider = EPServiceProviderManager.getDefaultProvider
    // Step Two: Provide Information on Input Events
    val schemas: String =
      """create map schema extraInfoFields as (address string, phone string, uid int);
        |create map schema PersonEvent as (name string, age int, extraInfo extraInfoFields);
        |create map schema PersonEvent as (name string, age int, address string)""".stripMargin
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
    var epl: String = "select address, name from PersonEvent"
    var statement: EPStatement = engine.getEPAdministrator.createEPL(epl, "epl1")
    statement.addListener(
      new UpdateListener() {
        override def update(newEvents: Array[EventBean], oldEvents: Array[EventBean]): Unit = {
          val name = newEvents(0).get("name")
          val address = newEvents(0).get("address")
          println(s"String.format(Name: $name, address: $address)")
        }
      }
    )
    // Step Four: Send Events
    val event: util.Map[String, Any] = new util.HashMap[String, Any]()
    event.put("name", "Peter")
    event.put("address", "南京市")
    engine.getEPRuntime.sendEvent(event, "PersonEvent")
    // Step Five: Destroy engine
    engine.destroy()
  }

  @Test
  def mapNestedPropertiesArrayTest(): Unit = {
    // Step One: Obtain Engine Instance
    val engine: EPServiceProvider = EPServiceProviderManager.getDefaultProvider
    // Step Two: Provide Information on Input Events
    val schemas: String =
      """create map schema extraInfoFields as (address string, phone string, uid int);
        |create map schema PersonEvent as (name string, age int, extraInfo extraInfoFields)""".stripMargin
    schemas.split(";").foreach(schema => {
      engine.getEPAdministrator.createEPL(schema)
    })
    // Step Three: Create EPL Statements and Attach Callbacks
    val epl: String = "select name, age, extraInfo.address, extraInfo.phone as phone, extraInfo.uid from PersonEvent"
    val statement: EPStatement = engine.getEPAdministrator.createEPL(epl)
    statement.addListener(
      new UpdateListener() {
        override def update(newEvents: Array[EventBean], oldEvents: Array[EventBean]): Unit = {
          val name: AnyRef = newEvents(0).get("name")
          val age: Object = newEvents(0).get("age")
          val address: Object = newEvents(0).get("extraInfo.address")
          val phone: Object = newEvents(0).get("phone")
          val uid: AnyRef = newEvents(0).get("extraInfo.uid")
          println(s"String.format(Name: $name, Age: $age, Address: $address, Phone: $phone, Uid: $uid)")
        }
      }
    )
    // Step Four: Send Events
    val event: util.Map[String, Any] = new util.HashMap[String, Any]()
    event.put("name", "Peter")
    event.put("age", "10")
    val extraInfo: util.Map[String, Any] = new util.HashMap[String, Any]()
    extraInfo.put("address", "南京市")
    extraInfo.put("phone", "13888888888")
    extraInfo.put("uid", "2018")
    event.put("extraInfo", extraInfo)
    engine.getEPRuntime.sendEvent(event, "PersonEvent")
  }

  @Test
  def mapNestedPropertiesStatementDestroyTest(): Unit = {
    // Step One: Obtain Engine Instance
    val engine: EPServiceProvider = EPServiceProviderManager.getDefaultProvider
    // Step Two: Provide Information on Input Events
    val schemas: String =
      """create map schema extraInfoFields as (address string, phone string, uid int);
        |create map schema PersonEvent as (name string, age int, extraInfo extraInfoFields);
        |create map schema PersonEvent as (name string, age int, address string)""".stripMargin
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
    var epl: String = "select address, age, name from PersonEvent"
    var statement: EPStatement = engine.getEPAdministrator.createEPL(epl, "PersonEvent")
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
    // Destroy EPL Statement
    val personEventStatement = engine.getEPAdministrator.getStatement("PersonEvent")
    if (personEventStatement != null) {
      personEventStatement.destroy()
    }
    epl = "select address from PersonEvent"
    statement = engine.getEPAdministrator.createEPL(epl, "PersonEvent")
    statement.addListener(
      new UpdateListener() {
        override def update(newEvents: Array[EventBean], oldEvents: Array[EventBean]): Unit = {
          val address = newEvents(0).get("address")
          println(s"String.format(address: $address)")
        }
      }
    )
    // Step Four: Send Events
    val event: util.Map[String, Any] = new util.HashMap[String, Any]()
    event.put("address", "南京市")
    engine.getEPRuntime.sendEvent(event, "PersonEvent")
    // Step Five: Destroy engine
    engine.destroy()
  }

  @Test
  def mapGroupTest(): Unit = {
    // Step One: Obtain Engine Instance
    val engine: EPServiceProvider = EPServiceProviderManager.getDefaultProvider
    // Step Two: Provide Information on Input Events
    val schema: String = "create map schema PersonEvent as (name string, age int)"
    engine.getEPAdministrator.createEPL(schema)
    // Step Three: Create EPL Statements and Attach Callbacks
    val epl: String = "select name, age, sum(age) count from PersonEvent group by name"
    val statement: EPStatement = engine.getEPAdministrator.createEPL(epl)
    statement.addListener(
      new UpdateListener() {
        override def update(newEvents: Array[EventBean], oldEvents: Array[EventBean]): Unit = {
          val name = newEvents(0).get("name")
          val age = newEvents(0).get("age")
          val count = newEvents(0).get("count")
          println(s"String.format(Name: $name, Age: $age, count: $count)")
        }
      }
    )
    // Step Four: Send Events
    val event: util.Map[String, Any] = new util.HashMap[String, Any]()
    event.put("name", "Peter")
    event.put("age", 10)
    engine.getEPRuntime.sendEvent(event, "PersonEvent")
    event.put("name", "Samy")
    event.put("age", 11)
    engine.getEPRuntime.sendEvent(event, "PersonEvent")
    event.put("name", "Samy")
    event.put("age", 12)
    engine.getEPRuntime.sendEvent(event, "PersonEvent")
  }

  @Test
  def mapContextTest(): Unit = {
    // Step One: Obtain Engine Instance
    val engine: EPServiceProvider = EPServiceProviderManager.getDefaultProvider
    // Step Two: Provide Information on Input Events
    val schema: String = "create map schema PersonEvent as (name string, age int)"
    engine.getEPAdministrator.createEPL(schema)
    // Step Three: Create EPL Statements and Attach Callbacks
    val context: String = "create context SegmentedByName partition by name from PersonEvent"
    val epl: String = "context SegmentedByName select name, age, sum(age) count from PersonEvent group by name"
    engine.getEPAdministrator.createEPL(context)
    val statement: EPStatement = engine.getEPAdministrator.createEPL(epl)
    statement.addListener(
      new UpdateListener() {
        override def update(newEvents: Array[EventBean], oldEvents: Array[EventBean]): Unit = {
          val name = newEvents(0).get("name")
          val age = newEvents(0).get("age")
          val count = newEvents(0).get("count")
          println(s"String.format(Name: $name, Age: $age, count: $count)")
        }
      }
    )
    // Step Four: Send Events
    val event: util.Map[String, Any] = new util.HashMap[String, Any]()
    event.put("name", "Peter")
    event.put("age", 10)
    engine.getEPRuntime.sendEvent(event, "PersonEvent")
    event.put("name", "Peter")
    event.put("age", 12)
    engine.getEPRuntime.sendEvent(event, "PersonEvent")
    event.put("name", "Peter")
    event.put("age", 16)
    engine.getEPRuntime.sendEvent(event, "PersonEvent")
    event.put("name", "Samy")
    event.put("age", 11)
    engine.getEPRuntime.sendEvent(event, "PersonEvent")
    event.put("name", "Samy")
    event.put("age", 12)
    engine.getEPRuntime.sendEvent(event, "PersonEvent")
  }

  @Test
  def mapSchemaOneToManyRelationshipsTest(): Unit = {
    // Step One: Obtain Engine Instance
    val engine: EPServiceProvider = EPServiceProviderManager.getDefaultProvider
    // Step Two: Provide Information on Input Events
    val schemas: String =
      """create map schema ExtraInfoFields as (address string, phone string, uid int);
        |create map schema PersonEvent as (name string, age int, extraInfo ExtraInfoFields[])""".stripMargin
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
    var epl: String = "select age, name, extraInfo[0].address as address0, extraInfo[1].address as address1, extraInfo[2].address as address2 from PersonEvent where (extraInfo[0].address != '' and extraInfo[1].address != '') or extraInfo[2].address != ''"
    var statement: EPStatement = engine.getEPAdministrator.createEPL(epl, "PersonEvent")
    statement.addListener(
      new UpdateListener() {
        override def update(newEvents: Array[EventBean], oldEvents: Array[EventBean]): Unit = {
          val name = newEvents(0).get("name")
          val age = newEvents(0).get("age")
          val address0 = newEvents(0).get("address0")
          val address1 = newEvents(0).get("address1")
          println(s"String.format(Name: $name, age: $age, address0: $address0, address0: $address1)")
        }
      }
    )
    // Step Four: Send Events
    val event: util.Map[String, Any] = new util.HashMap[String, Any]()
    event.put("name", "Peter")
    event.put("age", 10)
    val extraInfo0: LinkedTreeMap[String, Any] = new LinkedTreeMap[String, Any]()
    extraInfo0.put("address","南京市")
    val extraInfo1: LinkedTreeMap[String, Any] = new LinkedTreeMap[String, Any]()
    extraInfo1.put("address","北京市")
    // 将Map中的ArrayList<T>转换成对应的定长数组T[]，因为Esper无法识别ArrayList，必须用对应类型的定长数组类型才能识别。
    val array:util.ArrayList[LinkedTreeMap[String, Any]] = new util.ArrayList[LinkedTreeMap[String, Any]]
    array.add(extraInfo0)
    array.add(extraInfo1)
    event.put("extraInfo", array)
    engine.getEPRuntime.sendEvent(event, "PersonEvent")
    // Step Five: Destroy engine
    engine.destroy()
  }

  @Test
  def eaEsperModuleTest(): Unit = {
    // Step One: Obtain Engine Instance
    val engine: EPServiceProvider = EPServiceProviderManager.getDefaultProvider
    // Step Two: Provide Information on Input Events
    val schemas: String =
      """create map schema IfEntryFileds as (ifIndex string,ifDescr string,ifType string,ifMtu string,ifSpeed string,ifPhysAddress string,ifAdminStatus string,ifOperStatus string,ifLastChange string,ifInOctets string,ifInUcastPkts string,ifInDiscards string,ifInErrors string,ifInUnknownProtos string,ifOutOctets string,ifOutUcastPkts string,ifOutDiscards string,ifOutErrors string);create map schema e_vaiops_switch as (SourceModuleType string,assetGuid string,avgBusy5 string,inDate string,ifEntry IfEntryFileds[],avgBusy1 string,runningTime string,EventReceivedTime string,event_Table_Name string,SourceModuleName string,DBConfXML string,busyPer string,ifNumber string,Event_Table_Name string,MessageSourceAddress string,icmpPing string,timeDifference string,cpuProcessorNum string,cpuCount string,createDate string,batchNumber string)""".stripMargin
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
    var epl: String = "select SourceModuleType,ifEntry[0].ifIndex as ifIndex0 from e_vaiops_switch where ifEntry[0].ifIndex != ''"
    var statement: EPStatement = engine.getEPAdministrator.createEPL(epl, "PersonEvent")
    statement.addListener(
      new UpdateListener() {
        override def update(newEvents: Array[EventBean], oldEvents: Array[EventBean]): Unit = {
          val ifIndex0 = newEvents(0).get("ifIndex0")
          val SourceModuleType = newEvents(0).get("SourceModuleType")
          println(s"String.format(ifIndex0: $ifIndex0,SourceModuleType: $SourceModuleType)")
        }
      }
    )
    // Step Four: Send Events
    val data:String = """{"MessageSourceAddress":"192.168.114.65","EventReceivedTime":"2018-06-14 16:54:44","SourceModuleName":"log_in","SourceModuleType":"im_udp","assetGuid":"dec3ff58cb294b108cf9c31fb1112eb3","inDate":"2018-06-13 13:51:23","busyPer":"","avgBusy1":"12","avgBusy5":"","cpuCount":"","cpuProcessorNum":"","ifNumber":"38","ifEntry":[{"ifIndex":"1","ifDescr":"Vlan1","ifType":"53","ifMtu":"1500","ifSpeed":"1000000000","ifPhysAddress":"b8:62:1f:72:12:c0","ifAdminStatus":"2","ifOperStatus":"2","ifLastChange":"0:00:46.61","ifInOctets":"221709172","ifInUcastPkts":"3688835","ifInDiscards":"9879","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10","ifDescr":"Vlan10","ifType":"53","ifMtu":"1500","ifSpeed":"1000000000","ifPhysAddress":"b8:62:1f:72:12:c1","ifAdminStatus":"1","ifOperStatus":"1","ifLastChange":"0:00:49.35","ifInOctets":"717836544","ifInUcastPkts":"11808653","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"426311126","ifOutUcastPkts":"5853188","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"20","ifDescr":"Vlan20","ifType":"53","ifMtu":"1500","ifSpeed":"1000000000","ifPhysAddress":"b8:62:1f:72:12:c2","ifAdminStatus":"1","ifOperStatus":"1","ifLastChange":"6 days, 5:54:38.33","ifInOctets":"2909417","ifInUcastPkts":"47171","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"58","ifOutOctets":"70111484","ifOutUcastPkts":"288893","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"30","ifDescr":"Vlan30","ifType":"53","ifMtu":"1500","ifSpeed":"1000000000","ifPhysAddress":"b8:62:1f:72:12:c3","ifAdminStatus":"1","ifOperStatus":"1","ifLastChange":"6 days, 5:55:06.67","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"70050704","ifOutUcastPkts":"287977","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"40","ifDescr":"Vlan40","ifType":"53","ifMtu":"1500","ifSpeed":"1000000000","ifPhysAddress":"b8:62:1f:72:12:c4","ifAdminStatus":"1","ifOperStatus":"1","ifLastChange":"6 days, 5:55:06.67","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"70033912","ifOutUcastPkts":"287703","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"100","ifDescr":"Vlan100","ifType":"53","ifMtu":"1500","ifSpeed":"1000000000","ifPhysAddress":"b8:62:1f:72:12:c5","ifAdminStatus":"1","ifOperStatus":"1","ifLastChange":"6 days, 5:54:38.52","ifInOctets":"776374793","ifInUcastPkts":"8758223","ifInDiscards":"23583","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"913373846","ifOutUcastPkts":"4512888","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"172","ifDescr":"Vlan172","ifType":"53","ifMtu":"1500","ifSpeed":"1000000000","ifPhysAddress":"b8:62:1f:72:12:c6","ifAdminStatus":"1","ifOperStatus":"1","ifLastChange":"6 days, 5:54:37.68","ifInOctets":"2380078881","ifInUcastPkts":"23742404","ifInDiscards":"26939","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"2395169519","ifOutUcastPkts":"22622203","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"400","ifDescr":"Vlan400","ifType":"53","ifMtu":"1500","ifSpeed":"1000000000","ifPhysAddress":"b8:62:1f:72:12:c7","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.94","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"5179","ifDescr":"StackPort1","ifType":"53","ifMtu":"0","ifSpeed":"0","ifPhysAddress":"","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:37.55","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"5180","ifDescr":"StackSub-St1-1","ifType":"53","ifSpeed":"0","ifPhysAddress":"","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"447 days, 10:01:43.29"},{"ifIndex":"5181","ifDescr":"StackSub-St1-2","ifType":"53","ifSpeed":"0","ifPhysAddress":"","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"447 days, 10:01:43.29"},{"ifIndex":"10001","ifDescr":"FastEthernet1/0/1","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:83","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.23","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10002","ifDescr":"FastEthernet1/0/2","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:84","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.23","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"512","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10003","ifDescr":"FastEthernet1/0/3","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:85","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.23","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10004","ifDescr":"FastEthernet1/0/4","ifType":"6","ifMtu":"1500","ifSpeed":"100000000","ifPhysAddress":"b8:62:1f:72:12:86","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.23","ifInOctets":"236906","ifInUcastPkts":"48","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"192","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10005","ifDescr":"FastEthernet1/0/5","ifType":"6","ifMtu":"1500","ifSpeed":"100000000","ifPhysAddress":"b8:62:1f:72:12:87","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.23","ifInOctets":"1124456","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"123","ifInUnknownProtos":"0","ifOutOctets":"5760","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10006","ifDescr":"FastEthernet1/0/6","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:88","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.23","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10007","ifDescr":"FastEthernet1/0/7","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:89","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.23","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10008","ifDescr":"FastEthernet1/0/8","ifType":"6","ifMtu":"1500","ifSpeed":"100000000","ifPhysAddress":"b8:62:1f:72:12:8a","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"6 days, 6:49:11.62","ifInOctets":"697457379","ifInUcastPkts":"1800068","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"1908791816","ifOutUcastPkts":"2041843","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10009","ifDescr":"FastEthernet1/0/9","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:8b","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.23","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10010","ifDescr":"FastEthernet1/0/10","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:8c","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"74 days, 23:49:38.08","ifInOctets":"35251767","ifInUcastPkts":"113447","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"176016898","ifOutUcastPkts":"190211","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10011","ifDescr":"FastEthernet1/0/11","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:8d","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.23","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10012","ifDescr":"FastEthernet1/0/12","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:8e","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.23","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10013","ifDescr":"FastEthernet1/0/13","ifType":"6","ifMtu":"1500","ifSpeed":"100000000","ifPhysAddress":"b8:62:1f:72:12:8f","ifAdminStatus":"1","ifOperStatus":"1","ifLastChange":"6 days, 5:54:37.65","ifInOctets":"2064951063","ifInUcastPkts":"660722647","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"2002100710","ifOutUcastPkts":"622960042","ifOutDiscards":"1762","ifOutErrors":"0"},{"ifIndex":"10014","ifDescr":"FastEthernet1/0/14","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:90","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.24","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10015","ifDescr":"FastEthernet1/0/15","ifType":"6","ifMtu":"1500","ifSpeed":"100000000","ifPhysAddress":"b8:62:1f:72:12:91","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"6 days, 6:14:02.24","ifInOctets":"13965344","ifInUcastPkts":"20620","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"8389830","ifOutUcastPkts":"33587","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10016","ifDescr":"FastEthernet1/0/16","ifType":"6","ifMtu":"1500","ifSpeed":"100000000","ifPhysAddress":"b8:62:1f:72:12:92","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"6 days, 5:57:11.85","ifInOctets":"45981","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"9134","ifOutUcastPkts":"11","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10017","ifDescr":"FastEthernet1/0/17","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:93","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.24","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10018","ifDescr":"FastEthernet1/0/18","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:94","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.24","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10019","ifDescr":"FastEthernet1/0/19","ifType":"6","ifMtu":"1500","ifSpeed":"100000000","ifPhysAddress":"b8:62:1f:72:12:95","ifAdminStatus":"1","ifOperStatus":"1","ifLastChange":"86 days, 23:59:00.19","ifInOctets":"4030647891","ifInUcastPkts":"77905374","ifInDiscards":"0","ifInErrors":"26","ifInUnknownProtos":"0","ifOutOctets":"3815300417","ifOutUcastPkts":"38335447","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10020","ifDescr":"FastEthernet1/0/20","ifType":"6","ifMtu":"1500","ifSpeed":"100000000","ifPhysAddress":"b8:62:1f:72:12:96","ifAdminStatus":"1","ifOperStatus":"1","ifLastChange":"44 days, 12:34:15.16","ifInOctets":"713565518","ifInUcastPkts":"518411600","ifInDiscards":"0","ifInErrors":"16","ifInUnknownProtos":"0","ifOutOctets":"1179819734","ifOutUcastPkts":"536938576","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10021","ifDescr":"FastEthernet1/0/21","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:97","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.24","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10022","ifDescr":"FastEthernet1/0/22","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:98","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.24","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10023","ifDescr":"FastEthernet1/0/23","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:99","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.24","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10024","ifDescr":"FastEthernet1/0/24","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:9a","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.24","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10101","ifDescr":"GigabitEthernet1/0/1","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:81","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.23","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"10102","ifDescr":"GigabitEthernet1/0/2","ifType":"6","ifMtu":"1500","ifSpeed":"10000000","ifPhysAddress":"b8:62:1f:72:12:82","ifAdminStatus":"1","ifOperStatus":"2","ifLastChange":"0:00:46.23","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"},{"ifIndex":"14501","ifDescr":"Null0","ifType":"1","ifMtu":"1500","ifSpeed":"4294967295","ifPhysAddress":"","ifAdminStatus":"1","ifOperStatus":"1","ifLastChange":"0:00:00.00","ifInOctets":"0","ifInUcastPkts":"0","ifInDiscards":"0","ifInErrors":"0","ifInUnknownProtos":"0","ifOutOctets":"0","ifOutUcastPkts":"0","ifOutDiscards":"0","ifOutErrors":"0"}],"icmpPing":"true","runningTime":"89天3小时49分钟27.67秒","Event_Table_Name":"","createDate":"2018-06-13 14:08:15","timeDifference":"1012","event_Table_Name":"e_vaiops_switch","DBConfXML":"e_vaiops_switch","batchNumber":"1551e146-efd7-4666-9651-690c6240b5c4"}"""
    val gson:Gson = (new GsonBuilder).create
    val dataMap:util.Map[_, _] = gson.fromJson(data, classOf[util.Map[_, _]])
    engine.getEPRuntime.sendEvent(dataMap, "e_vaiops_switch")
    // Step Five: Destroy engine
    engine.destroy()
  }

}


