/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

/**
 *
 * @author Jasmine
 */

public class UpdateOutline {
    
    private Long coursepackId;
    private Long outlineId;
    private String name;

    public UpdateOutline() {
    }

//    public UpdateOutline(Long coursepackId, Long outlineId, String name) {
//        this.coursepackId = coursepackId;
//        this.outlineId = outlineId;
//        this.name = name;
//    }
//    
    
    
    
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the coursepackId
     */
    public Long getCoursepackId() {
        return coursepackId;
    }

    /**
     * @param coursepackId the coursepackId to set
     */
    public void setCoursepackId(Long coursepackId) {
        this.coursepackId = coursepackId;
    }

    /**
     * @return the outlineId
     */
    public Long getOutlineId() {
        return outlineId;
    }

    /**
     * @param outlineId the outlineId to set
     */
    public void setOutlineId(Long outlineId) {
        this.outlineId = outlineId;
    }
    
}
