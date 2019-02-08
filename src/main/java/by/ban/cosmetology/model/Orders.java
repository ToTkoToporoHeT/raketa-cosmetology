/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dazz
 */
@Entity
@Table(name = "orders")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Orders.findAll", query = "SELECT o FROM Orders o"),
    @NamedQuery(name = "Orders.findById", query = "SELECT o FROM Orders o WHERE o.id = :id"),
    @NamedQuery(name = "Orders.findByStaff", query = "SELECT o FROM Orders o WHERE o.manager = :manager"),
    @NamedQuery(name = "Orders.findByCheckNumber", query = "SELECT o FROM Orders o WHERE o.check_number = :check_number"),
    @NamedQuery(name = "Orders.findByDate", query = "SELECT o FROM Orders o WHERE o.prepare_date = :date"),
    @NamedQuery(name = "Orders.getByDateInterval", query = "SELECT o FROM Orders o WHERE o.prepare_date >= :startDate AND o.prepare_date <= :endDate")})
public class Orders implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Size(max = 7, message = "Номер не может быть длиннее 7 символов")
    @Column(name = "check_number", unique = true)
    private String check_number;
    
    @Basic(optional = false)
    @NotNull(message = "Обязательное поле")
    @Column(name = "prepare_date")
    @Temporal(TemporalType.DATE)
    private Date prepare_date;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    @Valid
    private List<VisitDate> visitDatesList = new ArrayList<>();
        
    @JoinColumn(name = "customerId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @NotNull(message = "Обязательное поле")
    private Customers customer;
    
    @JoinColumn(name = "managerId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Staff manager;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "order")
    private List<Providedservices> providedservicesList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "orderId")
    private List<Usedmaterials> usedmaterialsList;

    {
        providedservicesList = new ArrayList<>();
        usedmaterialsList = new ArrayList<>();
    }
    
    public Orders() {
    }

    public Orders(Integer id) {
        this.id = id;
    }

    public Orders(Integer id, Date date) {
        this.id = id;
        this.prepare_date = date;
    }

    public Orders(List<Object> listObjects) {
        if (listObjects.size() > 0) {
            if (listObjects.get(0) instanceof Usedmaterials) {
                List<Usedmaterials> usedmaterialsListTemp = new ArrayList<>();
                for (Object obj : listObjects){
                    usedmaterialsListTemp.add((Usedmaterials) obj);
                }
                this.usedmaterialsList = usedmaterialsListTemp;
            }
            if (listObjects.get(0) instanceof Providedservices) {
                List<Providedservices> providedservicesListTemp = new ArrayList<>();
                for (Object obj : listObjects){
                    providedservicesListTemp.add((Providedservices) obj);
                }
                this.providedservicesList = providedservicesListTemp;
            }
        }
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCheck_number() {
        return check_number;
    }

    public void setCheck_number(String check_number) {
        this.check_number = check_number;
    }

    public Date getPrepare_date() {
        return prepare_date;
    }

    public void setPrepare_date(Date prepare_date) {
        this.prepare_date = prepare_date;
    }

    @XmlTransient
    public List<VisitDate> getVisitDatesList() {
        return visitDatesList;
    }

    public void setVisitDatesList(List<VisitDate> visitDatesList) {
        this.visitDatesList = visitDatesList;
    }
    
    @XmlTransient
    public List<Providedservices> getProvidedservicesList() {
        return providedservicesList;
    }

    public void setProvidedservicesList(List<Providedservices> providedservicesList) {
        this.providedservicesList = providedservicesList;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
    }

    public Staff getManager() {
        return manager;
    }

    public void setManager(Staff manager) {
        this.manager = manager;
    }

    @XmlTransient
    public List<Usedmaterials> getUsedmaterialsList() {
        return usedmaterialsList;
    }

    public void setUsedmaterialsList(List<Usedmaterials> usedmaterialsList) {
        this.usedmaterialsList = usedmaterialsList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Orders)) {
            return false;
        }
        Orders other = (Orders) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Order {" + "Id = " + id + ", Номер = " + check_number + ", Дата = " + prepare_date + ", Клиент = " + customer + ", Работник=" + manager + ",\nОказанные услуги = " + providedservicesList + ",\nИспользованные материалы=" + usedmaterialsList + '}';
    }

    @Override
    public Orders clone() throws CloneNotSupportedException {
        Orders clone = (Orders) super.clone();
        if (customer != null) {
            clone.customer = (Customers) customer.clone();
        }
        if (manager != null) {
            clone.manager = (Staff) manager.clone();
        }
        if (prepare_date != null) {
            clone.prepare_date = (Date) prepare_date.clone();
        }
        return clone;
    }
}
