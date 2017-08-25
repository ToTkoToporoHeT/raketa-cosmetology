/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller;

import by.ban.cosmetology.model.Services;
import by.ban.cosmetology.service.ServicesService;
import java.util.List;
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
@RequestMapping(value = "/services")
public class ServicesController {

    @Autowired
    private ServicesService servicesService;

    @RequestMapping(value = "/showAllServices", method = RequestMethod.GET)
    public ModelAndView showAllServices() {
        System.out.println("Controller level showAllServices is called");

        List<Services> services = servicesService.getAllServices();
        ModelAndView modelAndView = new ModelAndView("/services/viewServices");
        modelAndView.addObject("services", services);

        return modelAndView;
    }

    @RequestMapping(value = "/service/show_page/{action}")
    public String showServicePage(@ModelAttribute("service") Services services,
            @PathVariable String action, Model model) {
        System.out.println("Controller level showServicePage is called for "
                + action + " material");

        model.addAttribute(action);
        if (action.equals("add")) {
            model.addAttribute("service", new Services());
        } else if (action.equals("edit")) {
            Integer idServiceFC = services.getId();
            if (idServiceFC == null) {
                return "redirect:/services/showAllServices";
            }
            Services serviceFC = servicesService.findServiceById(idServiceFC);
            model.addAttribute("service", serviceFC);
        }

        return "/services/service";
    }

    @RequestMapping(value = "/service/add")
    public String addService(@ModelAttribute("service") Services service) {
        System.out.println("Controller level addService is called");

        boolean result = servicesService.addService(service.getName(), service.getCost());

        return "redirect:/services/showAllServices";
    }

    @RequestMapping(value = "/service/edit")
    public String editService(@ModelAttribute("service") Services service) {
        System.out.println("Controller level editService is called");

        boolean result = servicesService.updateService(service.getId(), service.getName(), service.getCost());

        return "redirect:/services/showAllServices";
    }

    @RequestMapping(value = "/service/delete", method = RequestMethod.POST)
    public String deleteService(@RequestParam("id") int serviceId) {
        System.out.println("Controller level deleteService is called");

        boolean result = servicesService.deleteService(serviceId);

        return "redirect:/services/showAllServices";
    }
}
