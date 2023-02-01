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
package com.taotao.cloud.sys.biz.service.business.impl;

import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.core.types.dsl.BooleanExpression;
import com.taotao.cloud.common.enums.ResultEnum;
import com.taotao.cloud.common.exception.BusinessException;
import com.taotao.cloud.common.utils.common.RandomUtils;
import com.taotao.cloud.common.utils.log.LogUtils;
import com.taotao.cloud.sys.api.model.page.DictPageQuery;
import com.taotao.cloud.sys.biz.mapper.IDictMapper;
import com.taotao.cloud.sys.biz.model.entity.dict.Dict;
import com.taotao.cloud.sys.biz.model.entity.dict.QDict;
import com.taotao.cloud.sys.biz.repository.cls.DictRepository;
import com.taotao.cloud.sys.biz.repository.inf.IDictRepository;
import com.taotao.cloud.sys.biz.service.business.IDictItemService;
import com.taotao.cloud.sys.biz.service.business.IDictService;
import com.taotao.cloud.web.base.service.impl.BaseSuperServiceImpl;
import io.seata.spring.annotation.GlobalTransactional;
import java.sql.SQLIntegrityConstraintViolationException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Future;
import lombok.AllArgsConstructor;
import org.slf4j.MDC;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * DictServiceImpl
 *
 * @author shuigedeng
 * @version 2021.10
 * @since 2021-10-09 20:26:36
 */
