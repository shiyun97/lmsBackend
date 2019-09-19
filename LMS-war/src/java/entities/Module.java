/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import unusedEntities.Student;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Vixson
 */
@Entity
@Table(name = "MODULE")
public class Module implements Serializable {

    public Module(String title, String description, String feedback, Integer semesterOffered, Integer creditUnit, String grade, Integer maxEnrollment, List<User> studentList, List<User> publicUserList, List<Folder> folderList, List<Annoucement> annoucementList, List<ForumPost> forumPostList, List<Quiz> quizList, List<GradeItem> gradeItemList, List<Attendance> attandanceList, List<Consultation> consultationList, List<LessonPlan> lessonPlanList, User owner, List<ClassGroupList> classGroupList) {
        this.title = title;
        this.description = description;
        this.feedback = feedback;
        this.semesterOffered = semesterOffered;
        this.creditUnit = creditUnit;
        this.grade = grade;
        this.maxEnrollment = maxEnrollment;
        this.studentList = studentList;
        this.publicUserList = publicUserList;
        this.folderList = folderList;
        this.annoucementList = annoucementList;
        this.forumPostList = forumPostList;
        this.quizList = quizList;
        this.gradeItemList = gradeItemList;
        this.attandanceList = attandanceList;
        this.consultationList = consultationList;
        this.lessonPlanList = lessonPlanList;
        this.owner = owner;
	this.classGroupList = classGroupList;
    }

    public Module() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String code;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String feedback;
    @Column
    private Integer semesterOffered;
    @Column
    private Integer creditUnit;
    @Column
    private String grade;
    @Column
    private Integer maxEnrollment;
    @ManyToMany
    private List<User> studentList;
    @ManyToMany
    private List<User> publicUserList;
    @OneToMany(mappedBy = "module")
    private List<Folder> folderList;
    @OneToMany(mappedBy = "module")
    private List<Annoucement> annoucementList;
    @OneToMany(mappedBy = "module")
    private List<ForumPost> forumPostList;
    @OneToMany(mappedBy = "module")
    private List<Quiz> quizList;
    @OneToMany(mappedBy = "module")
    private List<GradeItem> gradeItemList;
    @OneToMany(mappedBy = "module")
    private List<Attendance> attandanceList;
    @OneToMany(mappedBy = "module")
    private List<Consultation> consultationList;
    @OneToMany(mappedBy = "module")
    private List<LessonPlan> lessonPlanList;
    @ManyToOne
    private User owner;
    @OneToMany(mappedBy = "module")
    private List<ClassGroupList> classGroupList;
    @OneToMany(mappedBy = "module")
    private List<Feedback> feedbackList;
    
    /**
    public Module(String title, String description, String feedback, Integer semesterOffered, Integer creditUnit, String grade){
        this.title = title;
        this.description = description;
        this.feedback = feedback;
        this.semesterOffered = semesterOffered;
        this.creditUnit = creditUnit;
	this.grade = grade;
    }
**/
	
    @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the code fields are not set
        if (!(object instanceof Module)) {
            return false;
        }
        Module other = (Module) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.module[ id=" + code + " ]";
    }
    
    public String getModuleId() {
        return code;
    }

    public void setModuleId(String code) {
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

    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Integer getSemesterOffered() {
        return semesterOffered;
    }

    public void setSemesterOffered(Integer semesterOffered) {
        this.semesterOffered = semesterOffered;
    }

    public Integer getCreditUnit() {
        return creditUnit;
    }

    public void setCreditUnit(Integer creditUnit) {
        this.creditUnit = creditUnit;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public Integer getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(Integer maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    public List<User> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<User> studentList) {
        this.studentList = studentList;
    }

    public List<User> getPublicUserList() {
        return publicUserList;
    }

    public void setPublicUserList(List<User> publicUserList) {
        this.publicUserList = publicUserList;
    }

    public List<Folder> getFolderList() {
        return folderList;
    }

    public void setFolderList(List<Folder> folderList) {
        this.folderList = folderList;
    }

    public List<Annoucement> getAnnoucementList() {
        return annoucementList;
    }

    public void setAnnoucementList(List<Annoucement> annoucementList) {
        this.annoucementList = annoucementList;
    }

    public List<ForumPost> getForumPostList() {
        return forumPostList;
    }

    public void setForumPostList(List<ForumPost> forumPostList) {
        this.forumPostList = forumPostList;
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

    public List<Attendance> getAttandanceList() {
        return attandanceList;
    }

    public void setAttandanceList(List<Attendance> attandanceList) {
        this.attandanceList = attandanceList;
    }

    public List<Consultation> getConsultationList() {
        return consultationList;
    }

    public void setConsultationList(List<Consultation> consultationList) {
        this.consultationList = consultationList;
    }

    public List<LessonPlan> getLessonPlanList() {
        return lessonPlanList;
    }

    public void setLessonPlanList(List<LessonPlan> lessonPlanList) {
        this.lessonPlanList = lessonPlanList;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }
	
    public List<ClassGroupList> getClassGroupList() {
        return classGroupList;
    }

    public void setClassGroupList(List<ClassGroupList> classGroupList) {
        this.classGroupList = classGroupList;
    }

    public List<Feedback> getFeedbackList() {
        return feedbackList;
    }

    public void setFeedbackList(List<Feedback> feedbackList) {
        this.feedbackList = feedbackList;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
