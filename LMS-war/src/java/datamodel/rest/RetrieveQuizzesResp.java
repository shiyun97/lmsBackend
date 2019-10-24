/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Quiz;
import java.util.List;

/**
 *
 * @author Asus
 */
public class RetrieveQuizzesResp {
    List<Quiz> quizzes;

    public List<Quiz> getQuizzes() {
        return quizzes;
    }

    public void setQuizzes(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public RetrieveQuizzesResp(List<Quiz> quizzes) {
        this.quizzes = quizzes;
    }

    public RetrieveQuizzesResp() {
    }
    
    
}
