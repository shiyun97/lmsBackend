/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.QuestionAttempt;
import java.util.List;

/**
 *
 * @author Asus
 */
public class CreateQuizAttemptRqst {
    private Long quizId;
    private List<QuestionAttemptModel> questionAttempts;

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public List<QuestionAttemptModel> getQuestionAttempts() {
        return questionAttempts;
    }

    public void setQuestionAttempts(List<QuestionAttemptModel> questionAttempts) {
        this.questionAttempts = questionAttempts;
    }
    
}
