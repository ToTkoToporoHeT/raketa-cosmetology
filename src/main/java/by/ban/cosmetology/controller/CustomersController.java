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
import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
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
@Secured(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_ROOT"})
@RequestMapping("/customers")
public class CustomersController {

    @Autowired
    private CustomersService customersService;

    @RequestMapping("/showAllCustomers")
    public ModelAndView getAllCustomers() {
        System.out.println("Controller level getAllCustomers is called");
        List<Customers> customers = customersService.getAllCustomers();
        ModelAndView modelAndView = new ModelAndView("/customers/viewCustomers");
        modelAndView.addObject("customers", customers);
        modelAndView.addObject("customer", new Customers());

        return modelAndView;
    }

    @RequestMapping(value = "/customer/show_page/add")
    public String showAddPage(HttpServletRequest request, Model model) {
        System.out.println("Controller level showAddPage is called");

        model.addAttribute("action", "add");
        model.addAttribute("requestFromURL", request.getHeader("referer"));

        Customers tempCustomer = new Customers(true);
        List<Telephonenumbers> telephonenumbers = new ArrayList<>();
        tempCustomer.setTelephonenumbersList(telephonenumbers);

        model.addAttribute("customer", tempCustomer);

        return "/customers/customer";
    }

    @RequestMapping(value = "/customer/show_page/edit")
    public String showEditPage(@ModelAttribute("customer") Customers customer, Model model) {
        System.out.println("Controller level showEditPage is called");

        model.addAttribute("action", "edit");

        Integer idCustomerFC = customer.getId();

        if (idCustomerFC == null) {
            return "redirect:/customers/showAllCustomers";
        }

        customer = customersService.findCustomer(idCustomerFC);
        model.addAttribute("customer", customer);

        return "/customers/customer";
    }

    @RequestMapping(value = "/customer/{action}")
    public String addOrUpdateCustomer(
            @ModelAttribute("customer") @Valid Customers customer, 
            BindingResult result, @PathVariable String action, 
            @RequestParam String requestFromURL, Model model) {

        if (result.hasErrors()) {
            model.addAttribute("action", action);

            return "/customers/customer";
        }

        System.out.println("Controller level " + action + "Customer is called");
        
        switch (action) {
            case "add": {
                customersService.addCustomer(customer);
                if (!requestFromURL.isEmpty()) {
                    return "redirect:" + requestFromURL;
                }
                
                break;
            }
            case "edit": {
                customersService.updateCustomer(customer);
                break;
            }
        }

        return "redirect:/customers/showAllCustomers";
    }

    @RequestMapping(value = "/customer/delete", method = RequestMethod.POST)
    public String deleteCustomer(@RequestParam("id") Integer customerId) {
        System.out.println("Controller level deleteCustomer is called");

        boolean result = customersService.deleteCustomer(customerId);

        return "redirect:/customers/showAllCustomers";
    }
}
