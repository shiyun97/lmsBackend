package datamodel.rest;

import entities.Module;
import entities.Tutorial;
import entities.User;
import java.sql.Timestamp;
import java.util.List;

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
    private String yearOffered;
    private Integer creditUnit;
    private Integer maxEnrollment;
    private boolean hasExam;
    private Timestamp examTime;
    private String examVenue;
    private User assignedTeacher;
    private String lectureDetails;
    
    private Tutorial tutorialList;
    private Long tutorialId;
    private String venue;
    private String timing;
    private List<User> studentList;
    
    private User user;
    
    private Long userId;
    private String username;
    private String password;
    
    private String email;

    
    public MountModuleReq() {
    }

    public MountModuleReq(Module module, Long moduleId, String code, String title, String description, Integer semesterOffered, String yearOffered, Integer creditUnit, Integer maxEnrollment, boolean hasExam, Timestamp examTime, String examVenue, User assignedTeacher, String lectureDetails, Tutorial tutorialList, Long tutorialId, String venue, String timing, List<User> studentList, User user, Long userId, String username, String password, String email) {
        this.module = module;
        this.moduleId = moduleId;
        this.code = code;
        this.title = title;
        this.description = description;
        this.semesterOffered = semesterOffered;
        this.yearOffered = yearOffered;
        this.creditUnit = creditUnit;
        this.maxEnrollment = maxEnrollment;
        this.hasExam = hasExam;
        this.examTime = examTime;
        this.examVenue = examVenue;
        this.assignedTeacher = assignedTeacher;
        this.lectureDetails = lectureDetails;
        this.tutorialList = tutorialList;
        this.tutorialId = tutorialId;
        this.venue = venue;
        this.timing = timing;
        this.studentList = studentList;
        this.user = user;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
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

    public String getYearOffered() {
        return yearOffered;
    }

    public void setYearOffered(String yearOffered) {
        this.yearOffered = yearOffered;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getLectureDetails() {
        return lectureDetails;
    }

    public void setLectureDetails(String lectureDetails) {
        this.lectureDetails = lectureDetails;
    }

    public Tutorial getTutorialList() {
        return tutorialList;
    }

    public void setTutorialList(Tutorial tutorialList) {
        this.tutorialList = tutorialList;
    }

    public Long getTutorialId() {
        return tutorialId;
    }

    public void setTutorialId(Long tutorialId) {
        this.tutorialId = tutorialId;
    }

    public String getVenue() {
        return venue;
    }

    public void setVenue(String venue) {
        this.venue = venue;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public List<User> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<User> studentList) {
        this.studentList = studentList;
    }
}
