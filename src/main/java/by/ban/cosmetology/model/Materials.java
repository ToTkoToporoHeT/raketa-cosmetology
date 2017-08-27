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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dazz
 */
@Entity
@Table(name = "materials")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Materials.findAll", query = "SELECT m FROM Materials m"),
    @NamedQuery(name = "Materials.findById", query = "SELECT m FROM Materials m WHERE m.id = :id"),
    @NamedQuery(name = "Materials.findByName", query = "SELECT m FROM Materials m WHERE m.name = :name"),
    @NamedQuery(name = "Materials.findByCount", query = "SELECT m FROM Materials m WHERE m.count = :count"),
    @NamedQuery(name = "Materials.findByCost", query = "SELECT m FROM Materials m WHERE m.cost = :cost")})
public class Materials implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 100)
    @Column(name = "name")
    private String name;
    @Basic(optional = false)
    @NotNull
    @Column(name = "count")
    private Integer count;
    @Basic(optional = false)
    @NotNull
    @Column(name = "cost")
    private Double cost;
    @JoinColumn(name = "unit", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Units unit;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "materialId")
    private List<Usedmaterials> usedmaterialsList;
    
    public Materials() {
    }

    public Materials(Integer id) {
        this.id = id;
    }

    public Materials(Integer id, String name, int count, double cost) {
        this.id = id;
        this.name = name;
        this.count = count;
        this.cost = cost;
    }
    
    public Materials(Integer id, String name, Units unit, int count, double cost) {
        this.id = id;
        this.name = name;
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

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
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
        return "by.ban.cosmetology.model.Materials[ id=" + id + ", name=" + name + 
                ", unit="+ unit + ", count=" + count + ", cost=" + cost + " ]";
    }
    
}
