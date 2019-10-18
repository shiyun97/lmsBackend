/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vixson
 */
@Entity
@Table(name = "QUIZ")
@XmlRootElement
public class Quiz implements Serializable {

    public Quiz(String title, String description, Timestamp startDate, Timestamp endDate, Timestamp createTs, Timestamp updateTs, Integer maxNoOfAttempt, Double maxMarks, String owner, Module module, List<Question> questionList, List<QuizAttempt> quizAttemptList, LessonOrder lessonOrder) {
        this.title = title;
        this.description = description;
        this.startDate = startDate;
        this.endDate = endDate;
        this.createTs = createTs;
        this.updateTs = updateTs;
        this.maxNoOfAttempt = maxNoOfAttempt;
        this.maxMarks = maxMarks;
        this.owner = owner;
        this.module = module;
        this.questionList = questionList;
        this.quizAttemptList = quizAttemptList;
        this.lessonOrder = lessonOrder; 
    }

    public Quiz() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long quizId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Timestamp startDate;
    @Column
    private Timestamp endDate;
    @Column
    private Timestamp createTs;
    @Column
    private Timestamp updateTs;
    @Column
    private Integer maxNoOfAttempt;
    @Column
    private Double maxMarks;
    @Column
    private String owner;
    @ManyToOne
    private Module module;
    @OneToMany(mappedBy = "quiz")
    private List<Question> questionList;
    @OneToMany(mappedBy = "quiz")
    private List<QuizAttempt> quizAttemptList;
    
    @OneToOne
    private LessonOrder lessonOrder;

    public LessonOrder getLessonOrder() {
        return lessonOrder;
    }

    public void setLessonOrder(LessonOrder lessonOrder) {
        this.lessonOrder = lessonOrder;
    }
    
    

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (quizId != null ? quizId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the quizId fields are not set
        if (!(object instanceof Quiz)) {
            return false;
        }
        Quiz other = (Quiz) object;
        if ((this.quizId == null && other.quizId != null) || (this.quizId != null && !this.quizId.equals(other.quizId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.quiz[ Id=" + quizId + " ]";
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

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Timestamp getEndDate() {
        return endDate;
    }

    public void setEndDate(Timestamp endDate) {
        this.endDate = endDate;
    }

    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
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

    public Integer getMaxNoOfAttempt() {
        return maxNoOfAttempt;
    }

    public void setMaxNoOfAttempt(Integer maxNoOfAttempt) {
        this.maxNoOfAttempt = maxNoOfAttempt;
    }

    public Double getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Double maxMarks) {
        this.maxMarks = maxMarks;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<Question> getQuestionList() {
        return questionList;
    }

    public void setQuestionList(List<Question> questionList) {
        this.questionList = questionList;
    }

    public List<QuizAttempt> getQuizAttemptList() {
        return quizAttemptList;
    }

    public void setQuizAttemptList(List<QuizAttempt> quizAttemptList) {
        this.quizAttemptList = quizAttemptList;
    }
    
}
