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
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author dazz
 */
@Entity
@Table(name = "telephonenumbers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Telephonenumbers.findAll", query = "SELECT t FROM Telephonenumbers t"),
    @NamedQuery(name = "Telephonenumbers.findById", query = "SELECT t FROM Telephonenumbers t WHERE t.id = :id"),
    @NamedQuery(name = "Telephonenumbers.findByTelephoneNumber", query = "SELECT t FROM Telephonenumbers t WHERE t.telephoneNumber = :telephoneNumber")})
public class Telephonenumbers implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    @Basic(optional = false)
    @NotNull
    @Size(min = 1, max = 17)
    @Column(name = "telephoneNumber")
    private String telephoneNumber;
    @JoinColumn(name = "Customer", referencedColumnName = "login")
    @ManyToOne(optional = false)
    private Customers customer;

    public Telephonenumbers() {
    }

    public Telephonenumbers(Integer id) {
        this.id = id;
    }

    public Telephonenumbers(Integer id, String telephoneNumber) {
        this.id = id;
        this.telephoneNumber = telephoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public void setTelephoneNumber(String telephoneNumber) {
        this.telephoneNumber = telephoneNumber;
    }

    public Customers getCustomer() {
        return customer;
    }

    public void setCustomer(Customers customer) {
        this.customer = customer;
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
        if (!(object instanceof Telephonenumbers)) {
            return false;
        }
        Telephonenumbers other = (Telephonenumbers) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "by.ban.cosmetology.model.Telephonenumbers[ id=" + id + " ]";
    }
    
}
