package entities;

import entities.Module;
import entities.User;
import java.time.LocalDate;
import java.time.LocalTime;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(ConsultationTimeslot.class)
public class ConsultationTimeslot_ { 

    public static volatile SingularAttribute<ConsultationTimeslot, Module> module;
    public static volatile SingularAttribute<ConsultationTimeslot, LocalTime> endTs;
    public static volatile SingularAttribute<ConsultationTimeslot, LocalTime> startTs;
    public static volatile SingularAttribute<ConsultationTimeslot, User> booker;
    public static volatile SingularAttribute<ConsultationTimeslot, Long> consultationTsId;
    public static volatile SingularAttribute<ConsultationTimeslot, LocalDate> startD;

}