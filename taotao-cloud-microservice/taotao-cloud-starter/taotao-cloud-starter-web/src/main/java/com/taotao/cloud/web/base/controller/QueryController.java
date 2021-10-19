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
package com.taotao.cloud.web.base.controller;

import cn.hutool.core.bean.BeanUtil;
import com.taotao.cloud.common.constant.CommonConstant;
import com.taotao.cloud.common.exception.BusinessException;
import com.taotao.cloud.common.model.Result;
import com.taotao.cloud.data.mybatis.plus.conditions.query.QueryWrap;
import com.taotao.cloud.log.annotation.RequestOperateLog;
import com.taotao.cloud.web.base.entity.SuperEntity;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import javax.validation.constraints.NotNull;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * QueryController
 *
 * @param <T>        实体
 * @param <I>        id
 * @param <QueryDTO> 查询参数
 * @param <QueryVO>  查询返回参数
 * @author shuigedeng
 * @version 2021.9
 * @since 2021-09-02 21:11:18
 */
public interface QueryController<T extends SuperEntity<T,I>, I extends Serializable, QueryDTO, QueryVO> extends
	PageController<T, I, QueryDTO, QueryVO> {

	/**
	 * 通用单体查询
	 *
	 * @param id 主键id
	 * @return {@link com.taotao.cloud.common.model.Result }
	 * @author shuigedeng
	 * @since 2021-09-02 21:11:48
	 */
	@Operation(summary = "通用单体查询", description = "通用单体查询")
	@GetMapping("/{id:[0-9]*}")
	@RequestOperateLog("通用单体查询")
	//@PreAuthorize("@permissionVerifier.hasPermission('get')")
	default Result<QueryVO> get(
		@Parameter(description = "id", required = true) @NotNull(message = "id不能为空")
		@PathVariable(value = "id") I id) {
		T data = service().getById(id);
		if (Objects.isNull(data)) {
			throw new BusinessException("未查询到数据");
		}
		QueryVO queryVO = BeanUtil.toBean(data, getQueryVOClass());
		return success(queryVO);
	}

	/**
	 * 通用批量查询
	 *
	 * @param queryDTO 通用批量查询
	 * @return {@link com.taotao.cloud.common.model.Result }
	 * @author shuigedeng
	 * @since 2021-09-02 21:12:04
	 */
	@Operation(summary = "通用批量查询", description = "通用批量查询")
	@PostMapping("/query")
	@RequestOperateLog(value = "通用批量查询")
	//@PreAuthorize("@permissionVerifier.hasPermission('query')")
	default Result<List<QueryVO>> query(
		@Parameter(description = "查询对象", required = true)
		@RequestBody @Validated QueryDTO queryDTO) {
		QueryWrap<T> wrapper = handlerWrapper(queryDTO);
		List<T> data = service().list(wrapper);
		List<QueryVO> result = Optional
			.ofNullable(data)
			.orElse(new ArrayList<>())
			.stream().filter(Objects::nonNull)
			.map(t -> BeanUtil.toBean(t, getQueryVOClass()))
			.toList();
		return success(result);
	}
}
