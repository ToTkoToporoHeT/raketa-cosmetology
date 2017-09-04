/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.UserTypesDAO;
import by.ban.cosmetology.model.Usertypes;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dazz
 */
@Service
public class UserTypesService {
    
    @Autowired
    UserTypesDAO userTypesDAO;
    
    public List<Usertypes> getAllUserTypes(){
        System.out.println("Service level getAllUserTypes is called");
        return userTypesDAO.getAllUserTypes();
    }
    
    public Usertypes getUserTypeById(Integer id){
        System.out.println("Service level getUserTypeById is called");
        return userTypesDAO.getUserTypeById(id);
    }
}
