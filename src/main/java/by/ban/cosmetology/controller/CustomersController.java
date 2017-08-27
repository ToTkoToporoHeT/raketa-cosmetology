/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller;

import by.ban.cosmetology.model.Customers;
import by.ban.cosmetology.service.CustomersService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author dazz
 */
@Controller
@RequestMapping("/customers")
public class CustomersController {
    
    @Autowired
    private CustomersService customersService;
    
    @RequestMapping("/showAllCustomers")
    public ModelAndView getAllCustomers(){
        System.out.println("Controller level showAllMaterials is called");
        List<Customers> customers = customersService.getAllCustomers();
        ModelAndView modelAndView = new ModelAndView("/customers/viewCustomers");
        modelAndView.addObject("customers", customers);
        
        return modelAndView;
    }    
    
    @RequestMapping(value = "/customer/show_page/{action}")
    public String showCustomerPage(@ModelAttribute("customer") Customers customer,
            @PathVariable String action, Model model) {
        System.out.println("Controller level showCustomerPage is called for "
                + action + " customer");
        
        model.addAttribute(action);
        if (action.equals("add")) {
            model.addAttribute("customer", new Customers());
        }
        else if (action.equals("edit")){
            String loginCustomerlFC = customer.getLogin();
            if (loginCustomerlFC == null)
                return "redirect:/customers/showAllCustomers";
            Customers customerFC = customersService.findCustomerByLogin(loginCustomerlFC);
            model.addAttribute("customer", customerFC);
        }

        return "/customers/customer";
    }

    @RequestMapping("/test")
    public @ResponseBody Customers showCustomerString(){
        
        return customersService.findCustomerByLogin("dazz01@mail.ru");
    }
    
    @RequestMapping(value = "/customer/add")    
    public String addCustomer(@ModelAttribute("customer") Customers customer) {
        System.out.println("Controller level addCustomer is called");

        //boolean result = customersService.addMaterial(customer);

        return "redirect:/materials/showAllMaterials";
    }
    
    @RequestMapping(value = "/customer/edit")
    public String editCustomer(@ModelAttribute("customer") Customers customer) {
        System.out.println("Controller level editCustomer is called");
        
        boolean result = customersService.updateCustomer(customer);

        return "redirect:/customers/showAllCustomers";
    }
    
    @RequestMapping(value = "/customer/delete", method = RequestMethod.POST)
    public String deleteCustomer(@RequestParam("login") String customerLogin) {
        System.out.println("Controller level deleteCustomer is called");

        boolean result = customersService.deleteCustomer(customerLogin);

        return "redirect:/customers/showAllCustomers";
    }
}
