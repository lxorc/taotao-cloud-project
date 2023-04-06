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

package com.taotao.cloud.payment.biz.bootx.core.paymodel.alipay.service;

import com.alipay.api.AlipayApiException;
import com.alipay.api.domain.AlipayTradeQueryModel;
import com.alipay.api.response.AlipayTradeQueryResponse;
import com.ijpay.alipay.AliPayApi;
import com.taotao.cloud.payment.biz.bootx.code.pay.PaySyncStatus;
import com.taotao.cloud.payment.biz.bootx.code.paymodel.AliPayCode;
import com.taotao.cloud.payment.biz.bootx.core.pay.result.PaySyncResult;
import com.taotao.cloud.payment.biz.bootx.core.payment.entity.Payment;
import java.util.HashMap;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * 支付宝同步
 *
 * @author xxm
 * @date 2021/5/17
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AlipaySyncService {

    /** 与支付宝网关同步状态 1 远程支付成功 2 交易创建，等待买家付款 3 超时关闭 4 查询不到 5 查询失败 */
    public PaySyncResult syncPayStatus(Payment payment) {
        PaySyncResult paySyncResult = new PaySyncResult().setPaySyncStatus(PaySyncStatus.FAIL);

        // 查询
        try {
            AlipayTradeQueryModel queryModel = new AlipayTradeQueryModel();
            queryModel.setOutTradeNo(String.valueOf(payment.getId()));
            AlipayTradeQueryResponse response = AliPayApi.tradeQueryToResponse(queryModel);
            String tradeStatus = response.getTradeStatus();

            // 支付完成
            if (Objects.equals(tradeStatus, AliPayCode.PAYMENT_TRADE_SUCCESS)
                    || Objects.equals(tradeStatus, AliPayCode.PAYMENT_TRADE_FINISHED)) {

                HashMap<String, String> map = new HashMap<>(1);
                map.put(AliPayCode.TRADE_NO, response.getTradeNo());
                return paySyncResult
                        .setPaySyncStatus(PaySyncStatus.TRADE_SUCCESS)
                        .setMap(map);
            }
            // 待支付
            if (Objects.equals(tradeStatus, AliPayCode.PAYMENT_WAIT_BUYER_PAY)) {
                return paySyncResult.setPaySyncStatus(PaySyncStatus.WAIT_BUYER_PAY);
            }
            // 已关闭
            if (Objects.equals(tradeStatus, AliPayCode.PAYMENT_TRADE_CLOSED)) {
                return paySyncResult.setPaySyncStatus(PaySyncStatus.TRADE_CLOSED);
            }
            // 未找到
            if (Objects.equals(response.getSubCode(), AliPayCode.ACQ_TRADE_NOT_EXIST)) {
                return paySyncResult.setPaySyncStatus(PaySyncStatus.NOT_FOUND);
            }

        } catch (AlipayApiException e) {
            log.error("查询订单失败:", e);
        }
        return paySyncResult;
    }
}
