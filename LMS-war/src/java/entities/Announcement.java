/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Vixson
 */
@Entity
@Table(name = "ANNOUCEMENT")
@XmlRootElement
public class Announcement implements Serializable {

    public Announcement(String title, String description, Timestamp createTs, Timestamp updateTs, Boolean systemWide, Module module, User owner) {
        this.title = title;
        this.description = description;
        this.createTs = createTs;
        this.updateTs = updateTs;
        this.systemWide = systemWide;
        this.module = module;
        this.owner = owner;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long annoucementId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Timestamp createTs;
    @Column
    private Timestamp updateTs;
    @Column
    private Boolean systemWide;
    @ManyToOne
    private Module module;
    @ManyToOne
    private User owner;
    
   /** 
    public Annoucement(String title, String description, String owner, Timestamp createTs, Timestamp updateTs, Boolean systemWide){
        this.title = title;
        this.description = description;
        this.owner = owner;
        this.createTs = createTs;
        this.updateTs = updateTs;
        this.systemWide = systemWide;
    }
**/
    public Announcement(){
        
    }
    
    public Long getAnnoucementId() {
        return annoucementId;
    }

    public void setAnnoucementId(Long annoucementId) {
        this.annoucementId = annoucementId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (annoucementId != null ? annoucementId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the annoucementId fields are not set
        if (!(object instanceof Announcement)) {
            return false;
        }
        Announcement other = (Announcement) object;
        if ((this.annoucementId == null && other.annoucementId != null) || (this.annoucementId != null && !this.annoucementId.equals(other.annoucementId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.annoucement[ Id=" + annoucementId + " ]";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Timestamp createTs) {
        this.createTs = createTs;
    }

    public Timestamp getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Timestamp updateTs) {
        this.updateTs = updateTs;
    }

    public Boolean getSystemWide() {
        return systemWide;
    }

    public void setSystemWide(Boolean systemWide) {
        this.systemWide = systemWide;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
    
}
