/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.OrdersDAO;
import by.ban.cosmetology.DAO.UsedmaterialsDAO;
import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.model.Usedmaterials;
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
    @Autowired
    UsedmaterialsDAO usedmaterialsDAO;
    
    private enum CalcAction {
        PLUS, MINUS
    }

    public List<Orders> getAllOrders() {
        System.out.println("Service level getAllOrders is called");
        return ordersDAO.getAllOrders();
    }

    public Orders findOrderById(Integer id) {
        System.out.println("Service level findOrderById is called");
        return ordersDAO.findOrderById(id);
    }

    public boolean addOrder(Orders order) {
        System.out.println("Service level addOrder is called");
        List<Usedmaterials> usedmaterialsList = calculateMaterialsBalance(order.getUsedmaterialsList(), CalcAction.MINUS);
        order.setUsedmaterialsList(usedmaterialsList);
        return ordersDAO.addOrder(order);
    }

    public boolean updateOrder(Orders order) {
        System.out.println("Service level updateOrder is called");
        
        //Доделать
        List<Usedmaterials> usedmaterialsList = calculateMaterialsBalance(order.getUsedmaterialsList(), CalcAction.MINUS);
        order.setUsedmaterialsList(usedmaterialsList);
        return ordersDAO.updateOrder(order);
    }

    public boolean deleteOrder(Integer id) {
        System.out.println("Service level deleteOrder is called");
        
        Orders order = ordersDAO.findOrderById(id);
        List<Usedmaterials> usedmaterialsList = calculateMaterialsBalance(order.getUsedmaterialsList(), CalcAction.PLUS);
        order.setUsedmaterialsList(usedmaterialsList);
        return ordersDAO.deleteOrder(order);
    }
    
    private List<Usedmaterials> calculateMaterialsBalance(List<Usedmaterials> usedmaterialsList, CalcAction calcAction) {
        //Переменная определяющая дейсвие сложения или вычитания будет производиться
        Integer sign;
        sign = (calcAction == calcAction.MINUS ? -1 : 1);
        
        for (Usedmaterials um : usedmaterialsList) {
            Integer materialInStore = um.getMaterial().getCount();
            Integer useMatCount = um.getCount();
            um.getMaterial().setCount(materialInStore + (useMatCount * sign));
        }
        
        return usedmaterialsList;
    }
}
