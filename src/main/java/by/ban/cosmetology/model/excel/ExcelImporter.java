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
import by.ban.cosmetology.model.excel.dataImport.MaterialRowColInfo;
import by.ban.cosmetology.model.excel.dataImport.ServicesRowColInfo;
import by.ban.cosmetology.service.MaterialsService;
import by.ban.cosmetology.service.ServicesService;
import by.ban.cosmetology.service.UnitsService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
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

    public List<String> importExcelFile(String fileType, File file, ExcelFile excelFile) throws Exception {

        //Map<String, List> errors = new HashMap<>();
        List<String> errors = new LinkedList<>();
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
                    /*List<String> list = new ArrayList<>();
                    list.add("/errors/importNotSupported");*/
                    errors.add("Файлы с расширением \"" + extension
                            + "\" не поддерживаются");
                    return errors;
                }
            }
        } catch (IOException iOException) {
            System.out.println("Ошибка чтения файла: " + file.getAbsolutePath());
            iOException.printStackTrace();
        } catch (InvalidFormatException invalidFormatException) {
            System.out.println("Не верный формат файла: " + file.getAbsolutePath());
            invalidFormatException.printStackTrace();
        }

        errors.add("Импорт прошел без ошибок.");
        try {
            switch (fileType) {
                case "materials": {
                    errors = importMaterials(document.getSheetAt(0), excelFile.getMaterialRowColInfo());
                    break;
                }
                case "services": {
                    errors = importServices(document.getSheetAt(0), excelFile.getServicesRowColInfo());
                    break;
                }
            }
            //errors = importDataInDB(document, excelFile.getServicesRowColInfo(), excelFile.getMaterialRowColInfo());
        } catch (Exception e) {
            throw e;
        } finally {
            file.delete();
        }

        return errors;
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
        Sheet sheet = document.getSheet("06,17");

        List<String> servicesErrors = importServices(sheet, serviceMap);
        List<String> materialsErrors = new LinkedList<>();
        try {
            materialsErrors = importMaterials(sheet, materialMap);
        } catch (Exception ex) {
            Logger.getLogger(ExcelImporter.class.getName()).log(Level.SEVERE, null, ex);
        }

        errors.put("services", servicesErrors);
        errors.put("materials", materialsErrors);

        return errors;
    }

    private List<String> importServices(Sheet sheet, ServicesRowColInfo serviceMap) {

        Map<String, Services> services = new HashMap<>();
        List<String> errors = new ArrayList<>();
        Row curRow;

        for (int i = serviceMap.getRowStartData() - 1, end = serviceMap.getRowEndData();
             i < end;
             i++) {
            curRow = sheet.getRow(i);
            boolean costCellIsEmpty = false;

            //Если в текущей строке нет ни стоимости для граждан рб, 
            //ни стоимости для иностранцев, то переходим на другую строку
            Cell nameCell = curRow.getCell(serviceMap.getName() - 1);
            Cell costCell = curRow.getCell(serviceMap.getPrice() - 1);
            if (isEmpty(nameCell) || !isStringCell(nameCell)) {
                continue;
            }
            if (isEmpty(costCell) || !isNumberCell(costCell)) {
                costCellIsEmpty = true;
                costCell = curRow.getCell(serviceMap.getPriceFF() - 1);
                if (isEmpty(costCell) || !isNumberCell(costCell)) {
                    continue;
                }
            }

            String name;
            Double cost;

            //Проверяет соответствие данных в ячейках необходимым форматам,
            //в случае несоответствия пропускает строку и добавляет данные об ошибке в список ошибок,
            //для вывода информации о не импортированных услугах
            if (isStringCell(nameCell) && isNumberCell(costCell)) {
                name = nameCell.getStringCellValue();
                cost = costCell.getNumericCellValue();
            } else {
                String error = "Ошибка формата ячейки. Услуга из строки №" + (i - 1) + " не была записана!";

                System.out.println(error);
                errors.add(error);

                continue;
            }

            Services serviceExc = services.get(name);
            //Если в services не содержится услуга с заданным именем
            //то содается новая услуга для добавления в services
            if (name == null) {
                serviceExc = new Services(name);
            }

            //Если costCellIsEmpty = true значит стоимость для граждан РБ не была указана в текущей строке,
            //а значит в вставляемый сервис стоит добавить стоимость для иностранцев
            if (costCellIsEmpty) {
                serviceExc.setCostFF(cost);
            } else {
                serviceExc.setCost(cost);
            }
            services.put(serviceExc.getName(), serviceExc);

        }

        servicesService.importServices(services);

        return errors;
    }

    private List<String> importMaterials(Sheet sheet, MaterialRowColInfo materialMap) throws Exception {
        Map<String, Materials> materials = new HashMap<>();
        List<String> errors = new ArrayList<>();
        Row curRow;
        Materials material = null;

        for (int i = materialMap.getRowStartData() - 1, end = materialMap.getRowEndData();
             i < end; i++) {
            try {
                material = new Materials();
                curRow = sheet.getRow(i);

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
                Cell numberCell = curRow.getCell(materialMap.getItemNumber() - 1);
                if (isNumberCell(numberCell)) {
                    material.setNumber(((Double) numberCell.getNumericCellValue()).intValue());
                } else {
                    errNumber = "Номер должен быть целым числом";
                }
                if (isStringCell(nameCell)) {
                    material.setName(nameCell.getStringCellValue());
                } else {
                    errName = "Имя должно быть строкой";
                }
                if (isNumberCell(costCell)) {
                    BigDecimal decimal = new BigDecimal(costCell.getNumericCellValue());
                    material.setCost(decimal.round(new MathContext(2)).doubleValue());
                } else {
                    errCost = "Стоимость должна быть числом";
                }

                Cell unitCell = curRow.getCell(materialMap.getUnit() - 1);
                Cell countCell = curRow.getCell(materialMap.getCount() - 1);

                //Импорт в метариал единицы измерения
                if (isEmpty(unitCell) || !isStringCell(unitCell)) {
                    material.setUnit(new Units(1));
                    errUnit = "Не задана единица измерения";
                } else {
                    String sUnit = unitCell.getStringCellValue().trim();
                    Units unit = unitsService.getUnit(sUnit);
                    /*if (unit == null) {
                    material.setUnit(new Units(1));
                    errUnit = "Нет такой единицы измерения: " + sUnit;
                } else {
                    material.setUnit(unit);
                }*/
                    material.setUnit(unit);
                }
                //Импорт в материал количества
                if (isEmpty(countCell) || !isNumberCell(countCell)) {
                    material.setCount(10.0);
                    errCount = "Не задано кол-во. По умолчанию равняется 10";
                } else {
                    Double count = countCell.getNumericCellValue();
                    material.setCount(count);
                }

                String error = "[ERROR] Материал не был создан. Строка №" + (i + 1) + ". Ошибки:";
                if (errName.isEmpty() && errCost.isEmpty()) {
                    error = "Материал создан с ошибками. Строка №" + (i + 1) + ". Ошибки:\n";
                    error += errUnit + "\n";
                    error += errCount + "\n";
                    materialsService.addMaterial(material);
                } else {
                    error += errNumber + "\n";
                    error += errName + "\n";
                    error += errCost + "\n";
                    error += errUnit + "\n";
                    error += errCount + "\n";
                }

                System.out.println(error);
                errors.add(error);
                //materials.put(material.getName(), material);
            } catch (Exception e) {
                throw new Exception("Ошибка импорта материала: " + material + "\nВ строке - " + (i + 1) + ".\n"
                        + e.getMessage(), e);
            }
        }
        //materialsService.importMaterials(materials);

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
    
    private void evalFormula(Cell cell) {
        
    }
}
