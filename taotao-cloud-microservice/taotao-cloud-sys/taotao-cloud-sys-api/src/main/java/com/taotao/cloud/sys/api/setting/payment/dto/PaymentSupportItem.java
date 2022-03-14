package com.taotao.cloud.sys.api.setting.payment.dto;

import lombok.Data;

import java.util.List;

/**
 * 支持的支付方式属性
 *
 */
@Data
public class PaymentSupportItem {


    @ApiModelProperty(value = "客户端 h5/app/小程序/pc")
    private String client;

    @ApiModelProperty(value = "支持的支付方式")
    private List<String> supports;


}
