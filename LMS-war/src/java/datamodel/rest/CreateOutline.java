/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Coursepack;
import javax.persistence.ManyToOne;

/**
 *
 * @author Jasmine
 */
public class CreateOutline {
    
    private Long outlineId;
    private String name;

    @ManyToOne
    private Coursepack coursepack;

    public CreateOutline(Long outlineId, String name, Coursepack coursepack) {
        this.outlineId = outlineId;
        this.name = name;
        this.coursepack = coursepack;
    }

    public Long getOutlineId() {
        return outlineId;
    }

    public void setOutlineId(Long outlineId) {
        this.outlineId = outlineId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Coursepack getCoursepack() {
        return coursepack;
    }

    public void setCoursepack(Coursepack coursepack) {
        this.coursepack = coursepack;
    }
    
    
    
    
    
}
