package datamodel.rest;

import entities.Feedback;
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
public class UpdateModuleTutorial {
    private Module module;
    
    private Tutorial tutorialList;
    private Long tutorialId;
    private int maxEnrollment;
    private String venue;
    private String timing;
    private List<User> studentList;
         

    public UpdateModuleTutorial() {
    }

    public UpdateModuleTutorial(Module module, Tutorial tutorialList, Long tutorialId, int maxEnrollment, String venue, String timing, List<User> studentList) {
        this.module = module;
        this.tutorialList = tutorialList;
        this.tutorialId = tutorialId;
        this.maxEnrollment = maxEnrollment;
        this.venue = venue;
        this.timing = timing;
        this.studentList = studentList;
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

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    
}
