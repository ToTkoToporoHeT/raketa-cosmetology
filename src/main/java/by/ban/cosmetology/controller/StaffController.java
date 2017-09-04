/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller;

import by.ban.cosmetology.model.Staff;
import by.ban.cosmetology.service.StaffService;
import by.ban.cosmetology.service.UserTypesService;
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
@RequestMapping("/staff")
public class StaffController {
    
    @Autowired
    StaffService staffService;
    @Autowired
    UserTypesService userTypesService;
    
    @RequestMapping("/showAllStaff")
    public ModelAndView showAllStaff(){
        System.out.println("Controller level showAllStaff is called");
        
        ModelAndView modelAndView = new ModelAndView("/staff/viewStaff");
        modelAndView.addObject("staff", staffService.getAllStaff());
        
        return modelAndView;
    }
    
    @RequestMapping(value = "/worker/show_page/{action}")
    public String showWorkerPage(@ModelAttribute("worker") Staff worker,
            @PathVariable String action, Model model) {
        System.out.println("Controller level showWorkerPage is called for "
                + action + " staff");

        model.addAttribute(action);
        model.addAttribute("userTypes", userTypesService.getAllUserTypes());
        if (action.equals("add")) {
            model.addAttribute("worker", new Staff());
        } else if (action.equals("edit")) {
            Integer workerId = worker.getId();
            if (workerId == null) {
                return "redirect:/staff/showAllStaff";
            }
            worker = staffService.findStaffById(workerId);              
            model.addAttribute("worker", worker);
        }

        return "/staff/worker";
    }

    @RequestMapping(value = "/worker/add")
    public String addWorker(@ModelAttribute Staff worker) {
        System.out.println("Controller level addWorker is called");

        boolean result = staffService.addStaff(worker);
        return "redirect:/staff/showAllStaff";
    }

    @RequestMapping(value = "/worker/edit")
    public String editWorker(@ModelAttribute Staff worker) {
        System.out.println("Controller level editWorker is called");

        boolean result = staffService.updateStaff(worker);
        return "redirect:/staff/showAllStaff";
    }

    @RequestMapping(value = "/worker/delete", method = RequestMethod.POST)
    public String deleteWorker(@RequestParam("id") Integer id) {
        System.out.println("Controller level deleteWorker is called");

        boolean result = staffService.deleteStaff(id);

        return "redirect:/staff/showAllStaff";
    }
}
