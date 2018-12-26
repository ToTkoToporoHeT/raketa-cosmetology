/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel.layouts;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Класс содержащий номера столбцов и строк в EXCEL документе
 * в которых располагаются данные для таблицы БД
 * @author dazz
 */
public class DataMapForImport {

    @NotNull
    @Min(0)
    private int name_ColumnNumber;

    @NotNull
    @Min(0)
    private int cost_ColumnNumber;

    @NotNull
    @Min(0)
    private int rowStartData;

    @NotNull
    @Min(0)
    private int rowEndData;

    public DataMapForImport() {
    }

    public DataMapForImport(int rowStartData, int rowEndData, int name_ColumnNumber, int cost_ColumnNumber) {
        this.name_ColumnNumber = name_ColumnNumber;
        this.cost_ColumnNumber = cost_ColumnNumber;
        this.rowStartData = rowStartData;
        this.rowEndData = rowEndData;
    }   

    public int getName_ColumnNumber() {
        return name_ColumnNumber;
    }

    public void setName_ColumnNumber(int name_ColumnNumber) {
        this.name_ColumnNumber = name_ColumnNumber;
    }

    public int getCost_ColumnNumber() {
        return cost_ColumnNumber;
    }

    public void setCost_ColumnNumber(int cost_ColumnNumber) {
        this.cost_ColumnNumber = cost_ColumnNumber;
    }

    public int getRowStartData() {
        return rowStartData;
    }

    public void setRowStartData(int rowStartData) {
        this.rowStartData = rowStartData;
    }

    public int getRowEndData() {
        return rowEndData;
    }

    public void setRowEndData(int rowEndData) {
        this.rowEndData = rowEndData;
    }
}
