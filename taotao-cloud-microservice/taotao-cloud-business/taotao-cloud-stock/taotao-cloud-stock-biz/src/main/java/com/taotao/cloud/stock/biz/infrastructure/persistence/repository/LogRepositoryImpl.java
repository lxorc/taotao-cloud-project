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

package com.taotao.cloud.stock.biz.infrastructure.persistence.repository;

import com.baomidou.mybatisplus.extension.service.IService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Repository;

/**
 * 日志-Repository实现类
 *
 * @author shuigedeng
 * @date 2021-02-02
 */
@Repository
public class LogRepositoryImpl extends ServiceImpl<SysLogMapper, SysLogDO>
        implements LogRepository, IService<SysLogDO> {

    @Override
    public void store(Log log) {
        SysLogDO sysLogDO = LogConverter.fromLog(log);
        if (sysLogDO.getId() == null) {
            this.save(sysLogDO);
        } else {
            this.updateById(sysLogDO);
        }
    }
}
