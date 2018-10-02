/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.service;

import by.ban.cosmetology.DAO.OrdersDAO;
import by.ban.cosmetology.controller.OrdersController.OrderController;
import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.model.Providedservices;
import by.ban.cosmetology.model.Staff;
import by.ban.cosmetology.model.Usedmaterials;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.eclipse.persistence.jpa.jpql.Assert;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
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
    StaffService staffService;
    @Autowired
    UsedmaterialsService usedmaterialsService;

    private enum CalcAction {
        PLUS, MINUS, EDIT
    }

    public List<Orders> getAllOrders() {
        System.out.println("Service level getAllOrders is called");

        Staff manager = staffService.findStaffByLogin(getStaffLogin());

        if (manager.getLogin().equals("root")) {
            return ordersDAO.getAllOrders();
        }

        return ordersDAO.getAllOrders(manager);
    }

    public Orders findOrder(Integer id) {
        System.out.println("Service level findOrderById is called");

        return ordersDAO.findOrder(id);
    }

    public Orders findOrder(String number) {
        System.out.println("Service level findOrder by Number is called");

        return ordersDAO.findOrder(number);
    }

    public boolean addOrder(Orders order) {
        System.out.println("Service level addOrder is called");

        setEmptyNumber(order);
        order.setManager(staffService.findStaffByLogin(getStaffLogin()));
        calculateMaterialsBalance(order, CalcAction.MINUS);

        return ordersDAO.addOrder(order);
    }

    public boolean updateOrder(Orders order) {
        System.out.println("Service level updateOrder is called");

        setEmptyNumber(order);
        calculateMaterialsBalance(order, CalcAction.EDIT);

        return ordersDAO.updateOrder(order);
    }

    @Transactional
    public void deleteOrder(Integer id) {
        System.out.println("Service level deleteOrder is called");

        Orders order = ordersDAO.findOrder(id);

        if (!Hibernate.isInitialized(order.getUsedmaterialsList())) {
            Hibernate.initialize(order.getUsedmaterialsList());
        }

        calculateMaterialsBalance(order, CalcAction.PLUS);
        ordersDAO.deleteOrder(order);
    }
    
    public Double getOrderSum(Orders order) {
        Double materialsSum = 0.0;
        Double servicesSum = 0.0;
        
        for (Usedmaterials usedmaterial : order.getUsedmaterialsList())
            materialsSum += usedmaterial.getCost() * usedmaterial.getCount();
        
        for (Providedservices providedservice : order.getProvidedservicesList())
            servicesSum += providedservice.getCost() * providedservice.getRate();
        
        return materialsSum + servicesSum;
    }
    
    //формирует строку запроса в PHP скрипт для формирования/печати Excel документа
    public String getOpenInExceURL(Orders order) {
        String phpScriptURL = "192.168.1.16:8585/phpOrderPrinter/openOrderInExcel.php?";

        try {
            
            phpScriptURL += "staffFullName="    + getURLString(order.getManager().toString())                  + "&";
            phpScriptURL += "number="           + getURLString(order.getNumber())                              + "&";
            phpScriptURL += "date="             + getURLString(order.getPrepare_date().toString())             + "&";
            phpScriptURL += "clientFullName="   + getURLString(order.getCustomer().toString())                 + "&";
            phpScriptURL += "address="          + getURLString(order.getCustomer().getAddressId().toString())  + "&";
            phpScriptURL += getServicesURL(order.getProvidedservicesList());
            phpScriptURL += getMaterialsURL(order.getUsedmaterialsList());
            phpScriptURL += "sum=" + getOrderSum(order);
            
        } catch (UnsupportedEncodingException ex) {
            Logger.getLogger(OrderController.class.getName()).log(Level.SEVERE, null, ex);
        }
           
        return phpScriptURL;
    }

        //вычисляет кол-во материалов оставшихся на складе
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

        //Получает логин менеджера из Spring security
        private String getStaffLogin() {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            return auth.getName();
        }

        //если номер - пустая строка, то 
        private void setEmptyNumber(Orders order) {
            if (order.getNumber().isEmpty()) {
                order.setNumber(null);
            }
        }
        
        private String getServicesURL(List<Providedservices> pList) throws UnsupportedEncodingException {
            String result = "";
            for (Providedservices ps : pList) {
                result += "service[]=" 
                        + getURLString(ps.getService().getName())  + "_" 
                        + ps.getRate()                             + "_" 
                        + ps.getCost()                             + "&";
            }
            
            return result;
        }
        
        private String getMaterialsURL(List<Usedmaterials> uList) throws UnsupportedEncodingException {
            String result = "";
            for (Usedmaterials um : uList) {
                result += "material[]=" 
                        + getURLString(um.getMaterial().getName())            + "_" 
                        + getURLString(um.getMaterial().getUnit().getUnit())  + "_" 
                        + um.getCount()                                       + "_" 
                        + um.getCost()                                        + "&";
            }
            
            return result;
        }
    
        private String getURLString(String str) throws UnsupportedEncodingException {
            return URLEncoder.encode(str, "utf-8");
        }
}
