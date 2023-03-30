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

package com.taotao.cloud.payment.biz.jeepay.core.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

/**
 * 商户分账接收者账号绑定关系表
 *
 * @author [mybatis plus generator]
 * @since 2021-08-19
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Accessors(chain = true)
@TableName("t_mch_division_receiver")
public class MchDivisionReceiver implements Serializable {

    private static final long serialVersionUID = 1L;

    // gw
    public static final LambdaQueryWrapper<MchDivisionReceiver> gw() {
        return new LambdaQueryWrapper<>();
    }

    /** 分账接收者ID */
    @TableId(value = "receiver_id", type = IdType.AUTO)
    private Long receiverId;

    /** 接收者账号别名 */
    private String receiverAlias;

    /** 组ID（便于商户接口使用） */
    private Long receiverGroupId;

    /** 组名称 */
    private String receiverGroupName;

    /** 商户号 */
    private String mchNo;

    /** 服务商号 */
    private String isvNo;

    /** 应用ID */
    private String appId;

    /** 支付接口代码 */
    private String ifCode;

    /** 分账接收账号类型: 0-个人(对私) 1-商户(对公) */
    private Byte accType;

    /** 分账接收账号 */
    private String accNo;

    /** 分账接收账号名称 */
    private String accName;

    /** 分账关系类型（参考微信）， 如： SERVICE_PROVIDER 服务商等 */
    private String relationType;

    /** 当选择自定义时，需要录入该字段。 否则为对应的名称 */
    private String relationTypeName;

    /** 分账比例 */
    private BigDecimal divisionProfit;

    /** 分账状态（本系统状态，并不调用上游关联关系）: 1-正常分账, 0-暂停分账 */
    private Byte state;

    /** 上游绑定返回信息，一般用作查询绑定异常时的记录 */
    private String channelBindResult;

    /** 渠道特殊信息 */
    private String channelExtInfo;

    /** 绑定成功时间 */
    private Date bindSuccessTime;

    /** 创建时间 */
    private Date createdAt;

    /** 更新时间 */
    private Date updatedAt;
}
