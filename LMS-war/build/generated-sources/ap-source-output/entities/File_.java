package entities;

import entities.Folder;
import entities.Module;
import entities.User;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(File.class)
public class File_ { 

    public static volatile SingularAttribute<File, Timestamp> createdDt;
    public static volatile SingularAttribute<File, Folder> folder;
    public static volatile SingularAttribute<File, Boolean> isDelete;
    public static volatile SingularAttribute<File, User> uploader;
    public static volatile SingularAttribute<File, Module> module;
    public static volatile SingularAttribute<File, String> name;
    public static volatile SingularAttribute<File, String> location;
    public static volatile SingularAttribute<File, String> type;
    public static volatile SingularAttribute<File, Long> fileId;

}