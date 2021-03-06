/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.temp;

import by.ban.cosmetology.service.Utility.DateUtil;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author dazz
 */
@Controller
@RequestMapping("/test")
public class ControllerTemp {
    
    @RequestMapping("/show")
    public String show(Model model) throws Exception{
        ModelTemp modelTemp = new ModelTemp();
        modelTemp.setMasStr(new String[]{"Крем"});
        model.addAttribute("command", modelTemp);
        
        Date date = new Date();
        LocalDate startDateOfMonth = DateUtil
                .getLocalDate(date)
                .withDayOfMonth(1);
        LocalDate endDateOfMonth = DateUtil
                .getLocalDate(date)
                .withDayOfMonth(startDateOfMonth.lengthOfMonth());
        System.out.println(date);
        System.out.println("Start: " + startDateOfMonth);
        System.out.println("End: " + endDateOfMonth);
        
        return "/test/showTemp";
    }
    
    @RequestMapping("/result")
    public String getResult(@ModelAttribute ("modelFWeb") ModelTemp modelTemp,
            ModelMap model){
        model.addAttribute("masStr", modelTemp.getMasStr());
        return "/test/result";
    }
    
    @ModelAttribute("materialsNames")
    public List<String> getMaterialsNames(){
        List<String> list = new ArrayList<>();
        list.add("Мыло");
        list.add("Крем");
        list.add("Салфетки");
        list.add("Скраб");
        
        return list;
    }
}
