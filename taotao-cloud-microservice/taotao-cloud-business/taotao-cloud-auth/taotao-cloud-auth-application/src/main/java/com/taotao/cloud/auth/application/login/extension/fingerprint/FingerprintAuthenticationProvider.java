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

package com.taotao.cloud.auth.application.login.extension.fingerprint;

import com.taotao.cloud.auth.application.login.extension.fingerprint.service.FingerprintUserDetailsService;
import com.taotao.cloud.common.utils.log.LogUtils;
import java.util.Collection;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.context.support.MessageSourceAccessor;
import org.springframework.security.authentication.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.SpringSecurityMessageSource;
import org.springframework.security.core.authority.mapping.GrantedAuthoritiesMapper;
import org.springframework.security.core.authority.mapping.NullAuthoritiesMapper;
import org.springframework.security.core.userdetails.UserCache;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsChecker;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.core.userdetails.cache.NullUserCache;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.util.Assert;

/**
 * 指纹 登录
 *
 * @author shuigedeng
 * @version 2023.04
 * @since 2023-06-29 15:34:33
 */
public class FingerprintAuthenticationProvider implements AuthenticationProvider, InitializingBean, MessageSourceAware {

    private volatile String userNotFoundEncodedPassword;
    private final UserCache userCache = new NullUserCache();
    private final UserDetailsChecker preAuthenticationChecks = new DefaultPreAuthenticationChecks();
    private UserDetailsChecker postAuthenticationChecks = new DefaultPostAuthenticationChecks();
    private PasswordEncoder passwordEncoder;
    private static final String USER_NOT_FOUND_PASSWORD = "userNotFoundPassword";

    private final GrantedAuthoritiesMapper authoritiesMapper = new NullAuthoritiesMapper();
    private final FingerprintUserDetailsService fingerprintUserDetailsService;
    private MessageSourceAccessor messages = SpringSecurityMessageSource.getAccessor();

    public FingerprintAuthenticationProvider(FingerprintUserDetailsService fingerprintUserDetailsService) {
        this.fingerprintUserDetailsService = fingerprintUserDetailsService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        Assert.isInstanceOf(
                FingerprintAuthenticationToken.class,
                authentication,
                () -> this.messages.getMessage(
                        "FingerprintAuthenticationToken.onlySupports",
                        "Only FingerprintAuthenticationToken is supported"));

        String username = determineUsername(authentication);
        boolean cacheWasUsed = true;
        UserDetails user = this.userCache.getUserFromCache(username);
        if (user == null) {
            cacheWasUsed = false;
            try {
                user = retrieveUser(username, (FingerprintAuthenticationToken) authentication);
            } catch (UsernameNotFoundException ex) {
                LogUtils.error("Failed to find user '" + username + "'");
                throw new BadCredentialsException("用户不存在");
            }
            Assert.notNull(user, "retrieveUser returned null - a violation of the interface contract");
        }
        try {
            this.preAuthenticationChecks.check(user);
            additionalAuthenticationChecks(user, (FingerprintAuthenticationToken) authentication);
        } catch (AuthenticationException ex) {
            if (!cacheWasUsed) {
                throw ex;
            }
            // There was a problem, so try again after checking
            // we're using latest data (i.e. not from the cache)
            cacheWasUsed = false;
            user = retrieveUser(username, (FingerprintAuthenticationToken) authentication);
            this.preAuthenticationChecks.check(user);
            additionalAuthenticationChecks(user, (FingerprintAuthenticationToken) authentication);
        }
        this.postAuthenticationChecks.check(user);
        if (!cacheWasUsed) {
            this.userCache.putUserInCache(user);
        }

        return createSuccessAuthentication(user.getUsername(), authentication, user);
    }

    /**
     * 认证成功将非授信凭据转为授信凭据. 封装用户信息 角色信息。
     *
     * @param authentication the authentication
     * @param userDetails    the user
     * @return the authentication
     */
    protected Authentication createSuccessAuthentication(
            Object principal, Authentication authentication, UserDetails userDetails) {
        Collection<? extends GrantedAuthority> authorities =
                authoritiesMapper.mapAuthorities(userDetails.getAuthorities());

        String fingerPrint = "";
        if (authentication instanceof FingerprintAuthenticationToken fingerprintAuthenticationToken) {
            fingerPrint = fingerprintAuthenticationToken.getFingerPrint();
        }

        FingerprintAuthenticationToken authenticationToken =
                new FingerprintAuthenticationToken(principal, fingerPrint, authorities);
        authenticationToken.setDetails(authentication.getDetails());

        return authenticationToken;
    }

