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

package com.taotao.cloud.payment.biz.jeepay.pay.util;

import cn.hutool.core.util.StrUtil;
import com.taotao.cloud.payment.biz.jeepay.core.utils.SpringBeansUtil;
import com.taotao.cloud.payment.biz.jeepay.pay.channel.IPaymentService;

/*
 * 支付方式动态调用Utils
 * @author terrfly
 * @site https://www.jeequan.com
 * @date 2021/6/8 17:46
 */
public class PaywayUtil {

    private static final String PAYWAY_PACKAGE_NAME = "payway";
    private static final String PAYWAYV3_PACKAGE_NAME = "paywayV3";

    /** 获取真实的支付方式Service * */
    public static IPaymentService getRealPaywayService(Object obj, String wayCode) {

        try {

            // 下划线转换驼峰 & 首字母大写
            String clsName = StrUtil.upperFirst(StrUtil.toCamelCase(wayCode.toLowerCase()));
            return (IPaymentService) SpringBeansUtil.getBean(
                    Class.forName(obj.getClass().getPackage().getName() + "." + PAYWAY_PACKAGE_NAME + "." + clsName));

        } catch (ClassNotFoundException e) {
            return null;
        }
    }

    /** 获取微信V3真实的支付方式Service * */
    public static IPaymentService getRealPaywayV3Service(Object obj, String wayCode) {

        try {

            // 下划线转换驼峰 & 首字母大写
            String clsName = StrUtil.upperFirst(StrUtil.toCamelCase(wayCode.toLowerCase()));
            return (IPaymentService) SpringBeansUtil.getBean(
                    Class.forName(obj.getClass().getPackage().getName() + "." + PAYWAYV3_PACKAGE_NAME + "." + clsName));

        } catch (ClassNotFoundException e) {
            return null;
        }
    }
}
