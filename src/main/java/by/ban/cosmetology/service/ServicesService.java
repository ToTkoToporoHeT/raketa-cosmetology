/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.ServicesDAO;
import by.ban.cosmetology.model.Services;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dazz
 */
@Service
public class ServicesService {
    
    @Autowired
    ServicesDAO servicesDAO;
    
    public List<Services> getAllServices(){
        System.out.println("Service level getAllServices is called");
        return servicesDAO.getAllServices();
    }
    
    public Services findServiceById(int id){
        System.out.println("Service level findServiceById is called");
        return servicesDAO.findServiceById(id);
    }
    
    public Services findServiceByName(String name){
        System.out.println("Service level findServiceByName is called");
        return servicesDAO.findServiceByName(name);
    }
    
    public boolean updateService(int id, String name, double cost){
        System.out.println("Service level updateService is called");
        return servicesDAO.updateService(id, name, cost);
    }
    
    public boolean addService(String name, double cost){
        System.out.println("Service level addService is called");
        return servicesDAO.addService(name, cost);
    }
    
    public boolean deleteService(int idService) {
        System.out.println("Service level deleteService is called");
        return servicesDAO.deleteService(idService);
    }
}
