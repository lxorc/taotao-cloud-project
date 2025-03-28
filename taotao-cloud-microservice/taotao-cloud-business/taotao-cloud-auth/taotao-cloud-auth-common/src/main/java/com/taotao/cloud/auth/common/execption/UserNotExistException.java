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

package com.taotao.cloud.auth.common.execption;

import com.taotao.cloud.auth.common.enums.ErrorCodeEnum;
import com.taotao.cloud.auth.common.execption.AbstractResponseJsonAuthenticationException;

/**
 * 用户不存在异常
 */
public class UserNotExistException extends AbstractResponseJsonAuthenticationException {
    private static final long serialVersionUID = 3042211783958201322L;

    public UserNotExistException(ErrorCodeEnum errorCodeEnum, String userId) {
        super(errorCodeEnum, null, userId);
    }

    public UserNotExistException(ErrorCodeEnum errorCodeEnum, Throwable cause, String userId) {
        super(errorCodeEnum, cause, null, userId);
    }
}
