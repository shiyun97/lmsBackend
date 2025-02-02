/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Vixson
 */
@Entity
public class Badge implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long badgeId;
    @Column
    private String title;
    @Column
    private String location;
    @Column
    private Timestamp createdDt;
    @Column
    private boolean isDelete;

    public Badge() {
    }

    public Badge(Long badgeId, String title, String location, Timestamp createdDt, boolean isDelete) {
        this.badgeId = badgeId;
        this.title = title;
        this.location = location;
        this.createdDt = createdDt;
        this.isDelete = isDelete;
    }
    
    
    
    public Long getId() {
        return badgeId;
    }

    public void setId(Long badgeId) {
        this.badgeId = badgeId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (badgeId != null ? badgeId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the badgeId fields are not set
        if (!(object instanceof Badge)) {
            return false;
        }
        Badge other = (Badge) object;
        if ((this.badgeId == null && other.badgeId != null) || (this.badgeId != null && !this.badgeId.equals(other.badgeId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Badge[ Id=" + badgeId + " ]";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Timestamp getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Timestamp createdDt) {
        this.createdDt = createdDt;
    }

    public boolean isIsDelete() {
        return isDelete;
    }

    public void setIsDelete(boolean isDelete) {
        this.isDelete = isDelete;
    }
}
