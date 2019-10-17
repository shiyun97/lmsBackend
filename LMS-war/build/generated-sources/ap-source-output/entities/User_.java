package entities;

import entities.ClassGroup;
import entities.ConsultationTimeslot;
import entities.Module;
import entities.QuizAttempt;
import entities.SurveyAttempt;
import entities.Tutorial;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import util.AccessRightEnum;
import util.GenderEnum;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(User.class)
public class User_ { 

    public static volatile SingularAttribute<User, String> lastName;
    public static volatile ListAttribute<User, SurveyAttempt> surveyAttemptList;
    public static volatile SingularAttribute<User, AccessRightEnum> accessRight;
    public static volatile SingularAttribute<User, GenderEnum> gender;
    public static volatile ListAttribute<User, Module> publicUserModuleList;
    public static volatile ListAttribute<User, ConsultationTimeslot> consultationTimeslotList;
    public static volatile ListAttribute<User, Module> teacherModuleList;
    public static volatile ListAttribute<User, Module> studentModuleList;
    public static volatile SingularAttribute<User, Long> userId;
    public static volatile ListAttribute<User, QuizAttempt> quizAttemptList;
    public static volatile SingularAttribute<User, String> firstName;
    public static volatile SingularAttribute<User, String> password;
    public static volatile ListAttribute<User, Tutorial> tutorials;
    public static volatile ListAttribute<User, ClassGroup> classGroupList;
    public static volatile SingularAttribute<User, String> email;
    public static volatile SingularAttribute<User, String> username;

}