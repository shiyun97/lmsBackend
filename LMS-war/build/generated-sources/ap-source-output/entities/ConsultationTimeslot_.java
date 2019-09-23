package entities;

import entities.Consultation;
import entities.User;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-23T17:21:54")
@StaticMetamodel(ConsultationTimeslot.class)
public class ConsultationTimeslot_ { 

    public static volatile SingularAttribute<ConsultationTimeslot, Boolean> booked;
    public static volatile SingularAttribute<ConsultationTimeslot, Integer> length;
    public static volatile SingularAttribute<ConsultationTimeslot, Timestamp> startTs;
    public static volatile SingularAttribute<ConsultationTimeslot, Consultation> consultation;
    public static volatile SingularAttribute<ConsultationTimeslot, User> booker;
    public static volatile SingularAttribute<ConsultationTimeslot, Long> consultationTsId;

}