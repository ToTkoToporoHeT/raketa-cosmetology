/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model;

import java.io.Serializable;
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
import javax.persistence.Table;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dazz
 */
@Entity
@Table(name = "usedmaterials")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usedmaterials.findAll", query = "SELECT u FROM Usedmaterials u"),
    @NamedQuery(name = "Usedmaterials.findById", query = "SELECT u FROM Usedmaterials u WHERE u.id = :id"),
    @NamedQuery(name = "Usedmaterials.findByCount", query = "SELECT u FROM Usedmaterials u WHERE u.count = :count")})
public class Usedmaterials implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @Column(name = "count", precision = 3, scale = 2)
    private Double count;
    
    @Basic(optional = false)
    @NotNull
    @Min(value = 0)
    @Column(name = "cost", precision = 3, scale = 2)
    private Double cost;
    
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Orders orderId;
    
    @JoinColumn(name = "materialId", referencedColumnName = "id")
    @ManyToOne(optional = false, cascade = CascadeType.MERGE)
    private Materials material;

    public Usedmaterials() {
    }

    public Usedmaterials(Integer id) {
        this.id = id;
    }

    public Usedmaterials(Materials materialId) {
        this.material = materialId;
    }

    public Usedmaterials(Integer id, double count) {
        this.id = id;
        this.count = count;
    }

    public Usedmaterials(double count, Orders orderId, Materials materialId) {
        this.count = count;
        this.orderId = orderId;
        this.material = materialId;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public Materials getMaterial() {
        return material;
    }

    public void setMaterial(Materials material) {
        this.material = material;
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
        if (!(object instanceof Usedmaterials)) {
            return false;
        }
        Usedmaterials other = (Usedmaterials) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "Id=" + id + ", Наименование=" + material.getName() + ", Кол-во=" + count + " " + material.getUnit() + '}';
    }

    @Override
    public Usedmaterials clone() throws CloneNotSupportedException {
        Usedmaterials clone = (Usedmaterials) super.clone();
        if (material != null) clone.material = (Materials) material.clone();
        if (orderId != null) clone.orderId = (Orders) orderId.clone();
        return clone;
    }
}
