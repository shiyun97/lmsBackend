package entities;

import entities.Module;
import entities.Tutorial;
import entities.User;
import java.util.Date;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import util.AppealStatusEnum;
import util.AppealTypeEnum;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(Appeal.class)
public class Appeal_ { 

    public static volatile SingularAttribute<Appeal, String> reason;
    public static volatile SingularAttribute<Appeal, String> resultDetails;
    public static volatile SingularAttribute<Appeal, Tutorial> newTutorial;
    public static volatile SingularAttribute<Appeal, Tutorial> oldTutorial;
    public static volatile SingularAttribute<Appeal, User> student;
    public static volatile SingularAttribute<Appeal, Module> module;
    public static volatile SingularAttribute<Appeal, User> admin;
    public static volatile SingularAttribute<Appeal, AppealTypeEnum> type;
    public static volatile SingularAttribute<Appeal, Long> appealId;
    public static volatile SingularAttribute<Appeal, Date> createDate;
    public static volatile SingularAttribute<Appeal, AppealStatusEnum> status;

}