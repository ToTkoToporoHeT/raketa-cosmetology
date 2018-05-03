/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package by.ban.cosmetology.model;

import java.io.Serializable;
import java.util.List;
import java.util.Objects;
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
@Table(name = "address")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Address.findAll", query = "SELECT a FROM Address a"),
    @NamedQuery(name = "Address.findById", query = "SELECT a FROM Address a WHERE a.id = :id"),
    @NamedQuery(name = "Address.findByStreet", query = "SELECT a FROM Address a WHERE a.street = :street"),
    @NamedQuery(name = "Address.findByHouse", query = "SELECT a FROM Address a WHERE a.house = :house"),
    @NamedQuery(name = "Address.findByFlat", query = "SELECT a FROM Address a WHERE a.flat = :flat")})
public class Address implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @NotEmpty(message = "Обязательное поле")
    @Size(max = 30, message = "Размер должен быть не больше 25 символов")
    @Column(name = "Country")
    private String country;
    
    @Basic(optional = false)
    @NotNull
    @NotEmpty(message = "Обязательное поле")
    @Size(max = 30, message = "Размер должен быть не больше 25 символов")
    @Column(name = "City")
    private String city;
    
    @Basic(optional = false)
    @Size(max = 30, message = "Размер должен быть не больше 25 символов")
    @Column(name = "Street")
    private String street;
    
    @Basic(optional = false)
    @Size(max = 4, message = "Размер должен быть не больше 4 символов")
    @Column(name = "House")
    private String house;
    
    @Basic(optional = false)
    @Size(max = 5, message = "Размер должен быть не больше 5 символов")
    @Column(name = "Flat")
    private String flat;
    
    @OneToMany(cascade = {CascadeType.DETACH, CascadeType.MERGE, CascadeType.PERSIST, CascadeType.REFRESH, CascadeType.REMOVE}, mappedBy = "addressId")
    private List<Customers> customersList;

    public Address() {
    }

    public Address(Integer id) {
        this.id = id;
    }

    public Address(Integer id, String street, String house) {
        this.id = id;
        this.street = street;
        this.house = house;
    }

    public Address(Integer id, String country, String city, String street, String house, String flat) {
        this.id = id;
        this.country = country;
        this.city = city;
        this.street = street;
        this.house = house;
        this.flat = flat;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }   

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getHouse() {
        return house;
    }

    public void setHouse(String house) {
        this.house = house;
    }

    public String getFlat() {
        return flat;
    }

    public void setFlat(String flat) {
        this.flat = flat;
    }

    @XmlTransient
    public List<Customers> getCustomersList() {
        return customersList;
    }

    public void setCustomersList(List<Customers> customersList) {
        this.customersList = customersList;
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
        if (!(object instanceof Address)) {
            return false;
        }
        Address other = (Address) object;
        if (Objects.equals(this.id, other.id))
            if (this.country.equals(other.country))
                if (this.city.equals(other.city))
                    if (this.street.equals(other.street))
                        if (this.house.equals(other.house))
                            if (this.flat.equals(other.flat))
                                return true;
        return false;
    }

    @Override
    public String toString() {
        String s = country;
        
        s = confirmPAddress(s, city);
        s = confirmPAddress(s, street);
        s = confirmPAddress(s, house);
        s = confirmPAddress(s, flat);
        
        return  s;
    }
    
    private String confirmPAddress(String str, Object pAddress){
        if (pAddress != null && !pAddress.equals(""))
            str += ", " + pAddress;
        
        return str;
    }

    @Override
    public Address clone() throws CloneNotSupportedException {
        Address clone = (Address) super.clone();
        return clone;
    }
}
