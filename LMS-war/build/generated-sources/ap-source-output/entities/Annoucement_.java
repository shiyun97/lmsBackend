package entities;

import entities.Module;
import entities.User;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(Annoucement.class)
public class Annoucement_ { 

    public static volatile SingularAttribute<Annoucement, Boolean> systemWide;
    public static volatile SingularAttribute<Annoucement, User> owner;
    public static volatile SingularAttribute<Annoucement, Module> module;
    public static volatile SingularAttribute<Annoucement, Long> annoucementId;
    public static volatile SingularAttribute<Annoucement, String> description;
    public static volatile SingularAttribute<Annoucement, Timestamp> createTs;
    public static volatile SingularAttribute<Annoucement, String> title;
    public static volatile SingularAttribute<Annoucement, Timestamp> updateTs;

}