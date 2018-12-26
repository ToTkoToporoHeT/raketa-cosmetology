/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.UsedmaterialsDAO;
import by.ban.cosmetology.model.Usedmaterials;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dazz
 */
@Service
public class UsedmaterialsService {
    
    @Autowired
    UsedmaterialsDAO usedmaterialsDAO;
    
    public Usedmaterials findUsMaterById(Integer id){
        System.out.println("Service level findUsedMaterialById is called");
        return usedmaterialsDAO.findUsMaterById(id);
    }   
    
    public Integer getUsMatCount(Integer id){
        System.out.println("Service level getUsMatCount is called");
        Usedmaterials usedmaterials = usedmaterialsDAO.findUsMaterById(id);
        return usedmaterials.getCount();
    }
}
