package com.taotao.cloud.goods.biz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.taotao.cloud.web.base.entity.BaseSuperEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

/**
 * 店铺商品分类表
 *
 * @since 2020-02-18 15:18:56
 */
@Setter
@Getter
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = StoreGoodsLabel.TABLE_NAME)
@TableName(StoreGoodsLabel.TABLE_NAME)
@org.hibernate.annotations.Table(appliesTo = StoreGoodsLabel.TABLE_NAME, comment = "店铺商品分类表")
public class StoreGoodsLabel extends BaseSuperEntity<StoreGoodsLabel, Long> {

	public static final String TABLE_NAME = "tt_store_goods_label";

	/**
	 * 店铺ID
	 */
	@Column(name = "store_id", nullable = false, columnDefinition = "varchar(64) not null comment '店铺ID'")
	private String storeId;

	/**
	 * 店铺商品分类名称
	 */
	@Column(name = "label_name", nullable = false, columnDefinition = "varchar(64) not null comment '店铺商品分类名称'")
	private String labelName;

	/**
	 * 店铺商品分类排序
	 */
	@Column(name = "sort_order", nullable = false, columnDefinition = "int not null comment '店铺商品分类排序'")
	private Integer sortOrder;

	/**
	 * 父id, 根节点为0
	 */
	@Column(name = "parent_id", nullable = false, columnDefinition = "varchar(64) not null comment '父id, 根节点为0'")
	private String parentId;

	/**
	 * 层级, 从0开始
	 */
	@Column(name = "level", nullable = false, columnDefinition = "int not null comment '层级, 从0开始'")
	private Integer level;
}
