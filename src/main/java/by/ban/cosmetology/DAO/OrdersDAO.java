/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Orders;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dazz
 */
@Repository
@Transactional
public class OrdersDAO {

    @PersistenceContext
    EntityManager entityManager;

    public List<Orders> getAllOrders() {
        System.out.println("DAO level getAllOrders is called");
        TypedQuery<Orders> tq = entityManager.createNamedQuery("Orders.findAll", Orders.class);
        List<Orders> orders = tq.getResultList();
        for (Orders o : orders) {
            entityManager.persist(o);
        }
        return orders;
    }

    public Orders findOrderById(Integer id) {
        System.out.println("DAO level findOrderById is called");
        Orders order = entityManager.find(Orders.class, id);
        return order;
    }
    
    public boolean updateOrder(Orders order) {
        System.out.println("DAO level updateOrder is called");

        entityManager.merge(order);
        return true;
    }

    public boolean addOrder(Orders order) {
        System.out.println("DAO level addOrder is called");

        entityManager.persist(order);
        return true;
    }

    public boolean deleteOrder(Integer id) {
        System.out.println("DAO level deleteOrder is called");

        Orders order = findOrderById(id);
        entityManager.remove(order);
        return true;
    }
}
