/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Customers;
import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.model.Providedservices;
import by.ban.cosmetology.model.Services;
import by.ban.cosmetology.model.Staff;
import by.ban.cosmetology.model.Usedmaterials;
import by.ban.cosmetology.model.VisitDate;
import by.ban.cosmetology.service.Utility.DateUtil;
import java.time.LocalDate;
import java.util.Date;
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
public class OrdersDAO {

    @PersistenceContext
    private EntityManager entityManager;

    public List<Orders> getAllOrders() {
        System.out.println("DAO level getAllOrders is called");
        TypedQuery<Orders> tq = entityManager.createNamedQuery("Orders.findAll", Orders.class);
        List<Orders> orders = tq.getResultList();

        for (Orders order : orders) {
            initializeData(order);
        }

        return orders;
    }

    public List<Orders> getAllOrders(Staff manager) {
        System.out.println("DAO level getAllOrders is called");

        TypedQuery<Orders> tq = entityManager.createNamedQuery("Orders.findByStaff", Orders.class);
        tq.setParameter("manager", manager);
        List<Orders> orders = tq.getResultList();

        for (Orders order : orders) {
            initializeData(order);
        }

        return orders;
    }
    
    public List<Orders> getMontOrders(Date date) {
        System.out.println("DAO level getMonthOrders is called");
        
        Date startDateOfMonth = DateUtil.getStartOfMonth(date);
        Date endDateOfMonth = DateUtil.getEndOfMonth(date);
        
        TypedQuery<Orders> tq = entityManager
                .createNamedQuery("Orders.getByDateInterval", Orders.class);
        tq.setParameter("startDate", startDateOfMonth);
        tq.setParameter("endDate", endDateOfMonth);
        List<Orders> orders = tq.getResultList();
        
        for (Orders order : orders) {
            initializeData(order);
        }
        
        return orders;
    }

    public Orders findOrder(Integer id) {
        System.out.println("DAO level findOrder by Id is called");

        Orders order = entityManager.find(Orders.class, id);

        return order;
    }

    public Orders findOrder(String check_number) {
        System.out.println("DAO level findOrder by Number is called");

        TypedQuery<Orders> tq = entityManager.createNamedQuery("Orders.findByCheckNumber", Orders.class);
        tq.setParameter("check_number", check_number);
        List<Orders> ordersList = tq.getResultList();

        if (ordersList.size() > 0) {
            return ordersList.get(0);
        }
        return null;
    }

    public boolean updateOrder(Orders order) {
        System.out.println("DAO level updateOrder is called");

        clearRedudantData(order);
        entityManager.merge(order);
        return true;
    }

    public boolean addOrder(Orders order) {
        System.out.println("DAO level addOrder is called");

        entityManager.persist(order);
        if (order.getUsedmaterialsList() != null) {
            for (Usedmaterials um : order.getUsedmaterialsList()) {
                entityManager.merge(um.getMaterial());
            }
        }

        return true;
    }

    public boolean deleteOrder(Orders order) {
        System.out.println("DAO level deleteOrder is called");
        
        
        removeDeleted(order);
        entityManager.remove(order);

        return true;
    }

    //Удаляет из базы данных использованные материалы, оказанные услуги и
    //даты посещений, которые были удалены во время редактирования договора
    private void clearRedudantData(Orders order) {
        Orders orderOld = findOrder(order.getId());
        //удаляет использованые миатериалы
        if (orderOld.getUsedmaterialsList() != null) {
            for (Usedmaterials usMat : orderOld.getUsedmaterialsList()) {
                if (!order.getUsedmaterialsList().contains(usMat)) {
                    double usMatCount = usMat.getCount();
                    double matCountOld = usMat.getMaterial().getCount();
                    usMat.getMaterial().setCount(matCountOld + usMatCount);//откатывает количество материалов на складе
                    entityManager.remove(usMat);
                }
            }
        }
        //удаляет оказанные услуги
        if (orderOld.getProvidedservicesList() != null) {
            for (Providedservices prServ : orderOld.getProvidedservicesList()) {
                if (!order.getProvidedservicesList().contains(prServ)) {
                    entityManager.remove(prServ);
                }
            }
        }
        
        //Удаляет даты посещений
        if (orderOld.getVisitDatesList() != null) {
            for (VisitDate visitDate : orderOld.getVisitDatesList()) {
                if (!order.getVisitDatesList().contains(visitDate)) {
                    entityManager.remove(visitDate);
                }
            }
        }        
    }

    //Удаляет помеченные на удаление и нигде больше не используемые ресурсы, кроме как в удаляемом договоре
    //так как если удалить их из БД до удаления договора, договор будет содержать не полные сведения
    private void removeDeleted(Orders order) {
        removeDeletedMaterials(order.getUsedmaterialsList());
        removeDeletedServices(order.getProvidedservicesList());
        removeDeletedCustomer(order.getCustomer());
        removeDeletedStaff(order.getManager());
    }

    private int removeDeletedMaterials(List<Usedmaterials> usedmaterialsList) {
        int countRemoved = 0;

        for (Usedmaterials um : usedmaterialsList) {
            if (um.getMaterial().isForDelete() && um.getMaterial().getUsedmaterialsList().size() <= 1) {
                Materials material = entityManager.find(Materials.class, um.getMaterial().getId());
                entityManager.remove(material);

                System.out.println(material.toString() + " был удален.");
                countRemoved++;
            }
        }

        return countRemoved;
    }

    private int removeDeletedServices(List<Providedservices> providedservicesList) {
        int countRemoved = 0;

        for (Providedservices ps : providedservicesList) {
            if (ps.getService().isForDelete() && ps.getService().getProvidedservicesList().size() <= 1) {
                Services service = entityManager.find(Services.class, ps.getService().getId());
                entityManager.remove(service);

                System.out.println(service.toString() + " был удален.");
                countRemoved++;
            }
        }

        return countRemoved;
    }

    private boolean removeDeletedCustomer(Customers customer) {
        if (!customer.isEnabled() && customer.getOrdersList().size() <= 1) {
            entityManager.remove(customer);

            System.out.println(customer.toString() + " был удален.");
            return true;
        }
        return false;
    }

    private boolean removeDeletedStaff(Staff manager) {
        if (!manager.isEnabled() && manager.getOrdersList().size() <= 1) {
            entityManager.remove(manager);

            System.out.println(manager.toString() + " был удален.");
            return true;
        }
        return false;
    }

    private void initializeData(Orders order) {
        if (!Hibernate.isInitialized(order.getProvidedservicesList())) {
            Hibernate.initialize(order.getProvidedservicesList());
        }
        if (!Hibernate.isInitialized(order.getUsedmaterialsList())) {
            Hibernate.initialize(order.getUsedmaterialsList());
        }
        if (!Hibernate.isInitialized(order.getVisitDatesList())) {
            Hibernate.initialize(order.getVisitDatesList());
        }
    }
}
