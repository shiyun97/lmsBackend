package entities;

import entities.Annoucement;
import entities.Attendance;
import entities.ClassGroup;
import entities.ConsultationTimeslot;
import entities.Feedback;
import entities.File;
import entities.Folder;
import entities.ForumPost;
import entities.GradeItem;
import entities.LessonPlan;
import entities.Quiz;
import entities.Tutorial;
import entities.User;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(Module.class)
public class Module_ { 

    public static volatile SingularAttribute<Module, Integer> creditUnit;
    public static volatile SingularAttribute<Module, Integer> semesterOffered;
    public static volatile SingularAttribute<Module, Integer> maxEnrollment;
    public static volatile SingularAttribute<Module, String> code;
    public static volatile SingularAttribute<Module, String> description;
    public static volatile SingularAttribute<Module, String> title;
    public static volatile ListAttribute<Module, Folder> folderList;
    public static volatile SingularAttribute<Module, String> faculty;
    public static volatile SingularAttribute<Module, String> yearOffered;
    public static volatile ListAttribute<Module, Attendance> attandanceList;
    public static volatile ListAttribute<Module, File> multimediaList;
    public static volatile SingularAttribute<Module, Boolean> hasExam;
    public static volatile SingularAttribute<Module, User> assignedTeacher;
    public static volatile SingularAttribute<Module, Long> moduleId;
    public static volatile SingularAttribute<Module, String> department;
    public static volatile ListAttribute<Module, Feedback> feedbackList;
    public static volatile ListAttribute<Module, User> publicUserList;
    public static volatile ListAttribute<Module, ForumPost> forumPostList;
    public static volatile SingularAttribute<Module, String> examVenue;
    public static volatile ListAttribute<Module, Annoucement> annoucementList;
    public static volatile ListAttribute<Module, User> studentList;
    public static volatile SingularAttribute<Module, String> lectureDetails;
    public static volatile ListAttribute<Module, ConsultationTimeslot> consultationList;
    public static volatile ListAttribute<Module, Quiz> quizList;
    public static volatile ListAttribute<Module, Tutorial> tutorials;
    public static volatile SingularAttribute<Module, String> grade;
    public static volatile ListAttribute<Module, GradeItem> gradeItemList;
    public static volatile ListAttribute<Module, ClassGroup> classGroupList;
    public static volatile ListAttribute<Module, LessonPlan> lessonPlanList;
    public static volatile SingularAttribute<Module, Timestamp> examTime;

}