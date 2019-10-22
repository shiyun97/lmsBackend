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
public class CreateLessonOrder {
    
    private Long outlineId;
    private int number;
    private String name;
    private Boolean type;


    public CreateLessonOrder(Long lessonOrderId, int number, String name, Boolean type) {
        this.outlineId = lessonOrderId;
        this.number = number;
        this.name = name;
        this.type = type;
    }

    public Long getOutlineId() {
        return outlineId;
    }

    public void setOutlineId(Long outlineId) {
        this.outlineId = outlineId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    
    
    
}
