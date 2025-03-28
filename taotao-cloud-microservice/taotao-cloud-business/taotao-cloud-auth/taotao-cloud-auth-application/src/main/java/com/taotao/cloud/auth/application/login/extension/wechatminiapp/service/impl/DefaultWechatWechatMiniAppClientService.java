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

package com.taotao.cloud.auth.application.login.extension.wechatminiapp.service.impl;

import com.taotao.cloud.auth.application.login.extension.wechatminiapp.client.WechatMiniAppClient;
import com.taotao.cloud.auth.application.login.extension.wechatminiapp.service.WechatMiniAppClientService;
import org.springframework.stereotype.Service;

/** // 根据请求携带的clientid 查询小程序的appid和secret 1 在此处配置 优先级最高 2 注册为Spring Bean 可以免配置 */
@Service
public class DefaultWechatWechatMiniAppClientService implements WechatMiniAppClientService {

    @Override
    public WechatMiniAppClient get(String clientId) {
        WechatMiniAppClient wechatMiniAppClient = new WechatMiniAppClient();
        wechatMiniAppClient.setClientId(clientId);
        wechatMiniAppClient.setAppId("wxcd395c35c45eb823");
        wechatMiniAppClient.setSecret("75f9a12c82bd24ecac0d37bf1156c749");
        return wechatMiniAppClient;
    }
}
