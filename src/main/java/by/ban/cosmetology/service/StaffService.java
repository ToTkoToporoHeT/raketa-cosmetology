/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.StaffDAO;
import by.ban.cosmetology.model.Staff;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dazz
 */
@Service
public class StaffService {
    
    @Autowired
    StaffDAO staffDAO;
    @Autowired
    UserTypesService userTypesService;
    
    public List<Staff> getAllStaff() {
        System.out.println("Service level getAllStaff is called");        
        return staffDAO.getAllStaff();
    }

    public Staff findStaffById(Integer id) {
        System.out.println("Service level findStaffById is called");
        return staffDAO.findStaffById(id);
    }
    
    public Staff findStaffByLogin(String login){
        System.out.println("Service level findStaffById is called");
        return staffDAO.findStaffByLogin(login);
    }
    
    public boolean updateStaff(Staff staff) {
        System.out.println("Service level updateStaff is called");
        return staffDAO.updateStaff(staff);
    }

    public boolean addStaff(Staff staff) {
        System.out.println("Service level addStaff is called");
        
        return staffDAO.addStaff(staff);
    }

    public boolean deleteStaff(Integer id) {
        System.out.println("Service level deleteCustomer is called");
        return staffDAO.deleteStaff(id);
    }
}
