package entities;

import entities.Attendance;
import entities.Module;
import entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(Tutorial.class)
public class Tutorial_ { 

    public static volatile SingularAttribute<Tutorial, String> venue;
    public static volatile SingularAttribute<Tutorial, Integer> maxEnrollment;
    public static volatile SingularAttribute<Tutorial, String> timing;
    public static volatile SingularAttribute<Tutorial, Module> module;
    public static volatile ListAttribute<Tutorial, User> studentList;
    public static volatile SingularAttribute<Tutorial, Long> tutorialId;
    public static volatile SingularAttribute<Tutorial, Attendance> attendance;

}