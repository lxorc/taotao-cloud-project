package com.taotao.cloud.order.api.feign;

import com.taotao.cloud.common.constant.ServiceName;
import com.taotao.cloud.common.model.Result;
import com.taotao.cloud.order.api.dto.order_item.OrderItemSaveDTO;
import com.taotao.cloud.order.api.feign.fallback.FeignOrderItemFallbackImpl;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * 远程调用订单项模块
 *
 * @author shuigedeng
 * @since 2020/5/2 16:42
 */
@FeignClient(contextId = "RemoteOrderItemService", value = ServiceName.TAOTAO_CLOUD_ORDER_CENTER, fallbackFactory = FeignOrderItemFallbackImpl.class)
public interface IFeignOrderItemService {

	@PostMapping(value = "/order/item/save")
	Result<Boolean> saveOrderItem(@RequestBody OrderItemSaveDTO orderItemSaveDTO);
}

