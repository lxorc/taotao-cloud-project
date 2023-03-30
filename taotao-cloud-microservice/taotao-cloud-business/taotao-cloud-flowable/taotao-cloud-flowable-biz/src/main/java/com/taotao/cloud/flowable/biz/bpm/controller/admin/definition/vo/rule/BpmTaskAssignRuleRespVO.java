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

package com.taotao.cloud.flowable.biz.bpm.controller.admin.definition.vo.rule;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.ToString;

@ApiModel("管理后台 - 流程任务分配规则的 Response VO")
@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
public class BpmTaskAssignRuleRespVO extends BpmTaskAssignRuleBaseVO {

    @ApiModelProperty(value = "任务分配规则的编号", required = true, example = "1024")
    private Long id;

    @ApiModelProperty(value = "流程模型的编号", required = true, example = "2048")
    private String modelId;

    @ApiModelProperty(value = "流程定义的编号", required = true, example = "4096")
    private String processDefinitionId;

    @ApiModelProperty(value = "流程任务定义的编号", required = true, example = "2048")
    private String taskDefinitionKey;

    @ApiModelProperty(value = "流程任务定义的名字", required = true, example = "关注芋道")
    private String taskDefinitionName;
}
