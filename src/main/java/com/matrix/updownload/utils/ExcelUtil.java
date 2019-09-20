package com.matrix.updownload.utils;

import com.alibaba.fastjson.JSONArray;
import org.apache.poi.hssf.usermodel.*;

import java.io.ByteArrayOutputStream;
import java.io.IOException;

/**
 * @ClassName ExcelUtil
 * @Author wby
 * @Date 2019/4/19 17:45
 * @Version 1.0
 * @Description TODO
 **/
public class ExcelUtil {
    public static HSSFWorkbook getExcelFile(JSONArray headers, String sheetName) {

        //创建HSSFWorkbook对象(excel的文档对象)
        HSSFWorkbook workBook = new HSSFWorkbook();
        //建立新的sheet对象（excel的表单）
        HSSFSheet sheet = workBook.createSheet(sheetName);
        //在sheet里创建第一行，参数为行索引(excel的行)，可以是0～65535之间的任何一个
        HSSFRow tableHead = sheet.createRow(0);
        HSSFCellStyle cellStyle = workBook.createCellStyle();
        //水平居中
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        //创建单元格（excel的单元格，参数为列索引，可以是0～255之间的任何一个
        for (int i = 0; i < headers.size(); i++) {
            tableHead.createCell(i, HSSFCell.CELL_TYPE_STRING).setCellValue(headers.getString(i));
        }
        //在sheet里创建第二行
        for (int i = 0; i < 5; i++) {
            HSSFRow context = sheet.createRow(i + 1);
            context.createCell(0, HSSFCell.CELL_TYPE_NUMERIC).setCellValue(i);
            context.createCell(1, HSSFCell.CELL_TYPE_STRING).setCellValue("xxx");
            context.createCell(2, HSSFCell.CELL_TYPE_STRING).setCellValue("bbb");
        }
        /*ByteArrayOutputStream bos = new ByteArrayOutputStream();
        try {
            workBook.write(bos);
        } catch (IOException e) {
            throw new RuntimeException("export excel error");
        }
        return bos;*/
        return workBook;
    }
}
