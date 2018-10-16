/*
 * Copyright 2014 NAVER Corp.
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

package com.vrv.pinpoint.example.web.vo;


import com.vrv.pinpoint.example.common.trace.ServiceType;
import com.vrv.pinpoint.example.web.applicationmap.histogram.Histogram;
import com.vrv.pinpoint.example.web.applicationmap.histogram.TimeHistogram;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

/**
 * @author emeroad
 */
public class ResponseTime {
    // rowKey
    private final String applicationName;
    private final ServiceType applicationServiceType;
    private final long timeStamp;

    // agentId is the key
    private final Map<String, TimeHistogram> responseHistogramMap = new HashMap<>();


    public ResponseTime(String applicationName, ServiceType applicationServiceType, long timeStamp) {
        if (applicationName == null) {
            throw new NullPointerException("applicationName must not be null");
        }
        if (applicationServiceType == null) {
            throw new NullPointerException("applicationServiceType must not be null");
        }
        this.applicationName = applicationName;
        this.applicationServiceType = applicationServiceType;
        this.timeStamp = timeStamp;
    }


    public String getApplicationName() {
        return applicationName;
    }

    public short getApplicationServiceType() {
        return applicationServiceType.getCode();
    }

    public long getTimeStamp() {
        return timeStamp;
    }

    public Histogram findHistogram(String agentId) {
        if (agentId == null) {
            throw new NullPointerException("agentId must not be null");
        }
        return responseHistogramMap.get(agentId);
    }

    private Histogram getHistogram(String agentId) {
        if (agentId == null) {
            throw new NullPointerException("agentId must not be null");
        }
        TimeHistogram histogram = responseHistogramMap.computeIfAbsent(agentId, k -> new TimeHistogram(applicationServiceType, timeStamp));
        return histogram;
    }

    public void addResponseTime(String agentId, short slotNumber, long count) {
        Histogram histogram = getHistogram(agentId);
        histogram.addCallCount(slotNumber, count);
    }


    public void addResponseTime(String agentId, Histogram copyHistogram) {
        if (copyHistogram == null) {
            throw new NullPointerException("copyHistogram must not be null");
        }
        Histogram histogram = getHistogram(agentId);
        histogram.add(copyHistogram);
    }

    public void addResponseTime(String agentId, int elapsedTime, boolean error) {
        Histogram histogram = getHistogram(agentId);
        histogram.addCallCountByElapsedTime(elapsedTime, error);
    }

    public Collection<TimeHistogram> getAgentResponseHistogramList() {
        return responseHistogramMap.values();
    }

    public Histogram getApplicationResponseHistogram() {
        Histogram result = new Histogram(applicationServiceType);
        for (Histogram histogram : responseHistogramMap.values()) {
            result.add(histogram);
        }
        return result;
    }

    public Set<Map.Entry<String, TimeHistogram>> getAgentHistogram() {
        return this.responseHistogramMap.entrySet();
    }

    @Override
    public String toString() {
        final StringBuilder sb = new StringBuilder("{");
        sb.append("applicationName:'").append(applicationName).append('\'');
        sb.append(", applicationServiceType:'").append(applicationServiceType).append('\'');
        sb.append(", timeStamp:").append(timeStamp);
        sb.append(",responseHistogramMap:[");
        for (String key:responseHistogramMap.keySet()){
            sb.append("{agentId:'"+key+"'").append(",timeHistogram:"+responseHistogramMap.get(key)+"},");
        }
        sb.append(']');
        sb.append('}');
        return sb.toString();
    }
}
