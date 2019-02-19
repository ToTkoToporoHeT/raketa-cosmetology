/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel;

import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.model.Providedservices;
import by.ban.cosmetology.service.Utility.Transliterator;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author alabkovich
 */
public class ExcelOrder extends ExcelDocument {

    @Override
    protected void buildExcelDocument(Map<String, Object> map,
                                      Workbook wrkbk,
                                      HttpServletRequest request,
                                      HttpServletResponse response) throws Exception {
        try {
            Orders order = (Orders) map.get("order");
            //Дата договора
            Date date = order.getPrepare_date();
            SimpleDateFormat sdf = new SimpleDateFormat("«dd» MMMMM yyyy г.");
            setCellValue("I4", date);
            String strDate = sdf.format(date);
            setCellValue("A59", strDate);
            setCellValue("F59", strDate);
            setCellValue("I63", strDate);
            //ФИО
            String fio = order.getCustomer().toString();
            replaceTextInCell("A6", "FIO", fio);
            setCellValue("F49", fio);
            setCellValue("I58", "/" + fio);
            //Договор заполнил
            setCellValue("E63", order.getCustomer().toString());

            //заполнение табличной части
            CellReference firstTableCell = new CellReference("A12");
            int curRowNum = firstTableCell.getRow();
            int curColNum = firstTableCell.getCol();
            Integer rowIndex = 0;
            Double totalCost = 0.0;
            if (order.getProvidedservicesList().size() - 1 > 0) {
                sheet.shiftRows(curRowNum, sheet.getLastRowNum(),
                        order.getProvidedservicesList().size() - 1, true, false);
            }
            for (Providedservices ps : order.getProvidedservicesList()) {
                curColNum = firstTableCell.getCol();
                setCellValue(curRowNum, curColNum++, (++rowIndex).toString());
                setCellValue(curRowNum, curColNum++, ps.getService().getNumber());
                setCellValue(curRowNum, curColNum++, ps.getService().getName());
                setRowHeight(curRowNum, curColNum - 1);
                curColNum++;
                setCellValue(curRowNum, curColNum++, ps.getRate().doubleValue());
                setCellValue(curRowNum, curColNum++, ps.getCost());//узнать как добавить к стоимости услуги стоимость материалов
                Double cost = ps.getRate().doubleValue() * ps.getCost();
                setCellValue(curRowNum, curColNum, cost);
                totalCost += cost;

                //переход на следующую строку
                curRowNum++;
            }
            setCellValue(curRowNum, curColNum, totalCost);

            //Смена имени файла
            response.setHeader("Content-Disposition", "attachment; filename="
                    + Transliterator.translit(order.getCustomer().toString())
                    + " " + sdf.format(date) + ".xlsx");
        } catch (Exception ex) {
            Logger.getLogger(ExcelInvoice.class.getName()).log(Level.SEVERE, null, ex);
            response.sendError(0, "Ошибка при заполнении шаблона!");            
            wbx.close();
            File file = new File(path);
            file.delete();
        }
    }

    @Override
    protected Workbook createWorkbook(Map<String, Object> model,
                                      HttpServletRequest request) {
        return getWorkbook("C:\\excelTamplates\\Договор.xlsx", "Sheet1");
    }
}
