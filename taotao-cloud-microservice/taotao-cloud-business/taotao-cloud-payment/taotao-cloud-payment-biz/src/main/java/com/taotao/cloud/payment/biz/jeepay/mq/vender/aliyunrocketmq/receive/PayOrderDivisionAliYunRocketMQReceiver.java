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

package com.taotao.cloud.payment.biz.jeepay.mq.vender.aliyunrocketmq.receive;

import com.taotao.cloud.payment.biz.jeepay.mq.constant.MQVenderCS;
import com.taotao.cloud.payment.biz.jeepay.mq.model.PayOrderDivisionMQ;
import com.taotao.cloud.payment.biz.jeepay.mq.vender.aliyunrocketmq.AbstractAliYunRocketMQReceiver;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;

/** AliYunRocketMQ消息接收器：仅在vender=AliYunRocketMQ时 && 项目实现IMQReceiver接口时 进行实例化 业务： 支付订单分账通知 */
@Slf4j
@Component
@ConditionalOnProperty(name = MQVenderCS.YML_VENDER_KEY, havingValue = MQVenderCS.ALIYUN_ROCKET_MQ)
@ConditionalOnBean(PayOrderDivisionMQ.IMQReceiver.class)
public class PayOrderDivisionAliYunRocketMQReceiver extends AbstractAliYunRocketMQReceiver {

    private static final String CONSUMER_NAME = "支付订单分账消息";

    @Autowired
    private PayOrderDivisionMQ.IMQReceiver mqReceiver;

    /** 接收 【 queue 】 类型的消息 */
    @Override
    public void receiveMsg(String msg) {
        mqReceiver.receive(PayOrderDivisionMQ.parse(msg));
    }

    /**
     * 获取topic名称
     *
     * @return
     */
    @Override
    public String getMQName() {
        return PayOrderDivisionMQ.MQ_NAME;
    }

    /**
     * 获取业务名称
     *
     * @return
     */
    @Override
    public String getConsumerName() {
        return CONSUMER_NAME;
    }
}
