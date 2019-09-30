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
public class UpdateModule {
    private Module module;
    
    private Long moduleId;
    private String code;
    private String title;
    private String description;
    private Integer semesterOffered;
    private String yearOffered;
    private Integer creditUnit;
    private Integer maxEnrollment;
    private User assignedTeacher;
    private boolean hasExam;
    private Timestamp examTime;
    private String examVenue;
    private String lectureDetails;
    
    private Tutorial tutorialList;
    private Long tutorialId;
    private String venue;
    private String timing;
    private List<User> studentList;
    private String faculty;
    private String department;
          
    private User user;
    
    private Long userId;
    private String email;
    private String password;
    

    public UpdateModule() {
    }

    public UpdateModule(Module module, Long moduleId, String code, String title, String description, Integer semesterOffered, String yearOffered, Integer creditUnit, Integer maxEnrollment, User assignedTeacher, boolean hasExam, Timestamp examTime, String examVenue, String lectureDetails, Tutorial tutorialList, Long tutorialId, String venue, String timing, List<User> studentList, String faculty, String department, User user, Long userId, String email, String password) {
        this.module = module;
        this.moduleId = moduleId;
        this.code = code;
        this.title = title;
        this.description = description;
        this.semesterOffered = semesterOffered;
        this.yearOffered = yearOffered;
        this.creditUnit = creditUnit;
        this.maxEnrollment = maxEnrollment;
        this.assignedTeacher = assignedTeacher;
        this.hasExam = hasExam;
        this.examTime = examTime;
        this.examVenue = examVenue;
        this.lectureDetails = lectureDetails;
        this.tutorialList = tutorialList;
        this.tutorialId = tutorialId;
        this.venue = venue;
        this.timing = timing;
        this.studentList = studentList;
        this.faculty = faculty;
        this.department = department;
        this.user = user;
        this.userId = userId;
        this.email = email;
        this.password = password;
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

    public String getYearOffered() {
        return yearOffered;
    }

    public void setYearOffered(String yearOffered) {
        this.yearOffered = yearOffered;
    }

    public User getAssignedTeacher() {
        return assignedTeacher;
    }

    public void setAssignedTeacher(User assignedTeacher) {
        this.assignedTeacher = assignedTeacher;
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

    /**public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }**/

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

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }
}
