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

package com.taotao.cloud.auth.biz.demo.server.repository;

import cn.herodotus.engine.data.core.repository.BaseRepository;
import cn.herodotus.engine.oauth2.server.authentication.entity.OAuth2Application;

/**
 * Description: OAuth2ApplicationRepository
 *
 * @author : gengwei.zheng
 * @date : 2022/3/1 18:05
 */
public interface OAuth2ApplicationRepository extends BaseRepository<OAuth2Application, String> {

    /**
     * 根据 Client ID 查询 OAuth2Application
     *
     * @param clientId OAuth2Application 中的 clientId
     * @return {@link OAuth2Application}
     */
    OAuth2Application findByClientId(String clientId);
}
