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
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author dazz
 */
@Controller
@RequestMapping("/orders")
public class OrdersController {
    
    @Autowired
    OrdersService ordersService;
        
    @RequestMapping("/showAllOrders")
    public ModelAndView showAllStaff(){
        System.out.println("Controller level showAllOrders is called");
        
        ModelAndView modelAndView = new ModelAndView("/orders/viewOrders");
        modelAndView.addObject("orders", ordersService.getAllOrders());
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/order/show_page/edit")
    public String showOrderPage(@ModelAttribute Orders order,
            @PathVariable String action, Model model) {
        System.out.println("Controller level showOrderPage is called for "
                + action + " order");

        model.addAttribute(action);
        if (action.equals("add")) {
            model.addAttribute("order", new Orders());
        } else if (action.equals("edit")) {
            Integer orderId = order.getId();
            if (orderId == null) {
                return "redirect:/orders/showAllOrders";
            }
            order = ordersService.findOrderById(orderId);              
            model.addAttribute("order", order);
        }

        return "/orders/order";
    }

    @RequestMapping(value = "/order/edit")
    public String editOrder(@ModelAttribute Orders order) {
        System.out.println("Controller level editOrder is called");

        boolean result = ordersService.updateOrder(order);
        return "redirect:/orders/showAllOrders";
    }

    @RequestMapping(value = "/order/delete", method = RequestMethod.POST)
    public String deleteOrder(@RequestParam("id") Integer id) {
        System.out.println("Controller level deleteOrder is called");

        boolean result = ordersService.deleteOrder(id);

        return "redirect:/orders/showAllOrders";
    }
}
