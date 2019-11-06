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
public class QuestionStatistic {
    Long questionId;
    String question;
    List<AnswerStatistic> answers;

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<AnswerStatistic> getAnswers() {
        return answers;
    }

    public void setAnswers(List<AnswerStatistic> answers) {
        this.answers = answers;
    }

    public QuestionStatistic(Long questionId, String question, List<AnswerStatistic> answers) {
        this.questionId = questionId;
        this.question = question;
        this.answers = answers;
    }

    public QuestionStatistic() {
    }
}
