/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Vixson
 */
@Entity
public class SurveyAttempt implements Serializable {

    public SurveyAttempt() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long surveyAttemptId;
    @ManyToOne
    private Survey survey;
    @ManyToOne
    private User surveyTaker;
    @OneToMany
    private List<QuestionAttempt> questionAttemptList;

    public Long getSurveyAttemptId() {
        return surveyAttemptId;
    }

    public void setSurveyAttemptId(Long surveyAttemptId) {
        this.surveyAttemptId = surveyAttemptId;
    }

    public Survey getSurvey() {
        return survey;
    }

    public void setSurvey(Survey survey) {
        this.survey = survey;
    }

    public User getSurveyTaker() {
        return surveyTaker;
    }

    public void setSurveyTaker(User surveyTaker) {
        this.surveyTaker = surveyTaker;
    }

    public List<QuestionAttempt> getQuestionAttemptList() {
        return questionAttemptList;
    }

    public void setQuestionAttemptList(List<QuestionAttempt> questionAttemptList) {
        this.questionAttemptList = questionAttemptList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (surveyAttemptId != null ? surveyAttemptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the surveyAttemptId fields are not set
        if (!(object instanceof SurveyAttempt)) {
            return false;
        }
        SurveyAttempt other = (SurveyAttempt) object;
        if ((this.surveyAttemptId == null && other.surveyAttemptId != null) || (this.surveyAttemptId != null && !this.surveyAttemptId.equals(other.surveyAttemptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.SurveyAttempt[ id=" + surveyAttemptId + " ]";
    }
    
}
