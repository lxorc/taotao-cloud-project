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

package com.taotao.cloud.workflow.biz.engine.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.taotao.cloud.workflow.biz.common.model.engine.flowcomment.FlowCommentPagination;
import com.taotao.cloud.workflow.biz.engine.entity.FlowCommentEntity;
import java.util.List;

/** 流程评论 */
public interface FlowCommentService extends IService<FlowCommentEntity> {

    /**
     * 列表
     *
     * @param pagination 请求参数
     * @return
     */
    List<FlowCommentEntity> getlist(FlowCommentPagination pagination);

    /**
     * 信息
     *
     * @param id 主键值
     * @return
     */
    FlowCommentEntity getInfo(String id);

    /**
     * 创建
     *
     * @param entity 实体对象
     */
    void create(FlowCommentEntity entity);

    /**
     * 更新
     *
     * @param id 主键值
     * @param entity 实体对象
     * @return
     */
    void update(String id, FlowCommentEntity entity);

    /**
     * 删除
     *
     * @param entity 实体对象
     * @return
     */
    void delete(FlowCommentEntity entity);
}
