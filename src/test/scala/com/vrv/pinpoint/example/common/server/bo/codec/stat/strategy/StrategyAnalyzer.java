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

package com.vrv.pinpoint.example.common.server.bo.codec.stat.strategy;


import com.vrv.pinpoint.example.common.server.bo.codec.stat.strategy.impl.EncodingStrategy;

import java.util.List;

/**
 * @author HyunGil Jeong
 */
public interface StrategyAnalyzer<T> {

    EncodingStrategy<T> getBestStrategy();

    List<T> getValues();

    interface StrategyAnalyzerBuilder<T> {

        StrategyAnalyzerBuilder<T> addValue(T value);

        StrategyAnalyzer<T> build();
    }
}
