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
public class QuizAttempt implements Serializable {

    public QuizAttempt(Timestamp createTs, Timestamp updateTs, Timestamp submitTs, HashMap answers, Double totalMarks, Quiz quiz, User quizTaker) {
        this.createTs = createTs;
        this.updateTs = updateTs;
        this.submitTs = submitTs;
        this.answers = answers;
        this.totalMarks = totalMarks;
        this.quiz = quiz;
        this.quizTaker = quizTaker;
    }

    public QuizAttempt() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long quizAttemptId;
    @Column
    private Timestamp createTs;
    @Column
    private Timestamp updateTs;
    @Column
    private Timestamp submitTs;
    @Column
    private HashMap answers;
    @Column
    private Double totalMarks;
    @ManyToOne
    private Quiz quiz;
    @ManyToOne
    private User quizTaker;


    public Long getQuizAttemptId() {
        return quizAttemptId;
    }

    public void setQuizAttemptId(Long quizAttemptId) {
        this.quizAttemptId = quizAttemptId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (quizAttemptId != null ? quizAttemptId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the quizAttemptId fields are not set
        if (!(object instanceof QuizAttempt)) {
            return false;
        }
        QuizAttempt other = (QuizAttempt) object;
        if ((this.quizAttemptId == null && other.quizAttemptId != null) || (this.quizAttemptId != null && !this.quizAttemptId.equals(other.quizAttemptId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.QuizAttempt[ Id=" + quizAttemptId + " ]";
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

    public Double getTotalMarks() {
        return totalMarks;
    }

    public void setTotalMarks(Double totalMarks) {
        this.totalMarks = totalMarks;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }

    public User getQuizTaker() {
        return quizTaker;
    }

    public void setQuizTaker(User quizTaker) {
        this.quizTaker = quizTaker;
    }
    
}
