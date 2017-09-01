/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.CustomersDAO;
import by.ban.cosmetology.model.Address;
import by.ban.cosmetology.model.Customers;
import by.ban.cosmetology.model.Telephonenumbers;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dazz
 */
@Service
public class CustomersService {

    @Autowired
    private CustomersDAO custromersDAO;
    @Autowired
    private AddressService addressService;

    public List<Customers> getAllCustomers() {
        System.out.println("Service level getAllCustomers is called");
        return custromersDAO.getAllCustomers();
    }

    public Customers findCustomerByLogin(String login) {
        System.out.println("Service level findCustomerByLogin is called");
        return custromersDAO.findCustomerByLogin(login);
    }

    public boolean updateCustomer(Customers customer) {
        System.out.println("Service level updateCustomer is called");

        //Добавляет полученный Customer во все Telephonenumbers 
        //из telephonenumbersList и удаляет пустые телефонные номера
        List<Telephonenumbers> telephonenumbersList = customer.getTelephonenumbersList();
        for (Telephonenumbers t : telephonenumbersList) {
            if (t.getTelephoneNumber().isEmpty()) {
                telephonenumbersList.remove(t);
            } else {
                t.setCustomer(customer);
            }
        }
        customer.setTelephonenumbersList(telephonenumbersList);
        
        //Выясняет был ли изменен адрес и изменяет его, если это так
        Address address = customer.getAddressId();
        if (addressService.addressIsChanged(address)){
            address = addressService.updateAddress(address);
            customer.setAddressId(address);
        }
                
        custromersDAO.updateCustomer(customer);
        return true;
    }

    public boolean addCustomer(Customers customer) {
        System.out.println("Service level addCustomer is called");

        //Добавляет полученный Customer во все Telephonenumbers из telephonenumbersList
        List<Telephonenumbers> telephonenumbersList = customer.getTelephonenumbersList();
        for (Telephonenumbers t : telephonenumbersList) {
            if (t.getTelephoneNumber().isEmpty()) {
                telephonenumbersList.remove(t);
            } else {
                t.setCustomer(customer);
            }
        }
        customer.setTelephonenumbersList(telephonenumbersList);

        custromersDAO.addCustomer(customer);
        return true;
    }

    public boolean deleteCustomer(String customerLogin) {
        System.out.println("Service level deleteCustomer is called");
        return custromersDAO.deleteCustomer(customerLogin);
    }
}
