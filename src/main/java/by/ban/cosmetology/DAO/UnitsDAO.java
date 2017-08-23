/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.model.Units;
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
public class UnitsDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Units> getAllUnits(){
        System.out.println("DAO level getAllUnits is called");
        TypedQuery<Units> tq = entityManager.createNamedQuery("Units.findAll", Units.class);
        
        return tq.getResultList();
    }
    
    public Units findUnitById(int id){
        System.out.println("DAO level findUnitById is called");
        return entityManager.find(Units.class, id);
    }
}
