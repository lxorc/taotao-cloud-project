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

package com.taotao.cloud.log.biz.log.core.mongo.service;

import com.taotao.cloud.common.model.PageResult;
import com.taotao.cloud.log.biz.log.core.mongo.convert.LogConvert;
import com.taotao.cloud.log.biz.log.core.mongo.dao.LoginLogMongoRepository;
import com.taotao.cloud.log.biz.log.core.mongo.entity.LoginLogMongo;
import com.taotao.cloud.log.biz.log.dto.LoginLogDto;
import com.taotao.cloud.log.biz.log.param.LoginLogParam;
import com.taotao.cloud.log.biz.log.service.LoginLogService;
import java.util.List;
import java.util.stream.Collectors;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.dromara.hutool.core.data.id.IdUtil;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.data.domain.*;
import org.springframework.stereotype.Service;

/**
 * MongoDB存储实现
 *
 * @author shuigedeng
 * @since 2021/12/2
 */
@Slf4j
@Service
@ConditionalOnProperty(prefix = "log.store", value = "type", havingValue = "mongodb")
@RequiredArgsConstructor
public class LoginLogMongoService implements LoginLogService {
    private final LoginLogMongoRepository repository;

    @Override
    public void add(LoginLogParam loginLog) {
        LoginLogMongo loginLogMongo = LogConvert.CONVERT.convert(loginLog);
        loginLogMongo.setId(IdUtil.getSnowflakeNextId());
        repository.save(loginLogMongo);
    }

    @Override
    public LoginLogDto findById(Long id) {
        return repository.findById(id).map(LoginLogMongo::toDto).orElseThrow(RuntimeException::new);
    }

    @Override
    public PageResult<LoginLogDto> page(LoginLogParam loginLogParam) {
        // 查询条件
        ExampleMatcher matching = ExampleMatcher.matching().withStringMatcher(ExampleMatcher.StringMatcher.CONTAINING);
        Example<LoginLogMongo> example = Example.of(LogConvert.CONVERT.convert(loginLogParam), matching);
        // 设置分页条件 (第几页，每页大小，排序)
        Sort sort = Sort.by(Sort.Order.desc("id"));
        Pageable pageable = PageRequest.of(loginLogParam.getCurrentPage() - 1, loginLogParam.getPageSize(), sort);

        Page<LoginLogMongo> page = repository.findAll(example, pageable);
        List<LoginLogDto> records =
                page.getContent().stream().map(LoginLogMongo::toDto).toList();

        return PageResult.of(
                page.getTotalElements(), 1, loginLogParam.getCurrentPage(), loginLogParam.getPageSize(), records);
    }

    @Override
    public void delete(Long id) {
        repository.deleteById(id);
    }
}
