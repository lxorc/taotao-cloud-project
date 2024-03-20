

package com.taotao.cloud.auth.application.command.management.dto;

import com.taotao.cloud.auth.application.command.management.dto.clientobject.DictCO;
import com.taotao.cloud.ddd.application.model.CommonCommand;
import io.swagger.v3.oas.annotations.media.Schema;
import lombok.Data;

@Data
@Schema(name = "DictUpdateCmd", description = "修改字典命令请求")
public class DictUpdateCmd extends CommonCommand {

	@Schema(name = "dictCO", description = "字典")
	private DictCO dictCO;

}
