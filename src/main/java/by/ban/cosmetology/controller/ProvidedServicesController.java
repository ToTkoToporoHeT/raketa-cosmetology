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
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author dazz
 */
@Controller
@Secured(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_ROOT"})
@RequestMapping("/providedServices")
public class ProvidedServicesController {

    @Autowired
    ServicesService servicesService;

    @RequestMapping("/show_page/selectServices")
    public String selectProvServ(@ModelAttribute Orders orders, Model model) throws CloneNotSupportedException {
        System.out.println("Controller level selectProvServ is called");

        //создается список всех услуг
        Map<Integer, Providedservices> mapAllServices = new HashMap<>();
        for (Services s : servicesService.getAllServices()) {
            Providedservices ps = new Providedservices(1 ,s);
            mapAllServices.put(s.getId(), ps);
        }
                
        Map<Integer, Providedservices> mapOrderServices = new HashMap<>();
        for (Map.Entry<Integer, Providedservices> entry : mapAllServices.entrySet()) {
            int mapKey = entry.getKey();
            Providedservices ps = entry.getValue().clone();
            ps.getService().setId(-1);
            mapOrderServices.put(mapKey, ps);
        }
        //созданный ранее список редактируется, 
        //в него вносятся данные о уже выбранных услугах
        if (orders.getProvidedservicesList() != null) {
            for (Providedservices ps : orders.getProvidedservicesList()) {
                int servId = ps.getService().getId();
                Providedservices psTemp = mapOrderServices.get(servId);
                if (psTemp == null) {
                    //Если услуга используемая в Providedservices помечена на удаление, 
                    //то метод getAllServices его не вернет, а значит psTemp будет раняться null
                    //код ниже помещает такие Providedservices в конец списка выбора, а на странице их изменение блокируется
                    psTemp = new Providedservices(servicesService.findService(servId));
                    ps.setService(psTemp.getService());
                    
                    mapAllServices.put(servId, psTemp);
                } else {
                    ps.setService(psTemp.getService());
                    ps.getService().setId(servId);
                }
                    mapOrderServices.put(servId, ps);
            }
        }
        Orders oTemp = new Orders();
        oTemp.setProvidedservicesList(new ArrayList<>(mapAllServices.values()));
        model.addAttribute("allServices", oTemp);
        orders.setProvidedservicesList(new ArrayList<>(mapOrderServices.values()));
        model.addAttribute("orderTemp", orders);
        return "/services/selectServices";
    }
}