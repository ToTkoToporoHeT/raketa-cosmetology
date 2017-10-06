/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Materials;
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

    public Materials findMaterialById(int id) {
        System.out.println("DAO level findMaterialById is called");

        return entityManager.find(Materials.class, id);
    }

    public Materials findMaterialByName(String name) {
        System.out.println("DAO level findMaterialByName is called");

        return entityManager.find(Materials.class, name);
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

    public boolean deleteMaterial(int idMaterial) {
        System.out.println("DAO level deleteMaterial is called");

        Materials material = findMaterialById(idMaterial);
        if (material.getUsedmaterialsList().size() > 0) {
            material.setForDelete(true);
        } else {
            entityManager.remove(material);
        }

        return !material.isForDelete();
    }
}
