/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unusedEntities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vixson
 */
@Entity
@Table(name = "PUBLICUSER")
@XmlRootElement
public class PublicUser implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long publicUserId;
    @Column
    private String name;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private String address;
    @Column
    private Integer phone;
    
    
    public PublicUser(){
        
    }

    public Long getPublicUserId() {
        return publicUserId;
    }

    public void setPublicUserId(Long publicUserId) {
        this.publicUserId = publicUserId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (publicUserId != null ? publicUserId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the publicUserId fields are not set
        if (!(object instanceof PublicUser)) {
            return false;
        }
        PublicUser other = (PublicUser) object;
        if ((this.publicUserId == null && other.publicUserId != null) || (this.publicUserId != null && !this.publicUserId.equals(other.publicUserId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.publicUser[ Id=" + publicUserId + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }
    
}
