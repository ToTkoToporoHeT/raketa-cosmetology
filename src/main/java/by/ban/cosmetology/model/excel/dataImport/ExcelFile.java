/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel.dataImport;

import by.ban.cosmetology.model.excel.dataImport.layouts.ServicesRowColInfo;
import by.ban.cosmetology.model.excel.dataImport.layouts.MaterialRowColInfo;
import javax.validation.Valid;
import org.springframework.web.multipart.MultipartFile;

/**
 *
 * @author dazz
 */
public class ExcelFile {
    @Valid
    private MultipartFile mpFile;
    @Valid
    private ServicesRowColInfo servicesRowColInfo;
    @Valid
    private MaterialRowColInfo materilRowColInfo;

    public ExcelFile() {
    }

    public ExcelFile(MultipartFile mpFile) {
        this.mpFile = mpFile;
    }

    public ExcelFile(ServicesRowColInfo servicesRowColInfo, MaterialRowColInfo materialRowColInfo) {
        this.servicesRowColInfo = servicesRowColInfo;
        this.materilRowColInfo = materialRowColInfo;
    }

    public ExcelFile(MultipartFile mpFile, ServicesRowColInfo servicesRowColInfo, MaterialRowColInfo materialRowColInfo) {
        this.mpFile = mpFile;
        this.servicesRowColInfo = servicesRowColInfo;
        this.materilRowColInfo = materialRowColInfo;
    }

    public MultipartFile getMpFile() {
        return mpFile;
    }

    public void setMpFile(MultipartFile mpFile) {
        this.mpFile = mpFile;
    }

    public ServicesRowColInfo getServicesRowColInfo() {
        return servicesRowColInfo;
    }

    public void setServicesRowColInfo(ServicesRowColInfo servicesRowColInfo) {
        this.servicesRowColInfo = servicesRowColInfo;
    }

    public MaterialRowColInfo getMaterialRowColInfo() {
        return materilRowColInfo;
    }

    public void setMaterialRowColInfo(MaterialRowColInfo materilDataMap) {
        this.materilRowColInfo = materilDataMap;
    }
}
