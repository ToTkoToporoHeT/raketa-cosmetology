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
    
    public List<Address> getAllAddress(){
        System.out.println("DAO level getAllAddress is called");
        
        TypedQuery<Address> tq = entityManager.createNamedQuery("Address.findAll", Address.class);
        
        return tq.getResultList();
    }
    
    public Address findAddressById(int id){
        System.out.println("DAO level findAddressById is called");
        
        return entityManager.find(Address.class, id);
    }
    
    public Address findAddressWithoutId(Address address){
        System.out.println("DAO level findAddress is called");
        
        String country = address.getCountry();
        String city = address.getCity();
        String street = address.getStreet();
        String house = address.getHouse();
        String flat = address.getFlat();
        List<Address> addresses = getAllAddress();
        for (Address addressTemp : addresses) {
            if (addressTemp.getCountry().equals(country))
                if (addressTemp.getCity().equals(city))
                    if (addressTemp.getStreet().equals(street))
                        if (addressTemp.getHouse().equals(house))
                            if (addressTemp.getFlat().equals(flat))
                                return addressTemp;                
        }
        return null;
    }
    
    public boolean updateAddress(Integer id, String street, String house, String flat) {
        System.out.println("DAO level updateAddress is called");
 
        String query= "update Address set street = ?, house = ?, flat = ? where id = ?";
        Query nativeQuery = entityManager.createNativeQuery(query);
        nativeQuery.setParameter(1, street);
        nativeQuery.setParameter(2, house);
        nativeQuery.setParameter(3, flat);
        nativeQuery.setParameter(4, id);
        int result = nativeQuery.executeUpdate();
        return result > 0; // result show how many rows was updated.
    }
    
    public Address addAddress(Address address) {
        System.out.println("DAO level addAddress is called");
 
        entityManager.persist(address);
        
        return address;
    }
 
    public boolean deleteAddress(int idAddress) {
        System.out.println("DAO level deleteAddress is called");
 
        String qlString = "delete from Address where id=?";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, idAddress);
        int result = query.executeUpdate();
 
        return result > 0;
    }
}
