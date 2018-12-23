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
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.NamedQueries;
import javax.persistence.NamedQuery;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import javax.xml.bind.annotation.XmlRootElement;
import javax.xml.bind.annotation.XmlTransient;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

/**
 *
 * @author dazz
 */
@Entity
@Table(name = "customers")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Customers.findAll", query = "SELECT c FROM Customers c"),
    @NamedQuery(name = "Customers.findAllActive", query = "SELECT с FROM Customers с WHERE с.enabled = :enabled"),
    @NamedQuery(name = "Customers.findByLogin", query = "SELECT c FROM Customers c WHERE c.login = :login"),
    @NamedQuery(name = "Customers.findByFullName", query = "SELECT c FROM Customers c WHERE c.fullName = :fullName"),
    @NamedQuery(name = "Customers.findByAddressId", query = "SELECT c FROM Customers c WHERE c.addressId = :addressId")})
public class Customers implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @NotEmpty(message = "Обязательное поле")
    @Size(max = 30, message = "Размер должен быть не больше 30 символов")
    @Column(name = "fullName")
    private String fullName;
        
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer", fetch = FetchType.EAGER)
    @Valid
    private List<Telephonenumbers> telephonenumbersList;
    
    @Basic(optional = false)
    @Email(message = "Введите пожалуйста корректный e-mail")
    @Size(max = 40, message = "Размер должен быть не меньше 40 символов")
    @Column(name = "login")
    private String login;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "customer")
    private List<Orders> ordersList;
    
    @JoinColumn(name = "addressId", referencedColumnName = "id")
    @ManyToOne(optional = false)
    @Valid
    private Address addressId;
    
    @Basic(optional = false)
    @Column(name = "enabled")
    private boolean enabled;

    public Customers() {
    }

    public Customers(String login) {
        this.login = login;
    }

    public Customers(String login, String name) {
        this.login = login;
        this.fullName = name;
    }

    public Customers(boolean enabled) {
        this.enabled = enabled;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getFullName() {
        return fullName;
    }

    public void setFullName(String fullName) {
        this.fullName = fullName;
    }

    @XmlTransient
    public List<Telephonenumbers> getTelephonenumbersList() {
        return telephonenumbersList;
    }

    public void setTelephonenumbersList(List<Telephonenumbers> telephonenumbersList) {
        this.telephonenumbersList = telephonenumbersList;
    }

    @XmlTransient
    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    public Address getAddressId() {
        return addressId;
    }

    public void setAddressId(Address addressId) {
        this.addressId = addressId;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (login != null ? login.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Customers)) {
            return false;
        }
        Customers other = (Customers) object;
        if ((this.login == null && other.login != null) || (this.login != null && !this.login.equals(other.login))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        /*
        //представление ФИО в формате <Фамилия> <Первая буква имени>.<Первая буква отчества>.
        char nameFirstChar = ' ';
        char midNameFirstChar = ' ';
        if (firstName != null && !firstName.isEmpty()){
            nameFirstChar = firstName.charAt(0);
        }
        if (middleName != null && !middleName.isEmpty()){
            midNameFirstChar = middleName.charAt(0);
        }
        return fullName + " " + nameFirstChar + "." + midNameFirstChar + ".";*/
        return fullName;
    }

    @Override
    public Customers clone() throws CloneNotSupportedException {
        Customers clone = (Customers) super.clone();
        if (addressId != null) clone.addressId = (Address) addressId.clone();
        return clone; //To change body of generated methods, choose Tools | Templates.
    }
}
