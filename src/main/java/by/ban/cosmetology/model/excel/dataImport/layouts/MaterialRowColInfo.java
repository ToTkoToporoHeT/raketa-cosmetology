/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel.dataImport.layouts;

import by.ban.cosmetology.model.Staff;
import by.ban.cosmetology.model.excel.dataImport.layouts.DataRowColInfo;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import org.hibernate.validator.constraints.NotEmpty;

/**
 * Расширяет DataRowColInfo для возможности извлечения данных по материалам
 * @author dazz
 */
public class MaterialRowColInfo extends DataRowColInfo{
    
    @NotNull
    private Staff manager;
    
    @Min(0)
    private int numberInReport;
    
    @Min(0)
    private int itemNumber;
    
    @Min(0)
    private int unit;
    
    @Min(0)
    private int count;

    public MaterialRowColInfo() {
    }

    public MaterialRowColInfo(Staff manager, String sheetName, int rowStartData, int rowEndData, int numberInReport, int itemNumber, int name, int unit, int count, int price) {
        super(sheetName, rowStartData, rowEndData, name, price);
        this.manager = manager;
        this.numberInReport = numberInReport;
        this.itemNumber = itemNumber;
        this.unit = unit;
        this.count = count;
    }

    public Staff getManager() {
        return manager;
    }

    public void setManager(Staff manager) {
        this.manager = manager;
    }
    
    public int getUnit() {
        return unit;
    }

    public void setUnit(int unit) {
        this.unit = unit;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public int getNumberInReport() {
        return numberInReport;
    }

    public void setNumberInReport(int numberInReport) {
        this.numberInReport = numberInReport;
    }

    public int getItemNumber() {
        return itemNumber;
    }

    public void setItemNumber(int itemNumber) {
        this.itemNumber = itemNumber;
    }    
}
