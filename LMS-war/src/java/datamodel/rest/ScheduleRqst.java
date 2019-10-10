/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import java.util.Date;
import javax.persistence.Column;

/**
 *
 * @author Asus
 */
public class ScheduleRqst {
    
    private String year;
    private int semester;
    private String moduleRound1StartDate;
    private String moduleRound1EndDate;
    private String moduleRound2StartDate;
    private String moduleRound2EndDate;
    private String moduleRound3StartDate;
    private String moduleRound3EndDate;
    private String tutorialRound1StartDate;
    private String tutorialRound1EndDate;
    private String tutorialRound2StartDate;
    private String tutorialRound2EndDate;

    public ScheduleRqst() {
    }

    public String getTutorialRound1StartDate() {
        return tutorialRound1StartDate;
    }

    public void setTutorialRound1StartDate(String tutorialRound1StartDate) {
        this.tutorialRound1StartDate = tutorialRound1StartDate;
    }

    public String getTutorialRound1EndDate() {
        return tutorialRound1EndDate;
    }

    public void setTutorialRound1EndDate(String tutorialRound1EndDate) {
        this.tutorialRound1EndDate = tutorialRound1EndDate;
    }

    public String getTutorialRound2StartDate() {
        return tutorialRound2StartDate;
    }

    public void setTutorialRound2StartDate(String tutorialRound2StartDate) {
        this.tutorialRound2StartDate = tutorialRound2StartDate;
    }

    public String getTutorialRound2EndDate() {
        return tutorialRound2EndDate;
    }

    public void setTutorialRound2EndDate(String tutorialRound2EndDate) {
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

    public String getModuleRound1StartDate() {
        return moduleRound1StartDate;
    }

    public void setModuleRound1StartDate(String moduleRound1StartDate) {
        this.moduleRound1StartDate = moduleRound1StartDate;
    }

    public String getModuleRound1EndDate() {
        return moduleRound1EndDate;
    }

    public void setModuleRound1EndDate(String moduleRound1EndDate) {
        this.moduleRound1EndDate = moduleRound1EndDate;
    }

    public String getModuleRound2StartDate() {
        return moduleRound2StartDate;
    }

    public void setModuleRound2StartDate(String moduleRound2StartDate) {
        this.moduleRound2StartDate = moduleRound2StartDate;
    }

    public String getModuleRound2EndDate() {
        return moduleRound2EndDate;
    }

    public void setModuleRound2EndDate(String moduleRound2EndDate) {
        this.moduleRound2EndDate = moduleRound2EndDate;
    }

    public String getModuleRound3StartDate() {
        return moduleRound3StartDate;
    }

    public void setModuleRound3StartDate(String moduleRound3StartDate) {
        this.moduleRound3StartDate = moduleRound3StartDate;
    }

    public String getModuleRound3EndDate() {
        return moduleRound3EndDate;
    }

    public void setModuleRound3EndDate(String moduleRound3EndDate) {
        this.moduleRound3EndDate = moduleRound3EndDate;
    }
}
