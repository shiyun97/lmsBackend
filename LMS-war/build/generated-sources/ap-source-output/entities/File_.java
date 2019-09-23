package entities;

import entities.Folder;
import javax.annotation.Generated;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-09-23T17:21:54")
@StaticMetamodel(File.class)
public class File_ { 

    public static volatile SingularAttribute<File, Folder> folder;
    public static volatile SingularAttribute<File, String> name;
    public static volatile SingularAttribute<File, String> location;
    public static volatile SingularAttribute<File, String> type;
    public static volatile SingularAttribute<File, Long> fileId;

}