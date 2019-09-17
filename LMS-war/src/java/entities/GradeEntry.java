/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Vixson
 */
@Entity
public class GradeEntry implements Serializable {

    public GradeEntry(Double marks, String notes, Timestamp createTs, Timestamp updateTs, GradeItem gradeItem, User student, User grader) {
        this.marks = marks;
        this.notes = notes;
        this.createTs = createTs;
        this.updateTs = updateTs;
        this.gradeItem = gradeItem;
        this.student = student;
        this.grader = grader;
    }

    public GradeEntry() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gradeEntryId;
    @Column
    private Double marks;
    @Column
    private String notes;
    @Column 
    private Timestamp createTs;
    @Column 
    private Timestamp updateTs;
    @ManyToOne
    private GradeItem gradeItem;
    @ManyToOne
    private User student;
    @ManyToOne
    private User grader;
    


    public Long getGradeEntryId() {
        return gradeEntryId;
    }

    public void setGradeEntryId(Long gradeEntryId) {
        this.gradeEntryId = gradeEntryId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gradeEntryId != null ? gradeEntryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the gradeEntryId fields are not set
        if (!(object instanceof GradeEntry)) {
            return false;
        }
        GradeEntry other = (GradeEntry) object;
        if ((this.gradeEntryId == null && other.gradeEntryId != null) || (this.gradeEntryId != null && !this.gradeEntryId.equals(other.gradeEntryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.GradeEntry[ Id=" + gradeEntryId + " ]";
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public String getNotes() {
        return notes;
    }

    public void setNotes(String notes) {
        this.notes = notes;
    }

    public Timestamp getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Timestamp createTs) {
        this.createTs = createTs;
    }

    public Timestamp getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Timestamp updateTs) {
        this.updateTs = updateTs;
    }

    public GradeItem getGradeItem() {
        return gradeItem;
    }

    public void setGradeItem(GradeItem gradeItem) {
        this.gradeItem = gradeItem;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public User getGrader() {
        return grader;
    }

    public void setGrader(User grader) {
        this.grader = grader;
    }
    
}
