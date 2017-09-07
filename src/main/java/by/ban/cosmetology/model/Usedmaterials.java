/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model;

import java.io.Serializable;
import javax.persistence.Basic;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
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
public class Usedmaterials implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @Basic(optional = false)
    @NotNull
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Column(name = "count")
    private int count;
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Orders orderId;
    @JoinColumn(name = "materialId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Materials materialId;

    public Usedmaterials() {
    }

    public Usedmaterials(Integer id) {
        this.id = id;
    }

    public Usedmaterials(Materials materialId) {
        this.materialId = materialId;
    }

    public Usedmaterials(Integer id, int count) {
        this.id = id;
        this.count = count;
    }

    public Usedmaterials(int count, Orders orderId, Materials materialId) {
        this.count = count;
        this.orderId = orderId;
        this.materialId = materialId;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public Orders getOrderId() {
        return orderId;
    }

    public void setOrderId(Orders orderId) {
        this.orderId = orderId;
    }

    public Materials getMaterialId() {
        return materialId;
    }

    public void setMaterialId(Materials materialId) {
        this.materialId = materialId;
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
        return materialId.getName();
    }
    
}
