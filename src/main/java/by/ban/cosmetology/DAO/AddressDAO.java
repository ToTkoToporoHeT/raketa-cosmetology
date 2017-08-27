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
    
    public boolean addAddress(String street, String house, String flat) {
        System.out.println("DAO level addAddress is called");
 
        String qlString = "insert into Address (street, house, flat) values (?,?,?)";
        Query query = entityManager.createNativeQuery(qlString);
        query.setParameter(1, street);
        query.setParameter(2, house);
        query.setParameter(3, flat);
        
        int result = query.executeUpdate();
 
        return result > 0;
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
