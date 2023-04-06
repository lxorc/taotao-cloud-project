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

package com.taotao.cloud.payment.biz.bootx.controller;

import com.taotao.cloud.common.model.PageQuery;
import com.taotao.cloud.payment.biz.bootx.core.notify.service.PayNotifyRecordService;
import com.taotao.cloud.payment.biz.bootx.dto.notify.PayNotifyRecordDto;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 回调记录
 *
 * @author xxm
 * @date 2021/7/22
 */
@Tag(name = "支付回调记录")
@RestController
@RequestMapping("/pay/notify/record")
@RequiredArgsConstructor
public class PayNotifyRecordController {
    private final PayNotifyRecordService notifyRecordService;

    @Operation(summary = "分页")
    @GetMapping("/page")
    public ResResult<PageResult<PayNotifyRecordDto>> page(PageQuery PageQuery, PayNotifyRecordDto param) {
        return Res.ok(notifyRecordService.page(PageQuery, param));
    }

    @Operation(summary = "根据id查询")
    @GetMapping("/findById")
    public ResResult<PayNotifyRecordDto> findById(Long id) {
        return Res.ok(notifyRecordService.findById(id));
    }
}
