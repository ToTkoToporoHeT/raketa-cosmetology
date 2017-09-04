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
import java.util.Iterator;
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

        customer = prepareCustomer(customer);
        //Выясняет был ли изменен адрес и изменяет его, если это так
        Address address = customer.getAddressId();
        if (addressService.addressIsChanged(address)) {
            int oldAddressId = address.getId();
            address = addressService.updateAddress(address);
            customer.setAddressId(address);
            custromersDAO.updateCustomer(customer);
            addressService.deleteAddress(oldAddressId);
        } else {
            custromersDAO.updateCustomer(customer);
        }
        return true;
    }

    public boolean addCustomer(Customers customer) {
        System.out.println("Service level addCustomer is called");

        customer = prepareCustomer(customer);
        custromersDAO.addCustomer(customer);
        return true;
    }

    public boolean deleteCustomer(String customerLogin) {
        System.out.println("Service level deleteCustomer is called");
        return custromersDAO.deleteCustomer(customerLogin);
    }

    //Добавляет полученный Customer во все Telephonenumbers 
    //из telephonenumbersList и удаляет пустые телефонные номера
    private Customers prepareCustomer(Customers customer) {
        List<Telephonenumbers> telephonenumbersList = customer.getTelephonenumbersList();
        for (Iterator<Telephonenumbers> it = telephonenumbersList.iterator(); it.hasNext();) {
            Telephonenumbers tel = it.next();
            if (tel.getTelephoneNumber().isEmpty()) {
                it.remove();
            } else {
                tel.setCustomer(customer);
            }
        }
        customer.setTelephonenumbersList(telephonenumbersList);

        return customer;
    }
}
