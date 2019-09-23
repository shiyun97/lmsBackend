package entities;

import entities.Module;
import entities.Question;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-23T17:21:54")
@StaticMetamodel(Survey.class)
public class Survey_ { 

    public static volatile SingularAttribute<Survey, Long> surveyId;
    public static volatile SingularAttribute<Survey, Timestamp> endDate;
    public static volatile SingularAttribute<Survey, Module> module;
    public static volatile SingularAttribute<Survey, String> description;
    public static volatile SingularAttribute<Survey, Timestamp> createTs;
    public static volatile SingularAttribute<Survey, String> title;
    public static volatile SingularAttribute<Survey, Timestamp> updateTs;
    public static volatile ListAttribute<Survey, Question> questionList;
    public static volatile SingularAttribute<Survey, Timestamp> startDate;

}