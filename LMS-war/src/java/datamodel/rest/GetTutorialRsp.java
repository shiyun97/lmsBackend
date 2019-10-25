/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Tutorial;
import java.util.List;

/**
 *
 * @author Vixson
 */
public class GetTutorialRsp {
    
    private List<Tutorial> tutorials;
    private List<Integer> currentEnrollment;

    public GetTutorialRsp() {
    }

    public GetTutorialRsp(List<Tutorial> tutorials) {
        this.tutorials = tutorials;
    }

    public GetTutorialRsp(List<Tutorial> tutorials, List<Integer> currentEnrollment) {
        this.tutorials = tutorials;
        this.currentEnrollment = currentEnrollment;
    }

    public List<Tutorial> getTutorials() {
        return tutorials;
    }

    public void setTutorials(List<Tutorial> tutorials) {
        this.tutorials = tutorials;
    }
    
    public List<Integer> getCurrentEnrollment() {
        return currentEnrollment;
    }

    public void setCurrentEnrollment(List<Integer> currentEnrollment) {
        this.currentEnrollment = currentEnrollment;
    }
}
