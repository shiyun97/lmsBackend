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
    private String content;
    private String createdDate;
    private String lastUpdatedDate;
    private String startDate;
    private String endDate;
    private Boolean publish;
    private Boolean emailNotification;
    private Module module;
    private User owner;

    public CreateAnnoucement() {
    }

    public CreateAnnoucement(Annoucement annoucement, Long annoucementId, String title, String content, String createdDate, String lastUpdatedDate, String startDate, String endDate, Boolean publish, Boolean emailNotification, Module module, User owner) {
        this.annoucement = annoucement;
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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
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

    public String getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(String createdDate) {
        this.createdDate = createdDate;
    }

    public String getLastUpdatedDate() {
        return lastUpdatedDate;
    }

    public void setLastUpdatedDate(String lastUpdatedDate) {
        this.lastUpdatedDate = lastUpdatedDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }
    
}
