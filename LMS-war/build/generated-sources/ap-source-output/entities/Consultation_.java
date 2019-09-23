package entities;

import entities.ConsultationTimeslot;
import entities.Module;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-23T17:21:54")
@StaticMetamodel(Consultation.class)
public class Consultation_ { 

    public static volatile ListAttribute<Consultation, ConsultationTimeslot> consultationTimeslot;
    public static volatile SingularAttribute<Consultation, Module> module;
    public static volatile SingularAttribute<Consultation, String> description;
    public static volatile SingularAttribute<Consultation, Long> consultationId;
    public static volatile SingularAttribute<Consultation, String> title;

}