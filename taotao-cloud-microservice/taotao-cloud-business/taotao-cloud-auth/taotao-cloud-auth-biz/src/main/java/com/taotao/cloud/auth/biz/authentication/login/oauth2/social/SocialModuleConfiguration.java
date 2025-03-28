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

package com.taotao.cloud.auth.biz.authentication.login.oauth2.social;

import jakarta.annotation.PostConstruct;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * <p>UPMS 社交配置 </p>
 *
 *
 * @since : 2022/2/2 17:05
 */
@Configuration(proxyBeanMethods = false)
public class SocialModuleConfiguration {

    private static final Logger log = LoggerFactory.getLogger(SocialModuleConfiguration.class);

    @PostConstruct
    public void postConstruct() {
        log.info("SDK [Module Upms Social] Auto Configure.");
    }

    @Bean
    @ConditionalOnMissingBean
    public DefaultSocialAuthenticationHandler defaultSocialAuthenticationHandler() {
        DefaultSocialAuthenticationHandler defaultSocialAuthenticationHandler =
                new DefaultSocialAuthenticationHandler();
        log.trace("Bean [Default Social Authentication Handler] Auto Configure.");
        return defaultSocialAuthenticationHandler;
    }
}
