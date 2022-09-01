package com.taotao.cloud.office.easyexecl.temp;

import com.alibaba.excel.EasyExcel;
import com.alibaba.fastjson2.JSON;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.FileInputStream;
import java.util.List;
import java.util.Map;

/**
 * 临时测试
 *

 */
@Ignore
public class LockTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(LockTest.class);

    @Test
    public void test() throws Exception {
        List<Object> list =
            EasyExcel.read(new FileInputStream("/Users/zhuangjiaju/test/pic.xls")).doReadAllSync();
        for (Object data : list) {
            LOGGER.info("返回数据：{}", JSON.toJSONString(data));
        }
    }

    @Test
    public void test2() throws Exception {
        List<Object> list =
            EasyExcel.read(new FileInputStream("D:\\test\\开发部.xls")).sheet().headRowNumber(0).doReadSync();
        for (Object data : list) {
            LOGGER.info("返回数据：{}", ((Map)data).size());
            LOGGER.info("返回数据：{}", JSON.toJSONString(data));
        }
    }

}
