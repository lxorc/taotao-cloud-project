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

package com.taotao.cloud.auth.application.login.social;

import com.nimbusds.oauth2.sdk.GrantType;
import com.taotao.cloud.auth.application.login.social.wechat.WechatParameterNames;
import java.net.URI;
import org.springframework.core.convert.converter.Converter;
import org.springframework.http.RequestEntity;
import org.springframework.security.oauth2.client.endpoint.OAuth2RefreshTokenGrantRequest;
import org.springframework.security.oauth2.client.endpoint.OAuth2RefreshTokenGrantRequestEntityConverter;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.util.UriComponentsBuilder;

/**
 * 兼容微信登录 {@code
 * https://api.weixin.qq.com/sns/oauth2/refresh_token?appid=APPID&grant_type=refresh_token&refresh_token=REFRESH_TOKEN}
 *
 * @author shuigedeng
 * @version 2023.07
 * @see Converter
 * @since 2023-07-10 17:40:58
 */
public class SocialDelegateOAuth2RefreshTokenRequestEntityConverter
        implements Converter<OAuth2RefreshTokenGrantRequest, RequestEntity<?>> {

    /**
     * 刷新令牌端点
     */
    private static final String REFRESH_TOKEN_ENDPOINT = "https://api.weixin.qq.com/sns/oauth2/refresh_token";
    /**
     * 请求实体转换器
     */
    private final Converter<OAuth2RefreshTokenGrantRequest, RequestEntity<?>> requestEntityConverter =
            new OAuth2RefreshTokenGrantRequestEntityConverter();

    /**
     * 转换
     *
     * @param source 来源
     * @return {@link RequestEntity }<{@link ? }>
     * @since 2023-07-10 17:40:58
     */
    @Override
    public RequestEntity<?> convert(OAuth2RefreshTokenGrantRequest source) {
        ClientRegistration clientRegistration = source.getClientRegistration();
        if (SocialClientProviders.WECHAT_WEB_LOGIN_CLIENT.registrationId().equals(clientRegistration.getClientId())) {
            MultiValueMap<String, String> queryParameters = new LinkedMultiValueMap<>();
            queryParameters.add(WechatParameterNames.APP_ID, clientRegistration.getClientId());
            queryParameters.add(OAuth2ParameterNames.GRANT_TYPE, GrantType.REFRESH_TOKEN.getValue());
            queryParameters.add(
                    OAuth2ParameterNames.REFRESH_TOKEN, source.getRefreshToken().getTokenValue());

            URI uri = UriComponentsBuilder.fromUriString(REFRESH_TOKEN_ENDPOINT)
                    .queryParams(queryParameters)
                    .build()
                    .toUri();
            return RequestEntity.get(uri).build();
        }
        return requestEntityConverter.convert(source);
    }
}
