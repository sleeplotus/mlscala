/*
 * Copyright 2016 Naver Corp.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.vrv.pinpoint.example.common.server.bo.codec.stat.v1;


import com.vrv.pinpoint.example.common.server.bo.codec.AgentStatCodec;
import com.vrv.pinpoint.example.common.server.bo.codec.stat.AgentStatDataPointCodec;
import com.vrv.pinpoint.example.common.server.bo.codec.stat.CodecFactory;
import com.vrv.pinpoint.example.common.server.bo.codec.stat.v2.ActiveTraceCodecV2;
import com.vrv.pinpoint.example.common.server.bo.stat.ActiveTraceBo;
import org.elasticsearch.hadoop.util.Assert;

/**
 * @author HyunGil Jeong
 */
public class ActiveTraceCodecV1 extends AgentStatCodecV1<ActiveTraceBo> {

    public ActiveTraceCodecV1(AgentStatDataPointCodec codec) {
        super(new ActiveTraceCodecFactory(codec));
    }

    private static class ActiveTraceCodecFactory implements CodecFactory<ActiveTraceBo> {

        private final AgentStatDataPointCodec codec;

        private ActiveTraceCodecFactory(AgentStatDataPointCodec codec) {
            Assert.notNull(codec, "codec must not be null");
            this.codec = codec;
        }

        @Override
        public AgentStatDataPointCodec getCodec() {
            return codec;
        }

        @Override
        public AgentStatCodec.CodecEncoder<ActiveTraceBo> createCodecEncoder() {
            return new ActiveTraceCodecV2.ActiveTraceCodecEncoder(codec);
        }

        @Override
        public AgentStatCodec.CodecDecoder<ActiveTraceBo> createCodecDecoder() {
            return new ActiveTraceCodecV2.ActiveTraceCodecDecoder(codec);
        }
    }

}
