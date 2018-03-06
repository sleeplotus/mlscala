package com.vrv.ml.streaming.data.structure

import java.util.Date


case class NetSession(srcIp: String, srcPort: String, dstIp: String, dstPort: String, protocol: String,
                      flag: String, frameLength: String, dataLength: String, tcpSeq: String, tcpNxtSeq: String,
                      tcpAck: String, dateTime: Date)
