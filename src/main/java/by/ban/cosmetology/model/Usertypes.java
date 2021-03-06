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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;

/**
 *
 * @author dazz
 */
@Entity
@Table(name = "usertypes")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Usertypes.findAll", query = "SELECT u FROM Usertypes u"),
    @NamedQuery(name = "Usertypes.findAlmostAll", query = "SELECT u FROM Usertypes u WHERE u.accessConst != 'ROLE_ROOT'"),
    @NamedQuery(name = "Usertypes.findById", query = "SELECT u FROM Usertypes u WHERE u.id = :id"),
    @NamedQuery(name = "Usertypes.findByType", query = "SELECT u FROM Usertypes u WHERE u.type = :type")})
public class Usertypes implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "type")
    private String type;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 15)
    @Column(name = "accessConst")
    private String accessConst;
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "userType")
    private List<Staff> staffList;

    public Usertypes() {
    }

    public Usertypes(Integer id) {
        this.id = id;
    }

    public Usertypes(Integer id, String type) {
        this.id = id;
        this.type = type;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getAccessConst() {
        return accessConst;
    }

    public void setAccessConst(String accessConst) {
        this.accessConst = accessConst;
    }

    @XmlTransient
    public List<Staff> getStaffList() {
        return staffList;
    }

    public void setStaffList(List<Staff> staffList) {
        this.staffList = staffList;
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
        if (!(object instanceof Usertypes)) {
            return false;
        }
        Usertypes other = (Usertypes) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return type;
    }

    @Override
    public Usertypes clone() throws CloneNotSupportedException {
        Usertypes clone = (Usertypes) super.clone();
        return clone; 
    }
}
