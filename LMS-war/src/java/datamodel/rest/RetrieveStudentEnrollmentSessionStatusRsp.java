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
public class RetrieveStudentEnrollmentSessionStatusRsp {
    private boolean moduleRoundOpen;
    private boolean tutorialRoundOpen;

    public boolean isModuleRoundOpen() {
        return moduleRoundOpen;
    }

    public RetrieveStudentEnrollmentSessionStatusRsp() {
    }

    public void setModuleRoundOpen(boolean moduleRoundOpen) {
        this.moduleRoundOpen = moduleRoundOpen;
    }

    public boolean isTutorialRoundOpen() {
        return tutorialRoundOpen;
    }

    public void setTutorialRoundOpen(boolean tutorialRoundOpen) {
        this.tutorialRoundOpen = tutorialRoundOpen;
    }
    
    
}
