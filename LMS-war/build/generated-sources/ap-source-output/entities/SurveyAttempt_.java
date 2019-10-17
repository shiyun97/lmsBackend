package entities;

import entities.Survey;
import entities.User;
import java.sql.Timestamp;
import java.util.HashMap;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(SurveyAttempt.class)
public class SurveyAttempt_ { 

    public static volatile SingularAttribute<SurveyAttempt, HashMap> answers;
    public static volatile SingularAttribute<SurveyAttempt, Survey> survey;
    public static volatile SingularAttribute<SurveyAttempt, Timestamp> createTs;
    public static volatile SingularAttribute<SurveyAttempt, Timestamp> submitTs;
    public static volatile SingularAttribute<SurveyAttempt, Long> surveyAttemptId;
    public static volatile SingularAttribute<SurveyAttempt, Timestamp> updateTs;
    public static volatile SingularAttribute<SurveyAttempt, User> surveyTaker;

}