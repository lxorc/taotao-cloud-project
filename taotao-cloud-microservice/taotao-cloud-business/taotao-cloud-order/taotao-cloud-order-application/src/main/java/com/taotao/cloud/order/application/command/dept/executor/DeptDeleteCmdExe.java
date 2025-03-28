

package com.taotao.cloud.sys.application.command.dept.executor;

import com.baomidou.dynamic.datasource.annotation.DS;
import com.taotao.cloud.sys.application.command.dept.dto.DeptDeleteCmd;
import com.taotao.cloud.sys.domain.dept.service.DeptDomainService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;


/**
 * 删除部门执行器.
 *
 * 
 */
@Component
@RequiredArgsConstructor
public class DeptDeleteCmdExe {

	private final DeptDomainService deptDomainService;

	/**
	 * 执行删除部门.
	 *
	 * @param cmd 删除部门参数
	 * @return 执行删除结果
	 */
//	@DS(TENANT)
	public Boolean execute(DeptDeleteCmd cmd) {
		return deptDomainService.deleteById(cmd.getId());
	}

}
