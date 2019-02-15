/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller.forImportData;

import by.ban.cosmetology.controller.GlobalExceptiosHandler;
import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.model.Services;
import by.ban.cosmetology.model.Staff;
import by.ban.cosmetology.model.Units;
import by.ban.cosmetology.model.excel.ExcelImporter;
import by.ban.cosmetology.model.excel.dataImport.ExcelFile;
import by.ban.cosmetology.model.excel.dataImport.layouts.MaterialRowColInfo;
import by.ban.cosmetology.model.excel.dataImport.layouts.ServicesRowColInfo;
import by.ban.cosmetology.service.MaterialsService;
import by.ban.cosmetology.service.ServicesService;
import by.ban.cosmetology.service.StaffService;
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
    private StaffService staffService;
    @Autowired
    private ExcelImporter excelImporter;

    @ModelAttribute
    public void getExcelFile(Model model) {
        ServicesRowColInfo sdi = new ServicesRowColInfo("косметика 1", 12, 35, 1, 2, 6, 9);
        List<Staff> staff = staffService.getAllStaff();
        MaterialRowColInfo mdi = new MaterialRowColInfo(null, "прайс", 4, 95, 2, 3, 4, 5, 12, 11);
        model.addAttribute("excelFile", new ExcelFile(sdi, mdi));
        model.addAttribute("staff", staff);
    }
    
    @RequestMapping(value = "/createPage")
    public String createPage(Model model) {
        return "/excel/importDataForDB";
    } 

    @RequestMapping(value = "/uploadAndImport", method = RequestMethod.POST)
    public String uploadAndImport(@ModelAttribute @Valid ExcelFile excelFile,
                                  @RequestParam(required = false, name = "fileType") String fileType,
                                  Model model) {

        List<String> errors = new LinkedList<>();
        MultipartFile mpFile = excelFile.getMpFile();
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
