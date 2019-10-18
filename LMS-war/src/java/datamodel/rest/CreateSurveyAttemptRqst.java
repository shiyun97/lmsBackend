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
public class CreateSurveyAttemptRqst {
    private Long surveyId;
    private List<QuestionAttemptModel> questionAttempts;

    public Long getSurveyId() {
        return surveyId;
    }

    public void setSurveyId(Long surveyId) {
        this.surveyId = surveyId;
    }

    public List<QuestionAttemptModel> getQuestionAttempts() {
        return questionAttempts;
    }

    public void setQuestionAttempts(List<QuestionAttemptModel> questionAttempts) {
        this.questionAttempts = questionAttempts;
    }
}
