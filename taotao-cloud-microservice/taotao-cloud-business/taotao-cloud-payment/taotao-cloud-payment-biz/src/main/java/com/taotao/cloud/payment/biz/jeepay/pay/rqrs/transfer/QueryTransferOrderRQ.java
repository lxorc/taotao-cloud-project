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

package com.taotao.cloud.payment.biz.jeepay.pay.rqrs.transfer;

import com.taotao.cloud.payment.biz.jeepay.pay.rqrs.AbstractMchAppRQ;
import lombok.Data;

/*
 * 查询转账单请求参数对象
 *
 * @author terrfly
 * @site https://www.jeequan.com
 * @date 2021/6/17 14:07
 */
@Data
public class QueryTransferOrderRQ extends AbstractMchAppRQ {

    /** 商户转账单号 * */
    private String mchOrderNo;

    /** 支付系统转账单号 * */
    private String transferId;
}
