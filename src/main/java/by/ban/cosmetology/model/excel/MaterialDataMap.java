/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel;

import by.ban.cosmetology.model.excel.layouts.DataMapForImport;
import javax.validation.constraints.Min;

/**
 * Расширяет DataMapForImport для возможности извлечения данных по материалам
 * @author dazz
 */
public class MaterialDataMap extends DataMapForImport{
    @Min(0)
    private int unit_ColumnNumber;
    
    @Min(0)
    private int count_ColumnNumber;

    public MaterialDataMap() {
    }
    
    public MaterialDataMap(int rowStartData, int rowEndData, int name_ColumnNumber, int cost_ColumnNumber) {
        super(rowStartData, rowEndData, name_ColumnNumber, cost_ColumnNumber);
    }

    public MaterialDataMap(int rowStartData, int rowEndData, int name_ColumnNumber, int cost_ColumnNumber, int unit_ColumnNumber, int count_ColumnNumber) {
        super(rowStartData, rowEndData, name_ColumnNumber, cost_ColumnNumber);
        this.unit_ColumnNumber = unit_ColumnNumber;
        this.count_ColumnNumber = count_ColumnNumber;
    }
    
    public int getUnit_ColumnNumber() {
        return unit_ColumnNumber;
    }

    public void setUnit_ColumnNumber(int unit_ColumnNumber) {
        this.unit_ColumnNumber = unit_ColumnNumber;
    }

    public int getCount_ColumnNumber() {
        return count_ColumnNumber;
    }

    public void setCount_ColumnNumber(int count_ColumnNumber) {
        this.count_ColumnNumber = count_ColumnNumber;
    }
    
    
}
