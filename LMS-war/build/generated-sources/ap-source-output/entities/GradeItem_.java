package entities;

import entities.GradeEntry;
import entities.Module;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-23T17:21:54")
@StaticMetamodel(GradeItem.class)
public class GradeItem_ { 

    public static volatile SingularAttribute<GradeItem, Long> gradeItemId;
    public static volatile SingularAttribute<GradeItem, Double> median;
    public static volatile SingularAttribute<GradeItem, Double> mean;
    public static volatile SingularAttribute<GradeItem, Module> module;
    public static volatile SingularAttribute<GradeItem, Double> twentyFifth;
    public static volatile SingularAttribute<GradeItem, Timestamp> createTs;
    public static volatile ListAttribute<GradeItem, GradeEntry> gradeEntries;
    public static volatile SingularAttribute<GradeItem, Double> title;
    public static volatile SingularAttribute<GradeItem, Timestamp> updateTs;
    public static volatile SingularAttribute<GradeItem, Double> seventyFifth;
    public static volatile SingularAttribute<GradeItem, Boolean> released;

}