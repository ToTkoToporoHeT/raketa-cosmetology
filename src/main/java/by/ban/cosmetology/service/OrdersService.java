/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.OrdersDAO;
import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.model.Providedservices;
import by.ban.cosmetology.model.Services;
import by.ban.cosmetology.model.Usedmaterials;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 *
 * @author dazz
 */
@Service
public class OrdersService {

    @Autowired
    OrdersDAO ordersDAO;
    @Autowired
    UsedmaterialsService usedmaterialsService;

    private enum CalcAction {
        PLUS, MINUS, EDIT
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

        calculateMaterialsBalance(order, CalcAction.MINUS);
        return ordersDAO.addOrder(order);
    }

    public boolean updateOrder(Orders order) {
        System.out.println("Service level updateOrder is called");

        calculateMaterialsBalance(order, CalcAction.EDIT);
        return ordersDAO.updateOrder(order);
    }

    @Transactional
    public void deleteOrder(Integer id) {
        System.out.println("Service level deleteOrder is called");

        Orders order = ordersDAO.findOrderById(id);
        if (!Hibernate.isInitialized(order.getUsedmaterialsList())) {
            Hibernate.initialize(order.getUsedmaterialsList());
        }
        calculateMaterialsBalance(order, CalcAction.PLUS);
        ordersDAO.deleteOrder(order);
    }

    private List<Usedmaterials> calculateMaterialsBalance(Orders order, CalcAction calcAction) {
        List<Usedmaterials> usedmaterialsList = order.getUsedmaterialsList();
        if (usedmaterialsList == null) {
            return null;
        }
        //Переменная определяющая дейсвие сложения или вычитания будет производиться
        Integer sign;
        sign = (calcAction == calcAction.MINUS ? -1 : 1);

        if (calcAction == CalcAction.EDIT) {
            for (Usedmaterials um : usedmaterialsList) {
                Integer umId = um.getId();
                int materialCount = um.getMaterial().getCount();
                if (umId == null) {
                    materialCount -= um.getCount();
                } else {
                    int oldCountUseMat = usedmaterialsService.getUsMatCount(umId);
                    int newCountUseMat = um.getCount();
                    int diffCountUseMat = oldCountUseMat - newCountUseMat;
                    materialCount += diffCountUseMat;
                }
                Assert.isFalse(materialCount < 0, "Отрицательный остаток материала - " + um.getMaterial()
                        + ". Количество материалов на складе не может равняться " + materialCount + ".");
                um.getMaterial().setCount(materialCount);
            }
        } else {
            for (Usedmaterials um : usedmaterialsList) {
                int materialCount = um.getMaterial().getCount();
                int useMatCount = um.getCount();
                materialCount += useMatCount * sign;
                Assert.isFalse(materialCount < 0, "Отрицательный остаток материала - " + um.getMaterial()
                        + ". Количество материалов на складе не может равняться " + materialCount + ".");
                um.getMaterial().setCount(materialCount);
            }
        }

        return usedmaterialsList;
    }
}
