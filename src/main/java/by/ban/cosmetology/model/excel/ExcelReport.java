/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel;

import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.model.Providedservices;
import by.ban.cosmetology.model.Staff;
import by.ban.cosmetology.model.excel.instruments.StaffProvidedServices;
import by.ban.cosmetology.model.excel.instruments.TableRowReportPSUM;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.util.CellReference;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

/**
 *
 * @author alabkovich
 */
public class ExcelReport extends ExcelDocument {

    private static final CellReference FIRST_MANAGER_NAME = new CellReference("Лист1!F5");
    private static final CellReference FIRST_TABEL_CR = new CellReference("Лист1!A9");
    private final Map<String, TableRowReportPSUM> tableRows = new HashMap<>();
    private final Map<Staff, Double> managerTotalUMCost = new HashMap<>();
    private final Map<Staff, StaffProvidedServices> managerTotalPS = new HashMap<>();

    @Override
    protected void buildExcelDocument(Map<String, Object> map, Workbook wrkbk, HttpServletRequest hsr, HttpServletResponse hsr1) throws Exception {
        List<Orders> orders = (List<Orders>) map.get("ordersForReport");
        //List<Staff> staff = (List<Staff>) map.get("staff");
        //заполнение модели строки таблицы оказанных услуг
        fillPSTableRows(orders);

        //заполнение шаблона
        int tabRowCount = 0;
        int nCurRow = FIRST_TABEL_CR.getRow();
        int nCurCol = FIRST_TABEL_CR.getCol();
        for (Map.Entry<String, TableRowReportPSUM> entry : tableRows.entrySet()) {
            double rowCostSumPS = 0;
            TableRowReportPSUM tableRow = entry.getValue();

            setCellValue(nCurRow, nCurCol++, String.valueOf(++tabRowCount));
            setCellValue(nCurRow, nCurCol++, "");
            setCellValue(nCurRow, nCurCol++, tableRow.getPsName());
            setCellValue(nCurRow, nCurCol++, tableRow.getCostFF());
            setCellValue(nCurRow, nCurCol++, tableRow.getCost());

            for (Map.Entry<Staff, StaffProvidedServices> entryCountPS : tableRow.getMapStaffPS().entrySet()) {
                Staff manager = entryCountPS.getKey();
                StaffProvidedServices staffPS = entryCountPS.getValue();

                //заполняем количество оказанных услуг разбивая их по сотрудникам
                setCellValue(FIRST_MANAGER_NAME.getRow(), nCurCol,
                        manager.toString());
                setCellValue(nCurRow, nCurCol++, ((Integer) staffPS.getNumForeignCit()).doubleValue());
                setCellValue(nCurRow, nCurCol++, ((Integer) staffPS.getNumRBCit()).doubleValue());
                setCellValue(nCurRow, nCurCol++, staffPS.getSumCost());
                nCurCol++;

                //подсчет суммарной стоимости оказанных услуг по строке
                rowCostSumPS += staffPS.getSumCost();
                //подсчет сумм оказанных услуг по столбцам
                if (managerTotalPS.containsKey(manager)) {
                    managerTotalPS.get(manager).plusStaffPS(staffPS);
                } else {
                    managerTotalPS.put(manager,
                            staffPS.clone());
                }
            }

            setCellValue(nCurRow, nCurCol, rowCostSumPS);
            //переход на следующую строку таблицы
            nCurRow++;
        }

        //заполнение строки итогов по горизонтали
        nCurRow++;
        nCurCol = FIRST_MANAGER_NAME.getCol();
        double sumUMCost = 0.0;
        double sumPSCost = 0.0;
        for (Map.Entry<Staff, StaffProvidedServices> entry : managerTotalPS.entrySet()) {
            Staff manager = entry.getKey();
            StaffProvidedServices staffPS = entry.getValue();
            int numRBCit = staffPS.getNumRBCit();
            int numForeignCit = staffPS.getNumForeignCit();
            Double costPS = staffPS.getSumCost();
            Double costUM = managerTotalUMCost.get(manager); 
            
            setCellValue(nCurRow + 1, nCurCol, 
                    String.valueOf(numRBCit + numForeignCit));
            setCellValue(nCurRow, nCurCol++, String.valueOf(numForeignCit));
            setCellValue(nCurRow, nCurCol++, String.valueOf(numRBCit));
            setCellValue(nCurRow, nCurCol, costPS + costUM);
            setCellValue(nCurRow, nCurCol++, String.valueOf(costPS));                       
            setCellValue(nCurRow - 1, nCurCol, costUM);
            setCellValue(nCurRow, nCurCol++, costUM);
            
            sumPSCost += costPS;
            sumUMCost += costUM;
        }
        
        //заполнение итогов по документу
        setCellValue(nCurRow, nCurCol++, sumPSCost);
        setCellValue(nCurRow - 1, nCurCol, sumUMCost);
        setCellValue(nCurRow + 1, nCurCol, sumPSCost + sumUMCost);
        setCellValue(nCurRow, nCurCol++, sumUMCost);        
        
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
            sheet = wbx.getSheet("Лист1");
        } catch (IOException ex) {
            Logger.getLogger(ExcelInvoice.class.getName()).log(Level.SEVERE, null, ex);
        }

        return wbx;
    }

    private void fillPSTableRows(List<Orders> orders) {
        for (Orders order : orders) {
            for (Providedservices ps : order.getProvidedservicesList()) {
                String psName = ps.getService().getName();
                double cost = ps.getService().getCost();
                double costFF = ps.getService().getCostFF();

                //создание и заполнение строки таблицы
                TableRowReportPSUM tableRow = new TableRowReportPSUM(psName, cost, costFF);
                if (tableRows.putIfAbsent(psName, tableRow) != null) {
                    tableRows.get(psName);
                }
                    /*//заполнение 0 пустых ячеек
                    for (Staff manager : staff) {
                        tableRow.addStaffCountPS(manager, 0, 0);
                    }*/
                
                //заполнение в строке информации об оказанных услугах: по менеджерам                
                tableRow.addStaffCountPS(order.getManager(),
                        order.getCustomer().isRBcitizen(),
                        ps.getRate(), cost, costFF);
            }

            //считаем суммарную стоимсоть использованных материалов: по сотрудникам
            order.getUsedmaterialsList().forEach((um) -> {
                Staff manager = order.getManager();
                Double costUM = um.getCost() * um.getCount();
                if (managerTotalUMCost.putIfAbsent(manager, costUM) != null) {
                    managerTotalUMCost.merge(manager, costUM, (oldValue, incValue) -> oldValue + incValue);
                }
            });
        }
    }
}
