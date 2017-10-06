/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.DAO;

import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.model.Providedservices;
import by.ban.cosmetology.model.Services;
import by.ban.cosmetology.model.Usedmaterials;
import java.util.Iterator;
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

        clearRedudantUMandPS(order);
        entityManager.merge(order);
        return true;
    }

    public boolean addOrder(Orders order) {
        System.out.println("DAO level addOrder is called");

        entityManager.persist(order);
        return true;
    }

    public boolean deleteOrder(Orders order) {
        System.out.println("DAO level deleteOrder is called");
        
        entityManager.remove(order);
        for (Usedmaterials um : order.getUsedmaterialsList()) {
            if (um.getMaterial().isForDelete() && um.getMaterial().getUsedmaterialsList().size() <= 1) {
                Materials material = entityManager.find(Materials.class, um.getMaterial().getId());
                entityManager.remove(material);
            }
        }
        for (Providedservices ps : order.getProvidedservicesList()) {
            if (ps.getService().isForDelete() && ps.getService().getProvidedservicesList().size() <= 1) {
                Services service = entityManager.find(Services.class, ps.getService().getId());
                entityManager.remove(service);
            }
        }
        return true;
    }

    //Удаляет из базы данных использованные материалы и оказанные услуги
    //которые были удалены во время редактирования договора
    private void clearRedudantUMandPS(Orders order) {
        Orders orderOld = findOrderById(order.getId());
        for (Usedmaterials usMat : orderOld.getUsedmaterialsList()) {
            if (!order.getUsedmaterialsList().contains(usMat)) {
                int usMatCount = usMat.getCount();
                int matCountOld = usMat.getMaterial().getCount();
                usMat.getMaterial().setCount(matCountOld + usMatCount);//откатывает количество материалов на складе
                entityManager.remove(usMat);
            }
        }
        for (Providedservices prServ : orderOld.getProvidedservicesList()) {
            if (!order.getProvidedservicesList().contains(prServ)) {
                entityManager.remove(prServ);
            }
        }
    }
}
