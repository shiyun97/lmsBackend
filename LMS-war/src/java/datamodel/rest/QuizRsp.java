/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import util.QuestionOrderEnum;
import util.QuizTypeEnum;

/**
 *
 * @author Asus
 */
public class QuizRsp {
    private Long quizId;
    private String title;
    private String description;
    private QuizTypeEnum quizType;
    private Double maxMarks;
    private String showProgressBar = "top";
    private String startSurveyText = "Start";
    private String completeText = "Submit";
    private String showTimerPanel = "top";
    private QuestionOrderEnum questionsOrder;
    private String openingDate;
    private String closingDate;
    private Integer noOfAttempts;
    private boolean publish;
    private Integer maxTimeToFinish;
    private Long moduleId;
    private List<PageModel> pages = new ArrayList<>();

    public QuizRsp() {
    }

    public Double getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Double maxMarks) {
        this.maxMarks = maxMarks;
    }

    public String getShowProgressBar() {
        return showProgressBar;
    }

    public void setShowProgressBar(String showProgressBar) {
        this.showProgressBar = showProgressBar;
    }

    public String getStartSurveyText() {
        return startSurveyText;
    }

    public void setStartSurveyText(String startSurveyText) {
        this.startSurveyText = startSurveyText;
    }

    public String getCompleteText() {
        return completeText;
    }

    public void setCompleteText(String completeText) {
        this.completeText = completeText;
    }

    public String getShowTimerPanel() {
        return showTimerPanel;
    }

    public void setShowTimerPanel(String showTimerPanel) {
        this.showTimerPanel = showTimerPanel;
    }

    public List<PageModel> getPages() {
        return pages;
    }

    public void setPages(List<PageModel> pages) {
        this.pages = pages;
    }

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
}
