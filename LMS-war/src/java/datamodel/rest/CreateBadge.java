/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Badge;

/**
 *
 * @author Vixson
 */
public class CreateBadge {

    private Badge badge;
    private String title;
    private String description;
    private String criteria;

    public CreateBadge() {
    }

    public CreateBadge(Badge badge, String title, String description, String criteria) {
        this.badge = badge;
        this.title = title;
        this.description = description;
        this.criteria = criteria;
    }

    public Badge getBadge() {
        return badge;
    }

    public void setBadge(Badge badge) {
        this.badge = badge;
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

    public String getCriteria() {
        return criteria;
    }

    public void setCriteria(String criteria) {
        this.criteria = criteria;
    }
}
