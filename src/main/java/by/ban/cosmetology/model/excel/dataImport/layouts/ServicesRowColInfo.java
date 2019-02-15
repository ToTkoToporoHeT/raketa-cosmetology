/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel.dataImport.layouts;

import by.ban.cosmetology.model.excel.dataImport.layouts.DataRowColInfo;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Расширяет DataRowColInfo для возможности извлечения данных по услугам
 * @author dazz
 */
public class ServicesRowColInfo extends DataRowColInfo{

    @NotNull
    @Min(0)
    private int numberInPL;//стоимость услуг для не граждан РБ
    
    @NotNull
    @Min(1)
    private int priceFF;//стоимость услуг для не граждан РБ
    
    public ServicesRowColInfo() {
    }

    public ServicesRowColInfo(String sheetName, int rowStartData, int rowEndData, int numberInPL, int name, int cost, int costFNotRes) {
        super(sheetName, rowStartData, rowEndData, name, cost);
        this.numberInPL = numberInPL;
        this.priceFF = costFNotRes;
    }

    public int getNumberInPL() {
        return numberInPL;
    }

    public void setNumberInPL(int numberInPL) {
        this.numberInPL = numberInPL;
    }
    
    public int getPriceFF() {
        return priceFF;
    }

    public void setPriceFF(int priceFF) {
        this.priceFF = priceFF;
    }
}
