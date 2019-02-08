/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel.dataImport;

import by.ban.cosmetology.model.excel.layouts.DataRowColInfo;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

/**
 * Расширяет DataRowColInfo для возможности извлечения данных по услугам
 * @author dazz
 */
public class ServicesRowColInfo extends DataRowColInfo{

    @NotNull
    @Min(0)
    private int priceFF;//стоимость услуг для не граждан РБ
    
    public ServicesRowColInfo() {
    }

    public ServicesRowColInfo(int rowStartData, int rowEndData, int name, int cost, int costFNotRes) {
        super(rowStartData, rowEndData, name, cost);
        this.priceFF = costFNotRes;
    }
    
    public int getPriceFF() {
        return priceFF;
    }

    public void setPriceFF(int priceFF) {
        this.priceFF = priceFF;
    }
}
