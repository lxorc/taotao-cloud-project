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

package com.taotao.cloud.message.biz.austin.handler.handler;

import com.taotao.cloud.message.biz.austin.common.domain.TaskInfo;
import com.taotao.cloud.message.biz.austin.support.domain.MessageTemplate;

/**
 * @author 3y 消息处理器
 */
public interface Handler {

    /**
     * 处理器
     *
     * @param taskInfo
     */
    void doHandler(TaskInfo taskInfo);

    /**
     * 撤回消息
     *
     * @param messageTemplate
     * @return
     */
    void recall(MessageTemplate messageTemplate);
}
