package com.vrv.ml.spark

import com.twitter.bijection.Injection
import com.twitter.bijection.avro.GenericAvroCodecs
import kafka.serializer.{DefaultDecoder, StringDecoder}
import org.apache.avro.Schema
import org.apache.avro.generic.GenericRecord
import org.apache.spark.SparkConf
import org.apache.spark.streaming.kafka.KafkaUtils
import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.junit._

import scala.collection.mutable.ListBuffer

class SparkKafkaStreaming {
  @Test
  def sparkKafkaStreaming = {
    val conf = new SparkConf().setAppName("SparkTest").setMaster("local[2]")
    val ssc = new StreamingContext(conf, Seconds(10))
    val kafkaParams = Map[String, String](
      "metadata.broker.list" -> "192.168.2.118:9092",
      "auto.offset.reset" -> "smallest"
    )
    val topics = Set[String]("DemoTest04")
    val directKafkaStream = KafkaUtils.createDirectStream[String, Array[Byte], StringDecoder, DefaultDecoder](
      ssc,
      kafkaParams,
      topics
    )
    // Decode Avro-Encoded Kafka Data
    var dataList: ListBuffer[GenericRecord] = null
    directKafkaStream.map(message => SparkKafkaStreaming.recordInjection.invert(message._2).get)
      .foreachRDD(rdd => {
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

object SparkKafkaStreaming {
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
}
