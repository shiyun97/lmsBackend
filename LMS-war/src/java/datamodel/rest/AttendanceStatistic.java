/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import java.util.Date;

/**
 *
 * @author Asus
 */
public class AttendanceStatistic {
    Date startDate;
    Date endDate;
    int presentLecture;
    int presentTutorial;
    int absentLecture;
    int absentTutorial;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getPresentLecture() {
        return presentLecture;
    }

    public void setPresentLecture(int presentLecture) {
        this.presentLecture = presentLecture;
    }

    public int getPresentTutorial() {
        return presentTutorial;
    }

    public void setPresentTutorial(int presentTutorial) {
        this.presentTutorial = presentTutorial;
    }

    public int getAbsentLecture() {
        return absentLecture;
    }

    public void setAbsentLecture(int absentLecture) {
        this.absentLecture = absentLecture;
    }

    public int getAbsentTutorial() {
        return absentTutorial;
    }

    public void setAbsentTutorial(int absentTutorial) {
        this.absentTutorial = absentTutorial;
    }
}