@AllArgsConstructor
@Service
public class DictServiceImpl extends
	BaseSuperServiceImpl<IDictMapper, Dict, DictRepository, IDictRepository, Long>
	implements IDictService {

	private final IDictItemService dictItemService;

	private final QDict SYS_DICT = QDict.dict;
	private final BooleanExpression PREDICATE = SYS_DICT.eq(SYS_DICT);
	private final OrderSpecifier<Integer> SORT_DESC = SYS_DICT.sortNum.desc();
	private final OrderSpecifier<LocalDateTime> CREATE_TIME_DESC = SYS_DICT.createTime.desc();

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Dict saveDict(Dict sysDict) {
		String dictCode = sysDict.getDictCode();
		if (cr().existsByDictCode(dictCode)) {
			throw new BusinessException(ResultEnum.DICT_CODE_REPEAT_ERROR);
		}
		return cr().saveAndFlush(sysDict);
	}

	@Override
	public List<Dict> getAll() {
		return cr().findAll();
	}

	@Override
	public Page<Dict> queryPage(Pageable page, DictPageQuery dictPageQuery) {
		//Optional.ofNullable(dictPageQuery.getDictName())
		//	.ifPresent(dictName -> PREDICATE.and(SYS_DICT.dictName.like(dictName)));
		//Optional.ofNullable(dictPageQuery.getDictCode())
		//	.ifPresent(dictCode -> PREDICATE.and(SYS_DICT.dictCode.eq(dictCode)));
		//Optional.ofNullable(dictPageQuery.getDescription())
		//	.ifPresent(description -> PREDICATE.and(SYS_DICT.description.like(description)));
		//Optional.ofNullable(dictPageQuery.getRemark())
		//	.ifPresent(remark -> PREDICATE.and(SYS_DICT.remark.like(remark)));
		return cr().findPageable(PREDICATE, page, SORT_DESC, CREATE_TIME_DESC);
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean removeById(Long id) {
		Optional<Dict> optionalDict = cr().findById(id);
		optionalDict.orElseThrow(() -> new BusinessException(ResultEnum.DICT_NOT_EXIST));
		cr().deleteById(id);
		dictItemService.deleteByDictId(id);
		return true;
	}

	@Override
	@Transactional(rollbackFor = Exception.class)
	public Boolean deleteByCode(String code) {
		Dict dict = findByCode(code);
		cr().delete(dict);

		Long dictId = dict.getId();
		dictItemService.deleteByDictId(dictId);
		return true;
	}

	@Override
	public Dict findById(Long id) {
		Optional<Dict> optionalDict = cr().findById(id);
		return optionalDict.orElseThrow(() -> new BusinessException(ResultEnum.DICT_NOT_EXIST));
	}


	@Override
	public Dict update(Dict dict) {
		return cr().saveAndFlush(dict);
	}

	@Override
	public Dict findByCode(String code) {
		//List<Dict> all = ir().findAll();
		//List<Dict> all1 = cr().findAll();

		Optional<Dict> optionalDict = cr().findByCode(code);
		return optionalDict.orElseThrow(() -> new BusinessException(ResultEnum.DICT_NOT_EXIST));
		//return Dict.builder().id(2L).createBy(2L).createTime(LocalDateTime.now())
		//	.dictCode("123123123").dictName("lsdfjaslf")
		//	.remark("sdfasfd")
		//	.description("sdflasjdfl")
		//	.build();
	}

	@Override
	@Async
	public Future<Dict> findAsyncByCode(String code) {
		LogUtils.info("异步查询字典, 当前线程名称：{}", Thread.currentThread().getName());

		try {
			Thread.sleep(3000);
		} catch (InterruptedException ignored) {
		}

		Dict result = Dict.builder().id(2L).createBy(2L).createTime(LocalDateTime.now())
			.dictCode("async123123123").dictName("asynclsdfjaslf")
			.remark("asyncsdfasfd")
			.description("asyncsdflasjdfl")
			.build();

		Map<String, String> copyOfContextMap = MDC.getCopyOfContextMap();

		LogUtils.info("findAsyncByCode: {}", result);

		return CompletableFuture.completedFuture(result);
	}

	@Override
	public String async() {
		return "sdfasf";
	}

	@Override
	@GlobalTransactional(name = "sys-dict-global-transactional-1", rollbackFor = Exception.class)
	public Boolean add(String type) throws SQLIntegrityConstraintViolationException {

		if ("1".equals(type)) {
			throw new BusinessException("xxxxxx");
		}
		if ("2".equals(type)) {
			throw new SQLIntegrityConstraintViolationException(
				"SQLIntegrityConstraintViolationException");
		}

		Dict d1 = Dict.builder().dictCode("asdfsadf").dictName("sldf").sortNum(3).build();
		Dict d2 = Dict.builder().dictCode("asdfsadf222").dictName("sldf222").sortNum(5).build();
		int i = im().insertBatchSomeColumn(List.of(d1, d2));

		return true;
	}

	@Override
	//@GlobalTransactional(name = "sys-dict-global-transactional-2", rollbackFor = Exception.class)
	@Transactional(rollbackFor = Exception.class)
	public Boolean add1() {

		String s1 = RandomUtils.randomChar(6);
		String s2 = RandomUtils.randomChar(6);

		Dict d1 = Dict.builder().dictCode(s1).dictName("sldf").sortNum(3).build();
		Dict d2 = Dict.builder().dictCode(s2).dictName("sldf222").sortNum(5).build();
		//int i = im().insertBatchSomeColumn(List.of(d1, d2));

		List<Dict> dicts = cr().saveAll(List.of(d1, d2));

		String s5 = RandomUtils.randomChar(6);
		Dict d5 = Dict.builder().dictCode(s5).dictName("sldf").sortNum(3).build();
		Dict save = cr().save(d5);

		String s3 = RandomUtils.randomChar(6);
		String s4 = RandomUtils.randomChar(6);
		Dict d3 = Dict.builder().dictCode(s3).dictName("sldf").sortNum(3).build();
		Dict d4 = Dict.builder().dictCode(s4).dictName("sldf222").sortNum(5).build();

		String s6 = RandomUtils.randomChar(6);
		Dict d6 = Dict.builder().dictCode(s6).dictName("sldf222").sortNum(5).build();
		Dict save1 = ir().save(d6);

		List<Dict> dicts1 = ir().saveAllAndFlush(List.of(d3, d4));

		return true;
	}
}
