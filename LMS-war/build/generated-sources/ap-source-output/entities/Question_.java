package entities;

import entities.Quiz;
import entities.Survey;
import java.util.HashMap;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import util.QuestionTypeEnum;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-23T17:21:54")
@StaticMetamodel(Question.class)
public class Question_ { 

    public static volatile SingularAttribute<Question, Quiz> quiz;
    public static volatile SingularAttribute<Question, Integer> number;
    public static volatile SingularAttribute<Question, String> rightAnswer;
    public static volatile SingularAttribute<Question, Long> questionId;
    public static volatile SingularAttribute<Question, HashMap> options;
    public static volatile SingularAttribute<Question, String> description;
    public static volatile SingularAttribute<Question, Survey> survey;
    public static volatile SingularAttribute<Question, Double> marks;
    public static volatile SingularAttribute<Question, QuestionTypeEnum> type;
    public static volatile SingularAttribute<Question, Boolean> mandatory;
    public static volatile SingularAttribute<Question, Integer> maxLength;

}