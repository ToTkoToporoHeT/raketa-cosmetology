/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Address;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.hibernate.Hibernate;
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

    public Address initializeCustomersList(Address address) {
        System.out.println("DAO level initializeCustomersList is called");

        Address addressTemp = findAddressById(address.getId());
        Hibernate.initialize(addressTemp.getCustomersList());
        address.setCustomersList(addressTemp.getCustomersList());
        return address;
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

    public boolean deleteAddress(Address address) {
        System.out.println("DAO level deleteAddress is called");

        address = findAddressById(address.getId());
        if (address.getCustomersList().size() < 1) {
            entityManager.remove(address);
            return true;
        }
        return false;
    }
    
    public boolean deleteAddress(int addressId) {
        System.out.println("DAO level deleteAddress is called");

        Address address = findAddressById(addressId);
        if (address.getCustomersList().size() < 1) {
            entityManager.remove(address);
            return true;
        }
        return false;
    }

    public boolean deleteUnusedAddresses() {
        int countUnusedAddress = 0;
        for (Address address : getAllAddress()) {
            if (address.getCustomersList().size() < 1) {
                entityManager.remove(address);
                countUnusedAddress++;
            }
        }

        return countUnusedAddress > 0;
    }
}
