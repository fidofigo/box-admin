package com.ygg.admin.util.excel;

import org.apache.commons.lang.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.springframework.util.CollectionUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.net.URLEncoder;
import java.util.*;


public class ExcelUtil {
    /**
     * 这个方法是导出到用户本地
     *
     * @param response
     * @param list
     * @param <T>
     * @throws Exception
     */
    public static <T> void export(HttpServletResponse response, List<T> list) {
        Workbook workbook;

        if (CollectionUtils.isEmpty(list)) {
            return;
        }

        int count = 0;
        workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet("导出数据");
        Iterator<T> iterator1 = list.iterator();
        Row goodsTitleRow = sheet.createRow(count);
        setTitle(goodsTitleRow, list.get(0).getClass());
        count++;

        CellStyle textStyle = workbook.createCellStyle();
        DataFormat format = workbook.createDataFormat();
        textStyle.setDataFormat(format.getFormat("@"));
        try {
            while (iterator1.hasNext()) {
                T t = iterator1.next();
                Row rowGoods = sheet.createRow(count);
                setCellValue(rowGoods, t, workbook, textStyle);
                count++;
            }

            response.setHeader("Content-Disposition", "attachment; filename=export.xls");
            response.setContentType("application/octet-stream; charset=utf-8");
            OutputStream outputStream;
            outputStream = response.getOutputStream();
            workbook.write(outputStream);
            outputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("导出excel错误");
        }
    }

    /**
     * @param rowTitle
     * @param t
     * @param <T>
     */
    private static <T> void setTitle(Row rowTitle, Class<T> t) {
        Field[] fields = t.getDeclaredFields();
        Set<Integer> cellSet = new HashSet<>();
        for (Field field : fields) {

            ExcelAnnotation[] annotations = field.getAnnotationsByType(ExcelAnnotation.class);
            if (annotations.length <= 0)
                continue;

            if (cellSet.contains(annotations[0].cell())) {
                throw new RuntimeException("excel导出列行数重复");
            }
            cellSet.add(annotations[0].cell());
            Cell cell = rowTitle.createCell(annotations[0].cell());
            cell.setCellValue(annotations[0].header());
        }
    }

    /**
     * @descroption 给每一行赋值
     */
    private static <T> void setCellValue(Row row, T t, Workbook workbook, CellStyle textStyle) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        Map<String, String> valueMap;
        valueMap = BeanRefUtil.getFieldValueMap(t);
        Field[] fields = t.getClass().getDeclaredFields();
        for (Field field : fields) {

            ExcelAnnotation[] annotations = field.getAnnotationsByType(ExcelAnnotation.class);
            if (annotations.length <= 0)
                continue;
            Cell cell = row.createCell(annotations[0].cell());

            cell.setCellStyle(textStyle);//设置单元格格式为"文本"
            cell.setCellType(HSSFCell.CELL_TYPE_STRING);

            cell.setCellValue(valueMap.get(field.getName()));
        }

    }

    /**
     * 导出带有title的空白excel模板
     * @param response
     * @param titles
     */
    public static void exportBlankBodyWithTitle(HttpServletResponse response, String[] titles, String fileName) {
        response.setContentType("application/octet-stream; charset=utf-8");
        String codedFileName = StringUtils.isBlank(fileName) ? "importTemplate" : fileName;
        OutputStream fOut = null;
        Workbook workbook = null;
        try {
            response.setHeader("Content-Disposition", "attachment;filename=" + URLEncoder.encode(codedFileName, "UTF-8") + ".xls");
            workbook = new HSSFWorkbook();
            Sheet sheet = workbook.createSheet();
            Row row = sheet.createRow(0);
            for (int i = 0; i < titles.length; i++) {
                Cell cell = row.createCell(i);
                cell.setCellValue(titles[i]);
            }
            fOut = response.getOutputStream();
            workbook.write(fOut);
            fOut.flush();
        } catch (Exception e) {
            throw new RuntimeException("服务异常");
        } finally {
            if (fOut != null) {
                try {
                    fOut.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            if (workbook != null) {
                try {
                    workbook.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
