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
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.Lob;
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
    private Double price;
    @Column
    private Boolean published;
    @Column
    private Double rating;
    @Column
    private String teacherBackground;
    @Lob
    @Column
    private String imageLocation;
    @ManyToOne
    private Category category;

    @ManyToMany
    private List<User> studentList;
    @ManyToMany
    @JoinTable(
            name = "coursepack_publicUser",
            joinColumns = @JoinColumn(name = "coursepackId"),
            inverseJoinColumns = @JoinColumn(name = "publicUserId"))
    private List<User> publicUserList;
    @OneToMany(mappedBy = "coursepack")
    private List<ForumTopic> forumTopicList;
    @OneToMany(mappedBy = "coursepack")
    private List<GradeItem> gradeItemList;
    @ManyToOne
    private User assignedTeacher;
    @OneToMany
    private List<Feedback> feedbackList;
    @OneToMany
    private List<Outlines> outlineList;
    @OneToMany
    private List<Rating> ratingList;
    @OneToMany(mappedBy = "coursepack")
    private List<File> multimediaList;
    private List<File> fileList;
    @OneToMany
    private List<Quiz> quizList;

    public Coursepack(Long coursepackId, String code, String title, String description, Double price, Boolean published, Double rating, String imageLocation, Category category, String teacherBackground, List<User> publicUserList, List<ForumPost> forumPostList, List<GradeItem> gradeItemList, User assignedTeacher, List<Feedback> feedbackList, List<Outlines> outlineList, List<File> fileList, List<Quiz> quizList) {
        this.coursepackId = coursepackId;
        this.code = code;
        this.title = title;
        this.description = description;
        this.price = price;
        this.published = published;
        this.rating = rating;
        this.teacherBackground = teacherBackground;
        this.imageLocation = imageLocation;
        this.category = category;
        this.publicUserList = publicUserList;
        this.gradeItemList = gradeItemList;
        this.assignedTeacher = assignedTeacher;
        this.feedbackList = feedbackList;
        this.outlineList = outlineList;
        this.published = published;
        this.fileList = fileList;
        this.quizList = quizList;
    }

    public Coursepack(Long coursepackId, String code, String title, String description, Double price, Boolean published, Double rating, String teacherBackground, String imageLocation, Category category, List<User> publicUserList, List<ForumTopic> forumTopicList, List<GradeItem> gradeItemList, User assignedTeacher, List<Feedback> feedbackList, List<Outlines> outlineList, List<Rating> ratingList, List<File> multimediaList, List<File> fileList, List<Quiz> quizList) {
        this.coursepackId = coursepackId;
        this.code = code;
        this.title = title;
        this.description = description;
        this.price = price;
        this.published = published;
        this.rating = rating;
        this.teacherBackground = teacherBackground;
        this.imageLocation = imageLocation;
        this.category = category;
        this.publicUserList = publicUserList;
        this.forumTopicList = forumTopicList;
        this.gradeItemList = gradeItemList;
        this.assignedTeacher = assignedTeacher;
        this.feedbackList = feedbackList;
        this.outlineList = outlineList;
        this.ratingList = ratingList;
        this.multimediaList = multimediaList;
        this.fileList = fileList;
        this.quizList = quizList;
    }
    

    public Coursepack(){
        this.publicUserList = new ArrayList<>();
        this.gradeItemList = new ArrayList<>();
        this.feedbackList = new ArrayList<>();
        this.outlineList = new ArrayList<>();
        this.fileList = new ArrayList<>();
        this.quizList = new ArrayList<>();
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

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public List<User> getPublicUserList() {
        return publicUserList;
    }

    public void setPublicUserList(List<User> publicUserList) {
        this.publicUserList = publicUserList;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
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

    public List<Rating> getRatingList() {
        return ratingList;
    }

    public void setRatingList(List<Rating> ratingList) {
        this.ratingList = ratingList;
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

    public List<User> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<User> studentList) {
        this.studentList = studentList;
    }
}
