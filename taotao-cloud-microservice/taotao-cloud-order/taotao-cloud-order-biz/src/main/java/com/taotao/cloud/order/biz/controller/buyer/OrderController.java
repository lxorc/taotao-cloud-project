package com.taotao.cloud.order.biz.controller.buyer;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.taotao.cloud.common.enums.ResultEnum;
import com.taotao.cloud.common.exception.BusinessException;
import com.taotao.cloud.common.model.Result;
import com.taotao.cloud.common.utils.common.OperationalJudgment;
import com.taotao.cloud.logger.annotation.RequestLogger;
import com.taotao.cloud.order.api.query.order.OrderPageQuery;
import com.taotao.cloud.order.api.enums.order.OrderStatusEnum;
import com.taotao.cloud.order.api.vo.order.OrderDetailVO;
import com.taotao.cloud.order.api.vo.order.OrderSimpleVO;
import com.taotao.cloud.order.biz.entity.order.Order;
import com.taotao.cloud.order.biz.service.order.OrderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

import lombok.AllArgsConstructor;
import org.apache.shardingsphere.distsql.parser.autogen.CommonDistSQLStatementParser.UserContext;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

/**
 * 买家端,订单API
 */
@AllArgsConstructor
@Validated
@RestController
@Tag(name = "买家端-订单API", description = "买家端-订单API")
@RequestMapping("/order/buyer/order")
public class OrderController {
	private final OrderService orderService;
	@Operation(summary = "查询会员订单列表", description = "查询会员订单列表")
	@RequestLogger("查询会员订单列表")
	@PreAuthorize("hasAuthority('dept:tree:data')")
	@GetMapping("/page")
	public Result<IPage<OrderSimpleVO>> queryMineOrder(OrderPageQuery orderPageQuery) {
		AuthUser currentUser = Objects.requireNonNull(UserContext.getCurrentUser());
		orderPageQuery.setMemberId(currentUser.getId());
		return Result.success(orderService.queryByParams(orderPageQuery));
	}

	@Operation(summary = "订单明细", description = "订单明细")
	@RequestLogger("订单明细")
	@PreAuthorize("hasAuthority('dept:tree:data')")
	@GetMapping(value = "/{orderSn}")
	public Result<OrderDetailVO> detail(
		@NotNull(message = "订单编号不能为空") @PathVariable("orderSn") String orderSn) {
		OrderDetailVO orderDetailVO = orderService.queryDetail(orderSn);
		OperationalJudgment.judgment(orderDetailVO.getOrder());
		return Result.success(orderDetailVO);
	}

	@Operation(summary = "确认收货", description = "确认收货")
	@RequestLogger("确认收货")
	@PreAuthorize("hasAuthority('dept:tree:data')")
	@PostMapping(value = "/{orderSn}/receiving")
	public Result<Boolean> receiving(
		@NotNull(message = "订单编号不能为空") @PathVariable("orderSn") String orderSn) {
		Order order = orderService.getBySn(orderSn);
		if (order == null) {
			throw new BusinessException(ResultEnum.ORDER_NOT_EXIST);
		}
		//判定是否是待收货状态
		if (!order.getOrderStatus().equals(OrderStatusEnum.DELIVERED.name())) {
			throw new BusinessException(ResultEnum.ORDER_DELIVERED_ERROR);
		}
		orderService.complete(orderSn);
		return Result.success(true);
	}

	@Operation(summary = "取消订单", description = "取消订单")
	@RequestLogger("取消订单")
	@PreAuthorize("hasAuthority('dept:tree:data')")
	@PostMapping(value = "/{orderSn}/cancel")
	public Result<Boolean> cancel(@PathVariable String orderSn,
		@RequestParam String reason) {
		orderService.cancel(orderSn, reason);
		return Result.success(true);
	}

	@Operation(summary = "删除订单", description = "删除订单")
	@RequestLogger("删除订单")
	@PreAuthorize("hasAuthority('dept:tree:data')")
	@DeleteMapping(value = "/{orderSn}")
	public Result<Boolean> deleteOrder(@PathVariable String orderSn) {
		OperationalJudgment.judgment(orderService.getBySn(orderSn));
		orderService.deleteOrder(orderSn);
		return Result.success(true);
	}

	@Operation(summary = "查询物流踪迹", description = "查询物流踪迹")
	@RequestLogger("查询物流踪迹")
	@PreAuthorize("hasAuthority('dept:tree:data')")
	@PostMapping(value = "/traces/{orderSn}")
	public Result<Object> getTraces(
		@NotBlank(message = "订单编号不能为空") @PathVariable String orderSn) {
		OperationalJudgment.judgment(orderService.getBySn(orderSn));
		return Result.success(orderService.getTraces(orderSn));
	}

	@Operation(summary = "开票", description = "开票")
	@RequestLogger("开票")
	@PreAuthorize("hasAuthority('dept:tree:data')")
	@PostMapping(value = "/receipt/{orderSn}")
	public Result<Object> invoice(
		@NotBlank(message = "订单编号不能为空") @PathVariable String orderSn) {
		OperationalJudgment.judgment(orderService.getBySn(orderSn));
		return Result.success(orderService.invoice(orderSn));
	}


}
