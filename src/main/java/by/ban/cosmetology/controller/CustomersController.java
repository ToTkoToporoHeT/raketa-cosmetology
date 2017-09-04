/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller;

import by.ban.cosmetology.model.Customers;
import by.ban.cosmetology.model.Telephonenumbers;
import by.ban.cosmetology.service.CustomersService;
import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author dazz
 */
@Controller
@RequestMapping("/customers")
@SessionAttributes(types = Customers.class)
public class CustomersController {

    @Autowired
    private CustomersService customersService;

    @ModelAttribute("customer")
    public Customers populateCustomer() {
        return new Customers();
    }

    @RequestMapping("/showAllCustomers")
    public ModelAndView getAllCustomers() {
        System.out.println("Controller level getAllCustomers is called");
        List<Customers> customers = customersService.getAllCustomers();
        ModelAndView modelAndView = new ModelAndView("/customers/viewCustomers");
        modelAndView.addObject("customers", customers);
        modelAndView.addObject("customer", new Customers());

        return modelAndView;
    }

    @RequestMapping(value = "/customer/show_page/{action}")
    public String showCustomerPage(@ModelAttribute("customer") Customers customer,
            @PathVariable String action, Model model) {
        System.out.println("Controller level showCustomerPage is called for "
                + action + " customer");

        model.addAttribute(action);
        if (action.equals("add")) {
            Customers tempCustomer = new Customers();
            List<Telephonenumbers> telephonenumbers = new ArrayList<>();
            for (int i = 0; i < 3; i++) {
                telephonenumbers.add(new Telephonenumbers());
            }
            tempCustomer.setTelephonenumbersList(telephonenumbers);
            model.addAttribute("customer", tempCustomer);
        } else if (action.equals("edit")) {
            String loginCustomerFC = customer.getLogin();
            if (loginCustomerFC == null) {
                return "redirect:/customers/showAllCustomers";
            }
            customer = customersService.findCustomerByLogin(loginCustomerFC);
            int telCount = customer.getTelephonenumbersList().size();
            for (int i = 0; i < 3 - telCount; i++){
                customer.getTelephonenumbersList().add(new Telephonenumbers());
            }                
            model.addAttribute("customer", customer);
        }

        return "/customers/customer";
    }

    @RequestMapping(value = "/customer/add")
    public String addCustomer(@ModelAttribute("customer") Customers customer) {
        System.out.println("Controller level addCustomer is called");

        boolean result = customersService.addCustomer(customer);
        return "redirect:/customers/showAllCustomers";
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
