package com.taotao.cloud.promotion.biz.entity;

import cn.lili.modules.promotion.entity.dto.BasePromotions;
import com.baomidou.mybatisplus.annotation.TableName;
import com.taotao.cloud.web.base.entity.BaseSuperEntity;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NoArgsConstructor;


/**
 * 积分商品实体类
 *
 * @author paulG
 * @since 2020-03-19 10:44 上午
 **/
@Entity
@Table(name = PointsGoods.TABLE_NAME)
@TableName(PointsGoods.TABLE_NAME)
@org.hibernate.annotations.Table(appliesTo = PointsGoods.TABLE_NAME, comment = "积分商品实体类")
public class PointsGoods extends BaseSuperEntity<PointsGoods, Long> {

	public static final String TABLE_NAME = "li_points_goods";

    @ApiModelProperty(value = "商品编号")
    private String goodsId;

    @ApiModelProperty(value = "商品sku编号")
    private String skuId;

    @ApiModelProperty(value = "商品名称")
    private String goodsName;

    @ApiModelProperty(value = "商品原价")
    private Double originalPrice;

    @ApiModelProperty(value = "结算价格")
    private Double settlementPrice;

    @ApiModelProperty(value = "积分商品分类编号")
    private String pointsGoodsCategoryId;

    @ApiModelProperty(value = "分类名称")
    private String pointsGoodsCategoryName;

    @ApiModelProperty(value = "缩略图")
    private String thumbnail;

    @ApiModelProperty(value = "活动库存数量")
    private Integer activeStock;

    @ApiModelProperty(value = "兑换积分")
    private Long points;

}
