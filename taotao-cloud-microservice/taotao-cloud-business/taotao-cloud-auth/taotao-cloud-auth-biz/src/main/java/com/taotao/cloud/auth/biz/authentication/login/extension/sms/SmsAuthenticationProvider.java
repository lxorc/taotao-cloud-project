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

package com.taotao.cloud.auth.biz.authentication.login.extension.sms;

import com.taotao.cloud.auth.biz.authentication.login.extension.sms.service.SmsCheckCodeService;
import com.taotao.cloud.auth.biz.authentication.login.extension.sms.service.SmsUserDetailsService;
import java.util.Collection;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.util.Assert;

/** 手机号码+短信 登录 */
public class SmsAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final SmsUserDetailsService smsUserDetailsService;
    private final SmsCheckCodeService smsCheckCodeService;
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public SmsAuthenticationProvider(
            SmsUserDetailsService smsUserDetailsService, SmsCheckCodeService smsCheckCodeService) {
        this.smsUserDetailsService = smsUserDetailsService;
        this.smsCheckCodeService = smsCheckCodeService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(
                SmsAuthenticationToken.class,
                authentication,
                () -> messages.getMessage(
                        "CaptchaAuthenticationProvider.onlySupports", "Only CaptchaAuthenticationToken is supported"));

        SmsAuthenticationToken smsAuthenticationToken = (SmsAuthenticationToken) authentication;

        String phone = smsAuthenticationToken.getName();
        String rawCode = (String) smsAuthenticationToken.getCredentials();
        String type = smsAuthenticationToken.getType();

        // 验证码校验
        if (smsCheckCodeService.verifyCaptcha(phone, rawCode)) {
            UserDetails userDetails = smsUserDetailsService.loadUserByPhone(phone, type);
            // TODO 此处省略对UserDetails 的可用性 是否过期  是否锁定 是否失效的检验  建议根据实际情况添加  或者在 UserDetailsService
            // 的实现中处理
            return createSuccessAuthentication(authentication, userDetails);
        } else {
            throw new BadCredentialsException("captcha is not matched");
        }
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return SmsAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(smsUserDetailsService, "phoneUserDetailsService must not be null");
        Assert.notNull(smsCheckCodeService, "phoneService must not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    /**
     * 认证成功将非授信凭据转为授信凭据. 封装用户信息 角色信息。
     *
     * @param authentication the authentication
     * @param user the user
     * @return the authentication
     */
    protected Authentication createSuccessAuthentication(Authentication authentication, UserDetails user) {

        Collection<? extends GrantedAuthority> authorities = authoritiesMapper.mapAuthorities(user.getAuthorities());

        String type = "";
        String captcha = "";
        if (authentication instanceof SmsAuthenticationToken accountAuthenticationToken) {
            type = accountAuthenticationToken.getType();
            captcha = (String) accountAuthenticationToken.getCredentials();
        }

        SmsAuthenticationToken authenticationToken = new SmsAuthenticationToken(user, captcha, type, authorities);
        authenticationToken.setDetails(authentication.getDetails());

        return authenticationToken;
    }
}