    protected void additionalAuthenticationChecks(
            UserDetails userDetails, FingerprintAuthenticationToken authentication) throws AuthenticationException {
        if (authentication.getCredentials() == null) {
            LogUtils.error("Failed to authenticate since no credentials provided");
            throw new BadCredentialsException("用户密码错误");
        }

        //		if (!Objects.equals(authentication.getFingerPrint(), userDetails.getFingerPrint())){
        //			throw new BadCredentialsException("指纹不匹配");
        //		}

    }

    protected final UserDetails retrieveUser(String username, FingerprintAuthenticationToken authentication)
            throws AuthenticationException {
        prepareTimingAttackProtection();
        try {
            UserDetails loadedUser =
                    fingerprintUserDetailsService.loadUserByFingerprint((String) authentication.getPrincipal());
            if (loadedUser == null) {
                throw new InternalAuthenticationServiceException("用户不存在");
            }
            return loadedUser;
        } catch (UsernameNotFoundException ex) {
            mitigateAgainstTimingAttack(authentication);
            throw ex;
        } catch (InternalAuthenticationServiceException ex) {
            throw ex;
        } catch (Exception ex) {
            throw new InternalAuthenticationServiceException(ex.getMessage(), ex);
        }
    }

    private void prepareTimingAttackProtection() {
        if (this.userNotFoundEncodedPassword == null) {
            this.userNotFoundEncodedPassword = this.passwordEncoder.encode(USER_NOT_FOUND_PASSWORD);
        }
    }

    private void mitigateAgainstTimingAttack(FingerprintAuthenticationToken authentication) {
        if (authentication.getCredentials() != null) {
            String presentedPassword = authentication.getCredentials().toString();
            this.passwordEncoder.matches(presentedPassword, this.userNotFoundEncodedPassword);
        }
    }

    private String determineUsername(Authentication authentication) {
        return (authentication.getPrincipal() == null) ? "NONE_PROVIDED" : authentication.getName();
    }

    @Override
    public boolean supports(Class<?> authentication) {
        return FingerprintAuthenticationToken.class.isAssignableFrom(authentication);
    }

    @Override
    public void afterPropertiesSet() throws Exception {
        Assert.notNull(fingerprintUserDetailsService, "fingerprintUserDetailsService must not be null");
    }

    @Override
    public void setMessageSource(MessageSource messageSource) {
        this.messages = new MessageSourceAccessor(messageSource);
    }

    /**
     * 认证成功将非授信凭据转为授信凭据. 封装用户信息 角色信息。
     *
     * @param authentication the authentication
     * @param user           the user
     * @return the authentication
     */
    protected Authentication createSuccessAuthentication(Authentication authentication, UserDetails user) {

        Collection<? extends GrantedAuthority> authorities = authoritiesMapper.mapAuthorities(user.getAuthorities());
        FingerprintAuthenticationToken authenticationToken =
                new FingerprintAuthenticationToken(user, null, authorities);
        authenticationToken.setDetails(authentication.getDetails());

        return authenticationToken;
    }

    private class DefaultPreAuthenticationChecks implements UserDetailsChecker {

        @Override
        public void check(UserDetails user) {
            // 用户是否被锁定
            if (!user.isAccountNonLocked()) {
                LogUtils.error("Failed to authenticate since user account is locked");
                throw new LockedException("用户已被锁定");
            }
            // 用户启用
            if (!user.isEnabled()) {
                LogUtils.error("Failed to authenticate since user account is disabled");
                throw new DisabledException("用户未启用");
            }
            // 账号是否过期
            if (!user.isAccountNonExpired()) {
                LogUtils.error("Failed to authenticate since user account has expired");
                throw new AccountExpiredException("用户账号已过期");
            }
        }
    }

    private class DefaultPostAuthenticationChecks implements UserDetailsChecker {

        @Override
        public void check(UserDetails user) {
            // 用户密码是否过期
            if (!user.isCredentialsNonExpired()) {
                LogUtils.error("Failed to authenticate since user account credentials have expired");
                throw new CredentialsExpiredException("用户账号已过期");
            }
        }
    }
}
