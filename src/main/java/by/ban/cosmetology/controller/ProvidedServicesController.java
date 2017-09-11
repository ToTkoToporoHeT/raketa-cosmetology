/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller;

import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.model.Providedservices;
import by.ban.cosmetology.model.Services;
import by.ban.cosmetology.service.ServicesService;
import java.util.ArrayList;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author dazz
 */
@Controller
@RequestMapping("/providedServices")
public class ProvidedServicesController {

    @Autowired
    ServicesService servicesService;

    @RequestMapping("/show_page/selectServices")
    public String selectProvServ(@ModelAttribute Orders order, Model model) {
        System.out.println("Controller level selectProvServ is called");

        model.addAttribute("orderTemp", order);
        return "/services/selectServices";
    }

    @ModelAttribute("allServices")
    public Orders getAllServices() {
        List<Providedservices> providedServicesList = new ArrayList<>();
        List<Services> servicesList = servicesService.getAllServices();
        for (Services s : servicesList) {
            Providedservices p = new Providedservices(s);
            providedServicesList.add(p);
        }
        Orders orderResult = new Orders();
        orderResult.setProvidedservicesList(providedServicesList);
        return orderResult;
    }
}