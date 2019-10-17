package entities;

import entities.User;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(Badge.class)
public class Badge_ { 

    public static volatile SingularAttribute<Badge, Long> badgeId;
    public static volatile SingularAttribute<Badge, String> criteria;
    public static volatile SingularAttribute<Badge, String> description;
    public static volatile SingularAttribute<Badge, String> title;
    public static volatile SingularAttribute<Badge, User> user;

}