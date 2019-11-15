/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToOne;

/**
 *
 * @author Vixson
 */
@Entity
public class CoursepackEnrollment implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @Column
    private Date enrollDate;
    @Column
    private int numberEnrolled;

    public CoursepackEnrollment() {
    }

    public CoursepackEnrollment(Long id, Date enrollDate) {
        this.id = id;
        this.enrollDate = enrollDate;
    }

    public CoursepackEnrollment(Long id, Date enrollDate, int numberEnrolled) {
        this.id = id;
        this.enrollDate = enrollDate;
        this.numberEnrolled = numberEnrolled;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (id != null ? id.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof CoursepackEnrollment)) {
            return false;
        }
        CoursepackEnrollment other = (CoursepackEnrollment) object;
        if ((this.id == null && other.id != null) || (this.id != null && !this.id.equals(other.id))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.CoursepackEnrollment[ id=" + id + " ]";
    }

    public Date getEnrollDate() {
        return enrollDate;
    }

    public void setEnrollDate(Date enrollDate) {
        this.enrollDate = enrollDate;
    }

    public int getNumberEnrolled() {
        return numberEnrolled;
    }

    public void setNumberEnrolled(int numberEnrolled) {
        this.numberEnrolled = numberEnrolled;
    }
    
}
