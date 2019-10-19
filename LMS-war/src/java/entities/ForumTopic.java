/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

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

/**
 *
 * @author madeline
 */
@Entity
@Table(name = "FORUMTOPIC")
public class ForumTopic {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long forumTopicId;
    @Column
    private String title;
    @Column
    private String description;
    @OneToMany
    private List<ForumPost> threads;
    @ManyToOne
    private Module module;
    @ManyToOne 
    private Coursepack coursepack;

    public ForumTopic(String title, String description, List<ForumPost> threads, Module module, Coursepack coursepack ) {
        this.title = title;
        this.description = description;
        this.threads = threads;
        this.module = module;
        this.coursepack = coursepack;
        
    }

    public ForumTopic(Long forumTopicId, String title, String description, List<ForumPost> threads, Module module, Coursepack coursepack) {
        this.forumTopicId = forumTopicId;
        this.title = title;
        this.description = description;
        this.threads = threads;
        this.module = module;
        this.coursepack = coursepack;
    }

    public ForumTopic() {

    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (forumTopicId != null ? forumTopicId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof ForumTopic)) {
            return false;
        }
        ForumTopic other = (ForumTopic) object;
        if ((this.forumTopicId == null && other.forumTopicId != null) || (this.forumTopicId != null && !this.forumTopicId.equals(other.forumTopicId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.forumTopic[ Id=" + forumTopicId + " ]";
    }

    public Long getForumTopicId() {
        return forumTopicId;
    }

    public void setForumTopicId(Long forumTopicId) {
        this.forumTopicId = forumTopicId;
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

    public List<ForumPost> getThreads() {
        return threads;
    }

    public void setThreads(List<ForumPost> threads) {
        this.threads = threads;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
       public Coursepack getCoursepack() {
        return coursepack;
    }

    public void setCoursepack(Coursepack coursepack) {
        this.coursepack = coursepack;
    }
}
