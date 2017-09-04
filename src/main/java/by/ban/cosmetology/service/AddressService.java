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

    public List<Address> getAllAddress() {
        System.out.println("Service level getAllAddress is called");
        return addressDAO.getAllAddress();
    }

    public Address findAddressById(int id) {
        System.out.println("Service level findAddressById is called");
        return addressDAO.findAddressById(id);
    }

    public Address updateAddress(Address address) {
        System.out.println("Service level updateAddress is called");

        int oldAddressId = address.getId();
        address.setId(null);
        address = addAddress(address);
        return address;
    }

    public Address addAddress(Address address) {
        System.out.println("Service level addAddress is called");

        Address addressResult = findAddressWithoutId(address);
        if (addressResult == null) {
            addressResult = addressDAO.addAddress(address);
        }

        return addressResult;
    }

    //Удаление адреса, если он не используется ни с одним пользователем
    public boolean deleteAddress(Address address) {
        System.out.println("Service level deleteAddress is called");
        
        return addressDAO.deleteAddress(address);
    }
    
    //Удаление адреса, если он не используется ни с одним пользователем
    public boolean deleteAddress(int addressId) {
        System.out.println("Service level deleteAddress is called");
        
        return addressDAO.deleteAddress(addressId);
    }

    //Удаляет неиспользуемые адреса
    public boolean deleteUnusedAddresses() {
        System.out.println("Service level deleteUnusedAddresses is called");
        
        return addressDAO.deleteUnusedAddresses();        
    }

    public boolean addressIsChanged(Address address) {
        return !address.equals(findAddressById(address.getId()));
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
                            if (addressTemp.getFlat() != null) {
                                if (addressTemp.getFlat().equals(flat)) {
                                    return addressTemp;
                                }
                            } else {
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
