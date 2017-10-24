/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel;

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
    private ServiceDataMap serviceDataMap;
    @Valid
    private MaterialDataMap materilDataMap;

    public ExcelFile() {
    }

    public ExcelFile(MultipartFile mpFile) {
        this.mpFile = mpFile;
    }

    public ExcelFile(ServiceDataMap serviceDataMap, MaterialDataMap materilDataMap) {
        this.serviceDataMap = serviceDataMap;
        this.materilDataMap = materilDataMap;
    }

    public ExcelFile(MultipartFile mpFile, ServiceDataMap serviceDataMap, MaterialDataMap materilDataMap) {
        this.mpFile = mpFile;
        this.serviceDataMap = serviceDataMap;
        this.materilDataMap = materilDataMap;
    }

    public MultipartFile getMpFile() {
        return mpFile;
    }

    public void setMpFile(MultipartFile mpFile) {
        this.mpFile = mpFile;
    }

    public ServiceDataMap getServiceDataMap() {
        return serviceDataMap;
    }

    public void setServiceDataMap(ServiceDataMap serviceDataMap) {
        this.serviceDataMap = serviceDataMap;
    }

    public MaterialDataMap getMaterilDataMap() {
        return materilDataMap;
    }

    public void setMaterilDataMap(MaterialDataMap materilDataMap) {
        this.materilDataMap = materilDataMap;
    }
}
