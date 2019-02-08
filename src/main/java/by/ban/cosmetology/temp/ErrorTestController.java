/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.temp;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author alabkovich
 */
@Controller
@RequestMapping("/error")
public class ErrorTestController {
    
    @RequestMapping("/throw")
    public ModelAndView throwError() {
        throw new RuntimeException("Ошибочка вышла");
    }
}
