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

    public LessonPlan(String title, String description, Timestamp startDate, Timestamp endDate, Module module) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.module = module;
    }

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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
}
