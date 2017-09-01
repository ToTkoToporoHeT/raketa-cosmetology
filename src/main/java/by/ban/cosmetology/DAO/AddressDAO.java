/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Address;
import java.util.Iterator;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dazz
 */
@Repository
@Transactional
public class AddressDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Address> getAllAddress() {
        System.out.println("DAO level getAllAddress is called");

        TypedQuery<Address> tq = entityManager.createNamedQuery("Address.findAll", Address.class);

        return tq.getResultList();
    }

    public Address findAddressById(int id) {
        System.out.println("DAO level findAddressById is called");

        return entityManager.find(Address.class, id);
    }   

    public Address updateAddress(Address address) {
        System.out.println("DAO level updateAddress is called");

        entityManager.persist(address);        
        return address;
    }

    public Address addAddress(Address address) {
        System.out.println("DAO level addAddress is called");

        entityManager.persist(address);

        return address;
    }

    public void deleteAddress(Address address) {
        System.out.println("DAO level deleteAddress is called");

        entityManager.remove(addAddress(address));
    }
}
