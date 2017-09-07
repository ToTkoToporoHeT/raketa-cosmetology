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
import java.util.List;
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
    public String selectUsedMaterials(Orders order, Model model) {
        System.out.println("Controller level selectUsedMaterials is called");

        //model.addAttribute("materials", materialsService.getAllMaterials());
        model.addAttribute("order", order);
        return "/materials/selectMaterials";
    }

    @RequestMapping("/create")
    public String createUsedMaterials(@ModelAttribute Orders order) {
        System.out.println("Controller level selectUsedMaterials is called");

        for (Usedmaterials u : order.getUsedmaterialsList()) {
            System.out.println(u.getMaterialId());
        }
        return "done";//"/materials/selectMaterials";
    }

    @ModelAttribute("allMaterials")
    public Orders getallMaterials() {
        List<Usedmaterials> usedmaterialsList = new ArrayList<>();
        List<Materials> materialsList = materialsService.getAllMaterials();
        for (Materials m : materialsList) {
            /*Materials materialNew = new Materials();
            materialNew.setName(m.getName());*/
            Usedmaterials u = new Usedmaterials(m);
            usedmaterialsList.add(u);
        }
        Orders order = new Orders();
        order.setUsedmaterialsList(usedmaterialsList);
        return order;
    }
}

