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

package com.taotao.cloud.payment.biz.bootx.core.paymodel.union.entity;

import cn.bootx.common.core.function.EntityBaseFunction;
import cn.bootx.common.mybatisplus.base.MpBaseEntity;
import cn.bootx.payment.core.paymodel.union.convert.UnionPayConvert;
import cn.bootx.payment.dto.paymodel.union.UnionPayConfigDto;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * 云闪付
 *
 * @author xxm
 * @date 2022/3/11
 */
@Data
@Accessors(chain = true)
@TableName("pay_union_pay_config")
public class UnionPayConfig extends MpBaseEntity implements EntityBaseFunction<UnionPayConfigDto> {
    @Override
    public UnionPayConfigDto toDto() {
        return UnionPayConvert.CONVERT.convert(this);
    }
}
