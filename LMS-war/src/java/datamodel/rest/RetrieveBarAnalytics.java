/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

/**
 *
 * @author Asus
 */
public class RetrieveBarAnalytics {
    String moduleCode;
    String moduleTitle;
    Long moduleId;
    int classSize;
    int lectureAttendance;
    int bookedConsultations;
    int totalConsultations;
    int quizAttempts;
    int forumContributions;

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getModuleCode() {
        return moduleCode;
    }

    public void setModuleCode(String moduleCode) {
        this.moduleCode = moduleCode;
    }

    public String getModuleTitle() {
        return moduleTitle;
    }

    public void setModuleTitle(String moduleTitle) {
        this.moduleTitle = moduleTitle;
    }

    public int getClassSize() {
        return classSize;
    }

    public void setClassSize(int classSize) {
        this.classSize = classSize;
    }

    public int getLectureAttendance() {
        return lectureAttendance;
    }

    public void setLectureAttendance(int lectureAttendance) {
        this.lectureAttendance = lectureAttendance;
    }

    public int getBookedConsultations() {
        return bookedConsultations;
    }

    public void setBookedConsultations(int bookedConsultations) {
        this.bookedConsultations = bookedConsultations;
    }

    public int getTotalConsultations() {
        return totalConsultations;
    }

    public void setTotalConsultations(int totalConsultations) {
        this.totalConsultations = totalConsultations;
    }

    public int getQuizAttempts() {
        return quizAttempts;
    }

    public void setQuizAttempts(int quizAttempts) {
        this.quizAttempts = quizAttempts;
    }

    public int getForumContributions() {
        return forumContributions;
    }

    public void setForumContributions(int forumContributions) {
        this.forumContributions = forumContributions;
    }
    
    
}
