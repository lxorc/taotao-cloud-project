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

package com.taotao.cloud.auth.biz.authentication.login.extension.fingerprint;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationServiceException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import org.springframework.util.Assert;

public class FingerprintAuthenticationFilter extends AbstractAuthenticationProcessingFilter {

    private static final AntPathRequestMatcher DEFAULT_ANT_PATH_REQUEST_MATCHER =
            new AntPathRequestMatcher("/login/fingerprint", "POST");
    private Converter<HttpServletRequest, FingerprintAuthenticationToken> fingerprintAuthenticationTokenConverter;
    private boolean postOnly = true;

    public FingerprintAuthenticationFilter() {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER);
        this.fingerprintAuthenticationTokenConverter = new FingerprintAuthenticationConverter();
    }

    public FingerprintAuthenticationFilter(AuthenticationManager authenticationManager) {
        super(DEFAULT_ANT_PATH_REQUEST_MATCHER, authenticationManager);
        this.fingerprintAuthenticationTokenConverter = new FingerprintAuthenticationConverter();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
            throws AuthenticationException {
        if (this.postOnly && !HttpMethod.POST.matches(request.getMethod())) {
            throw new AuthenticationServiceException("Authentication method not supported: " + request.getMethod());
        }

        FingerprintAuthenticationToken authRequest = fingerprintAuthenticationTokenConverter.convert(request);
        // Allow subclasses to set the "details" property
        setDetails(request, authRequest);
        return this.getAuthenticationManager().authenticate(authRequest);
    }

    protected void setDetails(HttpServletRequest request, FingerprintAuthenticationToken authRequest) {
        authRequest.setDetails(this.authenticationDetailsSource.buildDetails(request));
    }

    public void setConverter(Converter<HttpServletRequest, FingerprintAuthenticationToken> converter) {
        Assert.notNull(converter, "Converter must not be null");
        this.fingerprintAuthenticationTokenConverter = converter;
    }

    public void setPostOnly(boolean postOnly) {
        this.postOnly = postOnly;
    }

    public Converter<HttpServletRequest, FingerprintAuthenticationToken> getFingerprintAuthenticationTokenConverter() {
        return fingerprintAuthenticationTokenConverter;
    }

    public void setFingerprintAuthenticationTokenConverter(
            Converter<HttpServletRequest, FingerprintAuthenticationToken> fingerprintAuthenticationTokenConverter) {
        this.fingerprintAuthenticationTokenConverter = fingerprintAuthenticationTokenConverter;
    }

    public boolean isPostOnly() {
        return postOnly;
    }
}
