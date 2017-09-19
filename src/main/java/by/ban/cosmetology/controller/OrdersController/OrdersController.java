/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller.OrdersController;

import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.service.OrdersService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author dazz
 */
@Controller
@RequestMapping("/orders")
@SessionAttributes(types = Orders.class)
public class OrdersController {

    @Autowired
    OrdersService ordersService;
    
    @RequestMapping("/showAllOrders")
    public String showAllOrders(Model model){
        System.out.println("Controller level showAllOrders is called");
        
        model.addAttribute("orders", ordersService.getAllOrders());
        return "/orders/viewOrders";
    }    
}
