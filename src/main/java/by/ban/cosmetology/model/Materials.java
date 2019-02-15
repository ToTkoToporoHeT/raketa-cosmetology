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
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
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
@Table(name = "materials")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materials.findAll", query = "SELECT m FROM Materials m"),
    @NamedQuery(name = "Materials.findAllByManager", 
            query = "SELECT m FROM Materials m "
                    + "WHERE m.manager = :manager and m.forDelete = :del"),
    @NamedQuery(name = "Materials.findAllActive", query = "SELECT m FROM Materials m WHERE m.forDelete = :del"),
    @NamedQuery(name = "Materials.findById", query = "SELECT m FROM Materials m WHERE m.id = :id"),
    @NamedQuery(name = "Materials.findByNumber", query = "SELECT m FROM Materials m WHERE m.number = :number"),
    @NamedQuery(name = "Materials.findByName", query = "SELECT m FROM Materials m WHERE m.name = :name"),
    @NamedQuery(name = "Materials.findByCount", query = "SELECT m FROM Materials m WHERE m.count = :count"),
    @NamedQuery(name = "Materials.findByCost", query = "SELECT m FROM Materials m WHERE m.cost = :cost")})
public class Materials implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    //номенклатурный номер
    @Basic(optional = false)
    @Min(value = 0, message = "Не может быть отрицательным")
    @Column(name = "number", unique = true)
    private Integer number;
    
    @Basic(optional = false)
    @NotNull
    @NotEmpty(message = "Обязательное поле")
    @Size(max = 255, message = "Длинна строки должна быть не больше 255 символов")
    @Column(name = "name")
    private String name;
    
    @Basic(optional = false)
    @NotNull(message = "Обязательное поле")
    @Min(value = 0, message = "Не может быть отрицательным")
    @Column(name = "count", precision = 3, scale = 2)
    private Double count;
    
    @Basic(optional = false)
    @NotNull(message = "Обязательное поле")
    @Min(value = 0, message = "Не может быть отрицательным")
    @Column(name = "cost", precision = 3, scale = 2)
    private Double cost;
    
    @Basic(optional = false)
    @Column(name = "forDelete")
    private boolean forDelete;
    
    @JoinColumn(name = "unit", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Units unit;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "material")
    private List<Usedmaterials> usedmaterialsList;
    
    @JoinColumn(name = "managerId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Staff manager;
    
    public Materials() {
    }

    public Materials(Integer id) {
        this.id = id;
    }

    public Materials(Integer id, Integer number, String name, double count, double cost) {
        this.id = id;
        this.name = name.trim();
        this.count = count;
        this.cost = cost;
    }
    
    public Materials(Integer id, Integer number, String name, Units unit, double count, double cost) {
        this.id = id;
        this.name = name.trim();
        this.count = count;
        this.cost = cost;
        this.unit = unit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name.trim();
    }

    public Double getCount() {
        return count;
    }

    public void setCount(Double count) {
        this.count = count;
    }

    public Double getCost() {
        return cost;
    }

    public void setCost(Double cost) {
        this.cost = cost;
    }
    
    public Units getUnit() {
        return unit;
    }

    public void setUnit(Units unit) {
        this.unit = unit;
    }

    @XmlTransient
    public List<Usedmaterials> getUsedmaterialsList() {
        return usedmaterialsList;
    }

    public void setUsedmaterialsList(List<Usedmaterials> usedmaterialsList) {
        this.usedmaterialsList = usedmaterialsList;
    }

    public Staff getManager() {
        return manager;
    }

    public void setManager(Staff manager) {
        this.manager = manager;
    }

    public boolean isForDelete() {
        return forDelete;
    }

    public void setForDelete(boolean forDelete) {
        this.forDelete = forDelete;
    }

    public void update(Materials material){
        name = material.getName();
        count = material.getCount();
        cost = material.getCost();
        unit = material.getUnit();
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
        if (!(object instanceof Materials)) {
            return false;
        }
        Materials other = (Materials) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "by.ban.cosmetology.model.Materials[ id=" + id + ", number=" + number +", name=" + name + 
                ", unit="+ unit + ", count=" + count + ", cost=" + cost + " ]";
    }

    @Override
    public Materials clone() throws CloneNotSupportedException {
        Materials clone = (Materials) super.clone();
        if (unit != null) clone.unit = (Units) unit.clone();
        return clone;
    }
}
