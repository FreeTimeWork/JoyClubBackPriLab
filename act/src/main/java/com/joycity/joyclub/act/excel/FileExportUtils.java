package com.joycity.joyclub.act.excel;


import com.joycity.joyclub.commons.constant.CrudType;
import com.joycity.joyclub.commons.utils.ExcelUtility;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.List;

public class FileExportUtils {



	public static void createActExcel(HSSFWorkbook workbook, List<EmployeeSalaryBill> salaryBills){
		
		HSSFSheet sheet = ExcelUtility.createSheet(workbook, CMBExcelBankingDirectiveConstants.SHEET_TITLE);
		
		CellStyle titleCellStyle = ExcelUtility.getCellStyle(workbook, true);
        int rowIndex = 0;
        int columnIndex = 0;
        
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, CMBExcelBankingDirectiveConstants.LABEL_CITY, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, CMBExcelBankingDirectiveConstants.LABEL_BUSINESS_AREA, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, CMBExcelBankingDirectiveConstants.LABEL_POSITION, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, CMBExcelBankingDirectiveConstants.LABEL_OPENNING_BANK, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, CMBExcelBankingDirectiveConstants.LABEL_ACCOUNT, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, CMBExcelBankingDirectiveConstants.LABEL_NAME, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, CMBExcelBankingDirectiveConstants.LABEL_FINAL_MONEY, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, CMBExcelBankingDirectiveConstants.LABEL_NOTE, titleCellStyle);
		
        
        rowIndex = 1;
        CellStyle rowCellStyle = ExcelUtility.getCellStyle(workbook, CrudType.UPDATE, false);
    	
        for (EmployeeSalaryBill salaryBill : salaryBills) {
        	columnIndex = 0;
        	
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, salaryBill.getPositionBill().getCityName(), rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, salaryBill.getPositionBill().getBusinessAreaName(), rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, salaryBill.getPositionBill().getPositionDescription(), rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, config.getOpeningBankName(), rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, salaryBill.getBankCardNumber(), rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, salaryBill.getEmployeeName(), rowCellStyle);
            if(separatePaid){
            	ExcelUtility.createCell(sheet, rowIndex, columnIndex++, salaryBill.getSeparatePaidSalaryAmount(), rowCellStyle);
            	
            }else{
            	ExcelUtility.createCell(sheet, rowIndex, columnIndex++, salaryBill.getActualFinalSalaryAmount().subtract(salaryBill.getSeparatePaidSalaryAmount()), rowCellStyle);
            }
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, "", rowCellStyle);
            
            rowIndex ++;
        }
	
	}
	
}
