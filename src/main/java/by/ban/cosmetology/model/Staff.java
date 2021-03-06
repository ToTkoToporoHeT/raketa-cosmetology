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
@Table(name = "staff")
@XmlRootElement
@NamedQueries({
    @NamedQuery(name = "Staff.findAll", query = "SELECT s FROM Staff s"),
    @NamedQuery(name = "Staff.findAlmostAll", query = "SELECT s FROM Staff s WHERE s.login != 'root'"),
    @NamedQuery(name = "Staff.findById", query = "SELECT s FROM Staff s WHERE s.id = :id"),
    @NamedQuery(name = "Staff.findByFirstName", query = "SELECT s FROM Staff s WHERE s.firstName = :firstName"),
    @NamedQuery(name = "Staff.findByMiddleName", query = "SELECT s FROM Staff s WHERE s.middleName = :middleName"),
    @NamedQuery(name = "Staff.findByLastName", query = "SELECT s FROM Staff s WHERE s.lastName = :lastName"),
    @NamedQuery(name = "Staff.findByLogin", query = "SELECT s FROM Staff s WHERE s.login = :login"),
    @NamedQuery(name = "Staff.findByPassword", query = "SELECT s FROM Staff s WHERE s.password = :password")})
public class Staff implements Serializable, Cloneable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Basic(optional = false)
    @Column(name = "id")
    private Integer id;
    
    @Basic(optional = false)
    @NotNull
    @NotEmpty(message = "Обязательное поле")
    @Size(max = 20, message = "Длинна строки должна быть не больше 20 символов")
    @Column(name = "login", unique = true)
    private String login;
    
    @Basic(optional = false)
    @NotNull
    @NotEmpty(message = "Обязательное поле")
    @Size(min = 4, max = 20, message = "Пароль должен быть больше 4 и меньше 20 символов")
    @Column(name = "password")
    private String password;
    
    @Basic(optional = false)
    @NotNull
    @NotEmpty(message = "Обязательное поле")
    @Size(max = 25, message = "Длинна строки должна быть не больше 25 символов")
    @Column(name = "lastName")
    private String lastName;
    
    @Basic(optional = false)
    @NotNull
    @NotEmpty(message = "Обязательное поле")
    @Size(max = 25, message = "Длинна строки должна быть не больше 25 символов")
    @Column(name = "firstName")
    private String firstName;
    
    @Basic(optional = false)
    @NotNull
    @NotEmpty(message = "Обязательное поле")
    @Size(max = 25, message = "Длинна строки должна быть не больше 25 символов")
    @Column(name = "middleName")
    private String middleName;
    
    @Basic(optional = false)
    @Column(name = "enabled")
    private boolean enabled;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manager")
    private List<Orders> ordersList;
    
    @OneToMany(cascade = CascadeType.ALL, mappedBy = "manager")
    private List<Materials> materials;
    
    @JoinColumn(name = "userType", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Usertypes userType;

    
    public Staff() { 
    }

    public Staff(Integer id) {
        this.id = id;
    }

    public Staff(Integer id, String firstName, String middleName, String lastName, String password) {
        this.id = id;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
        this.password = password;
    }

    public Staff(boolean enabled, Usertypes userType) {
        this.enabled = enabled;
        this.userType = userType;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @XmlTransient
    public List<Orders> getOrdersList() {
        return ordersList;
    }

    public void setOrdersList(List<Orders> ordersList) {
        this.ordersList = ordersList;
    }

    @XmlTransient
    public List<Materials> getMaterials() {
        return materials;
    }

    public void setMaterials(List<Materials> materials) {
        this.materials = materials;
    }

    public Usertypes getUserType() {
        return userType;
    }

    public void setUserType(Usertypes userType) {
        this.userType = userType;
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
        if (!(object instanceof Staff)) {
            return false;
        }
        Staff other = (Staff) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        char nameFirstChar = ' ';
        char midNameFirstChar = ' ';
        if (firstName != null && !firstName.isEmpty()){
            nameFirstChar = firstName.charAt(0);
        }
        if (middleName != null && !middleName.isEmpty()){
            midNameFirstChar = middleName.charAt(0);
        }
        return lastName + " " + nameFirstChar + "." + midNameFirstChar + ".";
    }

    @Override
    public Staff clone() throws CloneNotSupportedException {
        Staff clone = (Staff) super.clone();
        if (userType != null) clone.userType = (Usertypes) userType.clone();
        return clone; //To change body of generated methods, choose Tools | Templates.
    }
}
