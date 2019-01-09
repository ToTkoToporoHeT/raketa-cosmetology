/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Telephonenumbers;
import by.ban.cosmetology.model.VisitDate;
import java.util.Date;
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
public class VisitDateDAO {
    
    @PersistenceContext
    EntityManager entityManager;
    
    public List<VisitDate> getAllVisitDate(){
        System.out.println("DAO level getAllVisitDate is called");
        TypedQuery<VisitDate> tq = entityManager.createNamedQuery("VisitDate.findAll", VisitDate.class);
        
        return tq.getResultList();
    }
    
    public VisitDate findVisitDateById(int id){
        System.out.println("DAO level findVisitDateById is called");
        
        return entityManager.find(VisitDate.class, id);
    }
    
    public VisitDate findVisitDateByNumber(Date visit_date){
        System.out.println("DAO level findVisitDateByNumber is called");
        
        return entityManager.find(VisitDate.class, visit_date);
    }
    
    public boolean updateVisitDate(Integer id, Date visit_date, Integer orderId) {
        System.out.println("DAO level updateVisitDate is called");
 
        String query= "update VisitDate set visit_date = ?, orderId =? where id = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, visit_date);
        nativeQuery.setParameter(2, orderId);
        nativeQuery.setParameter(3, id);
        int result = nativeQuery.executeUpdate();
        return result > 0; // result show how many rows was updated.
    }
    
    public VisitDate addVisitDate(VisitDate visitDate) {
        System.out.println("DAO level addVisitDate is called");
 
        entityManager.persist(visitDate);
        
        return visitDate;
    }
    
    public List<VisitDate> addVisitDateList(List<VisitDate> visitDatesList) {
        System.out.println("DAO level addVisitDateList is called");
 
        for (VisitDate visitDate : visitDatesList)
            addVisitDate(visitDate);
        
        return visitDatesList;
    }
 
    public boolean deleteVisitDate(int idVisitDate) {
        System.out.println("DAO level deleteVisitDate is called");
 
        String qlString = "delete from VisitDate where id=?";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, idVisitDate);
        int result = query.executeUpdate();
 
        return result > 0;
    }
}
