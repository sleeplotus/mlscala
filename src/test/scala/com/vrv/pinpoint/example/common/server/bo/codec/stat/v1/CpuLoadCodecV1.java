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

import com.vrv.pinpoint.example.common.server.bo.CpuLoadBo;
import com.vrv.pinpoint.example.common.server.bo.codec.stat.AgentStatDataPointCodec;
import com.vrv.pinpoint.example.common.server.bo.codec.stat.CodecFactory;
import com.vrv.pinpoint.example.common.server.bo.codec.stat.v2.CpuLoadCodecV2;

/**
 * @author HyunGil Jeong
 */
public class CpuLoadCodecV1 extends AgentStatCodecV1<CpuLoadBo> {

    public CpuLoadCodecV1(AgentStatDataPointCodec codec) {
        super(new CpuLoadCodecFactory(codec));
    }


    private static class CpuLoadCodecFactory implements CodecFactory<CpuLoadBo> {

        private final AgentStatDataPointCodec codec;

        private CpuLoadCodecFactory(AgentStatDataPointCodec codec) {
            this.codec = codec;
        }

        @Override
        public AgentStatDataPointCodec getCodec() {
            return codec;
        }

        @Override
        public CodecEncoder<CpuLoadBo> createCodecEncoder() {
            return new CpuLoadCodecV2.CpuLoadCodecEncoder(codec);
        }

        @Override
        public CodecDecoder<CpuLoadBo> createCodecDecoder() {
            return new CpuLoadCodecV2.CpuLoadCodecDecoder(codec);
        }
    }

}
