/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import util.AccessRightEnum;
import util.GenderEnum;

/**
 *
 * @author Vixson
 */
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long userId;
    @Column
    private String firstName;
    @Column
    private String lastName;
    @Column
    private String email;
    @Column
    private String username;
    @Column
    private String password;
    @Column
    private GenderEnum gender;
    @Column
    private AccessRightEnum accessRight;
    @OneToMany(mappedBy = "booker")
    private List<ConsultationTimeslot> consultationTimeslotList;
    @OneToMany(mappedBy = "quizTaker")
    private List<QuizAttempt> quizAttemptList;
    @OneToMany(mappedBy = "surveyTaker")
    private List<SurveyAttempt> surveyAttemptList;
    @ManyToMany(mappedBy = "members")
    private List<ClassGroup> classGroupList;
    @OneToMany(mappedBy = "assignedTeacher")
    private List<Module> teacherModuleList;
    @ManyToMany(mappedBy = "studentList")
    private List<Module> studentModuleList;
    @ManyToMany(mappedBy = "publicUserList")
    private List<Module> publicUserModuleList;
    @ManyToMany(mappedBy = "studentList")
    private List<Tutorial> tutorials;
    @OneToMany(mappedBy = "student")
    private List<Attendance> attendanceList;
    
    public User(String firstName, String lastName, String email, String username, String password, GenderEnum gender, AccessRightEnum accessRight, List<ConsultationTimeslot> consultationTimeslotList, List<QuizAttempt> quizAttemptList, List<SurveyAttempt> surveyAttemptList, List<ClassGroup> classGroupList, List<Module> teacherModuleList, List<Module> studentModuleList, List<Module> publicUserModuleList) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.accessRight = accessRight;
        this.consultationTimeslotList = consultationTimeslotList;
        this.quizAttemptList = quizAttemptList;
        this.surveyAttemptList = surveyAttemptList;
        this.classGroupList = classGroupList;
        this.teacherModuleList = teacherModuleList;
        this.studentModuleList = studentModuleList;
        this.publicUserModuleList = publicUserModuleList;
    }

    public User(List<Tutorial> tutorials, Long userId, String firstName, String lastName, String email, String username, String password, GenderEnum gender, AccessRightEnum accessRight, List<ConsultationTimeslot> consultationTimeslotList, List<QuizAttempt> quizAttemptList, List<SurveyAttempt> surveyAttemptList, List<ClassGroup> classGroupList, List<Module> teacherModuleList, List<Module> studentModuleList, List<Module> publicUserModuleList) {
        this.tutorials = tutorials;
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.accessRight = accessRight;
        this.consultationTimeslotList = consultationTimeslotList;
        this.quizAttemptList = quizAttemptList;
        this.surveyAttemptList = surveyAttemptList;
        this.classGroupList = classGroupList;
        this.teacherModuleList = teacherModuleList;
        this.studentModuleList = studentModuleList;
        this.publicUserModuleList = publicUserModuleList;
    }

    public User(Long userId, String firstName, String lastName, String email, String username, String password, GenderEnum gender, AccessRightEnum accessRight, List<ConsultationTimeslot> consultationTimeslotList, List<QuizAttempt> quizAttemptList, List<SurveyAttempt> surveyAttemptList, List<ClassGroup> classGroupList, List<Module> teacherModuleList, List<Module> studentModuleList, List<Module> publicUserModuleList, List<Tutorial> tutorials, List<Attendance> attendanceList) {
        this.userId = userId;
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.username = username;
        this.password = password;
        this.gender = gender;
        this.accessRight = accessRight;
        this.consultationTimeslotList = consultationTimeslotList;
        this.quizAttemptList = quizAttemptList;
        this.surveyAttemptList = surveyAttemptList;
        this.classGroupList = classGroupList;
        this.teacherModuleList = teacherModuleList;
        this.studentModuleList = studentModuleList;
        this.publicUserModuleList = publicUserModuleList;
        this.tutorials = tutorials;
        this.attendanceList = attendanceList;
    }
    
    public User() {
    }
    @OneToMany(mappedBy = "assignedTeacher")
    private List<Coursepack> teacherCoursepackList;
    @ManyToMany(mappedBy = "publicUserList")
    private List<Coursepack> publicUserCoursepackList;

    public Long getId() {
        return userId;
    }

    public void setId(Long userId) {
        this.userId = userId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (userId != null ? userId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the userId fields are not set
        if (!(object instanceof User)) {
            return false;
        }
        User other = (User) object;
        if ((this.userId == null && other.userId != null) || (this.userId != null && !this.userId.equals(other.userId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.User[ Id=" + userId + " ]";
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public GenderEnum getGender() {
        return gender;
    }

    public void setGender(GenderEnum gender) {
        this.gender = gender;
    }

    public AccessRightEnum getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRightEnum accessRight) {
        this.accessRight = accessRight;
    }

    public List<ConsultationTimeslot> getConsultationTimeslotList() {
        return consultationTimeslotList;
    }

    public void setConsultationTimeslotList(List<ConsultationTimeslot> consultationTimeslotList) {
        this.consultationTimeslotList = consultationTimeslotList;
    }

    public List<QuizAttempt> getQuizAttemptList() {
        return quizAttemptList;
    }

    public void setQuizAttemptList(List<QuizAttempt> quizAttemptList) {
        this.quizAttemptList = quizAttemptList;
    }

    public List<SurveyAttempt> getSurveyAttemptList() {
        return surveyAttemptList;
    }

    public void setSurveyAttemptList(List<SurveyAttempt> surveyAttemptList) {
        this.surveyAttemptList = surveyAttemptList;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<ClassGroup> getClassGroupList() {
        return classGroupList;
    }

    public void setClassGroupList(List<ClassGroup> classGroupList) {
        this.classGroupList = classGroupList;
    }

    public List<Module> getTeacherModuleList() {
        return teacherModuleList;
    }

    public void setTeacherModuleList(List<Module> teacherModuleList) {
        this.teacherModuleList = teacherModuleList;
    }

    public List<Module> getStudentModuleList() {
        return studentModuleList;
    }

    public void setStudentModuleList(List<Module> studentModuleList) {
        this.studentModuleList = studentModuleList;
    }

    public List<Module> getPublicUserModuleList() {
        return publicUserModuleList;
    }

    public void setPublicUserModuleList(List<Module> publicUserModuleList) {
        this.publicUserModuleList = publicUserModuleList;
    }

    public List<Tutorial> getTutorials() {
        return tutorials;
    }

    public void setTutorials(List<Tutorial> tutorials) {
        this.tutorials = tutorials;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }
    public List<Coursepack> getTeacherCoursepackList() {
        return teacherCoursepackList;
    }

    public void setTeacherCoursepackList(List<Coursepack> teacherCoursepackList) {
        this.teacherCoursepackList = teacherCoursepackList;
    }

    public List<Coursepack> getPublicUserCoursepackList() {
        return publicUserCoursepackList;
    }

    public void setPublicUserCoursepackList(List<Coursepack> publicUserCoursepackList) {
        this.publicUserCoursepackList = publicUserCoursepackList;
    }
    
    
}