/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
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
public class Module implements Serializable {

    public Module() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long moduleId;
    @Column
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
    private String yearOffered;
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
    private User assignedTeacher;
    @OneToMany(mappedBy = "module")
    private List<ClassGroup> classGroupList;
    @Column
    private boolean hasExam;
    @Column
    private Timestamp examTime;
    @Column
    private String examVenue;
    @Column
    private String lectureDetails;
    @OneToMany(mappedBy = "module") 
    private List<Tutorial> tutorialList;

    public Module(String code, String title, String description, String feedback, Integer semesterOffered, String yearOffered, Integer creditUnit, String grade, Integer maxEnrollment, List<User> studentList, List<User> publicUserList, List<Folder> folderList, List<Annoucement> annoucementList, List<ForumPost> forumPostList, List<Quiz> quizList, List<GradeItem> gradeItemList, List<Attendance> attandanceList, List<Consultation> consultationList, List<LessonPlan> lessonPlanList, User assignedTeacher, List<ClassGroup> classGroupList, boolean hasExam, Timestamp examTime, String examVenue, String lectureDetails, List<Tutorial> tutorialList) {
        this.code = code;
        this.title = title;
        this.description = description;
        this.feedback = feedback;
        this.semesterOffered = semesterOffered;
        this.yearOffered = yearOffered;
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
        this.assignedTeacher = assignedTeacher;
        this.classGroupList = classGroupList;
        this.hasExam = hasExam;
        this.examTime = examTime;
        this.examVenue = examVenue;
        this.lectureDetails = lectureDetails;
        this.tutorialList = tutorialList;
    }
    
    
    
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
        hash += (moduleId != null ? moduleId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the code fields are not set
        if (!(object instanceof Module)) {
            return false;
        }
        Module other = (Module) object;
        if ((this.moduleId == null && other.moduleId != null) || (this.moduleId != null && !this.moduleId.equals(other.moduleId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.module[ id=" + moduleId + " ]";
    }
    
    public String getModuleId() {
        return getCode();
    }

    public void setModuleId(String code) {
        this.setCode(code);
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
	
    public List<ClassGroup> getClassGroupList() {
        return classGroupList;
    }

    public void setClassGroupList(List<ClassGroup> classGroupList) {
        this.classGroupList = classGroupList;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isHasExam() {
        return hasExam;
    }

    public void setHasExam(boolean hasExam) {
        this.hasExam = hasExam;
    }

    public Timestamp getExamTime() {
        return examTime;
    }

    public void setExamTime(Timestamp examTime) {
        this.examTime = examTime;
    }

    public String getExamVenue() {
        return examVenue;
    }

    public void setExamVenue(String examVenue) {
        this.examVenue = examVenue;
    }

    public User getAssignedTeacher() {
        return assignedTeacher;
    }

    public void setAssignedTeacher(User assignedTeacher) {
        this.assignedTeacher = assignedTeacher;
    }

    public String getYearOffered() {
        return yearOffered;
    }

    public void setYearOffered(String yearOffered) {
        this.yearOffered = yearOffered;
    }

    public String getLectureDetails() {
        return lectureDetails;
    }

    public void setLectureDetails(String lectureDetails) {
        this.lectureDetails = lectureDetails;
    }

    public List<Tutorial> getTutorialList() {
        return tutorialList;
    }

    public void setTutorialList(List<Tutorial> tutorialList) {
        this.tutorialList = tutorialList;
    }

}
