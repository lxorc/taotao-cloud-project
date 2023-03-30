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

package com.taotao.cloud.payment.biz.jeepay.pay.task;

import cn.hutool.core.date.DateUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.taotao.cloud.payment.biz.jeepay.core.entity.PayOrder;
import com.taotao.cloud.payment.biz.jeepay.pay.service.ChannelOrderReissueService;
import com.taotao.cloud.payment.biz.jeepay.service.impl.PayOrderService;
import java.util.Date;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

/*
 * 补单定时任务
 *
 * @author terrfly
 * @site https://www.jeequan.com
 * @date 2021/6/8 17:47
 */
@Slf4j
@Component
public class PayOrderReissueTask {

    private static final int QUERY_PAGE_SIZE = 100; // 每次查询数量

    @Autowired private PayOrderService payOrderService;
    @Autowired private ChannelOrderReissueService channelOrderReissueService;

    @Scheduled(cron = "0 0/1 * * * ?") // 每分钟执行一次
    public void start() {

        // 当前时间 减去10分钟。
        Date offsetDate = DateUtil.offsetMinute(new Date(), -10);

        // 查询条件： 支付中的订单 & （ 订单创建时间 + 10分钟 >= 当前时间 ）
        LambdaQueryWrapper<PayOrder> lambdaQueryWrapper =
                PayOrder.gw()
                        .eq(PayOrder::getState, PayOrder.STATE_ING)
                        .le(PayOrder::getCreatedAt, offsetDate);

        int currentPageIndex = 1; // 当前页码
        while (true) {

            try {
                IPage<PayOrder> payOrderIPage =
                        payOrderService.page(
                                new Page(currentPageIndex, QUERY_PAGE_SIZE), lambdaQueryWrapper);

                if (payOrderIPage == null
                        || payOrderIPage.getRecords().isEmpty()) { // 本次查询无结果, 不再继续查询;
                    break;
                }

                for (PayOrder payOrder : payOrderIPage.getRecords()) {
                    channelOrderReissueService.processPayOrder(payOrder);
                }

                // 已经到达页码最大量，无需再次查询
                if (payOrderIPage.getPages() <= currentPageIndex) {
                    break;
                }
                currentPageIndex++;

            } catch (Exception e) { // 出现异常，直接退出，避免死循环。
                log.error("error", e);
                break;
            }
        }
    }
}
