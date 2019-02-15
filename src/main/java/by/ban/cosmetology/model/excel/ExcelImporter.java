/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel;

import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.model.Services;
import by.ban.cosmetology.model.Units;
import by.ban.cosmetology.model.excel.dataImport.ExcelFile;
import by.ban.cosmetology.model.excel.dataImport.layouts.MaterialRowColInfo;
import by.ban.cosmetology.model.excel.dataImport.layouts.ServicesRowColInfo;
import by.ban.cosmetology.service.MaterialsService;
import by.ban.cosmetology.service.ServicesService;
import by.ban.cosmetology.service.UnitsService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 *
 * @author dazz
 */
@Component
public class ExcelImporter {

    @Autowired
    private ServicesService servicesService;
    @Autowired
    private MaterialsService materialsService;
    @Autowired
    private UnitsService unitsService;

    List<String> errors = new LinkedList<>();

    public List<String> importExcelFile(String fileType, File file, ExcelFile excelFile) throws Exception {
        Workbook document = getWorkbook(file);
        if (document == null) {
            return errors;
        }

        errors.add("Импорт прошел без ошибок.");
        try {
            switch (fileType) {
                case "materials": {
                    errors = importMaterials(document,
                            excelFile.getMaterialRowColInfo());
                    break;
                }
                case "services": {
                    errors = importServices(document,
                            excelFile.getServicesRowColInfo());
                    break;
                }
            }
        } catch (Exception e) {
            throw e;
        } finally {
            if (document != null) {
                document.close();
            }
            file.delete();
        }

        return errors;
    }

    private Workbook getWorkbook(File file) {
        String extension = getExtension(file);
        Workbook document = null;

        try {
            switch (extension) {
                case "xls": {
                    document = getXLSWB(file);
                    break;
                }
                case "xlsx": {
                    document = getXLSXWB(file);
                    break;
                }
                case "xlsm": {
                    document = getXLSMWB(file);
                    break;
                }
                default: {
                    errors.add("Файлы с расширением \"" + extension
                            + "\" не поддерживаются");
                    return document;
                }
            }
        } catch (IOException iOException) {
            errors.add("Ошибка чтения файла: " + file.getAbsolutePath());
            iOException.printStackTrace();
        } catch (InvalidFormatException invalidFormatException) {
            errors.add("Не верный формат файла: " + file.getAbsolutePath());
            invalidFormatException.printStackTrace();
        }

        return document;
    }

    private HSSFWorkbook getXLSWB(File file) throws IOException, InvalidFormatException {
        System.out.println("Import data getXLS WorkBook method called");

        return new HSSFWorkbook(new FileInputStream(file));
    }

    private XSSFWorkbook getXLSXWB(File file) throws IOException, InvalidFormatException {
        System.out.println("Import data getXLSX WorkBook method called");

        return new XSSFWorkbook(file);
    }

    private Workbook getXLSMWB(File file) {
        System.out.println("Import data getXLSM WorkBook method called");
        throw new UnsupportedOperationException("Not supported yet.");
    }

    private String getExtension(File file) {
        String path = file.getPath();
        int index = path.lastIndexOf(".");

        return index == -1 ? null : path.substring(index + 1);
    }

    private Map<String, List> importDataInDB(Workbook document, ServicesRowColInfo serviceMap, MaterialRowColInfo materialMap) {
        Map<String, List> errors = new HashMap<>();

        List<String> servicesErrors = importServices(document, serviceMap);
        List<String> materialsErrors = new LinkedList<>();
        try {
            materialsErrors = importMaterials(document, materialMap);
        } catch (Exception ex) {
            Logger.getLogger(ExcelImporter.class.getName()).log(Level.SEVERE, null, ex);
        }

        errors.put("services", servicesErrors);
        errors.put("materials", materialsErrors);

        return errors;
    }

