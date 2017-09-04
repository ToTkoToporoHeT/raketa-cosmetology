/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Usertypes;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dazz
 */
@Repository
@Transactional
public class UserTypesDAO {
    
    @PersistenceContext
    EntityManager entityManager;
    
    public List<Usertypes> getAllUserTypes(){
        System.out.println("DAO level getAllUserTypes is called");
        
        TypedQuery<Usertypes> tq = entityManager.createNamedQuery("Usertypes.findAll", Usertypes.class);
        List<Usertypes> usertypesList = tq.getResultList();
        for (Usertypes usertype : usertypesList) {
            entityManager.persist(usertype);
        }
        return usertypesList;
    }
    
    public Usertypes getUserTypeById(Integer id){
        System.out.println("DAO level getUserTypeById is called");
        
        return entityManager.find(Usertypes.class, id);
    }    
}
