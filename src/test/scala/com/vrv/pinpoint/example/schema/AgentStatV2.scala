package com.vrv.pinpoint.example.schema


import java.util.{ArrayList, Calendar, List}

import com.vrv.pinpoint.example.common.server.bo.codec.AgentStatCodec
import com.vrv.pinpoint.example.common.server.bo.codec.stat._
import com.vrv.pinpoint.example.common.server.bo.codec.stat.v1.{ActiveTraceCodecV1, CpuLoadCodecV1, JvmGcCodecV1, JvmGcDetailedCodecV1}
import com.vrv.pinpoint.example.common.server.bo.codec.stat.v2._
import com.vrv.pinpoint.example.common.server.bo.serializer.stat.AgentStatHbaseOperationFactory
import com.vrv.pinpoint.example.common.server.bo.stat._
import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.stat.AgentStatMapperV2
import com.vrv.pinpoint.example.web.mapper.{RangeTimestampFilter, TimestampFilter}
import com.vrv.pinpoint.example.web.vo.Range
import org.junit.Test

class AgentStatV2 {

  val AGENT_STAT_VER2_STR: String = "AgentStatV2"

  /**
    * ActiveTraceBo
    */
  @Test
  def activeTraceBoMapRow(): Unit = {
    val timeRanger = getCurrentTimeRange
    val hbaseOperationFactory: AgentStatHbaseOperationFactory = new AgentStatHbaseOperationFactory
    // Codecs
    val codecs: List[AgentStatCodec[ActiveTraceBo]] = new ArrayList[AgentStatCodec[ActiveTraceBo]]()
    codecs.add(new ActiveTraceCodecV1(new AgentStatDataPointCodec))
    codecs.add(new ActiveTraceCodecV2(new AgentStatDataPointCodec))
    // Decoder
    val decoder: AgentStatDecoder[ActiveTraceBo] = new ActiveTraceDecoder(codecs)
    // Filter
    val filter: TimestampFilter = new RangeTimestampFilter(new Range(timeRanger._1, timeRanger._2))
    // Mapper
    val mapper = new AgentStatMapperV2[ActiveTraceBo](hbaseOperationFactory, decoder, filter)
    SchemaUtils.findTable(AGENT_STAT_VER2_STR, mapper)
  }

  /**
    * Retrieves the current time
    *
    * @return
    */
  def getCurrentTimeRange(): Tuple2[Long, Long] = {
    val calender: Calendar = Calendar.getInstance()
    val endTime = calender.getTime.getTime
    calender.add(Calendar.DAY_OF_YEAR, -2)
    val startTime = calender.getTime.getTime
    println(s"startTime=$startTime-->endTime=$endTime")
    (startTime, endTime)
  }

  /**
    * CpuLoadBo
    */
  @Test
  def cpuLoadBoMapRow(): Unit = {
    val timeRanger = getCurrentTimeRange
    val hbaseOperationFactory: AgentStatHbaseOperationFactory = new AgentStatHbaseOperationFactory
    // Codecs
    val codecs: List[AgentStatCodec[CpuLoadBo]] = new ArrayList[AgentStatCodec[CpuLoadBo]]()
    codecs.add(new CpuLoadCodecV1(new AgentStatDataPointCodec))
    codecs.add(new CpuLoadCodecV2(new AgentStatDataPointCodec))
    // Decoder
    val decoder: AgentStatDecoder[CpuLoadBo] = new CpuLoadDecoder(codecs)
    // Filter
    val filter: TimestampFilter = new RangeTimestampFilter(new Range(timeRanger._1, timeRanger._2))
    // Mapper
    val mapper = new AgentStatMapperV2[CpuLoadBo](hbaseOperationFactory, decoder, filter)
    SchemaUtils.findTable(AGENT_STAT_VER2_STR, mapper)
  }

