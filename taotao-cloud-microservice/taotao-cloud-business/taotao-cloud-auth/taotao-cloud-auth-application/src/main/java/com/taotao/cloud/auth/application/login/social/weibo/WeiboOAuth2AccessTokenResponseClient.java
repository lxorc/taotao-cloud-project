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

package com.taotao.cloud.auth.application.login.social.weibo;

import com.alibaba.fastjson2.JSONObject;
import java.util.HashMap;
import java.util.Map;
import org.springframework.security.oauth2.client.endpoint.OAuth2AccessTokenResponseClient;
import org.springframework.security.oauth2.client.endpoint.OAuth2AuthorizationCodeGrantRequest;
import org.springframework.security.oauth2.client.registration.ClientRegistration;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.security.oauth2.core.endpoint.OAuth2AccessTokenResponse;
import org.springframework.security.oauth2.core.endpoint.OAuth2AuthorizationExchange;
import org.springframework.web.client.RestOperations;

/**
 * https://open.weibo.com/wiki/Oauth2/authorize
 *
 * @author shuigedeng
 * @version 2023.04
 * @since 2023-06-16 09:49:58
 */
public class WeiboOAuth2AccessTokenResponseClient
        implements OAuth2AccessTokenResponseClient<OAuth2AuthorizationCodeGrantRequest> {

    private final RestOperations restOperations;

    public WeiboOAuth2AccessTokenResponseClient(RestOperations restOperations) {
        this.restOperations = restOperations;
    }

    @Override
    public OAuth2AccessTokenResponse getTokenResponse(
            OAuth2AuthorizationCodeGrantRequest oAuth2AuthorizationCodeGrantRequest) {
        ClientRegistration clientRegistration = oAuth2AuthorizationCodeGrantRequest.getClientRegistration();
        OAuth2AuthorizationExchange oAuth2AuthorizationExchange =
                oAuth2AuthorizationCodeGrantRequest.getAuthorizationExchange();

        Map<String, String> params = new HashMap<>();
        params.put("client_id", clientRegistration.getClientId());
        params.put("client_secret", clientRegistration.getClientSecret());
        params.put("grant_type", clientRegistration.getAuthorizationGrantType().getValue());
        params.put(
                "code", oAuth2AuthorizationExchange.getAuthorizationResponse().getCode());
        params.put(
                "redirect_uri",
                oAuth2AuthorizationExchange.getAuthorizationResponse().getRedirectUri());

        String baseUri = clientRegistration.getProviderDetails().getTokenUri();

        String accessTokenUri = baseUri + "?client_id={client_id}" + "&client_secret={client_secret}"
                + "&grant_type={grant_type}"
                + "&redirect_uri={redirect_uri}"
                + "&code={code}";

        String accessTokenResponse = restOperations.postForObject(accessTokenUri, null, String.class, params);

        JSONObject object = JSONObject.parseObject(accessTokenResponse);
        String accessToken = object.getString("access_token");
        String expiresIn = object.getString("expires_in");
        String uid = object.getString("uid");

        Map<String, Object> additionalParameters = new HashMap<>();
        additionalParameters.put("uid", uid);

        return OAuth2AccessTokenResponse.withToken(accessToken)
                .expiresIn(Long.parseLong(expiresIn))
                .tokenType(OAuth2AccessToken.TokenType.BEARER)
                .scopes(oAuth2AuthorizationExchange.getAuthorizationRequest().getScopes())
                .additionalParameters(additionalParameters)
                .build();
    }
}
