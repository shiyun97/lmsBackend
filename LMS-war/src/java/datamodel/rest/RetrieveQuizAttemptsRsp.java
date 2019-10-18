/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.QuizAttempt;
import java.util.List;

/**
 *
 * @author Asus
 */
public class RetrieveQuizAttemptsRsp {
    List<QuizAttempt> quizAttempts;

    public List<QuizAttempt> getQuizAttempts() {
        return quizAttempts;
    }

    public void setQuizAttempts(List<QuizAttempt> quizAttempts) {
        this.quizAttempts = quizAttempts;
    }

    public RetrieveQuizAttemptsRsp(List<QuizAttempt> quizAttempts) {
        this.quizAttempts = quizAttempts;
    }

    public RetrieveQuizAttemptsRsp() {
    }
}
