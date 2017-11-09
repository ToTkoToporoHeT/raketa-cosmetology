/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.validators;

import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.service.OrdersService;
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
public class OrderValidator implements Validator {

    @Autowired
    OrdersService ordersService;

    @Override
    public boolean supports(Class<?> clazz) {
        if (Orders.class.isAssignableFrom(clazz)) {
            return true;
        }
        return String.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        
        if (target instanceof String) return;
        
        Orders order = (Orders) target;
        if (!isUniqueNymber(order)) {
            errors.rejectValue("number", "number.notUnique", "Договор с таким номером уже существует");
        }
    }

    private boolean isUniqueNymber(Orders order) {
        if (order.getNumber().isEmpty())
            return true;
        
        //Ищет договор в базе данных по номеру и присваевает его переменной, если договора с таким номером нет в базе, присваевает NULL
        Orders orderFromDB = ordersService.findOrder(order.getNumber());

        //Если договор из базы равен NULL значит договора с таким номером еще нет и номер уникален
        if (orderFromDB == null) {
            return true;
        }
        //Если договор с таким номером уже есть в базе, и ID договора из базы совпадает с ID проверяемого договора
        //то номер договора считается уникальным, так как это один и тот же договор
        return Objects.equals(orderFromDB.getId(), order.getId());
    }
}
