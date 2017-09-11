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
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dazz
 */
@Entity
@Table(name = "providedservices")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Providedservices.findAll", query = "SELECT p FROM Providedservices p"),
    @NamedQuery(name = "Providedservices.findById", query = "SELECT p FROM Providedservices p WHERE p.id = :id")})
public class Providedservices implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @JoinColumn(name = "orderId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Orders order;
    @JoinColumn(name = "serviceId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Services service;

    public Providedservices() {
    }

    public Providedservices(Integer id) {
        this.id = id;
    }

    public Providedservices(Services serviceId) {
        this.service = serviceId;
    }

    public Providedservices(Orders orderId, Services service) {
        this.order = orderId;
        this.service = service;
    }
    
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Orders getOrder() {
        return order;
    }

    public void setOrder(Orders order) {
        this.order = order;
    }

    public Services getService() {
        return service;
    }

    public void setService(Services service) {
        this.service = service;
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
        if (!(object instanceof Providedservices)) {
            return false;
        }
        Providedservices other = (Providedservices) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "by.ban.cosmetology.model.Providedservices[ id=" + id + " ]";
    }
    
}
