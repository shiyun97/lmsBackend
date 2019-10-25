/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.SurveyAttempt;
import java.util.List;

/**
 *
 * @author Asus
 */
public class RetrieveSurveyAttempts {
    List<SurveyAttempt> surveyAttempts;

    public List<SurveyAttempt> getSurveyAttempts() {
        return surveyAttempts;
    }

    public void setSurveyAttempts(List<SurveyAttempt> surveyAttempts) {
        this.surveyAttempts = surveyAttempts;
    }

    public RetrieveSurveyAttempts() {
    }

    public RetrieveSurveyAttempts(List<SurveyAttempt> surveyAttempts) {
        this.surveyAttempts = surveyAttempts;
    }
    
}
