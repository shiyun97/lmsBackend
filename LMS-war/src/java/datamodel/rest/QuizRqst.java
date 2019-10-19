/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Module;
import java.util.Date;
import java.util.List;
import util.QuestionOrderEnum;
import util.QuizTypeEnum;

/**
 *
 * @author Asus
 */
public class QuizRqst {
    
    private Long quizId;
    private String title;
    private String description;
    private QuizTypeEnum quizType;
    private QuestionOrderEnum questionsOrder;
    private String openingDate;
    private String closingDate;
    private Integer noOfAttempts;
    private boolean publish;
    private Integer maxTimeToFinish;
    private Long moduleId;
    private List<QuestionModel> questions;

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
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

    public QuizTypeEnum getQuizType() {
        return quizType;
    }

    public void setQuizType(QuizTypeEnum quizType) {
        this.quizType = quizType;
    }

    public QuestionOrderEnum getQuestionsOrder() {
        return questionsOrder;
    }

    public void setQuestionsOrder(QuestionOrderEnum questionsOrder) {
        this.questionsOrder = questionsOrder;
    }

    public String getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(String openingDate) {
        this.openingDate = openingDate;
    }

    public String getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(String closingDate) {
        this.closingDate = closingDate;
    }

    public Integer getNoOfAttempts() {
        return noOfAttempts;
    }

    public void setNoOfAttempts(Integer noOfAttempts) {
        this.noOfAttempts = noOfAttempts;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
    }

    public Integer getMaxTimeToFinish() {
        return maxTimeToFinish;
    }

    public void setMaxTimeToFinish(Integer maxTimeToFinish) {
        this.maxTimeToFinish = maxTimeToFinish;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public List<QuestionModel> getQuestions() {
        return questions;
    }

    public void setQuestions(List<QuestionModel> questions) {
        this.questions = questions;
    }
}
