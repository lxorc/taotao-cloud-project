/*
 * Copyright (c) 2020-2030, Shuigedeng (981376577@qq.com & https://blog.taotaocloud.top/).
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
package com.taotao.cloud.p6spy.logger;

import com.p6spy.engine.logging.Category;
import com.p6spy.engine.spy.appender.FormattedLogger;
import com.taotao.cloud.common.constant.CommonConstant;
import com.taotao.cloud.common.utils.common.PropertyUtil;
import com.taotao.cloud.common.utils.context.ContextUtil;
import java.util.Objects;
import org.springframework.kafka.core.KafkaTemplate;

/**
 * P6spy日志实现
 *
 * @author shuigedeng
 * @version 2022.03
 * @since 2020/10/14 09:38
 */
public class KafkaLogger extends FormattedLogger {

	private KafkaTemplate kafkaTemplate;
	private String applicationName;

	public KafkaLogger() {
		KafkaTemplate kafkaTemplate = ContextUtil.getBean(KafkaTemplate.class, true);
		String applicationName = PropertyUtil.getProperty(CommonConstant.SPRING_APP_NAME_KEY);

		this.kafkaTemplate = kafkaTemplate;
		this.applicationName = applicationName;
	}

	@Override
	public void logException(Exception e) {
		if (Objects.nonNull(kafkaTemplate)) {
			kafkaTemplate.send("sys-sql-" + applicationName, e.getMessage());
		}
	}

	@Override
	public void logText(String text) {
	}

	@Override
	public void logSQL(int connectionId, String now, long elapsed, Category category,
		String prepared, String sql, String url) {
		final String msg = strategy.formatMessage(connectionId, now, elapsed,
			category.toString(), prepared, sql, url);

		if (Objects.nonNull(kafkaTemplate)) {
			kafkaTemplate.send("sys-sql-" + applicationName, msg);
		}
	}

	@Override
	public boolean isCategoryEnabled(Category category) {
		return true;
	}
}
