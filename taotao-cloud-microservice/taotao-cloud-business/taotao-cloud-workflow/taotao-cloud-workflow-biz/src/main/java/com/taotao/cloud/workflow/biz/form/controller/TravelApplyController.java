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

package com.taotao.cloud.workflow.biz.form.controller;

import com.taotao.cloud.common.utils.common.JsonUtils;
import com.taotao.cloud.workflow.biz.common.model.form.travelapply.TravelApplyForm;
import com.taotao.cloud.workflow.biz.common.model.form.travelapply.TravelApplyInfoVO;
import com.taotao.cloud.workflow.biz.engine.entity.FlowTaskOperatorEntity;
import com.taotao.cloud.workflow.biz.engine.enums.FlowStatusEnum;
import com.taotao.cloud.workflow.biz.engine.service.FlowTaskOperatorService;
import com.taotao.cloud.workflow.biz.form.entity.TravelApplyEntity;
import com.taotao.cloud.workflow.biz.form.service.TravelApplyService;
import org.hibernate.exception.DataException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/** 出差预支申请单 */
@Tag(tags = "出差预支申请单", value = "TravelApply")
@RestController
@RequestMapping("/api/workflow/Form/TravelApply")
public class TravelApplyController {

    @Autowired
    private TravelApplyService travelApplyService;

    @Autowired
    private FlowTaskOperatorService flowTaskOperatorService;

    /**
     * 获取出差预支申请单信息
     *
     * @param id 主键值
     * @return
     */
    @Operation("获取出差预支申请单信息")
    @GetMapping("/{id}")
    public Result<TravelApplyInfoVO> info(@PathVariable("id") String id, String taskOperatorId) throws DataException {
        TravelApplyInfoVO vo = null;
        boolean isData = true;
        if (StringUtil.isNotEmpty(taskOperatorId)) {
            FlowTaskOperatorEntity operator = flowTaskOperatorService.getInfo(taskOperatorId);
            if (operator != null) {
                if (StringUtil.isNotEmpty(operator.getDraftData())) {
                    vo = JsonUtils.getJsonToBean(operator.getDraftData(), TravelApplyInfoVO.class);
                    isData = false;
                }
            }
        }
        if (isData) {
            TravelApplyEntity entity = travelApplyService.getInfo(id);
            vo = JsonUtils.getJsonToBean(entity, TravelApplyInfoVO.class);
        }
        return Result.success(vo);
    }

    /**
     * 新建出差预支申请单
     *
     * @param travelApplyForm 表单对象
     * @return
     */
    @Operation("新建出差预支申请单")
    @PostMapping
    public Result create(@RequestBody TravelApplyForm travelApplyForm) throws WorkFlowException {
        TravelApplyEntity entity = JsonUtils.getJsonToBean(travelApplyForm, TravelApplyEntity.class);
        if (FlowStatusEnum.save.getMessage().equals(travelApplyForm.getStatus())) {
            travelApplyService.save(entity.getId(), entity);
            return Result.success(MsgCode.SU002.get());
        }
        travelApplyService.submit(entity.getId(), entity, travelApplyForm.getCandidateList());
        return Result.success(MsgCode.SU006.get());
    }

    /**
     * 修改出差预支申请单
     *
     * @param travelApplyForm 表单对象
     * @param id 主键
     * @return
     */
    @Operation("修改出差预支申请单")
    @PutMapping("/{id}")
    public Result update(@RequestBody TravelApplyForm travelApplyForm, @PathVariable("id") String id)
            throws WorkFlowException {
        TravelApplyEntity entity = JsonUtils.getJsonToBean(travelApplyForm, TravelApplyEntity.class);
        if (FlowStatusEnum.save.getMessage().equals(travelApplyForm.getStatus())) {
            travelApplyService.save(id, entity);
            return Result.success(MsgCode.SU002.get());
        }
        travelApplyService.submit(id, entity, travelApplyForm.getCandidateList());
        return Result.success(MsgCode.SU006.get());
    }
}
