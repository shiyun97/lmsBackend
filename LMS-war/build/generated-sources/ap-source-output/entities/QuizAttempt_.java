package entities;

import entities.Quiz;
import entities.User;
import java.sql.Timestamp;
import java.util.HashMap;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-23T17:21:54")
@StaticMetamodel(QuizAttempt.class)
public class QuizAttempt_ { 

    public static volatile SingularAttribute<QuizAttempt, Quiz> quiz;
    public static volatile SingularAttribute<QuizAttempt, HashMap> answers;
    public static volatile SingularAttribute<QuizAttempt, Timestamp> createTs;
    public static volatile SingularAttribute<QuizAttempt, Timestamp> submitTs;
    public static volatile SingularAttribute<QuizAttempt, Double> totalMarks;
    public static volatile SingularAttribute<QuizAttempt, Timestamp> updateTs;
    public static volatile SingularAttribute<QuizAttempt, Long> quizAttemptId;
    public static volatile SingularAttribute<QuizAttempt, User> quizTaker;

}