package com.joycity.joyclub.act.excel;


import com.joycity.joyclub.act.constant.ActOrderStatus;
import com.joycity.joyclub.act.modal.ActOrderForBack;
import com.joycity.joyclub.commons.constant.CrudType;
import com.joycity.joyclub.commons.utils.DateTimeUtil;
import com.joycity.joyclub.commons.utils.ExcelUtility;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.List;

public class ActFileExportUtils {


    public static void createActOrderExcel(HSSFWorkbook workbook, List<ActOrderForBack> actOrderForBacks) {
        HSSFSheet sheet = ExcelUtility.createSheet(workbook, ActExcelConstants.SHEET_TITLE);
        CellStyle titleCellStyle = ExcelUtility.getCellStyle(workbook, true);
        int rowIndex = 0;
        int columnIndex = 0;

        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, ActExcelConstants.LABEL_DATE, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, ActExcelConstants.LABEL_NAME, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, ActExcelConstants.LABEL_TEL, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, ActExcelConstants.LABEL_ORDER, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, ActExcelConstants.LABEL_STATUS, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, ActExcelConstants.LABEL_MOENY, titleCellStyle);

        rowIndex = 1;
        CellStyle rowCellStyle = ExcelUtility.getCellStyle(workbook, CrudType.UPDATE,false);

        for (ActOrderForBack actOrderForBack : actOrderForBacks) {
            columnIndex = 0;
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, actOrderForBack.getPayTime() != null ? DateTimeUtil.formatYYYYMMDDHHMMSS(actOrderForBack.getPayTime()) : null, rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, actOrderForBack.getClientName() , rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, actOrderForBack.getClientPhone() , rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, actOrderForBack.getCode() , rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, ActOrderStatus.actOrderStatusMap.get(actOrderForBack.getStatus()) , rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, actOrderForBack.getMoneySum().toString() , rowCellStyle);
            rowIndex ++;
        }


    }


	
}
