/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller.forImportData;

import by.ban.cosmetology.controller.GlobalExceptiosHandler;
import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.model.Services;
import by.ban.cosmetology.model.Units;
import by.ban.cosmetology.model.excel.ExcelImporter;
import by.ban.cosmetology.model.excel.dataImport.ExcelFile;
import by.ban.cosmetology.model.excel.dataImport.MaterialRowColInfo;
import by.ban.cosmetology.model.excel.dataImport.ServicesRowColInfo;
import by.ban.cosmetology.service.MaterialsService;
import by.ban.cosmetology.service.ServicesService;
import by.ban.cosmetology.service.UnitsService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.util.LinkedList;
import java.util.List;
import javax.validation.Valid;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author dazz
 */
@Controller
@RequestMapping("/import")
public class FileUploadController {

    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptiosHandler.class);

    @Autowired
    private ServicesService servicesServ;
    @Autowired
    private MaterialsService materialsServ;
    @Autowired
    private UnitsService unitsServ;
    @Autowired
    private ExcelImporter excelImporter;

    @RequestMapping(value = "/createPage")
    public String createPage(Model model) {

        ServicesRowColInfo sdi = new ServicesRowColInfo(6, 612, 2, 6, 7);
        MaterialRowColInfo mdi = new MaterialRowColInfo(4, 95, 2, 3, 4, 5, 12, 11);
        ExcelFile ef = new ExcelFile(sdi, mdi);

        model.addAttribute("excelFile", ef);

        return "/excel/importDataForDB";
    } 

    @RequestMapping(value = "/uploadAndImport", method = RequestMethod.POST)
    public String uploadAndImport(@ModelAttribute @Valid ExcelFile excelFile,
                                  @RequestParam(required = false, name = "fileType") String fileType,
                                  Model model) {

        List<String> errors = new LinkedList<>();
        MultipartFile mpFile = excelFile.getMpFile();
        MaterialRowColInfo mRowCol = excelFile.getMaterialRowColInfo();
        File file;

        if (!mpFile.isEmpty()) {

            try {
                byte[] fileBytes = mpFile.getBytes();
                String rootPath = System.getProperty("catalina.home");
                System.out.println("Server rootPath: " + rootPath);
                System.out.println("File original name: " + mpFile.getOriginalFilename());
                System.out.println("File content type: " + mpFile.getContentType());

                String pathEF = rootPath + File.separator + mpFile.getOriginalFilename();
                file = new File(pathEF);
                try (BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file))) {
                    stream.write(fileBytes);
                }

                //запись из эксэль в базу
                errors = excelImporter.importExcelFile(fileType, file, excelFile);
                /*if (fileType.equals("materials")) {
                    Workbook wb = new XSSFWorkbook(file);
                    Sheet sheet = wb.getSheetAt(0);

                    for (int i = mRowCol.getRowStartData(); i <= mRowCol.getRowEndData(); i++) {
                        try {
                            Row row = sheet.getRow(i);
                            Materials material = new Materials();
                            material.setNumber(
                                    ((Double) row.getCell(mRowCol.getItemNumber()).getNumericCellValue())
                                            .intValue());
                            material.setName(row.getCell(mRowCol.getName()).getStringCellValue());

                            String unitName = row.getCell(mRowCol.getUnit()).getStringCellValue().trim();
                            material.setUnit(unitsServ.getUnit(unitName));
                            material.setCost(row.getCell(mRowCol.getPrice()).getNumericCellValue());
                            material.setCount(0.0);
                            material.setForDelete(false);
                            materialsServ.addMaterial(material);
                        } catch (Exception e) {
                            LOGGER.info("Ошибка при извлечении из строки " + i
                                    + ".\nОбъект не был создан.\n" + e.getMessage());
                        }
                    }
                } else if (fileType.equals("services")) {
                    
                }*/

                System.out.println("File is saved under: " + pathEF);
            } catch (Exception e) {
                e.printStackTrace();
                errors.add("Перенос данных не удался: " + e.getMessage());
            }
        } else {
            errors.add("File upload is failed: File is empty");
        }

        model.addAttribute("errors", errors);
        return "/excel/importDataForDB";
    }
}
