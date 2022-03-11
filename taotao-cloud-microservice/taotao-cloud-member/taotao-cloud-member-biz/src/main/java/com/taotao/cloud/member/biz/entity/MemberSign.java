package com.taotao.cloud.member.biz.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.taotao.cloud.web.base.entity.BaseSuperEntity;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

/**
 * 会员签到表
 *
 * @author shuigedeng
 * @version 2021.10
 * @since 2022-03-11 15:31:33
 */
@Entity
@Table(name = MemberSign.TABLE_NAME)
@TableName(MemberSign.TABLE_NAME)
@org.hibernate.annotations.Table(appliesTo = MemberSign.TABLE_NAME, comment = "会员签到表")
public class MemberSign extends BaseSuperEntity<MemberSign, Long> {

	public static final String TABLE_NAME = "tt_member_sign";
	/**
	 * 会员用户名
	 */
	@Column(name = "member_ame", nullable = false, columnDefinition = "varchar(32) not null comment '会员用户名'")
	private String memberName;

	/**
	 * 会员用户ID
	 */
	@Column(name = "member_id", nullable = false, columnDefinition = "varchar(32) not null comment '会员用户ID'")
	private String memberId;

	/**
	 * 连续签到天数
	 */
	@Column(name = "sign_day", nullable = false, columnDefinition = "int not null default 0 comment '连续签到天数'")
	private Integer signDay;

	public String getMemberName() {
		return memberName;
	}

	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public Integer getSignDay() {
		return signDay;
	}

	public void setSignDay(Integer signDay) {
		this.signDay = signDay;
	}
}
