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
package com.taotao.cloud.captcha.configuration;

import com.taotao.cloud.captcha.controller.CaptchaController;
import com.taotao.cloud.captcha.properties.CaptchaProperties;
import com.taotao.cloud.captcha.service.CaptchaCacheService;
import com.taotao.cloud.captcha.service.impl.CaptchaServiceFactory;
import com.taotao.cloud.common.constant.StarterNameConstant;
import com.taotao.cloud.common.utils.LogUtil;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * CaptchaStorageAutoConfiguration 
 *
 * @author shuigedeng
 * @version 2021.9
 * @since 2021-09-04 07:35:39
 */
@Configuration
@AutoConfigureAfter(CaptchaServiceAutoConfiguration.class)
@ConditionalOnProperty(prefix = CaptchaProperties.PREFIX, name = "enabled", havingValue = "true")
public class CaptchaStorageAutoConfiguration implements InitializingBean {

	@Override
	public void afterPropertiesSet() throws Exception {
		LogUtil.started(CaptchaStorageAutoConfiguration.class, StarterNameConstant.CAPTCHA_STARTER);
	}

	@Bean
	public CaptchaCacheService captchaCacheService(CaptchaProperties captchaProperties) {
		LogUtil.started(CaptchaCacheService.class, StarterNameConstant.CAPTCHA_STARTER);

		return CaptchaServiceFactory.getCache(captchaProperties.getCacheType().name());
	}
}
