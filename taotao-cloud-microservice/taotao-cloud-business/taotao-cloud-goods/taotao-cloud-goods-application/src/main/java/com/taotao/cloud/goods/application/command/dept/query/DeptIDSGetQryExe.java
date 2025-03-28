

package com.taotao.cloud.goods.application.command.dept.query;

import static org.laokou.common.i18n.common.DatasourceConstants.TENANT;

import com.baomidou.dynamic.datasource.annotation.DS;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.laokou.admin.domain.gateway.DeptGateway;
import org.laokou.admin.dto.dept.DeptIDSGetQry;
import org.laokou.common.i18n.dto.Result;
import org.springframework.stereotype.Component;

/**
 * 查看部门IDS执行器.
 *
 * 
 */
@Component
@RequiredArgsConstructor
public class DeptIDSGetQryExe {

	private final DeptGateway deptGateway;

	/**
	 * 执行查看部门IDS.
	 * @param qry 查看部门IDS参数
	 * @return 部门IDS
	 */
	@DS(TENANT)
	public Result<List<Long>> execute(DeptIDSGetQry qry) {
		return Result.of(deptGateway.getDeptIds(qry.getRoleId()));
	}

}
