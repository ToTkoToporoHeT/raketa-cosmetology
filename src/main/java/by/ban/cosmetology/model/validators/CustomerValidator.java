/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.validators;

import by.ban.cosmetology.model.Customers;
import by.ban.cosmetology.service.CustomersService;
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
public class CustomerValidator implements Validator{
    
    @Autowired
    CustomersService customersService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Customers.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Customers customer = (Customers) target;
        if (!isUniqueLogin(customer)){
            errors.rejectValue("login", "login.notUnicue", "Такой e-mail уже существует");
        }
    }
    
    private boolean isUniqueLogin(Customers customer){
        if (customer.getLogin().isEmpty()){
            return true;
        }
        
        Customers customerFromBD = customersService.findCustomer(customer.getLogin());
        
        if (customerFromBD == null)
            return true;
        
        return Objects.equals(customerFromBD.getId(), customer.getId());
    }
}
