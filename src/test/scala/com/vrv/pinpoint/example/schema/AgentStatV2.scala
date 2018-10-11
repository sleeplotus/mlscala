package com.vrv.pinpoint.example.schema


import java.util.{ArrayList, Calendar, List}

import com.vrv.pinpoint.example.common.server.bo.codec.AgentStatCodec
import com.vrv.pinpoint.example.common.server.bo.codec.stat.v1.JvmGcCodecV1
import com.vrv.pinpoint.example.common.server.bo.codec.stat.v2.JvmGcCodecV2
import com.vrv.pinpoint.example.common.server.bo.codec.stat.{AgentStatDataPointCodec, AgentStatDecoder, JvmGcDecoder}
import com.vrv.pinpoint.example.common.server.bo.serializer.stat.AgentStatHbaseOperationFactory
import com.vrv.pinpoint.example.common.server.bo.stat.JvmGcBo
import com.vrv.pinpoint.example.schema.util.SchemaUtils
import com.vrv.pinpoint.example.web.mapper.stat.AgentStatMapperV2
import com.vrv.pinpoint.example.web.mapper.{RangeTimestampFilter, TimestampFilter}
import com.vrv.pinpoint.example.web.vo.Range
import org.junit.Test

class AgentStatV2 {
  /**
    * JvmGcBo
    */
  @Test
  def jvmGcBoMapRow(): Unit = {
    val timeRanger = getCurrentTimeRange
    val hbaseOperationFactory: AgentStatHbaseOperationFactory = new AgentStatHbaseOperationFactory
    val jvmGcCodecs: List[AgentStatCodec[JvmGcBo]] = new ArrayList[AgentStatCodec[JvmGcBo]]()
    jvmGcCodecs.add(new JvmGcCodecV1(new AgentStatDataPointCodec))
    jvmGcCodecs.add(new JvmGcCodecV2(new AgentStatDataPointCodec))
    val decoder: AgentStatDecoder[JvmGcBo] = new JvmGcDecoder(jvmGcCodecs)
    val filter: TimestampFilter = new RangeTimestampFilter(new Range(timeRanger._1, timeRanger._2))
    val mapper = new AgentStatMapperV2[JvmGcBo](hbaseOperationFactory, decoder, filter)
    SchemaUtils.findTable("AgentStatV2", mapper)
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
}
