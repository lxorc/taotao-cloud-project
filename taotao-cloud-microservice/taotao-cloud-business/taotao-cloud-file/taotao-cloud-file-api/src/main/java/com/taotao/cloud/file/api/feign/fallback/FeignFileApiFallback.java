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

package com.taotao.cloud.file.api.feign.fallback;

import com.taotao.cloud.common.utils.log.LogUtils;
import com.taotao.cloud.file.api.feign.IFeignFileApi;
import com.taotao.cloud.file.api.feign.response.FeignFileResponse;
import io.seata.core.context.RootContext;
import io.seata.core.exception.TransactionException;
import io.seata.tm.api.GlobalTransactionContext;
import org.dromara.hutool.core.text.StrUtil;
import org.springframework.cloud.openfeign.FallbackFactory;

/**
 * FeignDictFallback
 *
 * @author shuigedeng
 * @since 2020/4/29 21:43
 */
public class FeignFileApiFallback implements FallbackFactory<IFeignFileApi> {

    @Override
    public IFeignFileApi create(Throwable throwable) {
        LogUtils.info("throwablethrowablethrowablethrowablethrowable");

        return new IFeignFileApi() {
            @Override
            public FeignFileResponse findByCode(String code) {

                if (StrUtil.isNotBlank(RootContext.getXID())) {
                    try {
                        GlobalTransactionContext.reload(RootContext.getXID()).rollback();
                    } catch (TransactionException e) {
                        LogUtils.error(e);
                    }
                }

                return null;
            }
        };
    }
}
