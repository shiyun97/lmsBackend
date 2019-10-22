/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * 
 */
@Entity
public class Coursepack implements Serializable {
    
    public Coursepack(){
        
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long coursepackId;
    @Column
    private String code;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String category;
    @Column
    private Double price;
    @Column 
    private Timestamp startDate;
    @Column
    private Double rating;
    @Column
    private String teacherBackground;

    @ManyToMany
    private List<User> publicUserList;
    //@OneToMany(mappedBy = "coursepack")
    //private List<Folder> folderList;
    @OneToMany(mappedBy = "coursepack")
    private List<ForumTopic> forumTopicList;
    //@OneToMany(mappedBy = "coursepack")
    //private List<Quiz> quizList;
    @OneToMany(mappedBy = "coursepack")
    private List<GradeItem> gradeItemList;
    @ManyToOne
    private User assignedTeacher;
    @OneToMany
    private List<Feedback> feedbackList;
    
    @OneToMany
    private List<Outlines> outlineList;
    @OneToMany(mappedBy = "coursepack")
    private List<File> multimediaList;
     
    

    public Coursepack(Long coursepackId, String code, String title, String description, String category, Double price, Timestamp startDate, Double rating, String teacherBackground, List<User> publicUserList, List<ForumTopic> forumTopicList, List<GradeItem> gradeItemList, User assignedTeacher, List<Feedback> feedbackList, List<Outlines> outlineList) {
        this.coursepackId = coursepackId;
        this.code = code;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.startDate = startDate;
        this.rating = rating;
        this.teacherBackground = teacherBackground;
        this.publicUserList = publicUserList;
        //this.folderList = folderList;
        this.forumTopicList = forumTopicList;
        //this.quizList = quizList;
        this.gradeItemList = gradeItemList;
        this.assignedTeacher = assignedTeacher;
        this.feedbackList = feedbackList;
        this.outlineList = outlineList;
    }
    
    public Coursepack(Long coursepackId, String code, String title, String description, String category, Double price, Timestamp startDate, Double rating, String teacherBackground, List<User> publicUserList, List<ForumTopic> forumTopicList, List<GradeItem> gradeItemList, User assignedTeacher, List<Feedback> feedbackList, List<Outlines> outlineList, List<File> multimediaList) {
        this.coursepackId = coursepackId;
        this.code = code;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.startDate = startDate;
        this.rating = rating;
        this.teacherBackground = teacherBackground;
        this.publicUserList = publicUserList;
        //this.folderList = folderList;
        this.forumTopicList = forumTopicList;
        //this.quizList = quizList;
        this.gradeItemList = gradeItemList;
        this.assignedTeacher = assignedTeacher;
        this.feedbackList = feedbackList;
        this.outlineList = outlineList;
        this.multimediaList = multimediaList;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (coursepackId != null ? coursepackId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the coursepackId fields are not set
        if (!(object instanceof Coursepack)) {
            return false;
        }
        Coursepack other = (Coursepack) object;
        if ((this.coursepackId == null && other.coursepackId != null) || (this.coursepackId != null && !this.coursepackId.equals(other.coursepackId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Coursepack[ coursepackId=" + coursepackId + " ]";
    }

    public Long getCoursepackId() {
        return coursepackId;
    }

    public void setCoursepackId(Long coursepackId) {
        this.coursepackId = coursepackId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getTeacherBackground() {
        return teacherBackground;
    }

    public void setTeacherBackground(String teacherBackground) {
        this.teacherBackground = teacherBackground;
    }

    public List<User> getPublicUserList() {
        return publicUserList;
    }

    public void setPublicUserList(List<User> publicUserList) {
        this.publicUserList = publicUserList;
    }

    /*public List<Folder> getFolderList() {
        return folderList;
    }

    public void setFolderList(List<Folder> folderList) {
        this.folderList = folderList;
    }*/

    /*public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }*/

    public List<GradeItem> getGradeItemList() {
        return gradeItemList;
    }

    public void setGradeItemList(List<GradeItem> gradeItemList) {
        this.gradeItemList = gradeItemList;
    }

    public User getAssignedTeacher() {
        return assignedTeacher;
    }

    public void setAssignedTeacher(User assignedTeacher) {
        this.assignedTeacher = assignedTeacher;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public List<Outlines> getOutlineList() {
        return outlineList;
    }

    public void setOutlineList(List<Outlines> outlineList) {
        this.outlineList = outlineList;
    }

       public List<ForumTopic> getForumTopicList() {
        return forumTopicList;
    }

    public void setForumTopicList(List<ForumTopic> forumTopicList) {
        this.forumTopicList = forumTopicList;
    }  

    public List<File> getMultimediaList() {
        return multimediaList;
    }

    public void setMultimediaList(List<File> multimediaList) {
        this.multimediaList = multimediaList;
    }
    
}