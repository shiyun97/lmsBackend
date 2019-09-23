package entities;

import entities.Module;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-23T17:21:54")
@StaticMetamodel(LessonPlan.class)
public class LessonPlan_ { 

    public static volatile SingularAttribute<LessonPlan, Timestamp> endDate;
    public static volatile SingularAttribute<LessonPlan, Long> lessonPlanid;
    public static volatile SingularAttribute<LessonPlan, Module> module;
    public static volatile SingularAttribute<LessonPlan, String> description;
    public static volatile SingularAttribute<LessonPlan, String> title;
    public static volatile SingularAttribute<LessonPlan, Timestamp> startDate;

}