/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vixson
 */
@Entity
@Table(name = "FORUMPOST")
@XmlRootElement
public class ForumPost implements Serializable {

    public ForumPost(String title, String message, LocalDateTime createTs, LocalDateTime updateTs, Boolean threadStarter, User owner, ForumTopic topic, String type) {
        this.title = title;
        this.message = message;
        this.createTs = createTs;
        this.updateTs = updateTs;
        this.threadStarter = threadStarter;
        this.owner = owner;
        this.topic = topic;
        this.replies = new ArrayList<>();
        this.comments = new ArrayList<>();
        this.type = type;
    }

    public ForumPost(Long forumPostId, String title, String message, LocalDateTime createTs, LocalDateTime updateTs, Boolean threadStarter, User owner, ForumTopic topic, List<ForumPost> replies, List<ForumPost> comments, String type) {
        this.forumPostId = forumPostId;
        this.title = title;
        this.message = message;
        //this.numberOfReply = numberOfReply;
        this.createTs = createTs;
        this.updateTs = updateTs;
        this.threadStarter = threadStarter;
        this.owner = owner;
        this.topic = topic;
        this.replies = replies;
        this.comments = comments;
        this.type = type;

    }

    public ForumPost() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long forumPostId;
    @Column
    private String title;
    @Lob
    @Column
    private String message;
    @Column
    private LocalDateTime createTs;
    @Column
    private LocalDateTime updateTs;
    @Column
    private Boolean threadStarter;
    /**
     * @Column private attachment attachment;
     */
    @Column
    private String type;
    @ManyToOne
    private User owner;
    @ManyToOne
    private ForumTopic topic;
    @OneToMany(mappedBy="parentOfReply")
    private List<ForumPost> replies = new ArrayList<>();
    @OneToMany(mappedBy="parentOfComments")
    private List<ForumPost> comments = new ArrayList<>();
    @ManyToOne
    private ForumPost parentOfReply;
    @ManyToOne
    private ForumPost parentOfComments;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (forumPostId != null ? forumPostId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the forumPostId fields are not set
        if (!(object instanceof ForumPost)) {
            return false;
        }
        ForumPost other = (ForumPost) object;
        if ((this.forumPostId == null && other.forumPostId != null) || (this.forumPostId != null && !this.forumPostId.equals(other.forumPostId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.forumPost[ Id=" + forumPostId + " ]";
    }

//    public static long getSerialVersionUID() {
//        return serialVersionUID;
//    }
//
//    public static void setSerialVersionUID(long aSerialVersionUID) {
//        serialVersionUID = aSerialVersionUID;
//    }
    public Long getForumPostId() {
        return forumPostId;
    }

    public void setForumPostId(Long forumPostId) {
        this.forumPostId = forumPostId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

//    public Integer getNumberOfReply() {
//        return numberOfReply;
//    }
//
//    public void setNumberOfReply(Integer numberOfReply) {
//        this.numberOfReply = numberOfReply;
//    }
    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public LocalDateTime getCreateTs() {
        return createTs;
    }

    public void setCreateTs(LocalDateTime createTs) {
        this.createTs = createTs;
    }

    public LocalDateTime getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(LocalDateTime updateTs) {
        this.updateTs = updateTs;
    }

    public Boolean getThreadStarter() {
        return threadStarter;
    }

    public void setThreadStarter(Boolean threadStarter) {
        this.threadStarter = threadStarter;
    }

//    public Module getModule() {
//        return module;
//    }
//
//    public void setModule(Module module) {
//        this.module = module;
//    }
    public List<ForumPost> getReplies() {
        return replies;
    }

    public void setReplies(List<ForumPost> replies) {
        this.replies = replies;
    }

    public ForumTopic getTopic() {
        return topic;
    }

    public void setTopic(ForumTopic topic) {
        this.topic = topic;
    }

    public List<ForumPost> getComments() {
        return comments;
    }

    public void setComments(List<ForumPost> comments) {
        this.comments = comments;
    }
        
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public ForumPost getParentOfReply() {
        return parentOfReply;
    }

    public void setParentOfReply(ForumPost parentOfReply) {
        this.parentOfReply = parentOfReply;
    }

    public ForumPost getParentOfComments() {
        return parentOfComments;
    }

    public void setParentOfComments(ForumPost parentOfComments) {
        this.parentOfComments = parentOfComments;
    }

}
