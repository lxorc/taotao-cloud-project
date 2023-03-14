package com.taotao.cloud.flowable.biz.flowable.controller;


import com.taotao.cloud.flowable.biz.flowable.domain.vo.FlowTaskVo;
import com.taotao.cloud.flowable.biz.flowable.service.IFlowInstanceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import java.util.Map;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * <p>工作流流程实例管理<p>
 *
 * @author Tony
 * @date 2021-04-03
 */
@Slf4j
@Api(tags = "工作流流程实例管理")
@RestController
@RequestMapping("/flowable/instance")
public class FlowInstanceController {

	@Autowired
	private IFlowInstanceService flowInstanceService;

	@ApiOperation(value = "根据流程定义id启动流程实例")
	@PostMapping("/startBy/{procDefId}")
	public AjaxResult startById(
		@ApiParam(value = "流程定义id") @PathVariable(value = "procDefId") String procDefId,
		@ApiParam(value = "变量集合,json对象") @RequestBody Map<String, Object> variables) {
		return flowInstanceService.startProcessInstanceById(procDefId, variables);

	}


	@ApiOperation(value = "激活或挂起流程实例")
	@PostMapping(value = "/updateState")
	public AjaxResult updateState(
		@ApiParam(value = "1:激活,2:挂起", required = true) @RequestParam Integer state,
		@ApiParam(value = "流程实例ID", required = true) @RequestParam String instanceId) {
		flowInstanceService.updateState(state, instanceId);
		return AjaxResult.success();
	}

	@ApiOperation("结束流程实例")
	@PostMapping(value = "/stopProcessInstance")
	public AjaxResult stopProcessInstance(@RequestBody FlowTaskVo flowTaskVo) {
		flowInstanceService.stopProcessInstance(flowTaskVo);
		return AjaxResult.success();
	}

	@ApiOperation(value = "删除流程实例")
	@DeleteMapping(value = "/delete/{instanceIds}")
	public AjaxResult delete(
		@ApiParam(value = "流程实例ID", required = true) @PathVariable String[] instanceIds,
		@ApiParam(value = "删除原因") @RequestParam(required = false) String deleteReason) {
		for (String instanceId : instanceIds) {
			flowInstanceService.delete(instanceId, deleteReason);
		}
		return AjaxResult.success();
	}
}
