/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

/**
 *
 * @author Asus
 */
public class EnterMarksRqst {
    private Long gradeEntryId;
    private Double marks;
    private String remarks;

    public Long getGradeEntryId() {
        return gradeEntryId;
    }

    public void setGradeEntryId(Long gradeEntryId) {
        this.gradeEntryId = gradeEntryId;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }
    
    
}
