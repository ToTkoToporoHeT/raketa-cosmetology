/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel.dataImport;

import by.ban.cosmetology.model.excel.layouts.DataRowColInfo;
import javax.validation.constraints.Min;

/**
 * Расширяет DataRowColInfo для возможности извлечения данных по материалам
 * @author dazz
 */
public class MaterialRowColInfo extends DataRowColInfo{
    
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

    public MaterialRowColInfo(int rowStartData, int rowEndData, int numberInReport, int itemNumber, int name, int unit, int count, int price) {
        super(rowStartData, rowEndData, name, price);
        this.numberInReport = numberInReport;
        this.itemNumber = itemNumber;
        this.unit = unit;
        this.count = count;
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