  /**
    * DataSourceListBo
    */
  @Test
  def dataSourceListBoMapRow(): Unit = {
    val timeRanger = getCurrentTimeRange
    val hbaseOperationFactory: AgentStatHbaseOperationFactory = new AgentStatHbaseOperationFactory
    // Codecs
    val codecs: List[AgentStatCodec[DataSourceListBo]] = new ArrayList[AgentStatCodec[DataSourceListBo]]()
    codecs.add(new DataSourceCodecV2(new AgentStatDataPointCodec))
    // Decoder
    val decoder: AgentStatDecoder[DataSourceListBo] = new DataSourceDecoder(codecs)
    // Filter
    val filter: TimestampFilter = new RangeTimestampFilter(new Range(timeRanger._1, timeRanger._2))
    // Mapper
    val mapper = new AgentStatMapperV2[DataSourceListBo](hbaseOperationFactory, decoder, filter)
    SchemaUtils.findTable(AGENT_STAT_VER2_STR, mapper)
  }

  /**
    * DeadlockBo
    */
  @Test
  def DeadlockBoMapRow(): Unit = {
    val timeRanger = getCurrentTimeRange
    val hbaseOperationFactory: AgentStatHbaseOperationFactory = new AgentStatHbaseOperationFactory
    // Codecs
    val codecs: List[AgentStatCodec[DeadlockBo]] = new ArrayList[AgentStatCodec[DeadlockBo]]()
    codecs.add(new DeadlockCodecV2(new AgentStatDataPointCodec))
    // Decoder
    val decoder: AgentStatDecoder[DeadlockBo] = new DeadlockDecoder(codecs)
    // Filter
    val filter: TimestampFilter = new RangeTimestampFilter(new Range(timeRanger._1, timeRanger._2))
    // Mapper
    val mapper = new AgentStatMapperV2[DeadlockBo](hbaseOperationFactory, decoder, filter)
    SchemaUtils.findTable(AGENT_STAT_VER2_STR, mapper)
  }

  /**
    * JvmGcBo
    */
  @Test
  def jvmGcBoMapRow(): Unit = {
    val timeRanger = getCurrentTimeRange
    val hbaseOperationFactory: AgentStatHbaseOperationFactory = new AgentStatHbaseOperationFactory
    // Codecs
    val codecs: List[AgentStatCodec[JvmGcBo]] = new ArrayList[AgentStatCodec[JvmGcBo]]()
    codecs.add(new JvmGcCodecV1(new AgentStatDataPointCodec))
    codecs.add(new JvmGcCodecV2(new AgentStatDataPointCodec))
    // Decoder
    val decoder: AgentStatDecoder[JvmGcBo] = new JvmGcDecoder(codecs)
    // Filter
    val filter: TimestampFilter = new RangeTimestampFilter(new Range(timeRanger._1, timeRanger._2))
    // Mapper
    val mapper = new AgentStatMapperV2[JvmGcBo](hbaseOperationFactory, decoder, filter)
    SchemaUtils.findTable(AGENT_STAT_VER2_STR, mapper)
  }

  /**
    * JvmGcDetailedBo
    */
  @Test
  def jvmGcDetailedBoMapRow(): Unit = {
    val timeRanger = getCurrentTimeRange
    val hbaseOperationFactory: AgentStatHbaseOperationFactory = new AgentStatHbaseOperationFactory
    // Codecs
    val codecs: List[AgentStatCodec[JvmGcDetailedBo]] = new ArrayList[AgentStatCodec[JvmGcDetailedBo]]()
    codecs.add(new JvmGcDetailedCodecV1(new AgentStatDataPointCodec))
    codecs.add(new JvmGcDetailedCodecV2(new AgentStatDataPointCodec))
    // Decoder
    val decoder: AgentStatDecoder[JvmGcDetailedBo] = new JvmGcDetailedDecoder(codecs)
    // Filter
    val filter: TimestampFilter = new RangeTimestampFilter(new Range(timeRanger._1, timeRanger._2))
    // Mapper
    val mapper = new AgentStatMapperV2[JvmGcDetailedBo](hbaseOperationFactory, decoder, filter)
    SchemaUtils.findTable(AGENT_STAT_VER2_STR, mapper)
  }


}
