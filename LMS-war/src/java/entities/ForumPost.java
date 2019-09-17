/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
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

    public ForumPost(String title, String description, Integer numberOfReply, Timestamp createTs, Timestamp updateTs, Boolean threadStarter, String owner, Module module) {
        this.title = title;
        this.description = description;
        this.numberOfReply = numberOfReply;
        this.createTs = createTs;
        this.updateTs = updateTs;
        this.threadStarter = threadStarter;
        this.owner = owner;
        this.module = module;
        this.replies = new ArrayList<>();
    }

    private static long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long forumPostId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Integer numberOfReply;
    @Column
    private Timestamp createTs;
    @Column
    private Timestamp updateTs;
    @Column
    private Boolean threadStarter;
    /**
    @Column
    private attachment attachment;
     */
    @Column
    private String owner;
    @ManyToOne
    private Module module;
    @OneToMany
    private List<ForumPost> replies;
    


    public Long getForumPostId() {
        return forumPostId;
    }

    public void setForumPostId(Long forumPostId) {
        this.forumPostId = forumPostId;
    }

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

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public static void setSerialVersionUID(long aSerialVersionUID) {
        serialVersionUID = aSerialVersionUID;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getNumberOfReply() {
        return numberOfReply;
    }

    public void setNumberOfReply(Integer numberOfReply) {
        this.numberOfReply = numberOfReply;
    }
    
    public String getOwner() {
        return owner;
    }

    public void setOwner(String owner) {
        this.owner = owner;
    }

    public Timestamp getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Timestamp createTs) {
        this.createTs = createTs;
    }

    public Timestamp getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Timestamp updateTs) {
        this.updateTs = updateTs;
    }

    public Boolean getThreadStarter() {
        return threadStarter;
    }

    public void setThreadStarter(Boolean threadStarter) {
        this.threadStarter = threadStarter;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<ForumPost> getReplies() {
        return replies;
    }

    public void setReplies(List<ForumPost> replies) {
        this.replies = replies;
    }
}
