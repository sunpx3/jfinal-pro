package com.sunpx.demo.controller;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.beanutils.PropertyUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.lang.math.NumberUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Font;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.streaming.SXSSFSheet;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;


public class ExportExcel {

    private static SimpleDateFormat SDF = null;


    private static final int sheetRowsNums = 500000;

    /**
     *
     * 方法描述:excel导出方法
     *
     * @param strMeaning
     *            表头的数组
     * @param strName
     *            表字段数组
     * @param collection
     *            表数据集合
     * @param fileName
     *            文件名
     * @throws Exception
     *
     * @author zxh
     */
    public static void exportExcel(String[] strMeaning, String[] strName, List<?> list, String fileName,HttpServletResponse res) throws Exception {
        ByteArrayOutputStream os = new ByteArrayOutputStream();

        Workbook wb = ExportExcel.generateExcelforObject(strMeaning, strName, "", list);

        wb.write(os);
        byte[] content = os.toByteArray();
        InputStream is = new ByteArrayInputStream(content);
        // 设置response参数，可以打开下载页面
        res.reset();
        res.setContentType("application/vnd.ms-excel;charset=utf-8");
        res.setHeader("Content-Disposition",
                "attachment;filename=" + new String((fileName + ".xlsx").getBytes(), "iso-8859-1"));
        ServletOutputStream out = res.getOutputStream();
        BufferedInputStream bis = null;
        BufferedOutputStream bos = null;

        try {
            bis = new BufferedInputStream(is);
            bos = new BufferedOutputStream(out);
            byte[] buff = new byte[2048];
            int bytesRead;
            // Simple read/write loop.
            while (-1 != (bytesRead = bis.read(buff, 0, buff.length))) {
                bos.write(buff, 0, bytesRead);
            }
        } catch (Exception e) {
            // TODO: handle exception
            e.printStackTrace();
        } finally {
            if (bis != null)
                bis.close();
            if (bos != null)
                bos.close();
        }
    }

    private static Workbook generateExcelforObject(String[] strMeaning, String[] strName, String str,
                                                   List<?> list) throws IllegalAccessException, InvocationTargetException, NoSuchMethodException, IOException {
        Workbook wb = new SXSSFWorkbook();
        if (list == null || list.size() < 1)
            return wb; // 无数据侧返回
        if (strMeaning == null || strMeaning.length < 1)
            return wb; // 表头为空侧返回
        if (strName == null || strName.length < 1)
            return wb; // 字段侧返回
        if (strMeaning.length != strName.length)
            return wb; // 两个数组长度不同侧返回

        int result = 0;
        //计算可以分多少个sheet
        if(list.size() > 0){
            result = list.size() % sheetRowsNums == 0?list.size() / sheetRowsNums:list.size() / sheetRowsNums + 1;
        }


        Row row = null;
        int rowNum = 0;
        int rowIndex = 1;
        // 设置工作簿的名称
        String sheetTitle = StringUtils.isEmpty(str) ? "Sheet" : str;
        //循环sheet，插入数据
        for (int i = 0; i < result; i++) {
            SXSSFSheet sheet = (SXSSFSheet) wb.createSheet();
            wb.setSheetName(i,sheetTitle + i);
            // 创建行
            row = sheet.createRow(rowNum);
            // 设置标题
            setTitle(row, strMeaning, wb);
            //每个sheet插入数据前初始化行数
            rowNum = 0;

            List<?> listTmp = new ArrayList();
            if(i == result - 1){
                listTmp = list.subList(i * sheetRowsNums, list.size());
            }else{
                listTmp = list.subList(i * sheetRowsNums, (i + 1) * sheetRowsNums);
            }


            for (Object allthree : listTmp) {
                rowIndex++;
                if(rowIndex % sheetRowsNums == 0){
                    //刷新写入硬盘数据阀值
                    ((SXSSFSheet)sheet).flushRows(sheetRowsNums);
                    break;
                }

                // 行对象
                row = sheet.createRow(++rowNum);
                // 设置对应值
                setRow(row, strName, allthree);
            }
        }

        return wb;
    }

    /**
     * 方法描述: 为Excel页中的每个横行设置标题
     *
     * @param row
     * @param strMeaning
     * @param wb
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     * @author zxh
     */
    @SuppressWarnings("deprecation")
    private static void setTitle(Row row, String[] strMeaning, Workbook wb)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        CellStyle style = wb.createCellStyle();
        // 设置这些样式
        style.setFillForegroundColor(HSSFColor.GREY_25_PERCENT.index);
        // style.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND); // 设置单元格北京颜色
        // style.setBorderBottom(HSSFCellStyle.BORDER_THIN); //设置单元格下部线加粗
        // style.setBorderLeft(HSSFCellStyle.BORDER_THIN); //设置单元格左部线加粗
        // style.setBorderRight(HSSFCellStyle.BORDER_THIN); //设置单元格右部线加粗
        // style.setBorderTop(HSSFCellStyle.BORDER_THIN); //设置单元格上部线加粗
        // style.setAlignment(HSSFCellStyle.ALIGN_CENTER); // 设置单元格字符居中

        // 生成一个字体
        Font font = wb.createFont();
        // font.setColor(HSSFColor.WHITE.index); //设置字体颜色
        font.setFontHeightInPoints((short) 10);
        // font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD); //设置字体加粗
        // 把字体应用到当前的样式
        style.setFont(font);
        for (int k = 0; k < strMeaning.length; k++) {
            Cell cell = row.createCell((short) k);
            cell.setCellStyle(style);
            cell.setCellValue(strMeaning[k]);
        }
    }

    /**
     *
     * 方法描述: 为Excel页中的每个横行设置值
     *
     * @param row
     * @param strName
     * @param exportModel
     * @throws IllegalAccessException
     * @throws InvocationTargetException
     * @throws NoSuchMethodException
     *
     */
    @SuppressWarnings("deprecation")
    private static void setRow(Row row, String[] strName, Object exportModel)
            throws IllegalAccessException, InvocationTargetException, NoSuchMethodException {
        Object temp = null;
        for (int k = 0; k < strName.length; k++) {
            // Cell对象
            Cell cell = row.createCell((short) k);
            // 设置对应值
            try {
                // 检查该实体是否有这个属性
                temp = PropertyUtils.getProperty(exportModel, strName[k]);
            } catch (Exception e) {
                e.getStackTrace();
                continue;
            }
            if (temp == null) {
                cell.setCellValue(StringUtils.EMPTY);
            } else {
                if (temp instanceof Date) {
                    cell.setCellValue(getDateTimeFormat().format(Date.class.cast(temp)));
                } else if (NumberUtils.isNumber(temp.toString())) {
                    cell.setCellValue(Double.parseDouble(temp.toString()));
                } else {
                    cell.setCellValue(temp.toString());
                }
            }
        }
    }

    /**
     * 方法描述: 获取系统时间格式
     *
     * @return SimpleDateFormat
     * @author zxh
     */
    public static SimpleDateFormat getDateFormat() {
        if (SDF == null) {
            SDF = new SimpleDateFormat("yyyy-MM-dd");
        }
        return SDF;
    }

    /**
     * 方法描述: 获取系统精确时间格式
     *
     * @return SimpleDateFormat
     * @author zxh
     */
    public static SimpleDateFormat getDateTimeFormat() {
        if (SDF == null) {
            SDF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        }
        return SDF;
    }



}
