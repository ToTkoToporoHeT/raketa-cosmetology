/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.MaterialsDAO;
import by.ban.cosmetology.model.Materials;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dazz
 */

@Service
public class MaterialsService {
    
    @Autowired
    private MaterialsDAO materialsDAO;
    
    public List<Materials> getAllMaterials(){
        System.out.println("Service level getAllMaterials is called");
        
        return materialsDAO.getAllMaterials();
    }
    
    public Materials findMaterialByNumber(int number){
        System.out.println("Service level findMaterial by Id is called");
        
        return materialsDAO.findMaterialByNumber(number);
    }
    
    public Materials findMaterial(int id){
        System.out.println("Service level findMaterial by Name is called");
        
        return materialsDAO.findMaterial(id);
    }
    
    public Materials findMaterial(String name){
        System.out.println("Service level findMaterial by Name is called");
        
        return materialsDAO.findMaterial(name);
    }
    
    public boolean updateMaterial(int id, String name, int unit, int count, double cost){
        System.out.println("Service level updateMaterial is called");
        
        return materialsDAO.updateMaterial(id, name, unit, count, cost);
    }
    
    public boolean updateMaterial(Materials material){
        System.out.println("Service level updateMaterial is called");
        
        return materialsDAO.updateMaterial(material);
    }
    
    public boolean addMaterial(String name, int unit, int count, double cost){
        System.out.println("Service level addMaterial is called");
        
        return materialsDAO.addMaterial(name, unit, count, cost);
    }
    
    public boolean addMaterial(Materials material) throws Exception{
        System.out.println("Service level addMaterial is called");
        
        return materialsDAO.addMaterial(material);
    }
    
    public boolean deleteMaterial(int idMaterial) {
        System.out.println("Service level deleteMaterial is called");
        
        return materialsDAO.deleteMaterial(idMaterial);
    }

    public void importMaterials(Map<String, Materials> materials) throws Exception{
        System.out.println("Service level importMaterials is called");
        
        for (Map.Entry<String, Materials> entry : materials.entrySet()){            
            Materials material = findMaterial(entry.getKey());
            
            if (material != null){
                addMaterial(entry.getValue());
                
            } else {
                material.update(entry.getValue());
            }
        }
    }
}
