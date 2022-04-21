package com.taotao.cloud.common.execl.temp.read;

import com.alibaba.excel.context.AnalysisContext;
import com.alibaba.excel.event.AnalysisEventListener;
import com.alibaba.fastjson.JSON;

import lombok.extern.slf4j.Slf4j;

/**
 * TODO
 *

 * @date 2020/4/9 16:33
 */
@Slf4j
public class TestListener extends AnalysisEventListener {

    @Override
    public void invoke(Object o, AnalysisContext analysisContext) {
        log.info("解析一条:{}", JSON.toJSONString(o));
    }

    @Override
    public void doAfterAllAnalysed(AnalysisContext analysisContext) {

    }
}
