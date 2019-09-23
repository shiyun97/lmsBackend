package datamodel.rest;

import entities.Module;
import entities.User;
import java.sql.Timestamp;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vixson
 */
public class MountModuleReq {
    private Module module;
    
    private Long moduleId;
    private String code;
    private String title;
    private String description;
    private Integer semesterOffered;
    private Integer creditUnit;
    private Integer maxEnrollment;
    private boolean hasExam;
    private Timestamp examTime;
    private String examVenue;
    private User assignedTeacher;
    
    
    public MountModuleReq() {
    }

    public MountModuleReq(Module module, Long moduleId, String code, String title, String description, Integer semesterOffered, Integer creditUnit, Integer maxEnrollment, boolean hasExam, Timestamp examTime, String examVenue, User assignedTeacher) {
        this.module = module;
        this.moduleId = moduleId;
        this.code = code;
        this.title = title;
        this.description = description;
        this.semesterOffered = semesterOffered;
        this.creditUnit = creditUnit;
        this.maxEnrollment = maxEnrollment;
        this.hasExam = hasExam;
        this.examTime = examTime;
        this.examVenue = examVenue;
        this.assignedTeacher = assignedTeacher;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public Integer getSemesterOffered() {
        return semesterOffered;
    }

    public void setSemesterOffered(Integer semesterOffered) {
        this.semesterOffered = semesterOffered;
    }

    public Integer getCreditUnit() {
        return creditUnit;
    }

    public void setCreditUnit(Integer creditUnit) {
        this.creditUnit = creditUnit;
    }

    public Integer getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(Integer maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public boolean isHasExam() {
        return hasExam;
    }

    public void setHasExam(boolean hasExam) {
        this.hasExam = hasExam;
    }

    public Timestamp getExamTime() {
        return examTime;
    }

    public void setExamTime(Timestamp examTime) {
        this.examTime = examTime;
    }

    public String getExamVenue() {
        return examVenue;
    }

    public void setExamVenue(String examVenue) {
        this.examVenue = examVenue;
    }

    public User getAssignedTeacher() {
        return assignedTeacher;
    }

    public void setAssignedTeacher(User assignedTeacher) {
        this.assignedTeacher = assignedTeacher;
    }
}
