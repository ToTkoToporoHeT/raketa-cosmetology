/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Telephonenumbers;
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
public class TelephonenumbersDAO {
    
    @PersistenceContext
    EntityManager entityManager;
    
    public List<Telephonenumbers> getAllTelephonenumbers(){
        System.out.println("DAO level getAllTelephonenumber is called");
        TypedQuery<Telephonenumbers> tq = entityManager.createNamedQuery("Telephonenumbers.findAll", Telephonenumbers.class);
        
        return tq.getResultList();
    }
    
    public Telephonenumbers findTelephonenumberById(int id){
        System.out.println("DAO level findTelephonenumberById is called");
        
        return entityManager.find(Telephonenumbers.class, id);
    }
    
    public Telephonenumbers findTelephoneByNumber(String telNumber){
        System.out.println("DAO level findTelephoneByNumber is called");
        
        return entityManager.find(Telephonenumbers.class, telNumber);
    }
    
    public boolean updateTelephonenumber(Integer id, String telephoneNumber, String customerLogin) {
        System.out.println("DAO level updateTelephonenumber is called");
 
        String query= "update Telephonenumbers set telephoneNumber = ?, customer =? where id = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, telephoneNumber);
        nativeQuery.setParameter(2, customerLogin);
        nativeQuery.setParameter(3, id);
        int result = nativeQuery.executeUpdate();
        return result > 0; // result show how many rows was updated.
    }
    
    public Telephonenumbers addTelephonenumber(Telephonenumbers telephonenumber) {
        System.out.println("DAO level addTelephonenumber is called");
 
        entityManager.persist(telephonenumber);
        
        return telephonenumber;
    }
    
    public List<Telephonenumbers> addTelephonenumbersList(List<Telephonenumbers> telephonenumbersList) {
        System.out.println("DAO level addTelephonenumbersList is called");
 
        for (Telephonenumbers telephonenumber : telephonenumbersList)
            addTelephonenumber(telephonenumber);
        
        return telephonenumbersList;
    }
 
    public boolean deleteMaterial(int idTelephonenumber) {
        System.out.println("DAO level deleteTelephonenumber is called");
 
        String qlString = "delete from Telephonenumbers where id=?";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, idTelephonenumber);
        int result = query.executeUpdate();
 
        return result > 0;
    }
}
