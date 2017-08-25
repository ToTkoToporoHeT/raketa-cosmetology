/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.MaterialsDAO;
import by.ban.cosmetology.model.Materials;
import java.util.List;
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
    
    public Materials findMaterialById(int id){
        System.out.println("Service level findMaterialById is called");
        return materialsDAO.findMaterialById(id);
    }
    
    public Materials findMaterialByName(String name){
        System.out.println("Service level findMaterialByName is called");
        return materialsDAO.findMaterialByName(name);
    }
    
    public boolean updateMaterial(int id, String name, int unit, int count, double cost){
        System.out.println("Service level updateMaterial is called");
        return materialsDAO.updateMaterial(id, name, unit, count, cost);
    }
    
    public boolean addMaterial(String name, int unit, int count, double cost){
        System.out.println("Service level addMaterial is called");
        return materialsDAO.addMaterial(name, unit, count, cost);
    }
    
    public boolean deleteMaterial(int idMaterial) {
        System.out.println("Service level deleteMaterial is called");
        return materialsDAO.deleteMaterial(idMaterial);
    }
}
