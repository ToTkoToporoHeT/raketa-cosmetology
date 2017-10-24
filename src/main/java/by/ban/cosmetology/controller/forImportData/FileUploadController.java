/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller.forImportData;

import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.model.Services;
import by.ban.cosmetology.model.Units;
import by.ban.cosmetology.model.excel.ExcelFile;
import by.ban.cosmetology.model.excel.MaterialDataMap;
import by.ban.cosmetology.model.excel.ServiceDataMap;
import by.ban.cosmetology.service.MaterialsService;
import by.ban.cosmetology.service.ServicesService;
import by.ban.cosmetology.service.UnitsService;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.validation.Valid;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author dazz
 */
@Controller
@RequestMapping("/import")
public class FileUploadController {

    @RequestMapping(value = "/createPage")
    public String createPage(Model model) {

        MaterialDataMap mdm = new MaterialDataMap(638, 855, 2, 8);
        ServiceDataMap sdm = new ServiceDataMap(6, 612, 2, 6, 7);
        ExcelFile ef = new ExcelFile(sdm, mdm);

        model.addAttribute("excelFile", ef);

        return "/excel/importDataForDB";
    }

    @RequestMapping(value = "/uploadAndImport", method = RequestMethod.POST)
    public String uploadAndImport(@ModelAttribute @Valid ExcelFile excelFile, Model model) {

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
                BufferedOutputStream stream = new BufferedOutputStream(new FileOutputStream(file));
                stream.write(fileBytes);
                stream.close();

                System.out.println("File is saved under: " + pathEF);
            } catch (Exception e) {
                e.printStackTrace();
                return "File upload is failed: " + e.getMessage();
            }
        } else {
            return "File upload is failed: File is empty";
        }

        return "/excel/importDataForDB";
    }
}
