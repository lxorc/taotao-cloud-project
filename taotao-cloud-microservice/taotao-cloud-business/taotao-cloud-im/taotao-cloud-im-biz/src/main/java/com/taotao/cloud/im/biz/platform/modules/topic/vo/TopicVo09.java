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

package com.taotao.cloud.im.biz.platform.modules.topic.vo;

import com.platform.modules.topic.enums.TopicNoticeTypeEnum;
import com.platform.modules.topic.enums.TopicTypeEnum;
import java.util.Date;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true) // 链式调用
public class TopicVo09 {

    /** 帖子id */
    private Long topicId;
    /** 帖子类型 */
    private TopicTypeEnum topicType;
    /** 帖子内容 */
    private String topicContent;
    /** 通知类型 1点赞 2回复 */
    private TopicNoticeTypeEnum noticeType;
    /** 用户id */
    private Long userId;
    /** 昵称 */
    private String nickName;
    /** 头像 */
    private String portrait;
    /** 回复内容 */
    private String replyContent;
    /** 回复时间 */
    private Date replyTime;
}
