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
 * @author Asus
 */
public class RetrieveEnrolledStudentTutorialsRsp {
    private List<Tutorial> tutorials;

    public RetrieveEnrolledStudentTutorialsRsp(List<Tutorial> tutorials) {
        this.tutorials = tutorials;
    }

    public RetrieveEnrolledStudentTutorialsRsp() {
    }

    public List<Tutorial> getTutorials() {
        return tutorials;
    }

    public void setTutorials(List<Tutorial> tutorials) {
        this.tutorials = tutorials;
    }
}
