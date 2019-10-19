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

    public GradeEntry() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gradeEntryId;
    @Column
    private Double marks;
    @Column
    private String remarks;
    @ManyToOne
    private GradeItem gradeItem;
    @ManyToOne
    private User student;
    


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

    public String getRemarks() {
        return remarks;
    }

    public void setRemarks(String remarks) {
        this.remarks = remarks;
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
    
}
