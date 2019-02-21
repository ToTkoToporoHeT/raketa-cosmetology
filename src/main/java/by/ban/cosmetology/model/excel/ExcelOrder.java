/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel;

import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.model.Providedservices;
import by.ban.cosmetology.service.Utility.Transliterator;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;

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
            SimpleDateFormat dateNumFormat = new SimpleDateFormat("dd.MM.YYYY");
            setCellValue("I4", date);
            String strDate = sdf.format(date);
            setCellValue("A59", strDate);
            setCellValue("F59", strDate);
            setCellValue("I63", strDate);
            //ФИО клиента
            String fio = order.getCustomer().toString();
            replaceTextInCell("A6", "FIO", fio);
            setCellValue("F49", fio);
            setCellValue("I58", "/" + fio);
            //адресс клиента
            setCellValue("F51", order.getCustomer().getAddressId().toString());
            //Договор заполнил
            setCellValue("E63", order.getCustomer().toString());

            //заполнение табличной части
            CellReference firstTableCell = new CellReference("A12");
            int curRowNum = firstTableCell.getRow();
            int curColNum = firstTableCell.getCol();
            Integer rowIndex = 0;
            Double totalCost = 0.0;
            if (order.getProvidedservicesList().size() - 1 > 0) {
                sheet.shiftRows(curRowNum + 1, sheet.getLastRowNum(),
                        order.getProvidedservicesList().size() - 1, true, false);
            }
            for (Providedservices ps : order.getProvidedservicesList()) {
                curColNum = firstTableCell.getCol();
                curColNum = setTableCellValue(curRowNum, curColNum, (++rowIndex).toString());
                curColNum = setTableCellValue(curRowNum, curColNum, ps.getService().getNumber());
                int nameCellCol = curColNum;
                curColNum = setTableCellValue(curRowNum, curColNum, ps.getService().getName());
                setRowHeight(curRowNum, nameCellCol);
                curColNum = nextVisibleCellCol(curRowNum, curColNum);
                curColNum = setTableCellValue(curRowNum, curColNum, ps.getRate().doubleValue());
                curColNum = setTableCellValue(curRowNum, curColNum, ps.getCost());
                Double cost = ps.getRate().doubleValue() * ps.getCost();
                setTableCellValue(curRowNum, curColNum, cost);
                totalCost += cost;

                //переход на следующую строку
                curRowNum++;
            }
            setCellValue(curRowNum, curColNum, totalCost);

            //Смена имени файла
            
            response.setHeader("Content-Disposition", "attachment; filename="
                    + "Dogovor "
                    + Transliterator.translit(order.getCustomer().toString())
                    + " " + dateNumFormat.format(date) + ".xlsx");
        } catch (Exception ex) {
            Logger.getLogger(ExcelInvoice.class.getName()).log(Level.SEVERE, null, ex);
            /*response.sendError(HttpServletResponse.SC_INTERNAL_SERVER_ERROR,
                    "Ошибка при заполнении шаблона!");*/
        }
    }

    @Override
    protected Workbook createWorkbook(Map<String, Object> model,
                                      HttpServletRequest request) {
        try {
            return getWorkbook("C:\\excelTamplates\\Договор.xlsx", "Sheet1");
        } catch (IOException ex) {
            Logger.getLogger(ExcelOrder.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return null;
    }
    
    private Integer setTableCellValue(int row, int col, Object value) {
        int nextTableCellColumn = nextVisibleCellCol(
                setCellValue(row, col, value)
        );
        
        return nextTableCellColumn;
    }
}
