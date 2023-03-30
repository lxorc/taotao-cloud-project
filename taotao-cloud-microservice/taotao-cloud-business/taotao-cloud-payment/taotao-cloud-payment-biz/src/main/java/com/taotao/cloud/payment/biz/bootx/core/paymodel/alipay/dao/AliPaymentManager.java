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

package com.taotao.cloud.payment.biz.bootx.core.paymodel.alipay.dao;

import com.taotao.cloud.payment.biz.bootx.core.paymodel.alipay.entity.AliPayment;
import com.taotao.cloud.payment.biz.bootx.core.paymodel.base.entity.BasePayment;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

/**
 * 支付宝
 *
 * @author xxm
 * @date 2021/2/26
 */
@Repository
@RequiredArgsConstructor
public class AliPaymentManager extends BaseManager<AliPaymentMapper, AliPayment> {
    private final AliPaymentMapper aliPaymentRepository;

    public Optional<AliPayment> findByPaymentId(Long paymentId) {
        return this.findByField(BasePayment::getPaymentId, paymentId);
    }
}
