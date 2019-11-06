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

    public CreateCertification() {
    }

    public CreateCertification(Certification certification, String title, String description) {
        this.certification = certification;
        this.title = title;
        this.description = description;
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
}
