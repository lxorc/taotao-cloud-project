package com.taotao.cloud.payment.biz.daxpay.single.service.core.channel.wechat.dao;

import com.taotao.cloud.payment.biz.daxpay.single.service.core.channel.wechat.entity.WxReconcileBillTotal;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 微信对账单总表
 * @author xxm
 * @since 2024/1/18
 */
@Mapper
public interface WxReconcileBillTotalMapper extends BaseMapper<WxReconcileBillTotal> {
}
