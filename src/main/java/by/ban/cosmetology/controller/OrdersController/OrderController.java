/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller.OrdersController;

import by.ban.cosmetology.model.Customers;
import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.model.Providedservices;
import by.ban.cosmetology.model.Usedmaterials;
import by.ban.cosmetology.model.validators.OrderValidator;
import by.ban.cosmetology.service.CustomersService;
import by.ban.cosmetology.service.MaterialsService;
import by.ban.cosmetology.service.OrdersService;
import by.ban.cosmetology.service.ServicesService;
import by.ban.cosmetology.service.StaffService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.Iterator;
import javax.validation.Valid;
import org.hibernate.Hibernate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.bind.support.SessionStatus;

/**
 *
 * @author dazz
 */
@Controller
@RequestMapping("/orders/order")
@SessionAttributes(types = {Orders.class, String.class}, names = {"orders", "action"})
public class OrderController {

    @Autowired
    private OrdersService ordersService;
    @Autowired
    private CustomersService customersService;
    @Autowired
    private MaterialsService materialsService;
    @Autowired
    private ServicesService servicesService;
    @Autowired
    private StaffService staffService;
    @Autowired
    private OrderValidator orderValidator;

    @InitBinder
    public void initBinder(WebDataBinder binder) {
        binder.addValidators(orderValidator);

        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "prepare_date", new CustomDateEditor(
                dateFormat, true));
    }

    @RequestMapping(value = "/create_page/{actionStr}")
    @Transactional
    public String createOrderPage(Orders order, @PathVariable String actionStr, Model model) {
        System.out.println("Controller level createOrderPage is called for "
                + actionStr + " order");

        String action = actionStr;
        model.addAttribute("action", action);

        if (action.equals("add")) {
            //Если страница открыта для добавления/создания, то создать новый Orders и положить в модель
            model.addAttribute("orders", new Orders());

        } else if (action.equals("edit")) {
            //Если страница открыта для редактирования, найти Orders в базе по id и положить в модель
            if (order.getId() == null) {
                return "redirect:/orders/showAllOrders";
            }

            Orders orders = ordersService.findOrder(order.getId());
            if (!Hibernate.isInitialized(orders.getProvidedservicesList())) {
                Hibernate.initialize(orders.getProvidedservicesList());
            }
            if (Hibernate.isInitialized(order.getUsedmaterialsList())) {
                Hibernate.initialize(orders.getUsedmaterialsList());
            }

            model.addAttribute("orders", orders);
        }

        return "/orders/order";
    }

    @RequestMapping(value = "/show_page/{actionStr}")
    public String showOrderPage(String action) {
        System.out.println("Controller level showOrderPage is called for "
                + action + " order");

        return "/orders/order";
    }

    @RequestMapping("/show_page/selectCustomer")
    public String showSelectPage(Model model) {
        System.out.println("Controller level showSelectPage is called for selectCustomer");

        model.addAttribute("customers", customersService.getAllCustomers());
        return "/customers/selectCustomer";
    }

    @RequestMapping("/selectCustomer")
    public String selectCustomer(Orders orders, String action, @RequestParam Integer customerId, Model model) {
        System.out.println("Controller level selectCustomer is called for " + action + " order");

        Customers customer = customersService.findCustomer(customerId);
        orders.setCustomer(customer);

        return "/orders/order";
    }

    @RequestMapping("/selectServices")
    public String selectServicesFOrder(Orders orders, String action, Model model) {
        System.out.println("Controller level selectServicesFOrder is called for " + action + " order");

        Iterator<Providedservices> iter = orders.getProvidedservicesList().iterator();
        while (iter.hasNext()) {
            Providedservices ps = iter.next();
            Integer servId = ps.getService().getId();
            if (servId == null) {
                iter.remove();
            } else {
                ps.setService(servicesService.findService(servId));
                ps.setCost(ps.getService().getCost());
                ps.setOrder(orders);
            }
        }
        return "/orders/order";
    }

    @RequestMapping("/selectMaterials")
    public String selectMaterialsFOrder(Orders orders, String action, Model model) {
        System.out.println("Controller level selectMaterialsFOrder is called for " + action + " order");

        Iterator<Usedmaterials> iter = orders.getUsedmaterialsList().iterator();
        while (iter.hasNext()) {
            Usedmaterials um = iter.next();
            Integer matId = um.getMaterial().getId();
            if (matId == null) {
                iter.remove();
            } else {
                um.setMaterial(materialsService.findMaterialById(matId));
                um.setCost(um.getMaterial().getCost());
                um.setOrderId(orders);
            }
        }
        return "/orders/order";
    }

    @RequestMapping("/material_delete")
    public String deleteMaterialFOrder(Orders orders, String action, @RequestParam Integer indexUsMat, Model model) {
        System.out.println("Controller level deleteMaterialFOrder is called for " + action + " order");

        if (indexUsMat != null) {
            orders.getUsedmaterialsList().remove(indexUsMat.intValue());
        }
        return "redirect:/orders/order/show_page/add";
    }

    @RequestMapping("/service_delete")
    public String deleteServiceFOrder(Orders orders, String action, @RequestParam Integer indexPrServ, Model model) {
        System.out.println("Controller level deleteServiceFOrder is called for " + action + " order");

        if (indexPrServ != null) {
            orders.getProvidedservicesList().remove(indexPrServ.intValue());
        }
        return "redirect:/orders/order/show_page/add";
    }

    @RequestMapping("/all_materials_delete")
    public String deleteAllMatFOrder(Orders orders, String action, Model model) {
        System.out.println("Controller level deleteAllMatFOrder is called for " + action + " order");

        orders.setUsedmaterialsList(new ArrayList<Usedmaterials>());
        return "/orders/order";
    }

    @RequestMapping("/all_services_delete")
    public String deleteAllServFOrder(Orders orders, String action, Model model) {
        System.out.println("Controller level deleteAllServFOrder is called for " + action + " order");

        orders.setProvidedservicesList(new ArrayList<Providedservices>());
        return "/orders/order";
    }

    @RequestMapping("/{action}")
    public String addOrUpdateOrder(@Valid Orders orders, BindingResult result, 
            Model model, @ModelAttribute("action") String action, SessionStatus sessionStatus) {

        if (result.hasErrors()) {
            return "/orders/order";
        }

        System.out.println("Controller level " + action + "Order is called");

        switch (action) {
            case "add": {
                ordersService.addOrder(orders);
                break;
            }
            case "edit": {
                ordersService.updateOrder(orders);
                break;
            }
        }

        if (!sessionStatus.isComplete()) {
            sessionStatus.setComplete();
        }
        return "redirect:/orders/showAllOrders";
    }

    @RequestMapping("/delete")
    public String deleteOrder(@RequestParam Integer id) {
        System.out.println("Controller level deleteOrder is called");

        ordersService.deleteOrder(id);
        return "redirect:/orders/showAllOrders";
    }

    @RequestMapping("/{action}/cancel")
    public String cancelOrder(String action, SessionStatus sessionStatus) {
        System.out.println("Controller level cancel" + action + "Order is called");

        if (!sessionStatus.isComplete()) {
            sessionStatus.setComplete();
        }

        return "redirect:/orders/showAllOrders";
    }
}