    private List<String> importServices(Workbook w, ServicesRowColInfo serviceMap) {

        Sheet sheet = w.getSheet(serviceMap.getSheetName());
        if (sheet == null) {
            errors.add("Не найдена страница с именем - \"" + serviceMap.getSheetName() + "\"");
            return errors;
        }

        int nStartRow = serviceMap.getRowStartData() - 1;
        int nEndRow = serviceMap.getRowEndData() - 1;
        Row curRow;
        int addedCount = 0;

        for (int i = nStartRow; i <= nEndRow; i++) {
            curRow = sheet.getRow(i);

            StringBuilder errorBuilder = new StringBuilder();
            Cell nameCell = curRow.getCell(serviceMap.getName() - 1);
            Cell priceCell = curRow.getCell(serviceMap.getPrice() - 1);
            Cell priceFFCell = curRow.getCell(serviceMap.getPriceFF() - 1);
            Cell numberPLCell = null;
            if (serviceMap.getNumberInPL() > 0) {
                numberPLCell = curRow.getCell(serviceMap.getNumberInPL() - 1);
            }

            //Т.к. в прейскурантах могут быть подпункты, а названия у подпунктов 
            //не отличаются и записаны в 1 строке, то пытаемся найти название в 
            //вышестоящих подпунктах
            if (isEmpty(nameCell) && !isEmpty(numberPLCell)) {
                nameCell = getNameCell(nameCell, numberPLCell);
            }

            //Если в текущей строке нет ни названия услуги, ни стоимости для граждан рб, 
            //ни стоимости для иностранцев, то переходим на другую строку
            if (isEmpty(nameCell) && isEmpty(priceCell) && isEmpty(priceFFCell)) {
                continue;
            }
            //Проверяем правильность данных в строке прайслиста
            if (isEmpty(nameCell)) {
                errorBuilder.append("Ячейка имени пуста.\n");
            }
            if (isEmpty(priceCell)) {
                errorBuilder.append("Ячейка стоимости пуста.\n");
            }
            if (isEmpty(priceFFCell)) {
                errorBuilder.append("Ячейка стоимости для ИГ пуста.\n");
            }
            if (!isStringCell(nameCell) && !isFormulaCell(nameCell)) {
                errorBuilder.append("Ячейка имени не является строкой.\n");
            }
            if (!isNumberCell(priceCell)) {
                errorBuilder.append("Ячейка стоимости не является числом.\n");
            }
            if (!isNumberCell(priceFFCell)) {
                errorBuilder.append("Ячейка стоимости для ИГ не является числом.\n");
            }

            if (errorBuilder.length() != 0) {
                errors.add("[ERROR] Услуга из строки №" + (i + 1) + " не была записана. Ошибки:\n" + errorBuilder.toString());
                continue;
            }

            //Создание обновление услуги
            Services service = new Services();

            if (!isEmpty(numberPLCell) && isStringCell(numberPLCell)) {
                String number = numberPLCell.getStringCellValue();
                if (number.endsWith(".")) {
                    number = deleteEndDots(number);
                }
                service.setNumber(number);
            }
            service.setName(nameCell.getStringCellValue());
            service.setCost(roundRfDigits(priceCell.getNumericCellValue(), 2));
            service.setCostFF(roundRfDigits(priceFFCell.getNumericCellValue(), 2));
            service.setForDelete(false);

            servicesService.addOrUpdateService(service);
            addedCount++;
        }

        errors.add("Всего было добавлено/обновлено " + addedCount + " услуг(а).");

        return errors;
    }

