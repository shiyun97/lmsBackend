package entities;

import entities.Module;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(Consultation.class)
public class Consultation_ { 

    public static volatile SingularAttribute<Consultation, Module> module;
    public static volatile SingularAttribute<Consultation, String> description;
    public static volatile SingularAttribute<Consultation, Long> consultationId;
    public static volatile SingularAttribute<Consultation, String> title;

}