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
        
        Units unit = null;
        try {
            unit = unitsDAO.findUnit(name);
        } catch (NoResultException noResultException) {
            noResultException.printStackTrace();
        }
        return unit;        
    }
}
