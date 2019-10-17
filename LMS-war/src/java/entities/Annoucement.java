/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Date;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToOne;

/**
 *
 * @author Vixson
 */
@Entity
public class Annoucement implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long annoucementId;
    @Column
    private String title;
    @Column
    private String content;
    @Column
    private LocalDateTime createdDate;
    @Column
    private LocalDateTime lastUpdatedDate;
    @Column
    private LocalDateTime startDate;
    @Column
    private LocalDateTime endDate;
    @Column
    private Boolean publish;
    @Column
    private Boolean emailNotification;
    @ManyToOne
    private Module module;
    @ManyToOne
    private User owner;
   
    public Annoucement(){
        
    }

    public Annoucement(Long annoucementId, String title, String content, LocalDateTime createdDate, LocalDateTime lastUpdatedDate, LocalDateTime startDate, LocalDateTime endDate, Boolean publish, Boolean emailNotification, Module module, User owner) {
        this.annoucementId = annoucementId;
        this.title = title;
        this.content = content;
        this.createdDate = createdDate;
        this.lastUpdatedDate = lastUpdatedDate;
        this.startDate = startDate;
        this.endDate = endDate;
        this.publish = publish;
        this.emailNotification = emailNotification;
        this.module = module;
        this.owner = owner;
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
        if (!(object instanceof Annoucement)) {
            return false;
        }
        Annoucement other = (Annoucement) object;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(LocalDateTime createdDate) {
        this.createdDate = createdDate;
    }

    public LocalDateTime getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(LocalDateTime lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public LocalDateTime getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDateTime startDate) {
        this.startDate = startDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public Boolean getEmailNotification() {
        return emailNotification;
    }

    public void setEmailNotification(Boolean emailNotification) {
        this.emailNotification = emailNotification;
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
