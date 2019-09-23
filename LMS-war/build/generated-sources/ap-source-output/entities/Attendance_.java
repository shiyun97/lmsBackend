package entities;

import entities.Module;
import entities.User;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-23T17:21:54")
@StaticMetamodel(Attendance.class)
public class Attendance_ { 

    public static volatile SingularAttribute<Attendance, Integer> total;
    public static volatile ListAttribute<Attendance, User> attendees;
    public static volatile SingularAttribute<Attendance, Module> module;
    public static volatile SingularAttribute<Attendance, Integer> semester;
    public static volatile SingularAttribute<Attendance, Timestamp> createTs;
    public static volatile SingularAttribute<Attendance, Integer> attendedNumber;
    public static volatile SingularAttribute<Attendance, Timestamp> updateTs;
    public static volatile SingularAttribute<Attendance, Long> attendanceId;

}