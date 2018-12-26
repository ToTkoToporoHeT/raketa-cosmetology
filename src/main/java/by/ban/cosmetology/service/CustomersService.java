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

    public Customers findCustomer(Integer id) {
        System.out.println("Service level findCustomer by Id is called");
        return custromersDAO.findCustomer(id);
    }

    public boolean updateCustomer(Customers customer) {
        System.out.println("Service level updateCustomer is called");

        setEmptyLogin(customer);
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

        setEmptyLogin(customer);
        customer = prepareCustomer(customer);
        custromersDAO.addCustomer(customer);
        return true;
    }

    public boolean deleteCustomer(Integer id) {
        System.out.println("Service level deleteCustomer is called");
        return custromersDAO.deleteCustomer(id);
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

    //Если логин(e-mail) пуст, то сделать его null
    private void setEmptyLogin(Customers customer) {
        if (customer.getLogin().isEmpty()) {
            customer.setLogin(null);
        }
    }
}
