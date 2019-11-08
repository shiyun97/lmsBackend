/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Vixson
 */
@Entity
public class Cart implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long cartId;
    @Column
    private Double totalPrice;
    @OneToOne
    private User publicUser;
    @OneToMany
    private List<Coursepack> coursepackList;

    public Cart(Long cartId, Double totalPrice, User publicUser, List<Coursepack> coursepackList) {
        this.cartId = cartId;
        this.totalPrice = totalPrice;
        this.publicUser = publicUser;
        this.coursepackList = coursepackList;
    }

    public Cart() {
    }

    public Long getId() {
        return cartId;
    }

    public void setId(Long cartId) {
        this.cartId = cartId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (cartId != null ? cartId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the cartId fields are not set
        if (!(object instanceof Cart)) {
            return false;
        }
        Cart other = (Cart) object;
        if ((this.cartId == null && other.cartId != null) || (this.cartId != null && !this.cartId.equals(other.cartId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Cart[ cartId=" + cartId + " ]";
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public User getPublicUser() {
        return publicUser;
    }

    public void setPublicUser(User publicUser) {
        this.publicUser = publicUser;
    }

    public List<Coursepack> getCoursepackList() {
        return coursepackList;
    }

    public void setCoursepackList(List<Coursepack> coursepackList) {
        this.coursepackList = coursepackList;
    }
    
}
