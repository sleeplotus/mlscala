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

package com.vrv.pinpoint.example.common.service;

import com.vrv.pinpoint.example.common.trace.AnnotationKey;
import com.vrv.pinpoint.example.common.trace.ServiceTypeInfo;
import com.vrv.pinpoint.example.common.trace.TraceMetadataLoader;
import com.vrv.pinpoint.example.common.trace.TraceMetadataProvider;
import com.vrv.pinpoint.example.common.util.ClassLoaderUtils;
import com.vrv.pinpoint.example.common.util.logger.CommonLoggerFactory;
import com.vrv.pinpoint.example.common.util.logger.StdoutCommonLoggerFactory;

import java.net.URL;
import java.util.List;

/**
 * @author emeroad
 */
public class DefaultTraceMetadataLoaderService implements TraceMetadataLoaderService {

    private final TraceMetadataLoader loader;

    public DefaultTraceMetadataLoaderService() {
        this(ClassLoaderUtils.getDefaultClassLoader(), StdoutCommonLoggerFactory.INSTANCE);
    }

    public DefaultTraceMetadataLoaderService(CommonLoggerFactory commonLoggerFactory) {
        this(ClassLoaderUtils.getDefaultClassLoader(), commonLoggerFactory);
    }

    public DefaultTraceMetadataLoaderService(URL[] jarLists, CommonLoggerFactory commonLoggerFactory) {
        if (jarLists == null) {
            throw new NullPointerException("jarLists must not be null");
        }
        this.loader = new TraceMetadataLoader(commonLoggerFactory);
        loader.load(jarLists);

    }

    public DefaultTraceMetadataLoaderService(List<TraceMetadataProvider> providers, CommonLoggerFactory commonLoggerFactory) {
        if (providers == null) {
            throw new NullPointerException("providers must not be null");
        }
        this.loader = new TraceMetadataLoader();
        loader.load(providers);

    }


    public DefaultTraceMetadataLoaderService(ClassLoader classLoader, CommonLoggerFactory commonLoggerFactory) {
        if (classLoader == null) {
            throw new NullPointerException("classLoader must not be null");
        }
        this.loader = new TraceMetadataLoader(commonLoggerFactory);
        loader.load(classLoader);
    }

    @Override
    public List<ServiceTypeInfo> getServiceTypeInfos() {
        return loader.getServiceTypeInfos();
    }

    @Override
    public List<AnnotationKey> getAnnotationKeys() {
        return loader.getAnnotationKeys();
    }


}
