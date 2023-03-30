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

package com.taotao.cloud.payment.biz.jeepay.pay.channel;

import com.taotao.cloud.payment.biz.jeepay.core.entity.PayOrder;
import com.taotao.cloud.payment.biz.jeepay.pay.model.MchAppConfigContext;
import com.taotao.cloud.payment.biz.jeepay.pay.rqrs.msg.ChannelRetMsg;

/**
 * 查单（渠道侧）接口定义
 *
 * @author terrfly
 * @site https://www.jeequan.com
 * @date 2021/5/19 15:16
 */
public interface IPayOrderQueryService {

    /** 获取到接口code * */
    String getIfCode();

    /** 查询订单 * */
    ChannelRetMsg query(PayOrder payOrder, MchAppConfigContext mchAppConfigContext)
            throws Exception;
}
