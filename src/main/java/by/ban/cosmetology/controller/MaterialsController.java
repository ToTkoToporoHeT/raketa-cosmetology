/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller;

import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.model.Units;
import by.ban.cosmetology.service.MaterialsService;
import by.ban.cosmetology.service.UnitsService;
import java.util.Date;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

/**
 *
 * @author dazz
 */
@Controller
@RequestMapping(value = "/materials")
public class MaterialsController {

    @Autowired
    MaterialsService materialsService;
    @Autowired
    UnitsService unitsService;

    @RequestMapping(value = "/showAllMaterials", method = RequestMethod.GET)
    public ModelAndView showAllMaterials() {
        System.out.println("Controller level showAllMaterials is called");
        List<Materials> materials = materialsService.getAllMaterials();
        ModelAndView modelAndView = new ModelAndView("/materials/viewMaterials");
        modelAndView.addObject("materials", materials);
        modelAndView.addObject("units", unitsService.getAllUnits());

        return modelAndView;
    }

    @RequestMapping(value = "/deleteMaterial", method = RequestMethod.GET)
    public String deleteMaterial(@RequestParam int materialsRadio) {
        System.out.println("Controller level deleteMaterial is called");

        boolean result = materialsService.deleteMaterial(materialsRadio);

        return "redirect:/materials/showAllMaterials";
    }

    @RequestMapping(value = "/material/show_page/{action}")
    public ModelAndView showMaterialPage(@ModelAttribute Materials material,
            @PathVariable String action) {
        System.out.println("Controller level showMaterialPage is called");

        ModelAndView modelAndView = new ModelAndView("/materials/material");
        if (action.equals("add")) {
            modelAndView.addObject("material", material);
            modelAndView.addObject("units", unitsService.getAllUnits());
        }
        else if (action.equals("edit")){
            modelAndView.addObject("material", material);
        }

        return modelAndView;
    }

    @RequestMapping(value = "/material/add")
    public String addMaterial(@ModelAttribute("material") Materials material,
            @RequestParam int materialUnitId) {
        System.out.println("Controller level addMaterial is called");

        materialsService.addMaterial(material.getName(), materialUnitId, material.getCount(), material.getCost());

        return "redirect:/materials/showAllMaterials";
    }
}
