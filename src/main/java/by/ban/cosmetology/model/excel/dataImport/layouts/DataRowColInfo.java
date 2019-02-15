/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel.dataImport.layouts;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Класс содержащий номера столбцов и строк в EXCEL документе
 * в которых располагаются данные для таблицы БД
 * @author dazz
 */
public class DataRowColInfo {
    
    @NotNull
    private String sheetName;

    @NotNull
    @Min(1)
    private int name;

    @NotNull
    @Min(1)
    private int price;

    @NotNull
    @Min(1)
    private int rowStartData;

    @NotNull
    @Min(1)
    private int rowEndData;

    public DataRowColInfo() {
    }

    public DataRowColInfo(String sheetName, int rowStartData, int rowEndData, int name, int price) {
        this.sheetName = sheetName;
        this.name = name;
        this.price = price;
        this.rowStartData = rowStartData;
        this.rowEndData = rowEndData;
    }   

    public String getSheetName() {
        return sheetName;
    }

    public void setSheetName(String sheetName) {
        this.sheetName = sheetName;
    }   
    
    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    public void setPrice(int price) {
        this.price = price;
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
