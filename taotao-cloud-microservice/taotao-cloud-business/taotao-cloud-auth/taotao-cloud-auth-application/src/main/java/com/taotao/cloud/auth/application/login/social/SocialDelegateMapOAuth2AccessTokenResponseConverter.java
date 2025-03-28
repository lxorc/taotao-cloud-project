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

import java.util.Map;
import org.springframework.core.convert.converter.Converter;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.DefaultMapOAuth2AccessTokenResponseConverter;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2ParameterNames;

/**
 * 兼容微信token接口返回转换的工具，扩展了{@link DefaultMapOAuth2AccessTokenResponseConverter}
 *
 * @author shuigedeng
 * @version 2023.07
 * @see Converter
 * @since 2023-07-10 17:40:49
 */
public class SocialDelegateMapOAuth2AccessTokenResponseConverter
        implements Converter<Map<String, Object>, OAuth2AccessTokenResponse> {

    /**
     * 委托
     */
    private final Converter<Map<String, Object>, OAuth2AccessTokenResponse> delegate =
            new DefaultMapOAuth2AccessTokenResponseConverter();

    /**
     * 转换
     *
     * @param tokenResponseParameters 令牌响应参数
     * @return {@link OAuth2AccessTokenResponse }
     * @since 2023-07-10 17:40:50
     */
    @Override
    public OAuth2AccessTokenResponse convert(Map<String, Object> tokenResponseParameters) {
        // 避免 token_type 空校验异常
        tokenResponseParameters.put(OAuth2ParameterNames.TOKEN_TYPE, OAuth2AccessToken.TokenType.BEARER.getValue());

        return this.delegate.convert(tokenResponseParameters);
    }
}
