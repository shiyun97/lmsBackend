/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
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
public class QuizAttempt implements Serializable {

    public QuizAttempt() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long quizAttemptId;
    @Column
    private Date createTs;
    @Column
    private Double totalMarks;
    @ManyToOne
    private Quiz quiz;
    @ManyToOne
    private User quizTaker;
    @OneToMany
    private List<QuestionAttempt> questionAttemptList;


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

    public Date getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Date createTs) {
        this.createTs = createTs;
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

    public List<QuestionAttempt> getQuestionAttemptList() {
        return questionAttemptList;
    }

    public void setQuestionAttemptList(List<QuestionAttempt> questionAttemptList) {
        this.questionAttemptList = questionAttemptList;
    }
    
}
