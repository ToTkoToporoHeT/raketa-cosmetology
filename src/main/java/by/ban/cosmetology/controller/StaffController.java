/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller;

import by.ban.cosmetology.model.Staff;
import by.ban.cosmetology.model.validators.StaffValidator;
import by.ban.cosmetology.service.StaffService;
import by.ban.cosmetology.service.UserTypesService;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
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
@Secured(value = {"ROLE_ADMIN", "ROLE_ROOT"})
@RequestMapping("/staff")
public class StaffController {

    @Autowired
    StaffService staffService;
    @Autowired
    UserTypesService userTypesService;
    @Autowired
    StaffValidator staffValidator;

    @InitBinder
    public void dataBinding(WebDataBinder binder) {
        binder.addValidators(staffValidator);
    }

    @RequestMapping("/showAllStaff")
    public ModelAndView showAllStaff() {
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
            model.addAttribute("worker", new Staff(true, userTypesService.getUserTypeById(2)));

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

    @RequestMapping(value = "/worker/{action}")
    public String addOrUpdateWorker(@ModelAttribute("worker") @Valid Staff worker,
            BindingResult result, Model model, @PathVariable String action) {

        if (result.hasErrors()) {
            model.addAttribute("action", action);
            model.addAttribute("userTypes", userTypesService.getAllUserTypes());

            return "/staff/worker";
        }

        System.out.println("Controller level " + action + "Worker is called");

        switch (action) {
            case "add": {
                staffService.addStaff(worker);
                break;
            }
            case "edit": {
                staffService.updateStaff(worker);
            }
        }

        return "redirect:/staff/showAllStaff";
    }

    @RequestMapping(value = "/worker/delete", method = RequestMethod.POST)
    public String deleteWorker(@RequestParam("id") Integer id) {
        System.out.println("Controller level deleteWorker is called");

        boolean result = staffService.deleteStaff(id);

        return "redirect:/staff/showAllStaff";
    }
}
