package com.taotao.cloud.payment.biz.daxpay.core.channel.union.dao;

import cn.bootx.platform.daxpay.core.channel.union.entity.UnionPayment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * @author xxm
 * @since 2022/3/11
 */
@Mapper
public interface UnionPaymentMapper extends BaseMapper<UnionPayment> {

}
