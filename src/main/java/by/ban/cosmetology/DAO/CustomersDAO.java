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
import java.util.Objects;
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

    public List<Customers> getAllCustomers() {
        System.out.println("DAO level getAllCustomers is called");
        TypedQuery<Customers> tq = entityManager.createNamedQuery("Customers.findAll", Customers.class);
        List<Customers> customers = tq.getResultList();
        for (Customers cust : customers) {
            entityManager.persist(cust);
        }
        return customers;
    }

    public Customers findCustomerByLogin(String login) {
        System.out.println("DAO level findCustomerByLogin is called");
        Customers customer = entityManager.find(Customers.class, login);
        return customer;
    }

    public boolean updateCustomer(Customers customer) {
        System.out.println("DAO level updateCustomer is called");

        //Удаляет номер телефона из БД, если он был удален на клиенте
        //(присвоено значение поля telephoneNumber = "")
        List<Telephonenumbers> telephonenumbersList = findCustomerByLogin(customer.getLogin()).getTelephonenumbersList();
        for (Telephonenumbers tel : telephonenumbersList) {
            boolean telExist = false;
            for (Telephonenumbers contrTel : customer.getTelephonenumbersList()) {
                if (Objects.equals(contrTel.getId(), tel.getId())) {
                    telExist = true;
                }
            }
            if (!telExist) {
                Telephonenumbers telForDelete = entityManager.find(Telephonenumbers.class, tel.getId());
                entityManager.remove(telForDelete);
            }
        }
        
        entityManager.merge(customer);
        return true;
    }

    public boolean updateCustomer(String login, String firstName, String middleName, String lastName, List<Telephonenumbers> telephonenumbersList, Address address) {
        System.out.println("DAO level updateCustomer is called");

        String query = "update Customers set firstName =?, middleName = ?, lastName = ?, where login = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, firstName);
        nativeQuery.setParameter(2, middleName);
        nativeQuery.setParameter(3, lastName);
        nativeQuery.setParameter(4, login);
        telephoneNumbersService.updateListOfTelephones(telephonenumbersList);

        int result = nativeQuery.executeUpdate();
        return result > 0; // result show how many rows was updated.
    }

    public boolean addCustomer(Customers customer) {
        System.out.println("DAO level addCustomer is called");

        Address address = customer.getAddressId();
        address = addressService.addAddress(address);
        customer.setAddressId(address);
        entityManager.persist(customer);
        return true;
    }

    public boolean deleteCustomer(String customerLogin) {
        System.out.println("DAO level deleteCustomer is called");

        Customers customer = findCustomerByLogin(customerLogin);
        entityManager.remove(customer);

        TypedQuery<Customers> typedQuery = entityManager.createNamedQuery("Customers.findByAddressId", Customers.class).setParameter("addressId", customer.getAddressId());
        if (typedQuery.getResultList().isEmpty()) {
            entityManager.remove(customer.getAddressId());
        }

        return true;
    }
}
