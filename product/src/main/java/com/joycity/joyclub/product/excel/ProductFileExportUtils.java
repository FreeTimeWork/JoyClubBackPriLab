package com.joycity.joyclub.product.excel;


import com.joycity.joyclub.commons.constant.CrudType;
import com.joycity.joyclub.commons.utils.DateTimeUtil;
import com.joycity.joyclub.commons.utils.ExcelUtility;
import com.joycity.joyclub.product.constant.ProductOrderStatus;
import com.joycity.joyclub.product.modal.ProductOrderStoreInfo;
import org.apache.commons.collections.CollectionUtils;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.CellStyle;

import java.util.List;

public class ProductFileExportUtils {


    public static void createProductOrderExcel(HSSFWorkbook workbook, List<ProductOrderStoreInfo> productOrderStoreInfos) {
        HSSFSheet sheet = ExcelUtility.createSheet(workbook, ProductExcelConstants.SHEET_TITLE);
        CellStyle titleCellStyle = ExcelUtility.getCellStyle(workbook, true);
        int rowIndex = 0;
        int columnIndex = 0;

        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, ProductExcelConstants.LABEL_DATE, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, ProductExcelConstants.LABEL_NAME, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, ProductExcelConstants.LABEL_TEL, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, ProductExcelConstants.LABEL_ORDER, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, ProductExcelConstants.LABEL_STATUS, titleCellStyle);
        ExcelUtility.createCell(sheet, rowIndex, columnIndex++, ProductExcelConstants.LABEL_MONENY, titleCellStyle);

        rowIndex = 1;
        CellStyle rowCellStyle = ExcelUtility.getCellStyle(workbook, CrudType.UPDATE,false);

        for (ProductOrderStoreInfo info : productOrderStoreInfos) {
            columnIndex = 0;
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, info.getMainOrder().getPayTime() != null ? DateTimeUtil.formatYYYYMMDDHHMMSS(info.getMainOrder().getPayTime()) : null, rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, info.getClientName() , rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, info.getClientPhone() , rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, info.getMainOrder().getCode() , rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, ProductOrderStatus.productOrderStatusMap.get(info.getStatus()) , rowCellStyle);
            ExcelUtility.createCell(sheet, rowIndex, columnIndex++, info.getMoneySum().toString() , rowCellStyle);
            rowIndex ++;
        }


    }


	
}
