/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import util.QuestionTypeEnum;

/**
 *
 * @author Vixson
 */
@Entity
public class Question implements Serializable {

    public Question(QuestionTypeEnum type, Integer number, String description, String key, Double marks, Integer maxLength, Boolean mandatory, HashMap options, Quiz quiz, Survey survey) {
        this.type = type;
        this.number = number;
        this.description = description;
        this.key = key;
        this.marks = marks;
        this.maxLength = maxLength;
        this.mandatory = mandatory;
        this.options = options;
        this.quiz = quiz;
        this.survey = survey;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionId;
    @Column
    private QuestionTypeEnum type; 
    @Column
    private Integer number;
    @Column
    private String description;
    @Column
    private String key;
    @Column
    private Double marks;
    @Column
    private Integer maxLength;
    @Column
    private Boolean mandatory;
    @Column
    private HashMap options;
    @ManyToOne
    private Quiz quiz;
    @ManyToOne
    private Survey survey;
    
    
    public Question(QuestionTypeEnum type, Integer number, String description, String key, Double marks, Integer maxLength, Boolean mandatory, HashMap options){
        this.type = type;
        this.number = number;
        this.description = description;
        this.key = key;
        this.marks = marks;
        this.maxLength = maxLength;
        this.mandatory = mandatory;
        this.options = options;
    }
    

    public Long getQuestionId() {
        return questionId;
    }

    public void setQuestionId(Long questionId) {
        this.questionId = questionId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionId != null ? questionId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the questionId fields are not set
        if (!(object instanceof Question)) {
            return false;
        }
        Question other = (Question) object;
        if ((this.questionId == null && other.questionId != null) || (this.questionId != null && !this.questionId.equals(other.questionId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Question[ Id=" + questionId + " ]";
    }

    public QuestionTypeEnum getType() {
        return type;
    }

    public void setType(QuestionTypeEnum type) {
        this.type = type;
    }

    public Integer getNumber() {
        return number;
    }

    public void setNumber(Integer number) {
        this.number = number;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public Integer getMaxLength() {
        return maxLength;
    }

    public void setMaxLength(Integer maxLength) {
        this.maxLength = maxLength;
    }

    public Boolean getMandatory() {
        return mandatory;
    }

    public void setMandatory(Boolean mandatory) {
        this.mandatory = mandatory;
    }

    public HashMap getOptions() {
        return options;
    }

    public void setOptions(HashMap options) {
        this.options = options;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }
    
}
