/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Staff;
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
public class StaffDAO {
    
    @PersistenceContext
    EntityManager entityManager;
    
    public List<Staff> getAllStaff() {
        System.out.println("DAO level getAllStaff is called");
        TypedQuery<Staff> tq = entityManager.createNamedQuery("Staff.findAll", Staff.class);
        List<Staff> staffs = tq.getResultList();
        for (Staff staff : staffs) {
            entityManager.persist(staff);
        }
        return staffs;
    }

    public Staff findStaffById(Integer id) {
        System.out.println("DAO level findStaffById is called");
        Staff staff = entityManager.find(Staff.class, id);
        return staff;
    }
    
    public boolean updateStaff(Staff staff) {
        System.out.println("DAO level updateStaff is called");

        entityManager.merge(staff);
        return true;
    }

    public boolean addStaff(Staff staff) {
        System.out.println("DAO level addStaff is called");

        entityManager.persist(staff);
        return true;
    }

    public boolean deleteStaff(Integer id) {
        System.out.println("DAO level deleteCustomer is called");

        Staff staff = findStaffById(id);
        entityManager.remove(staff);
        return true;
    }
}
