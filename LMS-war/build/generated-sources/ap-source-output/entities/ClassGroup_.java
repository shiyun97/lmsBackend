package entities;

import entities.Module;
import entities.User;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(ClassGroup.class)
public class ClassGroup_ { 

    public static volatile SingularAttribute<ClassGroup, Timestamp> closeTs;
    public static volatile SingularAttribute<ClassGroup, Integer> maxMember;
    public static volatile SingularAttribute<ClassGroup, Module> module;
    public static volatile ListAttribute<ClassGroup, User> members;
    public static volatile SingularAttribute<ClassGroup, String> name;
    public static volatile SingularAttribute<ClassGroup, Timestamp> startTs;
    public static volatile SingularAttribute<ClassGroup, Long> classGroupId;

}