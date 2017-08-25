/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Address;
import by.ban.cosmetology.model.Customers;
import by.ban.cosmetology.model.Telephonenumbers;
import by.ban.cosmetology.service.AddressService;
import by.ban.cosmetology.service.TelephonenumbersService;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dazz
 */

@Repository
@Transactional
public class CustomersDAO {
    
    @PersistenceContext
    private EntityManager entityManager;
    @Autowired
    private TelephonenumbersService telephoneNumbersService;
    @Autowired
    private AddressService addressService;
    
    public List<Customers> getAllCustomers(){
        System.out.println("DAO level getAllCustomers is called");
        TypedQuery<Customers> tq = entityManager.createNamedQuery("Customers.findAll", Customers.class);
        
        return tq.getResultList();
    }
    
    public Customers findCustomerByLogin(String login){
        System.out.println("DAO level findCustomerByLogin is called");
        
        return entityManager.find(Customers.class, login);
    }
    
    public boolean updateCustomer(String login, String firstName, String middleName, String lastName, List<Telephonenumbers> telephonenumbersList, Address address) {
        System.out.println("DAO level updateCustomer is called");
 
        String query= "update Customers set firstName =?, middleName = ?, lastName = ?, where login = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, firstName);
        nativeQuery.setParameter(2, middleName);
        nativeQuery.setParameter(3, lastName);
        nativeQuery.setParameter(4, login);        
        telephoneNumbersService.updateListOfTelephones(telephonenumbersList);
        
        
        int result = nativeQuery.executeUpdate();
        return result > 0; // result show how many rows was updated.
    }
    
    public boolean addCustomer(String login, String firstName, String middleName, String lastName, List<Telephonenumbers> telephonenumbersList, Address address) {
        System.out.println("DAO level addCustomer is called");
 
        String qlString = "insert into Customers (login,firstName,middleName,lastName,addressId) values (?,?,?,?,?)";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, login);
        query.setParameter(2, firstName);
        query.setParameter(3, middleName);
        query.setParameter(4, lastName);
        Integer addressId = addressService.addAddress(address.getStreet(), address.getHouse(), address.getFlat());
//Переделать что бы возвращала id созданного или существующего адреса
        if (addressId != 0)
            query.setParameter(5, addressId);
        telephoneNumbersService.addListOfTelephones(telephonenumbersList);
        
        
        int result = query.executeUpdate();
 
        return result > 0;
    }
 
    public boolean deleteCustomer(String customerLogin) {
        System.out.println("DAO level deleteCustomer is called");
 
        String qlString = "delete from Customers where id=?";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, customerLogin);
        
        int result = query.executeUpdate();
 
        return result > 0;
    }
}
