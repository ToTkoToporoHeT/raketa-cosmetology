/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.ServicesDAO;
import by.ban.cosmetology.model.Services;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dazz
 */
@Service
public class ServicesService {
    
    @Autowired
    private ServicesDAO servicesDAO;
    
    public List<Services> getAllServices(){
        System.out.println("Service level getAllServices is called");
        
        return servicesDAO.getAllServices(false);
    }
    
    public Services findService(int id){
        System.out.println("Service level findService by Id is called");
        
        return servicesDAO.findService(id);
    }
    
    public Services findService(String name){
        System.out.println("Service level findService by Name is called");
        
        return servicesDAO.findService(name);
    }
    
    public boolean updateService(int id, String name, double cost, double costFF){
        System.out.println("Service level updateService is called");
        
        return servicesDAO.updateService(id, name.trim(), cost, costFF);
    }
    
    public boolean addService(String name, double cost, double costFF){
        System.out.println("Service level addService is called");
        
        return servicesDAO.addService(name.trim(), cost, costFF);
    }
    
    public boolean addService(Services service){
        System.out.println("Service level addService is called");
        
        return servicesDAO.addService(service);
    }
    
    public boolean deleteService(int idService) {
        System.out.println("Service level deleteService is called");
        
        return servicesDAO.deleteService(idService);
    }

    @Transactional
    public void importServices(Map<String, Services> services) {
        System.out.println("Service level importServices is called");
        
        for (Map.Entry<String, Services> entry : services.entrySet()){            
            Services service = servicesDAO.findService(entry.getKey());
            
            if (service != null){
                addService(entry.getValue());
                
            } else {
                service.update(entry.getValue());
            }
        }
    }
}
