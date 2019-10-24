/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;

/**
 *
 * @author Asus
 */
@Entity
public class QuestionAttempt implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long questionAttemptId;
    @Column
    @Lob
    private String answer;
    @Column
    private Double marks;
    @ManyToOne
    private Question question;

    public Long getQuestionAttemptId() {
        return questionAttemptId;
    }

    public void setQuestionAttemptId(Long questionAttemptId) {
        this.questionAttemptId = questionAttemptId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (questionAttemptId != null ? questionAttemptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the questionAttemptId fields are not set
        if (!(object instanceof QuestionAttempt)) {
            return false;
        }
        QuestionAttempt other = (QuestionAttempt) object;
        if ((this.questionAttemptId == null && other.questionAttemptId != null) || (this.questionAttemptId != null && !this.questionAttemptId.equals(other.questionAttemptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.QuestionAttempt[ id=" + questionAttemptId + " ]";
    }

    public String getAnswer() {
        return answer;
    }

    public void setAnswer(String answer) {
        this.answer = answer;
    }

    public Double getMarks() {
        return marks;
    }

    public void setMarks(Double marks) {
        this.marks = marks;
    }

    public Question getQuestion() {
        return question;
    }

    public void setQuestion(Question question) {
        this.question = question;
    }
    
}
