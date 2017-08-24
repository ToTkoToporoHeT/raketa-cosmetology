/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller;

import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.service.MaterialsService;
import by.ban.cosmetology.service.UnitsService;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
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

    @RequestMapping(value = "/material/show_page/{action}")
    public String showMaterialPage(@ModelAttribute("material") Materials material,
            @PathVariable String action, Model model) {
        System.out.println("Controller level showMaterialPage is called for "
                + action + " material");
        
        model.addAttribute(action);
        if (action.equals("add")) {
            model.addAttribute("material", new Materials());
            model.addAttribute("units", unitsService.getAllUnits());
        }
        else if (action.equals("edit")){
            Integer idMaterialFC = material.getId();
            if (idMaterialFC == null)
                return "redirect:/materials/showAllMaterials";
            Materials materialFC = materialsService.findMaterialById(idMaterialFC);
            model.addAttribute("material", materialFC);
            model.addAttribute("units", unitsService.getAllUnits());
        }

        return "/materials/material";
    }

    @RequestMapping(value = "/material/add")
    public String addMaterial(@ModelAttribute("material") Materials material,
            @RequestParam int materialUnitId) {
        System.out.println("Controller level addMaterial is called");

        boolean result = materialsService.addMaterial(material.getName(), materialUnitId, material.getCount(), material.getCost());

        return "redirect:/materials/showAllMaterials";
    }
    
    @RequestMapping(value = "/material/edit")
    public String editMaterial(@ModelAttribute("material") Materials material,
            @RequestParam int materialUnitId) {
        System.out.println("Controller level editMaterial is called");
        
        boolean result = materialsService.updateMaterial(material.getId(), material.getName(), materialUnitId, material.getCount(), material.getCost());

        return "redirect:/materials/showAllMaterials";
    }
    
    @RequestMapping(value = "/material/delete", method = RequestMethod.POST)
    public String deleteMaterial(@RequestParam("id") int materialId) {
        System.out.println("Controller level deleteMaterial is called");

        boolean result = materialsService.deleteMaterial(materialId);

        return "redirect:/materials/showAllMaterials";
    }
}
