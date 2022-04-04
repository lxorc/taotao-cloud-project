package com.taotao.cloud.report.biz.service;

import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 商品统计业务层
 */
public interface GoodsStatisticsService extends IService<Goods> {

    /**
     * 获取所有的已上架的商品数量
     *
     * @param goodsStatusEnum 商品状态枚举
     * @param goodsAuthEnum   商品审核枚举
     * @return 所有的已上架的商品数量
     */
    long goodsNum(GoodsStatusEnum goodsStatusEnum, GoodsAuthEnum goodsAuthEnum);

    /**
     * 获取今天的已上架的商品数量
     *
     * @return 今天的已上架的商品数量
     */
    long todayUpperNum();
}
