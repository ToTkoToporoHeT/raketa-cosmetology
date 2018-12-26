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
import java.util.List;
import java.util.Objects;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
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
    private AddressService addressService;

    public List<Customers> getAllCustomers() {
        System.out.println("DAO level getAllCustomers is called");

        TypedQuery<Customers> tq = entityManager.createNamedQuery("Customers.findAllActive", Customers.class);
        tq.setParameter("enabled", true);

        return tq.getResultList();
    }

    public Customers findCustomer(Integer id) {
        System.out.println("DAO level findCustomer by Id is called");

        return entityManager.find(Customers.class, id);
    }

    /*
    //находит клиента по e-mail(логину)
    public Customers findCustomer(String login) {
        System.out.println("DAO level findCustomer by Login is called");

        TypedQuery<Customers> tq = entityManager.createNamedQuery("Customers.findByLogin", Customers.class);
        tq.setParameter("login", login);
        List<Customers> customersList = tq.getResultList();
        if (customersList.size() > 0) {
            return customersList.get(0);
        }
        return null;
    }*/

    public boolean updateCustomer(Customers customer) {
        System.out.println("DAO level updateCustomer is called");

        //Удаляет номер телефона из БД, если он был удален на клиенте
        //(присвоено значение поля telephoneNumber = "")
        List<Telephonenumbers> telephonenumbersList = findCustomer(customer.getId()).getTelephonenumbersList();
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

    public boolean addCustomer(Customers customer) {
        System.out.println("DAO level addCustomer is called");

        Address address = customer.getAddressId();
        address = addressService.addAddress(address);
        customer.setAddressId(address);
        entityManager.persist(customer);
        return true;
    }

    public boolean deleteCustomer(Integer id) {
        System.out.println("DAO level deleteCustomer is called");

        Customers customer = findCustomer(id);
        if (customer.getOrdersList().size() > 0) {
            customer.setEnabled(false);
        } else {
            entityManager.remove(customer);
            TypedQuery<Customers> typedQuery = entityManager.createNamedQuery("Customers.findByAddressId", Customers.class).setParameter("addressId", customer.getAddressId());
            if (typedQuery.getResultList().isEmpty()) {
                entityManager.remove(customer.getAddressId());
            }
        }
        
        return customer.isEnabled();
    }
}
