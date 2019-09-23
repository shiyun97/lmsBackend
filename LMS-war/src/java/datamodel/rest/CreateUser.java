package datamodel.rest;

import entities.ClassGroup;
import entities.ConsultationTimeslot;
import entities.Module;
import entities.QuizAttempt;
import entities.User;
import entities.SurveyAttempt;
import util.GenderEnum;
import util.AccessRightEnum;
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
public class CreateUser {
    private User user;
    
    private Long userId;
    private String firstName;
    private String lastName;
    private String email;
    private String username;
    private String password;
    private GenderEnum gender;
    private AccessRightEnum accessRight;
    private List<ConsultationTimeslot> consultationTimeslotList;
    private List<QuizAttempt> quizAttemptList;
    private List<SurveyAttempt> surveyAttemptList;
    private List<ClassGroup> classGroupList;
    private List<Module> moduleList;
    
    
    public CreateUser() {
    }

    public CreateUser(User user, Long userId, String firstName, String lastName, String email, String username, String password, GenderEnum gender, AccessRightEnum accessRight, List<ConsultationTimeslot> consultationTimeslotList, List<QuizAttempt> quizAttemptList, List<SurveyAttempt> surveyAttemptList, List<ClassGroup> classGroupList, List<Module> moduleList) {
        this.user = user;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.accessRight = accessRight;
        this.consultationTimeslotList = consultationTimeslotList;
        this.quizAttemptList = quizAttemptList;
        this.surveyAttemptList = surveyAttemptList;
        this.classGroupList = classGroupList;
        this.moduleList = moduleList;
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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public AccessRightEnum getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRightEnum accessRight) {
        this.accessRight = accessRight;
    }

    public List<ConsultationTimeslot> getConsultationTimeslotList() {
        return consultationTimeslotList;
    }

    public void setConsultationTimeslotList(List<ConsultationTimeslot> consultationTimeslotList) {
        this.consultationTimeslotList = consultationTimeslotList;
    }

    public List<QuizAttempt> getQuizAttemptList() {
        return quizAttemptList;
    }

    public void setQuizAttemptList(List<QuizAttempt> quizAttemptList) {
        this.quizAttemptList = quizAttemptList;
    }

    public List<SurveyAttempt> getSurveyAttemptList() {
        return surveyAttemptList;
    }

    public void setSurveyAttemptList(List<SurveyAttempt> surveyAttemptList) {
        this.surveyAttemptList = surveyAttemptList;
    }

    public List<ClassGroup> getClassGroupList() {
        return classGroupList;
    }

    public void setClassGroupList(List<ClassGroup> classGroupList) {
        this.classGroupList = classGroupList;
    }

    public List<Module> getModuleList() {
        return moduleList;
    }

    public void setModuleList(List<Module> moduleList) {
        this.moduleList = moduleList;
    }
}
