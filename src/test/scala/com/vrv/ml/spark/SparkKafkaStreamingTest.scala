package com.vrv.ml.spark

import com.twitter.bijection.Injection
import com.twitter.bijection.avro.GenericAvroCodecs
import com.vrv.ml.spark.AvroUtils.USER_SCHEMA
import kafka.serializer.{DefaultDecoder, StringDecoder}
import org.apache.avro.Schema
import org.apache.avro.generic.GenericRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.dstream.InputDStream
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.junit._

import scala.collection.mutable.ListBuffer

class AvroUtils extends Serializable {
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

  val recordInjection = {
    val parser: Schema.Parser = new Schema.Parser()
    val schema: Schema = parser.parse(USER_SCHEMA)
    val recordInjection: Injection[GenericRecord, Array[Byte]] = GenericAvroCodecs.toBinary(schema)
    recordInjection
  }
}

class SparkKafkaStreamingTest{
  @Test
  def sparkKafkaStreaming = {
    val conf = new SparkConf().setAppName("SparkTest").setMaster("local[2]")
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
      val dataList = ListBuffer[GenericRecord]()
      while (messages.hasNext) {
        dataList.append(AvroUtils.recordInjection.invert(messages.next._2).get)
      }
      dataList.toIterator
    }).foreachRDD(rdd => {
      rdd.foreach(record => {
        println(s"===============${record.get("protocol")}=====${record.get("datetime")}")
      });
    });

    // Start the computation
    ssc.start()
    // Wait for the computation to terminate
    ssc.awaitTermination()
  }
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

