/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Basic;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author dazz
 */
@Entity
@Table(name = "services")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Services.findAll", query = "SELECT s FROM Services s"),
    @NamedQuery(name = "Services.findAllActive", query = "SELECT s FROM Services s WHERE s.forDelete = :del"),
    @NamedQuery(name = "Services.findById", query = "SELECT s FROM Services s WHERE s.id = :id"),
    @NamedQuery(name = "Services.findByNumber", query = "SELECT s FROM Services s WHERE s.number = :number"),
    @NamedQuery(name = "Services.findByName", query = "SELECT s FROM Services s WHERE s.name = :name"),
    @NamedQuery(name = "Services.findByCost", query = "SELECT s FROM Services s WHERE s.cost = :cost")})
public class Services implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @Size(max = 10, message = "Длинна строки должна быть не больше 7 символов")
    @Column(name = "number", unique = true)
    private String number;
    
    @Basic(optional = false)
    @NotNull
    @NotEmpty(message = "Обязательное поле")
    @Size(max = 255, message = "Длинна строки должна быть не больше 255 символов")
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @NotNull(message = "Обязательное поле")
    @Min(value = 0, message = "Цена не может быть отрицательной")
    @Column(name = "cost", unique = true)
    private Double cost;
    
    @Basic(optional = false)
    @NotNull(message = "Обязательное поле")
    @Min(value = 0, message = "Цена не может быть отрицательной")
    @Column(name = "costFF")
    private Double costFF;
    
    @Basic(optional = false)    
    @Column(name = "forDelete")
    private boolean forDelete;

    @OneToMany(cascade = CascadeType.ALL, mappedBy = "service")
    private List<Providedservices> providedservicesList;

    public Services() {
    }

    public Services(Integer id) {
        this.id = id;
    }
    
    public Services(String name) {
        this.name = name.trim();
    }

    public Services(String name, Double cost) {
        this.name = name.trim();
        this.cost = cost;
    }
    
    public Services(Integer id, String name, double cost) {
        this.id = id;
        this.name = name.trim();
        this.cost = cost;
    }
    
    public Services(Integer id, double costFF, String name) {
        this.id = id;
        this.name = name.trim();
        this.costFF = costFF;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }

    public Double getCostFF() {
        return costFF;
    }

    public void setCostFF(Double costFF) {
        this.costFF = costFF;
    }
    
    public boolean isForDelete() {
        return forDelete;
    }

    public void setForDelete(boolean forDelete) {
        this.forDelete = forDelete;
    }

    @XmlTransient
    public List<Providedservices> getProvidedservicesList() {
        return providedservicesList;
    }

    public void setProvidedservicesList(List<Providedservices> providedservicesList) {
        this.providedservicesList = providedservicesList;
    }
    
    public void update(Services service){
        cost = service.getCost();
        costFF = service.getCostFF();
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
        if (!(object instanceof Services)) {
            return false;
        }
        Services other = (Services) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "by.ban.cosmetology.model.Services[ id=" + id + ", name=" + name + ", cost=" + cost + "]";
    }

    @Override
    public Services clone() throws CloneNotSupportedException {
        Services clone = (Services) super.clone();
        return clone; //To change body of generated methods, choose Tools | Templates.
    }
    
}
