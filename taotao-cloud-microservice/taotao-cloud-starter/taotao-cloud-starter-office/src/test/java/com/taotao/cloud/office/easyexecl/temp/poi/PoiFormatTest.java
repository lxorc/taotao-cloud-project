package com.taotao.cloud.office.easyexecl.temp.poi;

import com.taotao.cloud.common.execl.util.TestFileUtil;
import org.apache.poi.xssf.streaming.SXSSFRow;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Ignore;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.IOException;

/**
 * 测试poi
 *

 */
@Ignore
public class PoiFormatTest {
    private static final Logger LOGGER = LoggerFactory.getLogger(PoiFormatTest.class);

    @Test
    public void lastRowNum() throws IOException {
        String file = "D:\\test\\原文件.xlsx";
        SXSSFWorkbook xssfWorkbook = new SXSSFWorkbook(new XSSFWorkbook(file));
        SXSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        SXSSFRow row = xssfSheet.getRow(0);
        LOGGER.info("第一行数据:{}", row);
        xssfSheet.createRow(20);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
    }

    @Test
    public void lastRowNumXSSF() throws IOException {
        String file = TestFileUtil.getPath() + "demo" + File.separator + "demo.xlsx";
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        LOGGER.info("一共:{}个sheet", xssfWorkbook.getNumberOfSheets());
        XSSFSheet xssfSheet = xssfWorkbook.getSheetAt(0);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
        XSSFRow row = xssfSheet.getRow(0);
        LOGGER.info("第一行数据:{}", row);
        xssfSheet.createRow(20);
        LOGGER.info("一共行数:{}", xssfSheet.getLastRowNum());
    }
}
