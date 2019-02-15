/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller;

import by.ban.cosmetology.editors.DecimalEditor;
import by.ban.cosmetology.model.Services;
import by.ban.cosmetology.service.ServicesService;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
@RequestMapping(value = "/services")
public class ServicesController {

    @Autowired
    private ServicesService servicesService;
        
    @InitBinder
    public void initBinder(WebDataBinder binder){
        NumberFormat nf = NumberFormat.getInstance();
        if (nf instanceof DecimalFormat){
            DecimalFormat df = (DecimalFormat) nf;
            
            String pattern = "##0.00";
            df.applyPattern(pattern);
            df.setGroupingUsed(true);
            df.setGroupingSize(3);
            
            nf = df;
        }
        binder.registerCustomEditor(Double.class, new DecimalEditor(Double.class, nf, true));
    }
    
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
            Services serviceFC = servicesService.findService(idServiceFC);
            model.addAttribute("service", serviceFC);
        }

        return "/services/service";
    }

    @RequestMapping(value = "/service/{action}")
    public String addOrUpdateService(@ModelAttribute("service") @Valid Services service, BindingResult result, 
            Model model, @PathVariable String action) {
        
        if (result.hasErrors()){
            model.addAttribute("action", action);
            
            return "/services/service";
        }
        
        System.out.println("Controller level " + action + "Service is called");

        switch(action){
            case "add":{
                servicesService.addService(service);
                break;
            }
            case "edit":{
                servicesService.updateService(service);
            }
        }
        
        return "redirect:/services/showAllServices";
    }

    @RequestMapping(value = "/service/delete", method = RequestMethod.POST)
    public String deleteService(@RequestParam("id") int serviceId) {
        System.out.println("Controller level deleteService is called");

        boolean result = servicesService.deleteService(serviceId);

        return "redirect:/services/showAllServices";
    }
    
    @RequestMapping("/show_page/selectServices")
    public String selectServices(Model model){
        System.out.println("Controller level selectServices is called");
        
        model.addAttribute("services", servicesService.getAllServices());
        return "/materials/selectMaterials";
    }
    
    @RequestMapping("/deleteAll")
    public ResponseEntity<String> deleteAllServices() {
        System.out.println("Controller level deleteAllServices is called");

        servicesService.deleteAllServices();

        return new ResponseEntity<>(HttpStatus.OK);
    }
}
