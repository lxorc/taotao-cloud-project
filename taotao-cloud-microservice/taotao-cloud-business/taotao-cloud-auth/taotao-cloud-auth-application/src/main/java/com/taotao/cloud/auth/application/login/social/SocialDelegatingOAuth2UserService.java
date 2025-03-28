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

import java.util.*;
import org.springframework.security.oauth2.client.userinfo.DefaultOAuth2UserService;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserRequest;
import org.springframework.security.oauth2.client.userinfo.OAuth2UserService;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.core.user.OAuth2User;
import org.springframework.util.Assert;
import org.springframework.util.CollectionUtils;

/**
 * 社交委派oauth2用户服务
 *
 * @author shuigedeng
 * @version 2023.07
 * @see OAuth2UserService
 * @since 2023-07-10 17:41:04
 */
public class SocialDelegatingOAuth2UserService<R extends OAuth2UserRequest, U extends OAuth2User>
        implements OAuth2UserService<R, U> {
    /**
     * 默认oauth2用户服务
     */
    private final OAuth2UserService<OAuth2UserRequest, OAuth2User> defaultOAuth2UserService =
            new DefaultOAuth2UserService();
    /**
     * 用户服务
     */
    private final List<OAuth2UserService<R, U>> userServices;
    /**
     * 用户服务地图
     */
    private final Map<String, OAuth2UserService<R, U>> userServiceMap;

    /**
     * Constructs a {@code DelegatingOAuth2UserService} using the provided parameters.
     *
     * @param userServices a
     * @return
     * @since 2023-07-10 17:41:04
     */
    public SocialDelegatingOAuth2UserService(List<OAuth2UserService<R, U>> userServices) {
        Assert.notEmpty(userServices, "userServices cannot be empty");
        this.userServices = Collections.unmodifiableList(new ArrayList<>(userServices));
        this.userServiceMap = Collections.emptyMap();
    }

    /**
     * Constructs a {@code DelegatingOAuth2UserService} using the provided parameters.
     *
     * @param userServiceMap a
     * @return
     * @since 2023-07-10 17:41:04
     */
    public SocialDelegatingOAuth2UserService(Map<String, OAuth2UserService<R, U>> userServiceMap) {
        Assert.notEmpty(userServiceMap, "userServiceMap cannot be empty");
        this.userServiceMap = Collections.unmodifiableMap(userServiceMap);
        this.userServices = Collections.emptyList();
    }

    /**
     * 加载用户
     *
     * @param userRequest 用户请求
     * @return {@link U }
     * @since 2023-07-10 17:41:04
     */
    @SuppressWarnings("unchecked")
    @Override
    public U loadUser(R userRequest) throws OAuth2AuthenticationException {
        Assert.notNull(userRequest, "userRequest cannot be null");

        if (CollectionUtils.isEmpty(userServiceMap)) {
            return this.userServices.stream()
                    .map((userService) -> userService.loadUser(userRequest))
                    .filter(Objects::nonNull)
                    .findFirst()
                    .orElse(null);
        } else {
            String registrationId = userRequest.getClientRegistration().getRegistrationId();
            OAuth2UserService<R, U> oAuth2UserService = userServiceMap.get(registrationId);

            if (oAuth2UserService == null) {
                oAuth2UserService = (OAuth2UserService<R, U>) defaultOAuth2UserService;
            }

            return oAuth2UserService.loadUser(userRequest);
        }
    }
}
