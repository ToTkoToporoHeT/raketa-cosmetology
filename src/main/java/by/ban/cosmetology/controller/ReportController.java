/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller;

import by.ban.cosmetology.DAO.OrdersDAO;
import by.ban.cosmetology.model.Orders;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
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
    
    @Autowired
    private OrdersDAO ordersDAO;
    
    @RequestMapping("/PSandUM")
    public ModelAndView openReportPSandUM() {
        ModelAndView mav = new ModelAndView("excelReport");
        
        List<Orders> ordersForReport = ordersDAO.getMontOrders(new Date());
        mav.addObject("ordersForReport", ordersForReport);
        
        return mav;
    }
}
