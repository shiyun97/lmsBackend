package entities;

import entities.ClassGroupList;
import entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-23T17:21:54")
@StaticMetamodel(ClassGroup.class)
public class ClassGroup_ { 

    public static volatile SingularAttribute<ClassGroup, Integer> maxMember;
    public static volatile ListAttribute<ClassGroup, User> members;
    public static volatile SingularAttribute<ClassGroup, String> name;
    public static volatile SingularAttribute<ClassGroup, ClassGroupList> classGroupList;
    public static volatile SingularAttribute<ClassGroup, Long> classGroupId;

}