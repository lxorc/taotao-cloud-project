/*
 * Copyright 2002-2021 the original author or authors.
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
package com.taotao.cloud.sys.biz.service;

import com.taotao.cloud.sys.biz.entity.Setting;
import com.taotao.cloud.web.base.service.BaseSuperService;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;

/**
 * ISettingService
 *
 * @author shuigedeng
 * @version v1.0
 * @since 2022/03/10 10:31
 */
@CacheConfig(cacheNames = "{setting}")
public interface ISettingService extends BaseSuperService<Setting, Long> {

	/**
	 * 通过key获取
	 *
	 * @param key
	 */
	@Cacheable(key = "#key")
	Setting get(String key);

	/**
	 * 修改
	 *
	 * @param setting
	 */
	@CacheEvict(key = "#setting.id")
	boolean saveUpdate(Setting setting);
}
