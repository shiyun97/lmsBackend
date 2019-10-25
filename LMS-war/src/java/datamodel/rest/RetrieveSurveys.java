/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Survey;
import java.util.List;

/**
 *
 * @author Asus
 */
public class RetrieveSurveys {
    List<Survey> surveys;

    public RetrieveSurveys() {
    }

    public RetrieveSurveys(List<Survey> surveys) {
        this.surveys = surveys;
    }

    public List<Survey> getSurveys() {
        return surveys;
    }

    public void setSurveys(List<Survey> surveys) {
        this.surveys = surveys;
    }
}
