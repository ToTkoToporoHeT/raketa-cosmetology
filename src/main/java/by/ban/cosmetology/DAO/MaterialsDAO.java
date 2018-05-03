/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.model.Orders;
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

    //Получает все материалы у которых не помеченные на удаление (у которых forDelete = 0)
    //Выводить пользователю помеченные на удаление материалы нет необходимости
    public List<Materials> getAllMaterials() {
        System.out.println("DAO level getAllMaterials is called");

        TypedQuery<Materials> tq = entityManager.createNamedQuery("Materials.findAllActive", Materials.class);
        tq.setParameter("del", false);

        return tq.getResultList();
    }

    public Materials findMaterial(int id) {
        System.out.println("DAO level findMaterial by Id is called");

        return entityManager.find(Materials.class, id);
    }
    
    public Materials findMaterialByNumber(int number) {
        System.out.println("DAO level findMaterial by Number is called");

        TypedQuery<Materials> tq = entityManager.createNamedQuery("Materials.findByNumber", Materials.class);
        tq.setParameter("number", number);
        List<Materials> materialsList = tq.getResultList();

        if (materialsList.size() > 0) {
            return materialsList.get(0);
        }
        return null;
    }

    public Materials findMaterial(String name) {
        System.out.println("DAO level findMaterial by Name is called");

        TypedQuery<Materials> tq = entityManager.createNamedQuery("Materials.findByName", Materials.class);
        tq.setParameter("name", name);
        List<Materials> materials = tq.getResultList();
        
        if (materials.size() > 0)
            return materials.get(0);
        return null;
    }

    public boolean updateMaterial(int id, String name, int unit, int count, double cost) {
        System.out.println("DAO level updateMaterial is called");

        String query = "update Materials set name = ?, unit =?, count = ?, cost = ? where id = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, name);
        nativeQuery.setParameter(2, unit);
        nativeQuery.setParameter(3, count);
        nativeQuery.setParameter(4, cost);
        nativeQuery.setParameter(5, id);
        int result = nativeQuery.executeUpdate();
        return result > 0;
        //entityManager.refresh(new Materials(id, name, unitsDAO.findUnitById(unit), count, cost));
    }

    public boolean updateMaterial(Materials material) {
        System.out.println("DAO level updateMaterial is called");

        entityManager.merge(material);
        return true;
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
    
    public boolean addMaterial(Materials material) {
        System.out.println("DAO level addMaterial is called");
        
        entityManager.persist(material);
        return true;
    }

    public boolean deleteMaterial(int idMaterial) {
        System.out.println("DAO level deleteMaterial is called");

        Materials material = MaterialsDAO.this.findMaterial(idMaterial);
        if (material.getUsedmaterialsList().size() > 0) {
            material.setForDelete(true);
        } else {
            entityManager.remove(material);
        }

        return !material.isForDelete();
    }
}
