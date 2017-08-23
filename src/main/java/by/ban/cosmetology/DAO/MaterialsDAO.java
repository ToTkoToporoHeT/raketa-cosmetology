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
public class MaterialsDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    
    public List<Materials> getAllMaterials(){
        System.out.println("DAO level getAllMaterials is called");
        TypedQuery<Materials> tq = entityManager.createNamedQuery("Materials.findAll", Materials.class);
        
        return tq.getResultList();
    }
    
    public Materials findMaterialById(int id){
        System.out.println("DAO level findMaterialById is called");
        
        return entityManager.find(Materials.class, id);
    }
    
    public Materials findMaterialByName(String name){
        System.out.println("DAO level findMaterialByName is called");
        
        return entityManager.find(Materials.class, name);
    }
    
    public boolean updateMaterial(int id, int unit, int count, double cost) {
        System.out.println("DAO level updateMaterial is called");
 
        String query= "update Materials set unit =?, count = ?, cost = ? where id = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, unit);
        nativeQuery.setParameter(2, count);
        nativeQuery.setParameter(3, cost);
        nativeQuery.setParameter(4, id);
        int result = nativeQuery.executeUpdate();
        return result > 0; // result show how many rows was updated.
    }
    
    public boolean addMaterial(String name, int unit, int count, double cost) {
        System.out.println("DAO level addMaterial is called");
 
        String qlString = "insert into Materials (name,unit,count,cost) values (?,?,?,?)";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, name);
        query.setParameter(2, unit);
        query.setParameter(3, count);
        query.setParameter(4, cost);
        
        int result = query.executeUpdate();
 
        return result > 0;
    }
 
    public boolean deleteMaterial(int idMaterial) {
        System.out.println("DAO level deleteMaterial is called");
 
        String qlString = "delete from Materials where id=?";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, idMaterial);
        int result = query.executeUpdate();
 
        return result > 0;
    }
}
