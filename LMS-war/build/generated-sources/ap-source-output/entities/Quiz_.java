package entities;

import entities.Module;
import entities.Question;
import entities.QuizAttempt;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(Quiz.class)
public class Quiz_ { 

    public static volatile SingularAttribute<Quiz, String> owner;
    public static volatile SingularAttribute<Quiz, Long> quizId;
    public static volatile SingularAttribute<Quiz, Timestamp> endDate;
    public static volatile SingularAttribute<Quiz, Module> module;
    public static volatile SingularAttribute<Quiz, String> description;
    public static volatile SingularAttribute<Quiz, String> title;
    public static volatile ListAttribute<Quiz, QuizAttempt> quizAttemptList;
    public static volatile SingularAttribute<Quiz, Double> maxMarks;
    public static volatile SingularAttribute<Quiz, Timestamp> createTs;
    public static volatile SingularAttribute<Quiz, Timestamp> updateTs;
    public static volatile ListAttribute<Quiz, Question> questionList;
    public static volatile SingularAttribute<Quiz, Timestamp> startDate;
    public static volatile SingularAttribute<Quiz, Integer> maxNoOfAttempt;

}