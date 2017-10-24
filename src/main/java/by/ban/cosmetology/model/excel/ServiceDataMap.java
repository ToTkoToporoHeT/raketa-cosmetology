/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel;

import by.ban.cosmetology.model.excel.layouts.DataMapForImport;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Расширяет DataMapForImport для возможности извлечения данных по услугам
 * @author dazz
 */
public class ServiceDataMap extends DataMapForImport{

    @NotNull
    @Min(0)
    private int costFF_ColumnNumber;//стоимость услуг для не граждан РБ
    
    public ServiceDataMap() {
    }

    public ServiceDataMap(int rowStartData, int rowEndData, int name_ColumnNumber, int cost_ColumnNumber, int costFNotRes_ColumnNumber) {
        super(rowStartData, rowEndData, name_ColumnNumber, cost_ColumnNumber);
        this.costFF_ColumnNumber = costFNotRes_ColumnNumber;
    }
    
    public int getCostFF_ColumnNumber() {
        return costFF_ColumnNumber;
    }

    public void setCostFF_ColumnNumber(int costFF_ColumnNumber) {
        this.costFF_ColumnNumber = costFF_ColumnNumber;
    }
}
