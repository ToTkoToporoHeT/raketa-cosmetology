/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel;

import java.util.Date;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

/**
 *
 * @author alabkovich
 */
public abstract class ExcelDocument extends AbstractXlsxView{
    protected XSSFWorkbook wbx = new XSSFWorkbook();
    
    protected void setCellValue(CellReference cellRef, String value) {
        wbx.getSheet(cellRef.getSheetName()).getRow(cellRef.getRow()).getCell(cellRef.getCol()).setCellValue(value);
    }
    
    protected void setCellValue(CellReference cellRef, Double value) {
        wbx.getSheet(cellRef.getSheetName()).getRow(cellRef.getRow()).getCell(cellRef.getCol()).setCellValue(value);
    }
    
    protected void setCellValue(CellReference cellRef, Date value) {
        wbx.getSheet(cellRef.getSheetName()).getRow(cellRef.getRow()).getCell(cellRef.getCol()).setCellValue(value);
    }
}
