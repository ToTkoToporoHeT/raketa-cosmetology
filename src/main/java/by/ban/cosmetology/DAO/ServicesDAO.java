/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Services;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dazz
 */
@Repository
@Transactional
public class ServicesDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Services> getAllServices(){
        System.out.println("DAO level getAllServices is called");
        
        TypedQuery<Services> tq = entityManager.createNamedQuery("Services.findAll", Services.class);
        
        return tq.getResultList();
    }
    
    public Services findServiceById(int id){
        System.out.println("DAO level findServiceById is called");
        
        return entityManager.find(Services.class, id);
    }
    
    public Services findServiceByName(String name){
        System.out.println("DAO level findServiceByName is called");
        
        return entityManager.find(Services.class, name);
    }
    
    public boolean updateService(int id, String name, double cost) {
        System.out.println("DAO level updateService is called");
 
        String query= "update Services set name = ?, cost = ? where id = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, name);
        nativeQuery.setParameter(2, cost);
        nativeQuery.setParameter(3, id);
        int result = nativeQuery.executeUpdate();
        return result > 0; // result show how many rows was updated.
    }
    
    public boolean addService(String name, double cost) {
        System.out.println("DAO level addService is called");
 
        String qlString = "insert into Services (name, cost) values (?,?)";
        System.out.println("Name=" + name + " Cost=" + cost);
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, name);
        query.setParameter(2, cost);
        
        int result = query.executeUpdate();
 
        return result > 0;
    }
 
    public boolean deleteService(int idService) {
        System.out.println("DAO level deleteService is called");
 
        String qlString = "delete from Services where id=?";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, idService);
        int result = query.executeUpdate();
 
        return result > 0;
    }
}
