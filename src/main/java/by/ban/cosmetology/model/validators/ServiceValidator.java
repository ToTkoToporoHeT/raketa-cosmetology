/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.validators;

import by.ban.cosmetology.model.Services;
import by.ban.cosmetology.service.ServicesService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author dazz
 */
public class ServiceValidator implements Validator {
    @Autowired 
    ServicesService servicesService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Services.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Services service = (Services) target;
        if (!isUniqueName(service)){
            errors.rejectValue("name", "name.notUnicue", "Такая услуга уже есть");
        }
    }
    
    private boolean isUniqueName(Services service){
        if (service.getName().isEmpty()){
            return true;
        }
        
        Services serviceFromBD = servicesService.findService(service.getName());
        
        if (serviceFromBD == null)
            return true;
        
        return Objects.equals(serviceFromBD.getId(), service.getId());
    }    
}
