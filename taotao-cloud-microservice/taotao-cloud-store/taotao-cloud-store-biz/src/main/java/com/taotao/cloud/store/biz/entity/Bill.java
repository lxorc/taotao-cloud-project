package com.taotao.cloud.store.biz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.taotao.cloud.web.base.entity.BaseSuperEntity;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 结算单
 *
 * 
 * @since 2020/11/17 4:27 下午
 */
@Entity
@Table(name = Bill.TABLE_NAME)
@TableName(Bill.TABLE_NAME)
@org.hibernate.annotations.Table(appliesTo = Bill.TABLE_NAME, comment = "结算清单表")
public class Bill extends BaseSuperEntity<Bill, Long> {

	public static final String TABLE_NAME = "tt_sys_bill";

	@Column(name = "sn", nullable = false, columnDefinition = "varchar(64) not null comment '账单号'")
	private String sn;

	@Column(name = "create_time", columnDefinition = "TIMESTAMP comment '结算开始时间'")
	private LocalDateTime startTime;

	@Column(name = "end_time", columnDefinition = "TIMESTAMP comment '结算结束时间'")
	private LocalDateTime endTime;

	/**
	 * @see BillStatusEnum
	 */
	@Column(name = "bill_status", nullable = false, columnDefinition = "varchar(32) not null comment '状态：OUT(已出账),CHECK(已对账),EXAMINE(已审核),PAY(已付款)'")
	private String billStatus;

	@Column(name = "store_id", nullable = false, columnDefinition = "varchar(32) not null comment '店铺id'")
	private String storeId;

	@Column(name = "store_name", nullable = false, columnDefinition = "varchar(32) not null comment '店铺名称'")
	private String storeName;

	@Column(name = "pay_time", columnDefinition = "TIMESTAMP comment '平台付款时间'")
	private LocalDateTime payTime;

	@Column(name = "bank_account_name", nullable = false, columnDefinition = "varchar(32) not null comment '银行开户名'")
	private String bankAccountName;

	@Column(name = "bank_account_number", nullable = false, columnDefinition = "varchar(32) not null comment '公司银行账号'")
	private String bankAccountNumber;

	@Column(name = "bank_name", nullable = false, columnDefinition = "varchar(32) not null comment '开户银行支行名称'")
	private String bankName;

	@Column(name = "bank_code", nullable = false, columnDefinition = "varchar(32) not null comment '支行联行号'")
	private String bankCode;

	/**
	 * 算钱规则 billPrice=orderPrice-refundPrice -commissionPrice+refundCommissionPrice
	 * -distributionCommission+distributionRefundCommission +siteCouponCommission-siteCouponRefundCommission
	 * +kanjiaSettlementPrice+pointSettlementPrice
	 */
	@Column(name = "order_price", nullable = false, columnDefinition = "decimal(10,2) not null default 0 comment '结算周期内订单付款总金额'")
	private BigDecimal orderPrice;

	@Column(name = "refund_price", nullable = false, columnDefinition = "decimal(10,2) not null default 0 comment '退单金额'")
	private BigDecimal refundPrice;

	@Column(name = "commission_price", nullable = false, columnDefinition = "decimal(10,2) not null default 0 comment '平台收取佣金'")
	private BigDecimal commissionPrice;

	@Column(name = "refund_commission_price", nullable = false, columnDefinition = "decimal(10,2) not null default 0 comment '退单产生退还佣金金额'")
	private BigDecimal refundCommissionPrice;

	@Column(name = "distribution_commission", nullable = false, columnDefinition = "decimal(10,2) not null default 0 comment '分销返现支出'")
	private BigDecimal distributionCommission;

	@Column(name = "distribution_refund_commission", nullable = false, columnDefinition = "decimal(10,2) not null default 0 comment '分销订单退还，返现佣金返还'")
	private BigDecimal distributionRefundCommission;

	@Column(name = "site_coupon_commission", nullable = false, columnDefinition = "decimal(10,2) not null default 0 comment '平台优惠券补贴'")
	private BigDecimal siteCouponCommission;

	@Column(name = "site_coupon_refund_commission", nullable = false, columnDefinition = "decimal(10,2) not null default 0 comment '退货平台优惠券补贴返还'")
	private BigDecimal siteCouponRefundCommission;

	@Column(name = "point_settlement_price", nullable = false, columnDefinition = "decimal(10,2) not null default 0 comment '积分商品结算价格'")
	private BigDecimal pointSettlementPrice;

	@Column(name = "kanjia_settlement_price", nullable = false, columnDefinition = "decimal(10,2) not null default 0 comment '砍价商品结算价格'")
	private BigDecimal kanjiaSettlementPrice;

	@Column(name = "bill_price", nullable = false, columnDefinition = "decimal(10,2) not null default 0 comment '最终结算金额'")
	private BigDecimal billPrice;

}
