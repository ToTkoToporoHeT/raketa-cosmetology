/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.controller.OrdersController;

import by.ban.cosmetology.model.Customers;
import by.ban.cosmetology.model.Materials;
import by.ban.cosmetology.model.Orders;
import by.ban.cosmetology.model.Providedservices;
import by.ban.cosmetology.model.Services;
import by.ban.cosmetology.model.Usedmaterials;
import by.ban.cosmetology.service.CustomersService;
import by.ban.cosmetology.service.MaterialsService;
import by.ban.cosmetology.service.OrdersService;
import by.ban.cosmetology.service.ServicesService;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import org.springframework.util.Assert;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
@SessionAttributes(types = Orders.class)
public class AddOrderController {

    @Autowired
    OrdersService ordersService;
    @Autowired
    CustomersService customersService;
    @Autowired
    MaterialsService materialsService;
    @Autowired
    ServicesService servicesService;

    @ModelAttribute("order")
    public Orders populateOrder() {
        return new Orders();
    }

    @ModelAttribute("action")
    public String getAction() {
        return "add";
    }
    
    @InitBinder
    public void initBinder(WebDataBinder binder){
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        dateFormat.setLenient(false);
        binder.registerCustomEditor(Date.class, "date", new CustomDateEditor(
                dateFormat, true));
    }

    @RequestMapping(value = "/create_page/add")
    public String createOrderPage(Model model) {
        System.out.println("Controller level showOrderPage is called for add order");

        Orders order = new Orders();
        model.addAttribute("order", order);

        Assert.notNull(order);
        return "/orders/order";
    }

    @RequestMapping(value = "/show_page/add")
    public String showOrderPage(Orders order, Model model) {
        System.out.println("Controller level showOrderPage is called for add order");

        return "/orders/order";
    }
    
    @RequestMapping("/show_page/{page}")
    public String showSelectPage(Orders order, Model model, @PathVariable String page){
        System.out.println("Controller level showSelectPage is called");
        
        String packageView;
        switch (page){
            case "selectCustomer":{
                model.addAttribute("customers", customersService.getAllCustomers());
                packageView = "/customers/";
                break;
            }
            case "selectMaterials":{
                return "redirect:/usedMaterials/show_page/selectMaterials";
            }
            case "selectServices":{
                return "redirect:/providedServices/show_page/selectServices";
            }
            default:{
                return "redirect:/orders/order/show_page/add";
            }
        }
        
        return packageView + page;
    }
    
    @RequestMapping("/selectCustomer")
    public String selectCustomer(Orders order, @ModelAttribute Customers customer, Model model) {
        System.out.println("Controller level selectCustomer is called for add order");

        customer = customersService.findCustomerByLogin(customer.getLogin());
        order.setCustomer(customer);
        Assert.notNull(order.getCustomer());
        model.addAttribute("order", order);
        return "/orders/order";
    }

    @RequestMapping("/selectMaterials")
    public String selectMaterialsFOrder(Orders order, Model model) {
        System.out.println("Controller level selectMaterialsFOrder is called for add order");

        List<Usedmaterials> resultUsedmaterialsList = new ArrayList<>();
        for (Usedmaterials u : order.getUsedmaterialsList()) {
            Integer usedMaterialId = u.getMaterial().getId();
            if (usedMaterialId != null) {
                Materials usedMaterial = materialsService.findMaterialById(usedMaterialId);
                Usedmaterials usedmaterial = new Usedmaterials(u.getCount(), order, usedMaterial);
                resultUsedmaterialsList.add(usedmaterial);
            }
        }
        order.setUsedmaterialsList(resultUsedmaterialsList);
        Assert.notNull(order.getUsedmaterialsList());
        model.addAttribute("order", order);
        return "/orders/order";
    }

    @RequestMapping("/selectServices")
    public String selectServicesFOrder(Orders order, Model model) {
        System.out.println("Controller level selectServicesFOrder is called for add order");

        List<Providedservices> resultProvidedservicesList = new ArrayList();
        for (Providedservices p : order.getProvidedservicesList()) {
            Integer provServiceId = p.getService().getId();
            if (provServiceId != null) {
                Services service = this.servicesService.findServiceById(provServiceId);
                Providedservices provServices = new Providedservices(order, service);
                resultProvidedservicesList.add(provServices);
            }
        }
        order.setProvidedservicesList(resultProvidedservicesList);
        Assert.notNull(order.getProvidedservicesList());
        model.addAttribute("order", order);
        return "/orders/order";
    }

    @RequestMapping("/material_delete")
    public String deleteMaterialFOrder(Orders order, @RequestParam Integer indexUsMat, Model model) {
        System.out.println("Controller level deleteMaterialFOrder is called for add order");

        if (indexUsMat != null) {
            order.getUsedmaterialsList().remove(indexUsMat.intValue());
            Assert.notNull(order.getUsedmaterialsList());
        }
        return "redirect:/orders/order/show_page/add";
    }

    @RequestMapping("/service_delete")
    public String deleteServiceFOrder(Orders order, @RequestParam Integer indexPrServ, Model model) {
        System.out.println("Controller level deleteServiceFOrder is called for add order");

        if (indexPrServ != null) {
            order.getProvidedservicesList().remove(indexPrServ.intValue());
            Assert.notNull(order.getProvidedservicesList());
        }
        return "redirect:/orders/order/show_page/add";
    }

    @RequestMapping("/all_materials_delete")
    public String deleteAllMatFOrder(Orders order, Model model) {
        System.out.println("Controller level deleteAllMatFOrder is called for add order");

        order.setUsedmaterialsList(new ArrayList<Usedmaterials>());
        Assert.notNull(order.getUsedmaterialsList());
        model.addAttribute("order", order);
        return "/orders/order";
    }

    @RequestMapping("/all_services_delete")
    public String deleteAllServFOrder(Orders order, Model model) {
        System.out.println("Controller level deleteAllServFOrder is called for add order");

        order.setProvidedservicesList(new ArrayList<Providedservices>());
        Assert.notNull(order.getProvidedservicesList());
        model.addAttribute("order", order);
        return "/orders/order";
    }

    @RequestMapping("/add")
    public String addOrder(Orders order, SessionStatus sessionStatus) {
        System.out.println("Controller level addOrder is called");

        System.out.println(order);
        if (!sessionStatus.isComplete()) {
            sessionStatus.setComplete();
        }
        return "redirect:/orders/showAllOrders";
    }
}
