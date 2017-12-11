package com.vrv.ml.spark


import com.twitter.bijection.Injection
import com.twitter.bijection.avro.GenericAvroCodecs
import kafka.serializer._
import org.apache.avro.Schema
import org.apache.avro.generic.GenericRecord
import org.apache.spark.streaming._
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka._
import org.apache.spark.{HashPartitioner, SparkConf, SparkContext}
import org.elasticsearch.spark._
import org.junit.Assert.assertTrue
import org.junit._

import scala.collection.mutable.ListBuffer

@Test
class Spark {

  val conf = new SparkConf().setAppName("SparkTest").setMaster("local[2]")
  val sc = new SparkContext(conf)

  @Test
  def testOK() = assertTrue(true)

  @Test
  def spark1 = {
    //  val data = Array(1, 2, 3, 4, 5)
    //  val distData = sc.parallelize(data)
    val lines = sc.textFile("src/main/scala/com/vrv/resources/data.txt")
    //  lines.foreach(println)
    //  lines.collect().foreach(println)
    lines.take(5).foreach(println)
    val lineLengths = lines.map(s => s.length).persist()
    //  val rdd = lineLengths.collect()
    //  lineLengths.foreach(println)
    lineLengths.collect().foreach(println)
    lineLengths.take(5).foreach(println)

    val totalLength = lineLengths.reduce((a, b) => a + b)
    println(totalLength)
  }

  @Test
  def spark2 = {
    val tuple = (1, "2")
    println(tuple.toString())
    //Creates RDD from a textfile
    val lines = sc.textFile("src/main/scala/com/vrv/resources/data.txt")
    //Actions
    lines.collect().foreach(println)
    //Transformations
    val pairs = lines.map(s => (s, 1))
    //Actions
    pairs.collect().foreach(println)
    //Transformations
    val counts = pairs.reduceByKey((a, b) => a + b)
    //Actions
    counts.collect().foreach(println)
    //Transformations
    val counts_1 = counts.sortByKey()
    //Transformations
    val counts_2 = counts_1.filter(t => t._2 > 1)
    //Actions
    counts_2.saveAsTextFile("output")
  }

  object Utils {
    def sumOfEveryPartition(input: Iterator[Int]): Int = {
      var total = 0
      input.foreach(elem => total += elem)
      total
    }
  }

  @Test
  def spark3 = {
    val rdd = sc.parallelize(List(1, 2, 3, 4, 51, 61), 2)
    //语法1
    rdd.map(a => {
      a + 1
    }).collect().foreach(println)
    //语法2，“_”代表迭代中的RDD的当前元素
    rdd.map({
      _.toString + "~"
    }).collect().foreach(println)
    //语法3
    rdd.map {
      _.toString + "~~"
    }.collect().foreach(println)

  }

  @Test
  def spark5 = {
    val rdd = sc.parallelize(List(1, 2, 3, 4, 51, 61), 2)
    val rddOther = sc.parallelize(List(11, 21, 31, 41, 51, 61), 3)
    //Union
    var result = rdd.union(rddOther)
    result.partitions.foreach(partition => println(partition.index))
    //Intersection
    result = rdd.intersection(rddOther)
    result.partitions.foreach(partition => println(partition.index))
    //Distinct
    rdd.distinct().collect().foreach(println)
    result = rdd.intersection(rddOther).mapPartitions(elms => {
      var total = 0
      elms.foreach(elem => total += elem)
      Iterator(total)
    }) //partition是传入的参数，是个list，要求返回也是list，即Iterator(sumOfEveryPartition(partition))
    result.collect.foreach(println)
  }

  @Test
  def spark6 = {
    val rdd = sc.parallelize(List(("A", 1), ("B", 2), ("A", 3), ("B", 5), ("C", 6), ("B", 1)), 2)
    //    //groupByKey
    //    rdd.groupByKey().collect().foreach(println)
    //    //groupByKey
    //    rdd.reduceByKey((a, b) => a + b).collect().foreach(println)
    //aggregateByKey
    val rdd1 = sc.parallelize(List(1, 2, 3, 4, 5, 6), 2)
    println(rdd1.partitions.length)
    println(rdd1.aggregate(0)((a, b) => a + b, (a, b) => a + b))
    rdd.aggregateByKey(1)((a, b) => a + b, (a, b) => a + b).collect().foreach(println)
    //    rdd.aggregateByKey("1")((a, b) => a+"~"+b,(a, b) => a+":"+b).collect().foreach(println)
    //    rdd.aggregateByKey("1")((a, b) => a+b,(a, b) => a+b).collect().foreach(println)
  }

