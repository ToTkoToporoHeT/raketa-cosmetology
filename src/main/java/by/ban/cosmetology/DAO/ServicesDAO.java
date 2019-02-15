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
    
    public List<Services> getAllServices(boolean forDelete){
        System.out.println("DAO level getAllServices is called");
        
        TypedQuery<Services> tq = entityManager.createNamedQuery("Services.findAllActive", Services.class);
        tq.setParameter("del", forDelete);
        
        return tq.getResultList();
    }
    
    public Services findService(int id){
        System.out.println("DAO level findService by Id is called");
        
        return entityManager.find(Services.class, id);
    }

    public Services findService(String name){
        System.out.println("DAO level findService by Name is called");
        
        TypedQuery<Services> tq = entityManager.createNamedQuery("Services.findByName", Services.class);
        tq.setParameter("name", name);        
        List<Services> services = tq.getResultList();
        
        if (services.size() > 0){
            return services.get(0);
        }        
        return null;
    }
    
    public Services findServiceByNumber(String number){
        System.out.println("DAO level findService by Number is called");
        
        TypedQuery<Services> tq = entityManager.createNamedQuery("Services.findByNumber", Services.class);
        tq.setParameter("number", number);        
        List<Services> services = tq.getResultList();
        
        if (services.size() > 0){
            return services.get(0);
        }        
        return null;
    }
    
    public boolean updateService(int id, String name, double cost, double costFF) {
        System.out.println("DAO level updateService is called");
 
        String query= "update Services set name = ?, cost = ?, costFF = ? where id = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, name);
        nativeQuery.setParameter(2, cost);
        nativeQuery.setParameter(3, costFF);
        nativeQuery.setParameter(4, id);
        int result = nativeQuery.executeUpdate();
        return result > 0; // result show how many rows was updated.
    }
    
    public boolean updateService(Services service){
        System.out.println("DAO level updateService is called");
        
        entityManager.merge(service);
        return true;
    }
    
    public boolean addService(String name, double cost, double costFF) {
        System.out.println("DAO level addService is called");
 
        String qlString = "insert into Services (name, cost, costFF) values (?,?,?)";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, name);
        query.setParameter(2, cost);
        query.setParameter(3, costFF);
        
        int result = query.executeUpdate();
 
        return result > 0;
    }
    
    public boolean addService(Services service) {
        System.out.println("DAO level addService is called");
        
        entityManager.persist(service);
        return true;
    }
 
    public boolean deleteService(int idService) {
        System.out.println("DAO level deleteService is called");
 
        Services service = ServicesDAO.this.findService(idService);
        
        if (service.getProvidedservicesList().size() > 0) {
            service.setForDelete(true);
        } else {
            entityManager.remove(service);
        }

        //возвращает результат удаления,
        //если значение isForFelete = true значит,
        //услуга не была удалена, а лишь помечена на удаление,
        //соответственно вернуть нужно false.
        return !service.isForDelete();
    }    
}
