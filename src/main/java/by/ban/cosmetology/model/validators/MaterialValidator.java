/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model.validators;

import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.service.MaterialsService;
import java.util.Objects;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author dazz
 */
public class MaterialValidator implements Validator {
    
    @Autowired 
    MaterialsService materialsService;

    @Override
    public boolean supports(Class<?> clazz) {
        return Materials.class.isAssignableFrom(clazz);
    }

    @Override
    public void validate(Object target, Errors errors) {
        Materials material = (Materials) target;
        if (!isUniqueName(material)){
            errors.rejectValue("name", "name.notUnicue", "Такой материал уже есть");
        }
    }
    
    private boolean isUniqueName(Materials material){
        if (material.getName().isEmpty()){
            return true;
        }
        
        Materials materialFromBD = materialsService.findMaterial(material.getName());
        
        if (materialFromBD == null)
            return true;
        
        return Objects.equals(materialFromBD.getId(), material.getId());
    }
    
}
