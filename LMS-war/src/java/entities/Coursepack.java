/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
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
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * 
 */
@Entity
public class Coursepack implements Serializable {
    
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
    private Boolean published;
    @Column
    private Double rating;
    @Column
    private String teacherBackground;
    
    @ManyToMany
    private List<User> publicUserList;
    @OneToMany(mappedBy = "coursepack")
    private List<ForumPost> forumPostList;
    @OneToMany(mappedBy = "coursepack")
    private List<GradeItem> gradeItemList;
    @ManyToOne
    private User assignedTeacher;
    @OneToMany
    private List<Feedback> feedbackList;
    @OneToMany
    private List<Outlines> outlineList;
    @OneToMany
    private List<File> fileList;
    @OneToMany
    private List<Quiz> quizList;
    
    
    public Coursepack(){
        publicUserList = new ArrayList<>();
        forumPostList = new ArrayList<>();
        gradeItemList = new ArrayList<>();
        feedbackList = new ArrayList<>();
        outlineList = new ArrayList<>();
        fileList = new ArrayList<>();
        quizList = new ArrayList<>();
    }
    

    public Coursepack(Long coursepackId, String code, String title, String description, String category, Double price, Boolean published, Double rating, String teacherBackground, List<User> publicUserList, List<ForumPost> forumPostList, List<GradeItem> gradeItemList, User assignedTeacher, List<Feedback> feedbackList, List<Outlines> outlineList, List<File> fileList,List<Quiz> quizList ) {
        this.coursepackId = coursepackId;
        this.code = code;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.rating = rating;
        this.teacherBackground = teacherBackground;
        this.publicUserList = publicUserList;
        this.forumPostList = forumPostList;
        this.gradeItemList = gradeItemList;
        this.assignedTeacher = assignedTeacher;
        this.feedbackList = feedbackList;
        this.outlineList = outlineList;
        this.published = published;
        this.fileList = fileList;
        this.quizList = quizList;
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

    public List<ForumPost> getForumPostList() {
        return forumPostList;
    }

    public void setForumPostList(List<ForumPost> forumPostList) {
        this.forumPostList = forumPostList;
    }

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

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    
    
    
}