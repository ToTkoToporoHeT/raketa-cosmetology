/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.AddressDAO;
import by.ban.cosmetology.model.Address;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dazz
 */
@Service
public class AddressService {
    
    @Autowired
    private AddressDAO addressDAO;
    
    public List<Address> getAllAddress(){
        System.out.println("Service level getAllAddress is called");
        return addressDAO.getAllAddress();
    }
    
    public Address findAddressById(int id){
        System.out.println("Service level findAddressById is called");
        return addressDAO.findAddressById(id);
    }
        
    public boolean updateAddress(Integer id, String street, String house, String flat){
        System.out.println("Service level updateAddress is called");
        return addressDAO.updateAddress(id, street, house, flat);
    }
    
    public Address addAddress(Address address){
        System.out.println("Service level addAddress is called");
        
        Address addressResult = addressDAO.findAddressWithoutId(address);
        if (addressResult == null)
            addressResult = addressDAO.addAddress(address);
        
        return addressResult;
    }
    
    public boolean deleteAddress(int idAddress) {
        System.out.println("Service level deleteAddress is called");
        return addressDAO.deleteAddress(idAddress);
    }
}
