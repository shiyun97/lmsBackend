package entities;

import entities.ClassGroup;
import entities.Module;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-23T17:21:54")
@StaticMetamodel(ClassGroupList.class)
public class ClassGroupList_ { 

    public static volatile SingularAttribute<ClassGroupList, Timestamp> closeTs;
    public static volatile SingularAttribute<ClassGroupList, Module> module;
    public static volatile SingularAttribute<ClassGroupList, String> name;
    public static volatile ListAttribute<ClassGroupList, ClassGroup> classGroup;
    public static volatile SingularAttribute<ClassGroupList, Timestamp> startTs;
    public static volatile SingularAttribute<ClassGroupList, Long> classGroupListId;

}