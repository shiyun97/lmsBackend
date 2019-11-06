/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import java.util.List;

/**
 *
 * @author Asus
 */
public class RetrieveSurveyStatistics {
    Long surveyId;
    Long quizId;
    String title;
    String description;
    int attempts;
    List<QuestionStatistic> questions;

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public int getAttempts() {
        return attempts;
    }

    public void setAttempts(int attempts) {
        this.attempts = attempts;
    }

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
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

    public List<QuestionStatistic> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionStatistic> questions) {
        this.questions = questions;
    }

    public RetrieveSurveyStatistics(List<QuestionStatistic> questions) {
        this.questions = questions;
    }
    
}
