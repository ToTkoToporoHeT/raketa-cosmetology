/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.AddressDAO;
import by.ban.cosmetology.model.Address;
import java.util.List;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        
    public Address updateAddress(Address address){
        System.out.println("Service level updateAddress is called");   
                  
        int oldAddressId = address.getId();
        address.setId(null);
        address = addAddress(address);
        deleteUnusedAddress(findAddressById(oldAddressId));
        return address;
    }
    
    public Address addAddress(Address address){
        System.out.println("Service level addAddress is called");
        
        Address addressResult = findAddressWithoutId(address);
        if (addressResult == null)
            addressResult = addressDAO.addAddress(address);
        
        return addressResult;
    }
    
    public void deleteAddress(Address address) {
        System.out.println("Service level deleteAddress is called");
        addressDAO.deleteAddress(address);
    }
    
    //Если адрес не используется не с одним клиентом, он удаляется
    @Transactional
    public void deleteUnusedAddress(Address address){
        System.out.println("Service level verifyAddressToUse is called");
        
        Hibernate.initialize(address.getCustomersList());
        if (address.getCustomersList().isEmpty())
            deleteAddress(address);
    }

    public boolean addressIsChanged(Address address) {
        if (address.equals(findAddressById(address.getId())))
            return false;
        return true;
    }
    
    public Address findAddressWithoutId(Address address) {
        System.out.println("DAO level findAddress is called");

        String country = address.getCountry();
        String city = address.getCity();
        String street = address.getStreet();
        String house = address.getHouse();
        String flat = address.getFlat();
        List<Address> addresses = addressDAO.getAllAddress();
        for (Address addressTemp : addresses) {
            if (addressTemp.getCountry().equals(country)) {
                if (addressTemp.getCity().equals(city)) {
                    if (addressTemp.getStreet().equals(street)) {
                        if (addressTemp.getHouse().equals(house)) {
                            if (addressTemp.getFlat().equals(flat)) {
                                return addressTemp;
                            }
                        }
                    }
                }
            }
        }
        return null;
    }
}
