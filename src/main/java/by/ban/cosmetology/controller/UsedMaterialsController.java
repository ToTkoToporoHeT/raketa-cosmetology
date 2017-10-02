/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller;

import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.model.Usedmaterials;
import by.ban.cosmetology.service.MaterialsService;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 *
 * @author dazz
 */
@Controller
@RequestMapping("/usedMaterials")
public class UsedMaterialsController {

    @Autowired
    MaterialsService materialsService;

    @RequestMapping("/show_page/selectMaterials")
    public String selectUsedMaterials(@ModelAttribute Orders orders, Model model) throws CloneNotSupportedException {
        System.out.println("Controller level selectUsedMaterials is called");

        //создается список всех материалов
        Map<Integer, Usedmaterials> mapAllMaterials = new HashMap<>();
        for (Materials m : materialsService.getAllMaterials()) {
            Usedmaterials um = new Usedmaterials(m);
            mapAllMaterials.put(m.getId(), um);
        }
        Orders oTemp = new Orders();
        oTemp.setUsedmaterialsList(new ArrayList<>(mapAllMaterials.values()));
        model.addAttribute("allMaterials", oTemp);
        
        Map<Integer, Usedmaterials> mapOrderMaterials = new HashMap<>();
        for (Map.Entry<Integer, Usedmaterials> entry : mapAllMaterials.entrySet()) {
            int mapKey = entry.getKey();
            Usedmaterials um = entry.getValue().clone();
            um.getMaterial().setId(-1);
            mapOrderMaterials.put(mapKey, um);
        }
        //созданный ранее список редактируется, 
        //в него вносятся данные о уже выбранных материалах
        if (orders.getUsedmaterialsList() != null) {
            for (Usedmaterials um : orders.getUsedmaterialsList()) {
                int matId = um.getMaterial().getId();
                um.setMaterial(mapOrderMaterials.get(matId).getMaterial());
                um.getMaterial().setId(matId);
                mapOrderMaterials.put(matId, um);
            }
        }
        orders.setUsedmaterialsList(new ArrayList<>(mapOrderMaterials.values()));
        model.addAttribute("orderTemp", orders);
        return "/materials/selectMaterials";
    }
}
