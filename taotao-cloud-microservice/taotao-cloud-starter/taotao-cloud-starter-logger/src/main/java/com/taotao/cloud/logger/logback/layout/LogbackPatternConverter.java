/*
 * Copyright 2002-2021 the original author or authors.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      https://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.taotao.cloud.logger.logback.layout;

import ch.qos.logback.classic.pattern.ClassicConverter;
import ch.qos.logback.classic.spi.ILoggingEvent;

/**
 * LogbackPatternConverter
 *
 * @author shuigedeng
 * @version 2022.03
 * @since 2021/09/16 08:58
 */
public class LogbackPatternConverter extends ClassicConverter {

	public LogbackPatternConverter() {
	}

	public String convert(ILoggingEvent iLoggingEvent) {
		return "tid:";
	}
}

