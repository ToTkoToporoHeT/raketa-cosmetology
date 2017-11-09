/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller;

import by.ban.cosmetology.editors.DecimalEditor;
import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.service.MaterialsService;
import by.ban.cosmetology.service.UnitsService;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.List;
import javax.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomNumberEditor;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
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
@Secured(value = {"ROLE_ADMIN", "ROLE_USER", "ROLE_ROOT"})
@RequestMapping(value = "/materials")
public class MaterialsController {

    @Autowired
    private MaterialsService materialsService;
    @Autowired
    private UnitsService unitsService;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        NumberFormat nf = NumberFormat.getInstance();
        if (nf instanceof DecimalFormat) {
            DecimalFormat df = (DecimalFormat) nf;

            String pattern = "##0.0000";
            df.applyPattern(pattern);
            df.setGroupingUsed(true);
            df.setGroupingSize(3);

            nf = df;
        }
        binder.registerCustomEditor(Double.class, new DecimalEditor(Double.class, nf, true));
    }

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

        model.addAttribute("action", action);
        model.addAttribute("units", unitsService.getAllUnits());

        if (action.equals("add")) {
            model.addAttribute("material", new Materials());
        } else if (action.equals("edit")) {
            Integer idMaterialFC = material.getId();

            if (idMaterialFC == null) {
                return "redirect:/materials/showAllMaterials";
            }

            Materials materialFC = materialsService.findMaterial(idMaterialFC);
            model.addAttribute("material", materialFC);
        }

        return "/materials/material";
    }

    @RequestMapping(value = "/material/{action}")
    public String addOrUpdateMaterial(@ModelAttribute("material") @Valid Materials material,
            BindingResult result, Model model, @PathVariable String action) {

        if (result.hasErrors()) {
            model.addAttribute("action", action);
            model.addAttribute("units", unitsService.getAllUnits());

            return "/materials/material";
        }

        System.out.println("Controller level " + action + "Material is called");

        switch (action) {
            case "add": {
                materialsService.addMaterial(material.getName(), material.getUnit().getId(), material.getCount(), material.getCost());
                break;
            }
            case "edit": {
                materialsService.updateMaterial(material.getId(), material.getName(), material.getUnit().getId(), material.getCount(), material.getCost());
                break;
            }
        }

        return "redirect:/materials/showAllMaterials";
    }

    @RequestMapping(value = "/material/delete", method = RequestMethod.POST)
    public String deleteMaterial(@RequestParam("id") int materialId) {
        System.out.println("Controller level deleteMaterial is called");

        materialsService.deleteMaterial(materialId);

        return "redirect:/materials/showAllMaterials";
    }
}
