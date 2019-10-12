package datamodel.rest;

import entities.Feedback;
import entities.Module;
import entities.Tutorial;
import entities.User;
import java.sql.Timestamp;
import java.util.List;
import util.GenderEnum;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vixson
 */
public class UpdateTutorialStudent {
    private Module module;
    
    private Tutorial tutorialList;
    private Long tutorialId;
    private int maxEnrollment;
    private String venue;
    private String timing;
    private List<User> studentList;
    
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private GenderEnum gender;
         

    public UpdateTutorialStudent() {
    }

    public UpdateTutorialStudent(int maxEnrollment, String venue, String timing, List<User> studentList) {
        this.maxEnrollment = maxEnrollment;
        this.venue = venue;
        this.timing = timing;
        this.studentList = studentList;
    }

    public UpdateTutorialStudent(Module module, Tutorial tutorialList, Long tutorialId, int maxEnrollment, String venue, String timing, List<User> studentList, Long userId, String firstName, String lastName, String email, String username, GenderEnum gender) {
        this.module = module;
        this.tutorialList = tutorialList;
        this.tutorialId = tutorialId;
        this.maxEnrollment = maxEnrollment;
        this.venue = venue;
        this.timing = timing;
        this.studentList = studentList;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.gender = gender;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
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

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
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

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    
}
