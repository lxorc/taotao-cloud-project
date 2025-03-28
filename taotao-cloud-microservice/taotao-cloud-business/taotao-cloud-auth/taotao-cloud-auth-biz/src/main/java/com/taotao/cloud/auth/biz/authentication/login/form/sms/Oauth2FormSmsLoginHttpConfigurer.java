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

package com.taotao.cloud.auth.biz.authentication.login.form.sms;

import com.taotao.cloud.auth.biz.authentication.login.form.OAuth2FormLoginAuthenticationFailureHandler;
import com.taotao.cloud.auth.biz.authentication.login.form.OAuth2FormLoginWebAuthenticationDetailSource;
import com.taotao.cloud.auth.biz.authentication.login.form.sms.service.impl.DefaultOauth2FormSmsService;
import com.taotao.cloud.auth.biz.authentication.login.form.sms.service.impl.DefaultOauth2FormSmsUserDetailsService;
import com.taotao.cloud.auth.biz.authentication.properties.OAuth2AuthenticationProperties;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.HttpSecurityBuilder;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.context.SecurityContextRepository;

/**
 * <p>OAuth2 Form Login Configurer </p>
 * <p>
 * 使用此种方式，相当于额外增加了一种表单登录方式。因此对原有的 http.formlogin进行的配置，对当前此种方式的配置并不生效。
 *
 * @author shuigedeng
 * @version 2023.04
 * @since 2023-06-29 16:38:04
 */
public class Oauth2FormSmsLoginHttpConfigurer<H extends HttpSecurityBuilder<H>>
        extends AbstractHttpConfigurer<Oauth2FormSmsLoginHttpConfigurer<H>, H> {

    private final OAuth2AuthenticationProperties authenticationProperties;

    public Oauth2FormSmsLoginHttpConfigurer(OAuth2AuthenticationProperties authenticationProperties) {
        this.authenticationProperties = authenticationProperties;
    }

    @Override
    public void configure(H httpSecurity) throws Exception {
        AuthenticationManager authenticationManager = httpSecurity.getSharedObject(AuthenticationManager.class);
        SecurityContextRepository securityContextRepository =
                httpSecurity.getSharedObject(SecurityContextRepository.class);

        Oauth2FormSmsLoginAuthenticationFilter filter =
                new Oauth2FormSmsLoginAuthenticationFilter(authenticationManager);
        filter.setAuthenticationDetailsSource(
                new OAuth2FormLoginWebAuthenticationDetailSource(authenticationProperties));

        filter.setAuthenticationFailureHandler(
                new OAuth2FormLoginAuthenticationFailureHandler(getFormLogin().getFailureForwardUrl()));
        filter.setSecurityContextRepository(securityContextRepository);

        Oauth2FormSmsLoginAuthenticationProvider provider = new Oauth2FormSmsLoginAuthenticationProvider(
                new DefaultOauth2FormSmsUserDetailsService(), new DefaultOauth2FormSmsService());

        httpSecurity
                .authenticationProvider(provider)
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class);
    }

    private OAuth2AuthenticationProperties.FormLogin getFormLogin() {
        return authenticationProperties.getFormLogin();
    }

	public H httpSecurity() {
		return getBuilder();
	}
}
