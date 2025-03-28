package com.taotao.cloud.payment.biz.daxpay.single.service.core.record.close.dao;

import cn.bootx.platform.common.core.rest.param.PageParam;
import cn.bootx.platform.common.mybatisplus.impl.BaseManager;
import cn.bootx.platform.common.mybatisplus.util.MpUtil;
import cn.bootx.platform.common.query.generator.QueryGenerator;
import com.taotao.cloud.payment.biz.daxpay.single.service.core.record.close.entity.PayCloseRecord;
import com.taotao.cloud.payment.biz.daxpay.single.service.param.record.PayCloseRecordQuery;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Repository;

/**
 *
 * @author xxm
 * @since 2024/1/4
 */
@Slf4j
@Repository
@RequiredArgsConstructor
public class PayCloseRecordManager extends BaseManager<PayCloseRecordMapper, PayCloseRecord> {

    /**
     * 分页
     */
    public Page<PayCloseRecord> page(PageParam pageParam, PayCloseRecordQuery param){
        Page<PayCloseRecord> mpPage = MpUtil.getMpPage(pageParam, PayCloseRecord.class);
        QueryWrapper<PayCloseRecord> generator = QueryGenerator.generator(param);
        return page(mpPage, generator);
    }
}
