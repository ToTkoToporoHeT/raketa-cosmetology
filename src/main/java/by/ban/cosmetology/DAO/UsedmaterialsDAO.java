/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Usedmaterials;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dazz
 */
@Repository
@Transactional
public class UsedmaterialsDAO {
    
    @PersistenceContext
    EntityManager entityManager;
    
    public Usedmaterials findUsMaterById(Integer id){
        System.out.println("DAO level findUsedMaterialById is called");
        return entityManager.find(Usedmaterials.class, id);
    }
}
