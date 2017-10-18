/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.validators;

import by.ban.cosmetology.model.Staff;
import by.ban.cosmetology.service.StaffService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author dazz
 */
@Component
public class StaffValidator implements Validator {

    @Autowired
    StaffService staffService;
    
    @Override
    public boolean supports(Class<?> clazz) {
        return Staff.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Staff manager = (Staff) target;
        if (!isUniqueLogin(manager)){
            errors.rejectValue("login", "login.notUnicue", "Такой логин уже существует");
        }
    }
    
    private boolean isUniqueLogin(Staff manager){
        
        Staff managerFromBD = staffService.findStaffByLogin(manager.getLogin());
        
        if (managerFromBD == null)
            return true;
        
        return Objects.equals(managerFromBD.getId(), manager.getId());
    }
}
