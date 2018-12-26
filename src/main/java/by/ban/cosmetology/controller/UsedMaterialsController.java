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
                Usedmaterials umTemp = mapOrderMaterials.get(matId);
                if (umTemp == null) {
                    //Если материал используемый в Usedmaterials помечен на удаление, 
                    //то метод getAllMaterials его не вернет, а значит umTemp будет раняться null
                    //код ниже помещает такие Usedmaterials в конец списка выбора, а на странице их изменение блокируется
                    umTemp = new Usedmaterials(materialsService.findMaterial(matId));
                    um.setMaterial(umTemp.getMaterial());
                    mapAllMaterials.put(matId, umTemp);
                    mapOrderMaterials.put(matId, um);
                } else {
                    um.setMaterial(umTemp.getMaterial());
                    um.getMaterial().setId(matId);
                    mapOrderMaterials.put(matId, um);
                }
            }
        }
        Orders oTemp = new Orders();
        oTemp.setUsedmaterialsList(new ArrayList<>(mapAllMaterials.values()));
        model.addAttribute("allMaterials", oTemp);
        orders.setUsedmaterialsList(new ArrayList<>(mapOrderMaterials.values()));
        model.addAttribute("orderTemp", orders);
        return "/materials/selectMaterials";
    }
}
