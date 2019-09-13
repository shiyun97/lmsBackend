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
public class LessonPlan implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lessonPlanid;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Timestamp startDate;
    @Column
    private Timestamp endDate;
    @ManyToOne
    private Module module;

    public Long getId() {
        return lessonPlanid;
    }

    public void setId(Long lessonPlanid) {
        this.lessonPlanid = lessonPlanid;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lessonPlanid != null ? lessonPlanid.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the lessonPlanid fields are not set
        if (!(object instanceof LessonPlan)) {
            return false;
        }
        LessonPlan other = (LessonPlan) object;
        if ((this.lessonPlanid == null && other.lessonPlanid != null) || (this.lessonPlanid != null && !this.lessonPlanid.equals(other.lessonPlanid))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.LessonPlan[ id=" + lessonPlanid + " ]";
    }
    
}
