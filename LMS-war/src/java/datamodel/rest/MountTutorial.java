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
public class MountTutorial {
    private Module module; 
    private Long moduleId;
 
    private Tutorial tutorialList;
    private Long tutorialId;
    private int maxEnrollment;
    private String venue;
    private String timing;
    private List<User> studentList;
    
    
    public MountTutorial() {
    }

    public MountTutorial(Module module, int maxEnrollment, String venue, String timing, List<User> studentList) {
        this.module = module;
        this.maxEnrollment = maxEnrollment;
        this.venue = venue;
        this.timing = timing;
        this.studentList = studentList;
    }

    public MountTutorial(Module module, Long moduleId, Tutorial tutorialList, Long tutorialId, int maxEnrollment, String venue, String timing, List<User> studentList) {
        this.module = module;
        this.moduleId = moduleId;
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

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
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
