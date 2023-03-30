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

package com.taotao.cloud.payment.biz.bootx.code.pay;

import com.taotao.cloud.payment.biz.bootx.exception.payment.PayFailureException;
import java.util.Arrays;
import java.util.Objects;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * 支付方式
 *
 * @author xxm
 * @date 2021/7/26
 */
@Getter
@AllArgsConstructor
public enum PayWayEnum {
    NORMAL(PayWayCode.NORMAL, "NORMAL", "常规支付"),
    WAP(PayWayCode.WAP, "WAP", "wap支付"),
    APP(PayWayCode.APP, "APP", "应用支付"),
    WEB(PayWayCode.WEB, "WEB", "web支付"),
    QRCODE(PayWayCode.QRCODE, "QRCODE", "扫码支付"),
    BARCODE(PayWayCode.BARCODE, "BARCODE", "付款码"),

    JSAPI(PayWayCode.JSAPI, "JSAPI", "公众号/小程序支付");

    /** 支付方式数字编码 */
    private final int no;
    /** 支付方式字符编码 */
    private final String code;
    /** 名称 */
    private final String name;

    /** 根据数字编号获取 */
    public static PayWayEnum findByNo(int no) {
        return Arrays.stream(PayWayEnum.values())
                .filter(e -> e.getNo() == no)
                .findFirst()
                .orElseThrow(() -> new PayFailureException("不存在的支付方式"));
    }
    /** 根据字符编码获取 */
    public static PayWayEnum findByCode(String code) {
        return Arrays.stream(PayWayEnum.values())
                .filter(e -> Objects.equals(code, e.getCode()))
                .findFirst()
                .orElseThrow(() -> new PayFailureException("不存在的支付方式"));
    }
}
