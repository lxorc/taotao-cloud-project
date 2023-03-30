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

package com.taotao.cloud.wechat.biz.niefy.modules.wx.form;

import com.github.niefy.common.utils.Json;
import jakarta.validation.constraints.NotEmpty;
import lombok.Data;

@Data
public class WxH5OuthrizeForm {

    @NotEmpty(message = "code不得为空")
    private String code;

    @Override
    public String toString() {
        return Json.toJsonString(this);
    }
}
