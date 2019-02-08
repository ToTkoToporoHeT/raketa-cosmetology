/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.UnitsDAO;
import by.ban.cosmetology.model.Units;
import java.util.List;
import javax.persistence.NoResultException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dazz
 */
@Service
public class UnitsService {
    
    @Autowired
    private UnitsDAO unitsDAO;
    
    public Units getUnit(String name) {
        System.out.println("Service level getUnit is called");
        
        List<Units> units = getAllUnits();
        Units unit = null;
        
        for (Units curUnit : units) {
            if (deleteDotsFromStr(curUnit.getUnit()).equals(deleteDotsFromStr(name))) {
                unit = curUnit;
                break;
            }
        }
        
        if (unit == null)
            unit = addUnit(name);
            
        return unit;
    }
    
    public Units addUnit(String name) {
        System.out.println("Service level addUnit is called");        
        
        return unitsDAO.addUnit(name.trim());
    }
    
    public List<Units> getAllUnits(){
        System.out.println("Service level getAllUnits is called");
        
        return unitsDAO.getAllUnits();        
    }
    
    public Units findUnit(int id){
        System.out.println("Service level findUnit is called");
        
        return unitsDAO.findUnit(id);        
    }
    
    public Units findUnit(String name){
        System.out.println("Service level findUnit is called");
        
        return unitsDAO.findUnit(name);     
    }
    
    private String deleteDotsFromStr(String s) {
        while (s.contains(".")) {
            int dotIndex = s.indexOf(".");
            s = s.substring(0, dotIndex) + s.substring(dotIndex + 1);
        }

        return s;
    }
}
