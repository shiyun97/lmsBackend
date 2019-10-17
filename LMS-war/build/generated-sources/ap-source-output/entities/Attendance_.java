package entities;

import entities.Module;
import entities.Tutorial;
import entities.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(Attendance.class)
public class Attendance_ { 

    public static volatile SingularAttribute<Attendance, Integer> duration;
    public static volatile SingularAttribute<Attendance, Integer> total;
    public static volatile ListAttribute<Attendance, User> attendees;
    public static volatile SingularAttribute<Attendance, Module> module;
    public static volatile SingularAttribute<Attendance, Date> endTs;
    public static volatile SingularAttribute<Attendance, Integer> semester;
    public static volatile SingularAttribute<Attendance, Date> startTs;
    public static volatile SingularAttribute<Attendance, Tutorial> tutorial;
    public static volatile SingularAttribute<Attendance, Integer> attendedNumber;
    public static volatile SingularAttribute<Attendance, Long> attendanceId;

}