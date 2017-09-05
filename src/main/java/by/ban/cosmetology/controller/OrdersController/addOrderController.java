/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller.OrdersController;

import by.ban.cosmetology.model.Customers;
import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.service.CustomersService;
import by.ban.cosmetology.service.OrdersService;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

/**
 *
 * @author dazz
 */
@Controller
@RequestMapping("/orders/order")
@SessionAttributes(types = Orders.class)
public class addOrderController {
    
    @Autowired
    OrdersService ordersService;
    @Autowired
    CustomersService customersService;
    
    /*@ModelAttribute("order")
    public Orders populateOrder(){
        return new Orders();
    }*/
    
    /*@ModelAttribute("orders")
    public List<Orders> populateOrdersList(){
        return new ArrayList<Orders>();
    }*/
    
    @RequestMapping(value = "/create_page/add")
    public String createOrderPage(Model model) {
        System.out.println("Controller level showOrderPage is called for add order");

        model.addAttribute("action", "add");
        model.addAttribute("order", new Orders());
        
        return "/orders/order";
    }
    
    @RequestMapping(value = "/show_page/add")
    public String showOrderPage(Orders order, Model model) {
        System.out.println("Controller level showOrderPage is called for add order");

        model.addAttribute("action", "add");
        Assert.notNull(order);
        return "/orders/order";
    }
    
    @RequestMapping("/selectCustomer")
    public String selectCustomer(Orders order, @ModelAttribute Customers customer, Model model){
        
        customer = customersService.findCustomerByLogin(customer.getLogin());
        order.setCustomer(customer);
        Assert.notNull(order);
        Assert.notNull(order.getCustomer());
        model.addAttribute("order", order);
        return "/orders/order";
    }

    @RequestMapping(value = "/add")
    public String addOrder(@ModelAttribute Orders order) {
        System.out.println("Controller level addOrder is called");

        boolean result = ordersService.addOrder(order);
        return "redirect:/orders/showAllOrders";
    }
}
