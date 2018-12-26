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
import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

/**
 *
 * @author dazz
 */
@Component
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
        if (!isUniqueNumber(material)){
            errors.rejectValue("number", "number.notUnicue", "Материал c таким номенклатурным номером уже есть");
        }
    }
    
    private boolean isUniqueNumber(Materials material){
        if (material.getNumber() == null){
            return true;
        }
        
        Materials materialFromBD = materialsService.findMaterialByNumber(material.getNumber());
        
        if (materialFromBD == null)
            return true;
        
        return Objects.equals(materialFromBD.getId(), material.getId());
    }
    
}
