/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel;

import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.model.Providedservices;
import by.ban.cosmetology.model.Usedmaterials;
import java.io.IOException;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

/**
 *
 * @author dazz
 */
public class ExcelInvoice extends AbstractXlsxView {
    private Workbook wb;

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook workbook, HttpServletRequest request, HttpServletResponse response) throws Exception {
        
        Orders order = (Orders) map.get("order");
        //New Excel sheet
        Sheet excelSheet = workbook.getSheetAt(0);
        //Excel file name change
        response.setHeader("Content-Disposition", "attachment; filename=" + order.getCustomer().toString() + ".xlsx");
        
        //ФИО клиента
        setCellValue(new CellReference("Sheet1!K7"), order.getCustomer().toString());
        setCellValue(new CellReference("Sheet1!D24"), order.getCustomer().toString());
        //стоимость услуг
        Double psSum = 0.0;
        for (Providedservices ps : order.getProvidedservicesList()) {
            psSum += ps.getCost();
        }
        setCellValue(new CellReference("Sheet1!AW14"), psSum);
        //стоимость материалов
        Double umSum = 0.0;
        for (Usedmaterials um : order.getUsedmaterialsList()) {
            umSum += um.getCost();
        }
        setCellValue(new CellReference("Sheet1!AW15"), umSum);
        //сумма к оплате
        setCellValue(new CellReference("Sheet1!AW16"), psSum + umSum);
        //ФИО сотрудника
        setCellValue(new CellReference("Sheet1!S18"), 
                "косметик " + order.getManager().toString());
        setCellValue(new CellReference("Sheet1!AL27"), order.getManager().toString());
        //Дата
        setCellValue(new CellReference("Sheet1!A18"), new Date());
        setCellValue(new CellReference("Sheet1!A35"), new Date());
    }

    @Override
    protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
        try {
            wb = new XSSFWorkbook("C:\\excelTamplates\\Чек на оплату.xlsx");
        } catch (IOException ex) {
            Logger.getLogger(ExcelInvoice.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return wb;
    }
    
    protected void setCellValue(CellReference cellRef, String value) {
        wb.getSheet(cellRef.getSheetName()).getRow(cellRef.getRow()).getCell(cellRef.getCol()).setCellValue(value);
    }
    
    protected void setCellValue(CellReference cellRef, Double value) {
        wb.getSheet(cellRef.getSheetName()).getRow(cellRef.getRow()).getCell(cellRef.getCol()).setCellValue(value);
    }
    
    protected void setCellValue(CellReference cellRef, Date value) {
        wb.getSheet(cellRef.getSheetName()).getRow(cellRef.getRow()).getCell(cellRef.getCol()).setCellValue(value);
    }
}
