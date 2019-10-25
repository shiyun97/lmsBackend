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
public class RetrieveQuestionAttemptsRsp {
    List<QuestionAttempt> questionAttempts;

    public List<QuestionAttempt> getQuestionAttempts() {
        return questionAttempts;
    }

    public void setQuestionAttempts(List<QuestionAttempt> questionAttempts) {
        this.questionAttempts = questionAttempts;
    }

    public RetrieveQuestionAttemptsRsp() {
    }

    public RetrieveQuestionAttemptsRsp(List<QuestionAttempt> questionAttempts) {
        this.questionAttempts = questionAttempts;
    }
}
