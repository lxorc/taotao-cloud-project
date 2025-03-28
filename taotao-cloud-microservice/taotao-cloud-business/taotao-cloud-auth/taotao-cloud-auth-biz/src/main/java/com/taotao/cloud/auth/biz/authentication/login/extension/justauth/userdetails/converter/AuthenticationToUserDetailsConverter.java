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

package com.taotao.cloud.auth.biz.authentication.login.extension.justauth.userdetails.converter;

import org.springframework.lang.NonNull;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.server.resource.authentication.AbstractOAuth2TokenAuthenticationToken;

/**
 * {@link Authentication} to {@link UserDetails} converter.
 * @author YongWu zheng
 * @weixin z56133
 * @since 2021.2.25 14:58
 */
public interface AuthenticationToUserDetailsConverter {

    /**
     * Convert the source object of type {@code AbstractOAuth2TokenAuthenticationToken<OAuth2AccessToken>} to target type {@code UserDetails}.
     * @param source the source object to convert, which must be an instance of {@code AbstractOAuth2TokenAuthenticationToken<OAuth2AccessToken>} (never {@code null})
     * @return the converted object, which must be an instance of {@code UserDetails} (never {@code null})
     * @throws IllegalArgumentException if the source cannot be converted to the desired target type
     */
    @NonNull
    UserDetails convert(@NonNull AbstractOAuth2TokenAuthenticationToken<OAuth2AccessToken> source);
}
