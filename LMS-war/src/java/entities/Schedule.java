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
import javax.persistence.Table;

/**
 *
 * @author Asus
 */
@Entity
@Table(name="scheduleDetails")
public class Schedule implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long scheduleId;
    @Column
    private String year;
    @Column
    private int semester;
    @Column
    private Date moduleRound1StartDate;
    @Column
    private Date moduleRound1EndDate;
    @Column
    private Date moduleRound2StartDate;
    @Column
    private Date moduleRound2EndDate;
    @Column
    private Date moduleRound3StartDate;
    @Column
    private Date moduleRound3EndDate;
    @Column
    private Date tutorialRound1StartDate;
    @Column
    private Date tutorialRound1EndDate;
    @Column
    private Date tutorialRound2StartDate;
    @Column
    private Date tutorialRound2EndDate;

    public Schedule(Long scheduleId, String year, int semester, Date moduleRound1StartDate, Date moduleRound1EndDate, Date moduleRound2StartDate, Date moduleRound2EndDate, Date moduleRound3StartDate, Date moduleRound3EndDate, Date tutorialRound1StartDate, Date tutorialRound1EndDate, Date tutorialRound2StartDate, Date tutorialRound2EndDate) {
        this.scheduleId = scheduleId;
        this.year = year;
        this.semester = semester;
        this.moduleRound1StartDate = moduleRound1StartDate;
        this.moduleRound1EndDate = moduleRound1EndDate;
        this.moduleRound2StartDate = moduleRound2StartDate;
        this.moduleRound2EndDate = moduleRound2EndDate;
        this.moduleRound3StartDate = moduleRound3StartDate;
        this.moduleRound3EndDate = moduleRound3EndDate;
        this.tutorialRound1StartDate = tutorialRound1StartDate;
        this.tutorialRound1EndDate = tutorialRound1EndDate;
        this.tutorialRound2StartDate = tutorialRound2StartDate;
        this.tutorialRound2EndDate = tutorialRound2EndDate;
    }

    public Schedule() {
    }

    public Date getTutorialRound1StartDate() {
        return tutorialRound1StartDate;
    }

    public void setTutorialRound1StartDate(Date tutorialRound1StartDate) {
        this.tutorialRound1StartDate = tutorialRound1StartDate;
    }

    public Date getTutorialRound1EndDate() {
        return tutorialRound1EndDate;
    }

    public void setTutorialRound1EndDate(Date tutorialRound1EndDate) {
        this.tutorialRound1EndDate = tutorialRound1EndDate;
    }

    public Date getTutorialRound2StartDate() {
        return tutorialRound2StartDate;
    }

    public void setTutorialRound2StartDate(Date tutorialRound2StartDate) {
        this.tutorialRound2StartDate = tutorialRound2StartDate;
    }

    public Date getTutorialRound2EndDate() {
        return tutorialRound2EndDate;
    }

    public void setTutorialRound2EndDate(Date tutorialRound2EndDate) {
        this.tutorialRound2EndDate = tutorialRound2EndDate;
    }
    
    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }

    public int getSemester() {
        return semester;
    }

    public void setSemester(int semester) {
        this.semester = semester;
    }

    public Date getModuleRound1StartDate() {
        return moduleRound1StartDate;
    }

    public void setModuleRound1StartDate(Date moduleRound1StartDate) {
        this.moduleRound1StartDate = moduleRound1StartDate;
    }

    public Date getModuleRound1EndDate() {
        return moduleRound1EndDate;
    }

    public void setModuleRound1EndDate(Date moduleRound1EndDate) {
        this.moduleRound1EndDate = moduleRound1EndDate;
    }

    public Date getModuleRound2StartDate() {
        return moduleRound2StartDate;
    }

    public void setModuleRound2StartDate(Date moduleRound2StartDate) {
        this.moduleRound2StartDate = moduleRound2StartDate;
    }

    public Date getModuleRound2EndDate() {
        return moduleRound2EndDate;
    }

    public void setModuleRound2EndDate(Date moduleRound2EndDate) {
        this.moduleRound2EndDate = moduleRound2EndDate;
    }

    public Date getModuleRound3StartDate() {
        return moduleRound3StartDate;
    }

    public void setModuleRound3StartDate(Date moduleRound3StartDate) {
        this.moduleRound3StartDate = moduleRound3StartDate;
    }

    public Date getModuleRound3EndDate() {
        return moduleRound3EndDate;
    }

    public void setModuleRound3EndDate(Date moduleRound3EndDate) {
        this.moduleRound3EndDate = moduleRound3EndDate;
    }
    
    

    public Long getScheduleId() {
        return scheduleId;
    }

    public void setScheduleId(Long scheduleId) {
        this.scheduleId = scheduleId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (scheduleId != null ? scheduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the scheduleId fields are not set
        if (!(object instanceof Schedule)) {
            return false;
        }
        Schedule other = (Schedule) object;
        if ((this.scheduleId == null && other.scheduleId != null) || (this.scheduleId != null && !this.scheduleId.equals(other.scheduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Schedule[ id=" + scheduleId + " ]";
    }
    
}
