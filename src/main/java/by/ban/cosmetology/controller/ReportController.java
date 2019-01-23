/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller;

import by.ban.cosmetology.model.Orders;
import java.util.LinkedList;
import java.util.List;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author alabkovich
 */
@Controller
@Secured(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_ROOT"})
@RequestMapping("/reports")
public class ReportController {
    
    @RequestMapping("/PSandUM")
    public ModelAndView openReportPSandUM() {
        ModelAndView model = new ModelAndView("excelReport");
        
        List<Orders> ordersForReport = new LinkedList<>();
        model.addObject(ordersForReport);
        
        return model;
    }
}
