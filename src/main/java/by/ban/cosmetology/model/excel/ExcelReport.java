/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel;

import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.service.Utility.Transliterator;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.web.servlet.view.document.AbstractXlsxView;

/**
 *
 * @author alabkovich
 */
public class ExcelReport extends ExcelDocument{

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook wrkbk, HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        List<Orders> orders = (List<Orders>) map.get("ordersForReport");
        
        //заполнение шаблона
        
        //Дата
        SimpleDateFormat sdf = new SimpleDateFormat("dd.MM.yyyy");
        Date date = new Date();
         //Смена имени файла
        hsr1.setHeader("Content-Disposition", "attachment; filename=Отчет (услуги и материалы) от " + sdf.format(date) + ".xlsx");
    }
    
    @Override
    protected Workbook createWorkbook(Map<String, Object> model, HttpServletRequest request) {
        try {
            wbx = new XSSFWorkbook("C:\\excelTamplates\\Отчет (услуги и материалы).xlsx");
        } catch (IOException ex) {
            Logger.getLogger(ExcelInvoice.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return wbx;
    }    
}
