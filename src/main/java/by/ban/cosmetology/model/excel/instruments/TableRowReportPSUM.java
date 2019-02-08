/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.excel.instruments;

import by.ban.cosmetology.model.Staff;
import java.util.HashMap;
import java.util.Map;
import org.apache.poi.ss.util.CellReference;

/**
 *
 * @author alabkovich
 */
public class TableRowReportPSUM {

    //private CellReference firstCelRef;
    private String psName;
    private double cost = 0;
    private double costFF = 0;
    //количество оказанных услуг по мэнеджерам
    private final Map<Staff, StaffProvidedServices> mapStaffPS = new HashMap<>();

    public TableRowReportPSUM() {
    }

    public TableRowReportPSUM(String psName, double cost, double costFF) {
        this.psName = psName;
        this.cost = cost;
        this.costFF = costFF;
    }

    public String getPsName() {
        return psName;
    }

    public void setPsName(String psName) {
        this.psName = psName;
    }

    public double getCost() {
        return cost;
    }

    public void setCost(double cost) {
        this.cost = cost;
    }

    public double getCostFF() {
        return costFF;
    }

    public void setCostFF(double costFF) {
        this.costFF = costFF;
    }

    public StaffProvidedServices getStaffPS(Staff staff) {
        return mapStaffPS.get(staff);
    }
    
    public Map<Staff, StaffProvidedServices> getMapStaffPS() {
        return mapStaffPS;
    }
    
    public void addStaffCountPS(Staff manager, 
                                boolean isRBCititzen, 
                                int count,
                                double costRB,
                                double costForeign) {
        StaffProvidedServices staffPS = new StaffProvidedServices();
        if (mapStaffPS.putIfAbsent(manager, staffPS) != null) {
            staffPS = mapStaffPS.get(manager);
        }
        
        if (isRBCititzen) {
            staffPS.plusNumRBCit(count);
            staffPS.plusSumCost(count * costRB);
        } else {
            staffPS.plusNumForeignCit(count);
            staffPS.plusSumCost(count * costForeign);
        }        
    }
    
    public void removeStaffCountPS(Staff manager) {
        mapStaffPS.remove(manager);
    }
}
