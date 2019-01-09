/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.TelephonenumbersDAO;
import by.ban.cosmetology.model.Customers;
import by.ban.cosmetology.model.Telephonenumbers;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dazz
 */

@Service
public class TelephonenumbersService {
    
    @Autowired
    private TelephonenumbersDAO telephonenumbersDAO;
    
    public List<Telephonenumbers> getAllTelephonenumbers(){
        System.out.println("Service level getAllTelephonenumbers is called");
        return telephonenumbersDAO.getAllTelephonenumbers();
    }
    
    public Telephonenumbers findTelephonenumberById(int id){
        System.out.println("Service level findTelephonenumberById is called");
        return telephonenumbersDAO.findTelephonenumberById(id);
    }
    
    public Telephonenumbers findTelephoneByNumber(String number){
        System.out.println("Service level findTelephoneByNumber is called");
        return telephonenumbersDAO.findTelephoneByNumber(number);
    }
    
    public boolean updateTelephonenumber(int id, String telephoneNumber, String customerLogin){
        System.out.println("Service level updateTelephonenumber is called");
        return telephonenumbersDAO.updateTelephonenumber(id, telephoneNumber, customerLogin);
    }
    
    public List<Boolean> updateListOfTelephones(List<Telephonenumbers> telephones){
        System.out.println("Service level updateListOfTelephones is called");
        
        List<Boolean> results = new ArrayList <>();
        for (Telephonenumbers tel : telephones){
            results.add(updateTelephonenumber(tel.getId(), tel.getTelephoneNumber(), tel.getCustomer().getLogin()));
        }
        return results;
    }
    
    public Telephonenumbers addTelephonenumber(Telephonenumbers telephonenumber){
        System.out.println("Service level addTelephonenumber is called");
        return telephonenumbersDAO.addTelephonenumber(telephonenumber);
    }
    
    public List<Telephonenumbers> addTelephonenumbersList(List<Telephonenumbers> telephonenumbersList){
        System.out.println("Service level addTelephonenumbersList is called");
        return telephonenumbersDAO.addTelephonenumbersList(telephonenumbersList);
    }
    
    public boolean deleteTelephonenumber(int idTelephonenumber) {
        System.out.println("Service level deleteTelephonenumber is called");
        return telephonenumbersDAO.deleteTelephonenumber(idTelephonenumber);
    }
    
    public List<Boolean> deleteListOfTelephones(List<Integer> telephonesId){
        System.out.println("Service level deleteListOfTelephones is called");
        
        List<Boolean> results = new ArrayList <>();
        for (Integer telId : telephonesId){
            results.add(deleteTelephonenumber(telId));
        }
        return results;
    }
}
