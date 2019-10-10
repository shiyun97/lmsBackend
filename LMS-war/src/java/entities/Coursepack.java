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

/**
 *
 * @author Vixson
 */
@Entity
public class Coursepack implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long coursepackId;
    @Column
    private String code;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String category;
    @Column
    private Double price;
    @Column 
    private Date startDate;
    @Column
    private Double rating;
    @Column
    private String teacherBackground;

    public Long getCoursepackId() {
        return coursepackId;
    }

    public void setCoursepackId(Long coursepackId) {
        this.coursepackId = coursepackId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coursepackId != null ? coursepackId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the coursepackId fields are not set
        if (!(object instanceof Coursepack)) {
            return false;
        }
        Coursepack other = (Coursepack) object;
        if ((this.coursepackId == null && other.coursepackId != null) || (this.coursepackId != null && !this.coursepackId.equals(other.coursepackId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Coursepack[ coursepackId=" + coursepackId + " ]";
    }
    
}