  @Test
  def spark7 = {
    val rdd = sc.parallelize(List(("A", 1), ("B", 2), ("A", 3), ("B", 5), ("C", 6), ("B", 1), ("D", 1)), 2)
    val rdd1 = sc.parallelize(List(("A", 11), ("B", 21), ("A", 31), ("B", 51), ("C", 61), ("B", 11), ("F", 11)), 2)
    val rdd3 = sc.parallelize(List(1, 2, 3, 4, 5, 6), 2)
    val rdd5 = sc.parallelize(List("A", "B", "C", "D", "E", "F"), 2)
    //    rdd.sortByKey().collect().foreach(println)
    //    rdd.fullOuterJoin(rdd1).collect().foreach(println)
    //    rdd.cogroup(rdd1).collect().foreach(println)
    //    rdd.cartesian(rdd1).collect().foreach(println)
    //    println(rdd.partitions.length)
    //    println(rdd.coalesce(1).partitions.length)
    //    rdd5.collect().foreach(println)
    //    rdd5.repartition(1).collect().foreach(println)
    //    rdd5.repartition(2).collect().foreach(println)
    //    rdd5.collect().foreach(println)
    //    rdd5.coalesce(1).collect().foreach(println)
    //    rdd5.coalesce(2, false).collect().foreach(println)
    //    rdd5.coalesce(2, true).collect().foreach(println)
    rdd.repartitionAndSortWithinPartitions(new HashPartitioner(2)).collect().foreach(println)
  }

  @Test
  def spark8 = {
    val rdd = sc.parallelize(List(8, 7, 1, 2, 3, 4, 5, 6), 1)
    val rdd1 = sc.parallelize(List(("A", 3), ("B", 2), ("A", 1), ("B", 5), ("C", 6), ("B", 1), ("D", 1)), 1)
    val rdd2 = sc.parallelize(List(("A", "a1"), ("B", "b1"), ("A", "a2"), ("B", "b2"), ("C", "c1"), ("B", "b3"), ("D", "d1")), 2)
    //    println(rdd.reduce((a, b) => a + b))
    //    println(rdd.count())
    //    println(rdd.first())
    //    rdd.sample(true, 3, 2).collect().foreach(println)
    //    rdd.takeSample(false, 10, 2).foreach(println)
    //    rdd.takeOrdered(3).foreach(println)
    //    rdd.saveAsTextFile("output")
    //    rdd1.saveAsSequenceFile("output")
    //    rdd.saveAsObjectFile("output")
    //    val rdd2 = sc.objectFile("output/part-00000")
    //    rdd2.collect().foreach(println)
    //    rdd.foreach(println)
    //    val list = List(1, 2, 3, 4, 5, 6)
    //    println(list.map("1" + _))
    //    rdd2.reduceByKey((a,b) => a+b).saveAsTextFile("output")
    //    rdd1.sortBy(a => a).collect().foreach(println)
    //    rdd1.sortByKey().collect().foreach(println)
    //    val a = (1,3) + "1"
    //    println(a)

  }

  @Test
  def spark9 = {
    //    val broadcastVar = sc.broadcast(Array(1, 2, 3))
    //    broadcastVar.value.foreach(println)
    val rdd = sc.parallelize(Array(1, 2, 3, 4, 5, 6))
    val accum = sc.accumulator(0, "My Accumulator")
    rdd.foreach(x => accum.add(x))
    rdd.foreach(x => accum += x)
    rdd.map(x => accum += x).take(2)
    println(accum.value)
  }

