package com.taotao.cloud.goods.api.vo;

import io.soabase.recordbuilder.core.RecordBuilder;
import io.swagger.v3.oas.annotations.media.Schema;

import java.io.Serial;
import java.io.Serializable;
import java.util.List;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 商品规格VO
 *
 * @author shuigedeng
 * @version 2022.04
 * @since 2022-04-25 16:34:17
 */
@RecordBuilder
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GoodsSkuSpecVO implements Serializable {

	@Serial
	private static final long serialVersionUID = 3829199991161122317L;

	@Schema(description = "商品skuId")
	private Long skuId;

	@Schema(description = "商品sku所包含规格")
	private List<SpecValueVO> specValues;

	@Schema(description = "库存")
	private Integer quantity;

}
