/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Certification;

/**
 *
 * @author Vixson
 */
public class CreateCertification {

    private Certification certification;
    private String title;
    private String description;
    private String criteria;

    public CreateCertification() {
    }

    public CreateCertification(Certification certification, String title, String description, String criteria) {
        this.certification = certification;
        this.title = title;
        this.description = description;
        this.criteria = criteria;
    }

    public Certification getCertification() {
        return certification;
    }

    public void setCertification(Certification certification) {
        this.certification = certification;
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
