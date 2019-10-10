/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Annoucement;
import entities.Module;
import entities.User;
import java.util.Date;

/**
 *
 * @author Vixson
 */
public class CreateAnnoucement {
    private Annoucement annoucement;
    private Long annoucementId;
    private String title;
    private String description;
    private Date createTs;
    private Date updateTs;
    private Boolean systemWide;
    private Module module;
    private User owner;

    public CreateAnnoucement() {
    }

    public CreateAnnoucement(Annoucement annoucement, Long annoucementId, String title, String description, Date createTs, Date updateTs, Boolean systemWide, Module module, User owner) {
        this.annoucement = annoucement;
        this.annoucementId = annoucementId;
        this.title = title;
        this.description = description;
        this.createTs = createTs;
        this.updateTs = updateTs;
        this.systemWide = systemWide;
        this.module = module;
        this.owner = owner;
    }

    public Annoucement getAnnoucement() {
        return annoucement;
    }

    public void setAnnoucement(Annoucement annoucement) {
        this.annoucement = annoucement;
    }

    public Long getAnnoucementId() {
        return annoucementId;
    }

    public void setAnnoucementId(Long annoucementId) {
        this.annoucementId = annoucementId;
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

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
    }

    public Date getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Date updateTs) {
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
