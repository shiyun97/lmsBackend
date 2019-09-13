/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.HashMap;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Vixson
 */
@Entity
public class SurveyAttempt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long surveyAttemptId;
    @Column
    private Timestamp createTs;
    @Column
    private Timestamp updateTs;
    @Column
    private Timestamp submitTs;
    @Column
    private HashMap answers;
    @ManyToOne
    private Survey survey;
    @ManyToOne
    private User surveyTaker;

    public Long getSurveyAttemptId() {
        return surveyAttemptId;
    }

    public void setSurveyAttemptId(Long surveyAttemptId) {
        this.surveyAttemptId = surveyAttemptId;
    }

    public Timestamp getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Timestamp createTs) {
        this.createTs = createTs;
    }

    public Timestamp getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Timestamp updateTs) {
        this.updateTs = updateTs;
    }

    public Timestamp getSubmitTs() {
        return submitTs;
    }

    public void setSubmitTs(Timestamp submitTs) {
        this.submitTs = submitTs;
    }

    public HashMap getAnswers() {
        return answers;
    }

    public void setAnswers(HashMap answers) {
        this.answers = answers;
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

    
    public Long getId() {
        return surveyAttemptId;
    }

    public void setId(Long id) {
        this.surveyAttemptId = id;
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
