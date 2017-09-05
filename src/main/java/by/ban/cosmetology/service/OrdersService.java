/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.OrdersDAO;
import by.ban.cosmetology.model.Orders;
import java.util.List;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 *
 * @author dazz
 */
@Service
public class OrdersService {

    @Autowired
    OrdersDAO ordersDAO;

    public List<Orders> getAllOrders() {
        System.out.println("Service level getAllOrders is called");
        return ordersDAO.getAllOrders();
    }

    public Orders findOrderById(Integer id) {
        System.out.println("Service level findOrderById is called");
        return ordersDAO.findOrderById(id);
    }

    public boolean updateOrder(Orders order) {
        System.out.println("Service level updateOrder is called");
        return ordersDAO.updateOrder(order);
    }

    public boolean addOrder(Orders order) {
        System.out.println("Service level addOrder is called");
        return ordersDAO.addOrder(order);
    }

    public boolean deleteOrder(Integer id) {
        System.out.println("Service level deleteOrder is called");
        return ordersDAO.deleteOrder(id);
    }
}
