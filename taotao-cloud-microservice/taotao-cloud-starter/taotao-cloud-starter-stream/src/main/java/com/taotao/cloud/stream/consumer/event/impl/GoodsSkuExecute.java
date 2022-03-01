//package com.taotao.cloud.stream.consumer.event.impl;
//
//
//import cn.lili.event.GoodsCommentCompleteEvent;
//import cn.lili.modules.goods.service.GoodsSkuService;
//import cn.lili.modules.member.entity.dos.MemberEvaluation;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Service;
//
///**
// * 商品SKU变化
// *
// */
//@Service
//public class GoodsSkuExecute implements GoodsCommentCompleteEvent {
//
//    /**
//     * 商品
//     */
//    @Autowired
//    private GoodsSkuService goodsSkuService;
//
//
//    @Override
//    public void goodsComment(MemberEvaluation memberEvaluation) {
//        goodsSkuService.updateGoodsSkuCommentNum(memberEvaluation.getSkuId());
//    }
//}
