/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Coursepack;
import entities.Outlines;

/**
 *
 * @author Jasmine
 */
public class UpdateOutline {
    
    private Coursepack coursepack;
    
    private Outlines outlineList;
    private String name;

    public UpdateOutline(Coursepack coursepack, Outlines outlineList, String name) {
        this.coursepack = coursepack;
        this.outlineList = outlineList;
        this.name = name;
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

    public Outlines getOutlineList() {
        return outlineList;
    }

    public void setOutlineList(Outlines outlineList) {
        this.outlineList = outlineList;
    }
    
    
    
    

}
