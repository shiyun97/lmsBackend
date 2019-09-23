package entities;

import entities.GradeItem;
import entities.User;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-23T17:21:54")
@StaticMetamodel(GradeEntry.class)
public class GradeEntry_ { 

    public static volatile SingularAttribute<GradeEntry, String> notes;
    public static volatile SingularAttribute<GradeEntry, Long> gradeEntryId;
    public static volatile SingularAttribute<GradeEntry, User> student;
    public static volatile SingularAttribute<GradeEntry, Timestamp> createTs;
    public static volatile SingularAttribute<GradeEntry, Double> marks;
    public static volatile SingularAttribute<GradeEntry, GradeItem> gradeItem;
    public static volatile SingularAttribute<GradeEntry, User> grader;
    public static volatile SingularAttribute<GradeEntry, Timestamp> updateTs;

}