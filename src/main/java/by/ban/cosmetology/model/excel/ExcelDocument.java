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
import by.ban.cosmetology.model.excel.dataImport.MaterialDataMap;
import by.ban.cosmetology.model.excel.dataImport.ServiceDataMap;
import by.ban.cosmetology.service.MaterialsService;
import by.ban.cosmetology.service.ServicesService;
import by.ban.cosmetology.service.UnitsService;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;

/**
 *
 * @author dazz
 */
public class ExcelDocument {

    @Autowired
    private ServicesService servicesService;
    @Autowired
    private MaterialsService materialsService;
    @Autowired
    private UnitsService unitsService;

    public Map<String, List> importExcelFile(File file, ExcelFile excelFile) {
        
        Map<String, List> errors = new HashMap<>();
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
                    List<String> list = new ArrayList<>();
                    list.add("/errors/importNotSupported");
                    errors.put("notSuported", list);
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

        try {
            errors = importDataInDB(document, excelFile.getServiceDataMap(), excelFile.getMaterilDataMap());
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
        int index = path.indexOf(".");

        return index == -1 ? null : path.substring(index);
    }

    private Map<String, List> importDataInDB(Workbook document, ServiceDataMap serviceMap, MaterialDataMap materialMap) {
        Map<String, List> errors = new HashMap<>();
        Sheet sheet = document.getSheet("06,17");

        List<String> servicesErrors = importServices(sheet, serviceMap);
        List<String> materialsErrors = importMaterials(sheet, materialMap);

        errors.put("services", servicesErrors);
        errors.put("materials", materialsErrors);

        return errors;
    }

    private List<String> importServices(Sheet sheet, ServiceDataMap serviceMap) {

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
            Cell nameCell = curRow.getCell(serviceMap.getName_ColumnNumber() - 1);
            Cell costCell = curRow.getCell(serviceMap.getCost_ColumnNumber() - 1);
            if (isEmpty(nameCell) || !isStringCell(nameCell)) {
                continue;
            }
            if (isEmpty(costCell) || !isNumberCell(costCell)) {
                costCellIsEmpty = true;
                costCell = curRow.getCell(serviceMap.getCostFF_ColumnNumber() - 1);
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

    private List<String> importMaterials(Sheet sheet, MaterialDataMap materialMap) {
        Map<String, Materials> materials = new HashMap<>();
        List<String> errors = new ArrayList<>();
        Row curRow;

        for (int i = materialMap.getRowStartData() - 1, end = materialMap.getRowEndData();
                i < end;
                i++) {
            Materials material = new Materials();
            curRow = sheet.getRow(i);

            //Если в текущей строке нет ни стоимости для граждан рб, 
            //ни стоимости для иностранцев, то переходим на другую строку
            Cell nameCell = curRow.getCell(materialMap.getName_ColumnNumber() - 1);
            Cell costCell = curRow.getCell(materialMap.getCost_ColumnNumber() - 1);
            if (isEmpty(costCell) || isEmpty(nameCell)) {
                continue;
            }

            String errName = "";
            String errCost = "";
            String errUnit = "";
            String errCount = "";

            //Обязательные поля Name и Cost
            //Проверяет соответствие данных в ячейках необходимым форматам,
            //в случае несоответствия добавляет данные об ошибке,
            //для вывода информации о не импортированных материалах
            if (isStringCell(nameCell)) {
                material.setName(nameCell.getStringCellValue());
            } else {
                errName = "Имя должно быть строкой";
            }
            if (isNumberCell(costCell)) {
                material.setCost(costCell.getNumericCellValue());
            } else {
                errCost = "Стоимость должна быть числом";
            }

            Cell unitCell = curRow.getCell(materialMap.getUnit_ColumnNumber() - 1);
            Cell countCell = curRow.getCell(materialMap.getCount_ColumnNumber() - 1);

            //Импорт в метариал единицы измерения
            if (isEmpty(unitCell) || !isStringCell(unitCell)) {
                material.setUnit(new Units(1));
                errUnit = "Не задана единица измерения";
            } else {
                String sUnit = unitCell.getStringCellValue().trim();
                Units unit = unitsService.findUnit(sUnit);
                if (unit == null) {
                    material.setUnit(new Units(1));
                    errUnit = "Нет такой единицы измерения: " + sUnit;
                } else {
                    material.setUnit(unit);
                }
            }
            //Импорт в материал количества
            if (isEmpty(countCell) || !isNumberCell(countCell)) {
                material.setCount(10);
                errCount = "Не задано кол-во. По умолчанию равняется 10";
            } else {
                Integer count = ((Double) countCell.getNumericCellValue()).intValue();
                material.setCount(count);
            }

            String error = "Материал не был создан. Строка №" + (i + 1) + ". Ошибки:\n";
            if (errName.isEmpty() && errCost.isEmpty()) {
                error += errName + "\n";
                error += errCost + "\n";
                error += errUnit + "\n";
                error += errCount + "\n";

                System.out.println(error);
                errors.add(error);

                continue;
            } else {
                error = "Материал создан с ошибками. Строка №" + (i + 1) + ". Ошибки:\n";
                error += errUnit + "\n";
                error += errCount + "\n";
            }

            materials.put(material.getName(), material);
        }

        materialsService.importMaterials(materials);

        return errors;
    }

    private boolean isEmpty(Cell cell) {
        return cell == null || cell.getCellType() == Cell.CELL_TYPE_BLANK;
    }

    private boolean isStringCell(Cell cell) {
        return cell.getCellType() == Cell.CELL_TYPE_STRING;
    }

    private boolean isNumberCell(Cell cell) {
        return cell.getCellType() == Cell.CELL_TYPE_NUMERIC;
    }
}
