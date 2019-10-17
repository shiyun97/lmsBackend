package entities;

import entities.ForumPost;
import entities.Module;
import java.sql.Timestamp;
import javax.annotation.Generated;
import javax.persistence.metamodel.ListAttribute;
import javax.persistence.metamodel.SingularAttribute;
import javax.persistence.metamodel.StaticMetamodel;

@Generated(value="EclipseLink-2.5.2.v20140319-rNA", date="2019-10-10T21:39:26")
@StaticMetamodel(ForumPost.class)
public class ForumPost_ { 

    public static volatile SingularAttribute<ForumPost, String> owner;
    public static volatile SingularAttribute<ForumPost, Integer> numberOfReply;
    public static volatile ListAttribute<ForumPost, ForumPost> replies;
    public static volatile SingularAttribute<ForumPost, Boolean> threadStarter;
    public static volatile SingularAttribute<ForumPost, Module> module;
    public static volatile SingularAttribute<ForumPost, String> description;
    public static volatile SingularAttribute<ForumPost, Timestamp> createTs;
    public static volatile SingularAttribute<ForumPost, Long> forumPostId;
    public static volatile SingularAttribute<ForumPost, String> title;
    public static volatile SingularAttribute<ForumPost, Timestamp> updateTs;

}