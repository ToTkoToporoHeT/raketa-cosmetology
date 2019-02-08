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
import javax.persistence.NoResultException;
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

    public List<Units> getAllUnits() {
        System.out.println("DAO level getAllUnits is called");
        TypedQuery<Units> tq = entityManager.createNamedQuery("Units.findAll", Units.class);

        return tq.getResultList();
    }

    public Units findUnit(int id) {
        System.out.println("DAO level findUnit is called");
        return entityManager.find(Units.class, id);
    }

    public Units findUnit(String name) {
        System.out.println("DAO level findUnit is called");

        TypedQuery<Units> tq = entityManager.createNamedQuery("Units.findByUnit", Units.class);
        tq.setParameter("unit", name);

        Units unit = null;
        try {
            unit = tq.getSingleResult();
        } catch (NoResultException noResultException) {
            System.out.println("Единица измерения \"" + name + "\" не найдена в БД");
        }
        
        return unit;
    }

    public Units addUnit(String name) {
        System.out.println("DAO level addUnit is called");
        Units unit = new Units(name);
        entityManager.persist(unit);
        
        return unit;
    }
}
