/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.UnitsDAO;
import by.ban.cosmetology.model.Units;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dazz
 */
@Service
public class UnitsService {
    
    @Autowired
    UnitsDAO unitsDAO;
    
    public List<Units> getAllUnits(){
        System.out.println("Service level getAllUnits is called");
        return unitsDAO.getAllUnits();        
    }
    
    public List<Units> findUnitById(int id){
        System.out.println("Service level findUnitById is called");
        return unitsDAO.findUnitById(id);        
    }
}
