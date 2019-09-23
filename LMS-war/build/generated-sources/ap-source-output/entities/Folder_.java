package entities;

import entities.File;
import entities.Module;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;
import util.AccessRightEnum;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-23T17:21:54")
@StaticMetamodel(Folder.class)
public class Folder_ { 

    public static volatile SingularAttribute<Folder, Timestamp> submissionCloseTs;
    public static volatile SingularAttribute<Folder, AccessRightEnum> accessRight;
    public static volatile ListAttribute<Folder, File> file;
    public static volatile SingularAttribute<Folder, Double> size;
    public static volatile SingularAttribute<Folder, Timestamp> submissionOpenTs;
    public static volatile SingularAttribute<Folder, Module> module;
    public static volatile SingularAttribute<Folder, String> name;
    public static volatile SingularAttribute<Folder, Boolean> submission;
    public static volatile SingularAttribute<Folder, Long> folderId;

}