    private List<String> importMaterials(Workbook w, MaterialRowColInfo materialMap) throws Exception {
        Sheet sheet = w.getSheet(materialMap.getSheetName());
        Row curRow;
        Materials material = null;
        int addedCount = 0;

        for (int i = materialMap.getRowStartData() - 1, end = materialMap.getRowEndData();
             i < end; i++) {
            try {
                material = new Materials();
                material.setUnit(new Units(1));
                material.setCount(10.0);
                curRow = sheet.getRow(i);

                if (materialMap.getName() <= 0 && materialMap.getPrice() <= 0) {
                    errors.add("[ERROR] Укажите столбец имени и стоимости материалов."
                            + " Они должны быть больше 0.");
                    break;
                }
                //Если в текущей строке нет ни стоимости для граждан рб, 
                //ни стоимости для иностранцев, то переходим на другую строку
                Cell nameCell = curRow.getCell(materialMap.getName() - 1);
                Cell costCell = curRow.getCell(materialMap.getPrice() - 1);
                if (isEmpty(costCell) || isEmpty(nameCell)) {
                    continue;
                }

                String errNumber = "";
                String errName = "";
                String errCost = "";
                String errUnit = "";
                String errCount = "";

                //Обязательные поля Name и Cost
                //Проверяет соответствие данных в ячейках необходимым форматам,
                //в случае несоответствия добавляет данные об ошибке,
                //для вывода информации о не импортированных материалах
                if (materialMap.getItemNumber() > 0) {
                    Cell numberCell = curRow.getCell(materialMap.getItemNumber() - 1);
                    if (isNumberCell(numberCell)) {
                        material.setNumber(((Double) numberCell.getNumericCellValue()).intValue());
                    } else {
                        errNumber = "Номер должен быть целым числом";
                    }
                }
                if (isStringCell(nameCell)) {
                    material.setName(nameCell.getStringCellValue());
                } else {
                    errName = "Имя должно быть строкой";
                }
                if (isNumberCell(costCell)) {
                    /*BigDecimal decimal = new BigDecimal(costCell.getNumericCellValue());
                    material.setCost(decimal.round(new MathContext(5)).doubleValue());*/
                    Double cost = costCell.getNumericCellValue();
                    material.setCost(roundRfDigits(cost, 4));
                } else {
                    errCost = "Стоимость должна быть числом";
                }

                //Импорт в метариал единицы измерения
                if (materialMap.getUnit() > 0) {
                    Cell unitCell = curRow.getCell(materialMap.getUnit() - 1);
                    if (isEmpty(unitCell) || !isStringCell(unitCell)) {
                        errUnit = "Не задана единица измерения. Поставлено значение по умолчанию.";
                    } else {
                        String sUnit = unitCell.getStringCellValue().trim();
                        Units unit = unitsService.getUnit(sUnit);
                        material.setUnit(unit);
                    }
                }
                //Импорт в материал количества
                if (materialMap.getCount() > 0) {
                    Cell countCell = curRow.getCell(materialMap.getCount() - 1);
                    if (isEmpty(countCell) || !isNumberCell(countCell)) {
                        errCount = "Не задано кол-во. По умолчанию равняется 10";
                    } else {
                        Double count = countCell.getNumericCellValue();
                        material.setCount(count);
                    }
                }

                //заполнение сотрудника, который пользуется материалом
                material.setManager(materialMap.getManager());

                StringBuilder error = new StringBuilder();
                if (errName.isEmpty() && errCost.isEmpty()) {
                    if (!errUnit.isEmpty() && !errCount.isEmpty()) {
                        error.append("[INFO] Материал из строки ").append(i + 1).append(" записан с ошибками. Ошибки:\n");
                        error.append(errUnit).append("\n");
                        error.append(errCount).append("\n");
                    }
                    materialsService.addOrUpdateMaterial(material);
                    addedCount++;
                } else {
                    error.append("[ERROR] Материал из строки №").append(i + 1).append(" не был записан. Ошибки:\n");
                    error.append(errNumber).append("\n");
                    error.append(errName).append("\n");
                    error.append(errCost).append("\n");
                    error.append(errUnit).append("\n");
                    error.append(errCount).append("\n");
                }

                if (!error.toString().isEmpty()) {
                    errors.add(error.toString());
                }
            } catch (Exception e) {
                throw new Exception("Ошибка импорта материала: " + material + "\nВ строке - " + (i + 1) + ".\n"
                        + e.getMessage(), e);
            }
        }

        StringBuilder info = new StringBuilder();
        if (errors.isEmpty()) {
            info.append("Импорт прошел без ошибок.\n");
        }
        errors.add(info
                .append("Всего было добавлено/обновлено ")
                .append(addedCount)
                .append(" материала(ов).")
                .toString()
        );

        return errors;

    }

    private boolean isEmpty(Cell cell) {
        return cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK;
    }

    private boolean isStringCell(Cell cell) {
        return cell.getCellType() == Cell.CELL_TYPE_STRING;
    }

    private boolean isNumberCell(Cell cell) {
        return cell.getCellType() == Cell.CELL_TYPE_NUMERIC || isFormulaCell(cell);
    }

    private boolean isFormulaCell(Cell cell) {
        return cell.getCellType() == Cell.CELL_TYPE_FORMULA;
    }

    private double roundRfDigits(double val, int fractionDigits) {
        DecimalFormat df = new DecimalFormat("#0.#");
        DecimalFormatSymbols decimalFormatSymbols = df.getDecimalFormatSymbols();
        decimalFormatSymbols.setDecimalSeparator('.');
        df.setDecimalFormatSymbols(decimalFormatSymbols);
        df.setMaximumFractionDigits(fractionDigits);

        return Double.parseDouble(df.format(val));
    }

    private Cell getNameCell(Cell oldNameCell, Cell numPLCell) {
        Cell nameCell = null;
        Sheet sheet = oldNameCell.getSheet();
        int iCurRow = oldNameCell.getRow().getRowNum();

        while (isEmpty(nameCell) && iCurRow > 0) {
            nameCell = sheet.getRow(--iCurRow).getCell(oldNameCell.getColumnIndex());
        }

        String groupNumPL = numPLCell.getStringCellValue();
        int index;
        do {
            index = groupNumPL.lastIndexOf(".");
            if (index == -1) {
                break;
            }
            groupNumPL = groupNumPL.substring(0, index);
        } while (groupNumPL.length() == index);

        String curRowNumPL = sheet.getRow(iCurRow).
                getCell(numPLCell.getColumnIndex()).getStringCellValue();
        if (curRowNumPL.startsWith(groupNumPL)) {
            return nameCell;
        }

        return oldNameCell;
    }

    private String deleteEndDots(String s) {
        while (s.endsWith(".")) {
            s = s.substring(0, s.length() - 1);
        }

        return s;
    }
}
