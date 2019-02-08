/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller;

import java.util.Date;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author alabkovich
 */
@ControllerAdvice
public class GlobalExceptiosHandler {
    private static final Logger LOGGER = LoggerFactory.getLogger(GlobalExceptiosHandler.class);
    
    @ExceptionHandler(Exception.class) 
    public ModelAndView handleException(Exception ex) {
        LOGGER.error((new Date()).toString(), ex);
        ModelAndView mav = new ModelAndView("/errors/globalError");
        mav.addObject("error", ex);
        mav.addObject("stackTrace", ex.getStackTrace());
        
        //ex.printStackTrace();
        
        return mav;
    }
    
    /*@ExceptionHandler(AccessDeniedException.class) 
    public String handleException(AccessDeniedException ex) {
        return "/security/login";
    }*/
}
