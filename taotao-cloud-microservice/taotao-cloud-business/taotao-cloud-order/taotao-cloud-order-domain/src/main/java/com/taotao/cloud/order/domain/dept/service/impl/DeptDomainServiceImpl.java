package com.taotao.cloud.order.domain.dept.service.impl;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.taotao.cloud.common.model.PageQuery;
import com.taotao.cloud.order.domain.dept.entity.DeptEntity;
import com.taotao.cloud.order.domain.dept.repository.DeptRepository;
import com.taotao.cloud.order.domain.dept.service.DeptDomainService;

public class DeptDomainServiceImpl implements DeptDomainService {

	private DeptRepository deptRepository;

	@Override
	public Boolean insert(DeptEntity deptEntity) {
		return null;
	}

	@Override
	public Boolean update(DeptEntity deptEntity) {
		return null;
	}

	@Override
	public DeptEntity getById(Long id) {
		return null;
	}

	@Override
	public Boolean deleteById(Long id) {
		return null;
	}

	@Override
	public IPage<DeptEntity> list(DeptEntity deptEntity, PageQuery pageQuery) {
//		return deptRepository.list(deptEntity, pageQuery);
		return null;
	}
}
