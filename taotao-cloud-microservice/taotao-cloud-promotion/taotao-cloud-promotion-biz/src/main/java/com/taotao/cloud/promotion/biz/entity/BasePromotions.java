
package com.taotao.cloud.promotion.biz.entity;

import com.taotao.cloud.promotion.api.enums.PromotionsScopeTypeEnum;
import com.taotao.cloud.promotion.api.enums.PromotionsStatusEnum;
import com.taotao.cloud.web.base.entity.BaseSuperEntity;
import com.taotao.cloud.web.base.entity.SuperEntity;
import java.io.Serializable;
import java.time.LocalDateTime;
import javax.persistence.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 促销活动基础类
 */
@EqualsAndHashCode(callSuper = true)
@Data
public class BasePromotions<T extends SuperEntity<T, I>, I extends Serializable> extends
	BaseSuperEntity<T, I> {

	private static final long serialVersionUID = 7814832369110695758L;

	@Column(name = "store_name", nullable = false, columnDefinition = "varchar(64) not null comment '商家名称，如果是平台，这个值为 platform'")
	private String storeName;

	@Column(name = "store_id", nullable = false, columnDefinition = "varchar(64) not null comment '商家id，如果是平台，这个值为 platform'")
	private String storeId;

	@Column(name = "promotion_name", nullable = false, columnDefinition = "varchar(64) not null comment '活动名称'")
	private String promotionName;

	@Column(name = "start_time", nullable = false, columnDefinition = "varchar(64) not null comment '活动开始时间'")
	private LocalDateTime startTime;

	@Column(name = "end_time", nullable = false, columnDefinition = "varchar(64) not null comment '活动结束时间'")
	private LocalDateTime endTime;

	/**
	 * @see PromotionsScopeTypeEnum
	 */
	@Column(name = "scope_type", nullable = false, columnDefinition = "varchar(64) not null comment '关联范围类型'")
	private String scopeType = PromotionsScopeTypeEnum.PORTION_GOODS.name();

	@Column(name = "scope_id", nullable = false, columnDefinition = "varchar(64) not null comment '范围关联的id'")
	private String scopeId;

	/**
	 * @return 促销状态
	 * @see PromotionsStatusEnum
	 */
	//public String getPromotionStatus() {
	//	if (endTime == null) {
	//		return startTime != null ? PromotionsStatusEnum.START.name()
	//			: PromotionsStatusEnum.CLOSE.name();
	//	}
	//	Date now = new Date();
	//	if (now.before(startTime)) {
	//		return PromotionsStatusEnum.NEW.name();
	//	} else if (endTime.before(now)) {
	//		return PromotionsStatusEnum.END.name();
	//	} else if (now.before(endTime)) {
	//		return PromotionsStatusEnum.START.name();
	//	}
	//	return PromotionsStatusEnum.CLOSE.name();
	//}
	public String getStoreName() {
		return storeName;
	}

	public void setStoreName(String storeName) {
		this.storeName = storeName;
	}

	public String getStoreId() {
		return storeId;
	}

	public void setStoreId(String storeId) {
		this.storeId = storeId;
	}

	public String getPromotionName() {
		return promotionName;
	}

	public void setPromotionName(String promotionName) {
		this.promotionName = promotionName;
	}

	public LocalDateTime getStartTime() {
		return startTime;
	}

	public void setStartTime(LocalDateTime startTime) {
		this.startTime = startTime;
	}

	public LocalDateTime getEndTime() {
		return endTime;
	}

	public void setEndTime(LocalDateTime endTime) {
		this.endTime = endTime;
	}

	public String getScopeType() {
		return scopeType;
	}

	public void setScopeType(String scopeType) {
		this.scopeType = scopeType;
	}

	public String getScopeId() {
		return scopeId;
	}

	public void setScopeId(String scopeId) {
		this.scopeId = scopeId;
	}
}
