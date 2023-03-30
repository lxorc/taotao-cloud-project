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

package com.taotao.cloud.im.biz.platform.modules.topic.service;

import com.platform.common.web.service.BaseService;
import com.platform.modules.topic.domain.ChatTopicReply;
import com.platform.modules.topic.vo.TopicVo06;
import java.util.List;

/** 帖子回复表 服务层 q3z3 */
public interface ChatTopicReplyService extends BaseService<ChatTopicReply> {

    /** 根据帖子id删除 */
    void delByTopic(Long topicId);

    /** 根据帖子查询 */
    List<TopicVo06> queryReplyList(Long topicId);
}