  @Test
  def spark10 = {
    sc.stop()
    // Create a local StreamingContext with two working thread and batch interval of 1 second.
    // The master requires 2 cores to prevent from a starvation scenario.
    val ssc = new StreamingContext(conf, Seconds(5))
    // Create a DStream that will connect to hostname:port, like localhost:9999
    val lines = ssc.socketTextStream("192.168.2.15", 9999)
    // Split each line into words
    val words = lines.flatMap(_.split(" "))
    //    words.foreachRDD(_.collect().foreach(println))
    // Count each word in each batch
    val pairs = words.map(word => (word, 1))
    val wordCounts = pairs.reduceByKey(_ + _)
    // Print the first ten elements of each RDD generated in this DStream to the console
    wordCounts.print()
    ssc.start() // Start the computation
    ssc.awaitTermination() // Wait for the computation to terminate
  }

  object AvroUtils {
    val USER_SCHEMA =
      """{
            "namespace": "capture.avro",
            "type": "record",
            "name": "Network",
            "fields":
            [
                {"name": "src_ip", "type": "string"},
                {"name": "src_port", "type": "int"},
                {"name": "dst_ip", "type": "string"},
                {"name": "dst_port", "type": "int"},
                {"name": "protocol", "type": "string"},
                {"name": "flag", "type": "string"},
                {"name": "frame_length", "type": "int"},
                {"name": "data_length", "type": "int"},
                {"name": "seq", "type": "string"},
                {"name": "nxtseq", "type": "string"},
                {"name": "ack", "type": "string"},
                {"name": "datetime", "type": "string"}
            ]
        }"""

    val recordInjection: Injection[GenericRecord, Array[Byte]] = {
      val parser: Schema.Parser = new Schema.Parser()
      val schema: Schema = parser.parse(USER_SCHEMA)
      val recordInjection: Injection[GenericRecord, Array[Byte]] = GenericAvroCodecs.toBinary(schema)
      recordInjection
    }

    def decodeMessage(message: (String, Array[Byte])): GenericRecord = {
      recordInjection.invert(message._2).get
    }

    def test(directKafkaStream: InputDStream[(String, Array[Byte])]) = {
      directKafkaStream.map(decodeMessage)
    }

  }

  @Test
  def sparkKafkaStreaming = {
    sc.stop()
    val ssc = new StreamingContext(conf, Seconds(10))
    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> "192.168.2.118:9092",
      "auto.offset.reset" -> "smallest"
    )
    val topics = Set[String]("DemoTest01")
    val directKafkaStream = KafkaUtils.createDirectStream[String, Array[Byte], StringDecoder, DefaultDecoder](
      ssc,
      kafkaParams,
      topics
    )
    directKafkaStream.mapPartitions(messages => {
      val USER_SCHEMA =
        """{
            "namespace": "capture.avro",
            "type": "record",
            "name": "Network",
            "fields":
            [
                {"name": "src_ip", "type": "string"},
                {"name": "src_port", "type": "int"},
                {"name": "dst_ip", "type": "string"},
                {"name": "dst_port", "type": "int"},
                {"name": "protocol", "type": "string"},
                {"name": "flag", "type": "string"},
                {"name": "frame_length", "type": "int"},
                {"name": "data_length", "type": "int"},
                {"name": "seq", "type": "string"},
                {"name": "nxtseq", "type": "string"},
                {"name": "ack", "type": "string"},
                {"name": "datetime", "type": "string"}
            ]
        }"""
      val parser: Schema.Parser = new Schema.Parser()
      val schema: Schema = parser.parse(USER_SCHEMA)
      val recordInjection: Injection[GenericRecord, Array[Byte]] = GenericAvroCodecs.toBinary(schema)
      var record: GenericRecord = null
      val dataList = ListBuffer[GenericRecord]()
      while (messages.hasNext) {
        dataList.append(recordInjection.invert(messages.next._2).get)
      }
      dataList.toIterator
    }).foreachRDD(rdd => {
      rdd.foreach(record => {
        println(s"====================${record.get("datetime")}")
      });
    });

    // Start the computation
    ssc.start()
    ssc.awaitTermination()
  }

  @Test
  def spark12 = {
    sc.stop()
    conf.set("es.nodes", "192.168.2.16").set("es.port", "9200")
    val essc = new SparkContext(conf)
    val RDD = essc.esRDD("terminal-login-status/middleware")
    RDD.collect().foreach(println)
  }


}
