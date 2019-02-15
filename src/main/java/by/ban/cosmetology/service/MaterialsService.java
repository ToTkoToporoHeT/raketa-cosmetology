/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.MaterialsDAO;
import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.model.Staff;
import java.util.List;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

/**
 *
 * @author dazz
 */
@Service
public class MaterialsService {

    @Autowired
    private MaterialsDAO materialsDAO;
    @Autowired
    private StaffService staffService;

    public List<Materials> getAllMaterials() {
        System.out.println("Service level getAllMaterials is called");

        Staff manager = staffService.findStaffByLogin(getStaffLogin());
        
        String userType = manager.getUserType().getType();
        if (userType.equals("Root") || userType.equals("Admin")) {
            return materialsDAO.getAllMaterials();
        }
        
        return materialsDAO.getAllMaterials(manager);
    }

    public Materials findMaterialByNumber(int number) {
        System.out.println("Service level findMaterial by Id is called");

        return materialsDAO.findMaterialByNumber(number);
    }

    public Materials findMaterial(int id) {
        System.out.println("Service level findMaterial by Name is called");

        return materialsDAO.findMaterial(id);
    }

    public Materials findMaterial(String name) {
        System.out.println("Service level findMaterial by Name is called");

        return materialsDAO.findMaterial(name);
    }

    public boolean updateMaterial(int id, String name, int unit, int count, double cost) {
        System.out.println("Service level updateMaterial is called");

        return materialsDAO.updateMaterial(id, name, unit, count, cost);
    }

    public boolean updateMaterial(Materials material) {
        System.out.println("Service level updateMaterial is called");

        return materialsDAO.updateMaterial(material);
    }

    public boolean addMaterial(String name, int unit, int count, double cost) {
        System.out.println("Service level addMaterial is called");

        return materialsDAO.addMaterial(name, unit, count, cost);
    }

    public boolean addMaterial(Materials material) throws Exception {
        System.out.println("Service level addMaterial is called");

        return materialsDAO.addMaterial(material);
    }

    public Materials addOrUpdateMaterial(Materials material) throws Exception {
        System.out.println("Service level addOrUpdate is called");

        Materials oldMaterial;
        if (material.getNumber() != null) {
            oldMaterial = findMaterialByNumber(material.getNumber());
        } else {
            oldMaterial = findMaterial(material.getName());
        }

        if (oldMaterial != null) {
            material.setId(oldMaterial.getId());
            materialsDAO.updateMaterial(material);
        } else {
            materialsDAO.addMaterial(material);
        }

        return material;
    }

    public boolean deleteMaterial(int idMaterial) {
        System.out.println("Service level deleteMaterial is called");

        return materialsDAO.deleteMaterial(idMaterial);
    }

    public void deleteAllMaterials() {
        getAllMaterials().forEach((material) -> {
            deleteMaterial(material.getId());
        });
    }

    //Получает логин менеджера из Spring security
    private String getStaffLogin() {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        return auth.getName();
    }

    /*старый метод импорта из Excel
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
    }*/
}
