package com.joycity.joyclub.apiback.util;


import com.joycity.joyclub.apiback.exception.BusinessException;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import static com.joycity.joyclub.apiback.constant.ResultCode.ERR_IMPORT_EXCEL;

public class ExcelToBeanParser {
    public final static String DEFAULT_DATE_FORMATTER = "yyyy-MM-dd";

    /**
     * 将excel内容转换为JavaBean
     *
     * @param src
     * @param startRow
     * @param startCol
     */
    @SuppressWarnings("rawtypes")
    public static <T> List<T> parse(File src, Class clazz, int startRow,
                                    int startCol, String dateFormatter) {
        if (dateFormatter == null) {
            dateFormatter = DEFAULT_DATE_FORMATTER;
        }

        // 1.将数据从excel中抽出
        List<List<String>> dataset = null;
        dataset = loadDataFromExcel(src, startRow);
        // 2.转换bean
        List<T> res = new ArrayList<T>();
        if (dataset.size() > 0) {
            Field[] fs = clazz.getDeclaredFields();
            T temp = null;
            for (List<String> row : dataset) {
                startRow++;
                temp = parseRowToBean(row, clazz, fs, startRow, dateFormatter);
                if (temp == null) {
                    break;
                }
                res.add(temp);
            }
        }
        return res;
    }

    public static List<List<String>> loadDataFromExcel(File src,
                                                       int dataStartRow) {
        String suffix = null;
        String fileName = src.getName();
        Integer index = fileName.lastIndexOf(".");
        if (index + 1 <= fileName.length()) {
            suffix = src.getName().substring(index + 1,
                    fileName.length());
        }
        List<List<String>> dataset = new ArrayList<List<String>>();

        Sheet sheet = null;
        Row row = null;
        Workbook wb = null;
        try {
            if ("xls".equals(suffix)) {
                wb = new HSSFWorkbook(new FileInputStream(src));
            } else if ("xlsx".equals(suffix) || "xlsm".equals(suffix)) {
                wb = new XSSFWorkbook(new FileInputStream(src));
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(dataStartRow);
        int colNum = row.getPhysicalNumberOfCells();
        List<String> rowData = null;
        for (int i = dataStartRow; i <= rowNum; i++) {
            row = sheet.getRow(i);
            if (row == null) {
                break;
            }
            rowData = new ArrayList<String>();
            int j = 0;
            while (j < colNum) {
                String s = getCellValue(row.getCell(j));
                rowData.add(s == null ? null : s.trim());
                j++;
            }
            dataset.add(rowData);
        }
        return dataset;
    }

    @SuppressWarnings("resource")
    public static List<List<String>> loadDataFromExcel(InputStream is, String fileName,
                                                       int dataStartRow) {
        String suffix = null;
        Integer index = fileName.lastIndexOf(".");
        if (index + 1 <= fileName.length()) {
            suffix = fileName.substring(index + 1,
                    fileName.length());
        }
        List<List<String>> dataset = new ArrayList<List<String>>();

        Sheet sheet = null;
        Row row = null;
        Workbook wb = null;
        try {
            if ("xls".equals(suffix)) {
                wb = new HSSFWorkbook(is);
            } else if ("xlsx".equals(suffix) || "xlsm".equals(suffix)) {
                wb = new XSSFWorkbook(is);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);
        // 得到总行数
        int rowNum = sheet.getLastRowNum();
        row = sheet.getRow(dataStartRow);
        int colNum = row.getPhysicalNumberOfCells();
        List<String> rowData = null;
        for (int i = dataStartRow; i <= rowNum; i++) {
            row = sheet.getRow(i);
            if (row == null) {
                break;
            }
            rowData = new ArrayList<String>();
            int j = 0;
            while (j < colNum) {
                String s = getCellValue(row.getCell(j));
                rowData.add(s == null ? null : s.trim());
                j++;
            }
            dataset.add(rowData);
        }
        return dataset;
    }


    @SuppressWarnings("static-access")
    private static String getCellValue(Cell hssfCell) {
        if (hssfCell.getCellType() == hssfCell.CELL_TYPE_BOOLEAN) {
            return String.valueOf(hssfCell.getBooleanCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_STRING) {
            return String.valueOf(hssfCell.getStringCellValue());
        } else if (hssfCell.getCellType() == hssfCell.CELL_TYPE_NUMERIC) {
            return String.valueOf(hssfCell.getNumericCellValue());
        } else {
            return String.valueOf(hssfCell.getStringCellValue());
        }
    }

    /**
     * 将字符串数据转换为javabean
     *
     * @param row
     * @param clazz
     * @param fields
     * @param rowIndex
     * @param dateFormatter
     * @return
     */
    @SuppressWarnings({"unchecked", "rawtypes"})
    private static <T> T parseRowToBean(List<String> row, Class clazz,
                                        Field[] fields, int rowIndex, String dateFormatter) {
        T bean = null;
        try {
            bean = (T) clazz.newInstance();
        } catch (Exception e) {
            e.printStackTrace();
            throw new BusinessException(ERR_IMPORT_EXCEL,
                    "实例化模板类发生异常。");
        }
        if (row != null && row.size() > 0) {
            // 空行结束
            boolean isBlankRow = true;
            for (String s : row) {
                if (StringUtils.isNotBlank(s)) {
                    isBlankRow = false;
                    break;
                }
            }
            if (isBlankRow) {
                return null;
            }
            if (row.size() > fields.length) {
                throw new BusinessException(
                        ERR_IMPORT_EXCEL, "excel中的值过多，行数："
                        + (rowIndex + 1));
            }
            SimpleDateFormat sdf = new SimpleDateFormat(dateFormatter);
            Field field = null;
            String fieldGenericType = null;
            Object value = null;
            String valueStr = null;
            for (int i = 0; i < row.size(); i++) {
                field = fields[i];
                fieldGenericType = field.getGenericType().toString();
                valueStr = row.get(i);
                try {
                    if (valueStr == null) {
                        value = null;
                    } else if (fieldGenericType
                            .equals("class java.lang.String")) {
                        // String
                        value = String.valueOf(valueStr);
                    } else if (fieldGenericType.equals("class java.lang.Short")) {
                        // Short
                        value = Short.parseShort(valueStr);
                    } else if (fieldGenericType
                            .equals("class java.lang.Integer")) {
                        // Integer
                        value = new BigDecimal(valueStr).intValue();
                    }
                    if (fieldGenericType.equals("class java.lang.Double")) {
                        // Double
                        value = Double.parseDouble(valueStr);
                    } else if (fieldGenericType
                            .equals("class java.math.BigDecimal")) {
                        // BigDecimal
                        value = BigDecimal.valueOf(Double.valueOf(valueStr));
                    } else if (fieldGenericType
                            .equals("class java.lang.Boolean")
                            || fieldGenericType.equals("boolean")) {
                        // Boolean
                        value = Boolean.parseBoolean(valueStr);
                    } else if (fieldGenericType.equals("class java.util.Date")) {
                        // Date
                        value = sdf.parse(valueStr);
                    }
                } catch (NumberFormatException e) {
                    e.printStackTrace();
                    throw new BusinessException(
                            ERR_IMPORT_EXCEL, "数据格式错误，行："
                            + rowIndex + "，列：" + (i + 1));
                } catch (ParseException e) {
                    e.printStackTrace();
                    throw new BusinessException(
                            ERR_IMPORT_EXCEL, "数据格式错误，行："
                            + rowIndex + "，列：" + (i + 1));
                }
                try {
                    BeanUtils.setProperty(bean, field.getName(), value);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        }
        return bean;
    }

}
