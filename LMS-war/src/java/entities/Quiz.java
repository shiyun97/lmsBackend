/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.xml.bind.annotation.XmlRootElement;
import util.QuestionOrderEnum;
import util.QuizTypeEnum;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import util.xml.DateAdapter;

/**
 *
 * @author Vixson
 */
@Entity
@Table(name = "QUIZ")
@XmlRootElement
public class Quiz implements Serializable {

    public Quiz(Long quizId, String title, String description, QuizTypeEnum quizType, QuestionOrderEnum questionsOrder, Date openingDate, Date closingDate, Integer noOfAttempts, Double maxMarks, boolean publish, Integer maxTimeToFinish, Module module, List<Question> questionList, List<QuizAttempt> quizAttemptList) {
        this.quizId = quizId;
        this.title = title;
        this.description = description;
        this.quizType = quizType;
        this.questionsOrder = questionsOrder;
        this.openingDate = openingDate;
        this.closingDate = closingDate;
        this.noOfAttempts = noOfAttempts;
        this.maxMarks = maxMarks;
        this.publish = publish;
        this.maxTimeToFinish = maxTimeToFinish;
        this.module = module;
        this.questionList = questionList;
        this.quizAttemptList = quizAttemptList;
        this.lessonOrder = lessonOrder; 
    }

    public Quiz() {
        questionList = new ArrayList<>();
        quizAttemptList = new ArrayList<>();
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
    private QuizTypeEnum quizType;
    @Column
    private QuestionOrderEnum questionsOrder;
    @Column
    private Date openingDate;
    @Column
    private Date closingDate;
    @Column
    private Integer noOfAttempts;
    @Column
    private Double maxMarks;
    @Column
    private boolean publish;
    @Column
    private boolean publishAnswer;
    @Column
    private Integer maxTimeToFinish;
    @Column
    private boolean gradeitemCreated;
    @ManyToOne
    private Module module;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<Question> questionList;
    @OneToMany(mappedBy = "quiz")
    private List<QuizAttempt> quizAttemptList;
    @Column
    private boolean completed;
    
    @OneToOne
    private LessonOrder lessonOrder;

    public LessonOrder getLessonOrder() {
        return lessonOrder;
    }

    public boolean isPublish() {
        return publish;
    }

    public void setPublish(boolean publish) {
        this.publish = publish;
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

    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getOpeningDate() {
        return openingDate;
    }

    public void setOpeningDate(Date openingDate) {
        this.openingDate = openingDate;
    }

    @XmlJavaTypeAdapter(DateAdapter.class)
    public Date getClosingDate() {
        return closingDate;
    }

    public void setClosingDate(Date closingDate) {
        this.closingDate = closingDate;
    }

    public Integer getNoOfAttempts() {
        return noOfAttempts;
    }

    public void setNoOfAttempts(Integer noOfAttempts) {
        this.noOfAttempts = noOfAttempts;
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

    public Integer getMaxTimeToFinish() {
        return maxTimeToFinish;
    }

    public void setMaxTimeToFinish(Integer maxTimeToFinish) {
        this.maxTimeToFinish = maxTimeToFinish;
    }

    public boolean isPublishAnswer() {
        return publishAnswer;
    }

    public void setPublishAnswer(boolean publishAnswer) {
        this.publishAnswer = publishAnswer;
    }

    public boolean isGradeitemCreated() {
        return gradeitemCreated;
    }

    public void setGradeitemCreated(boolean gradeitemCreated) {
        this.gradeitemCreated = gradeitemCreated;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }
    
}
