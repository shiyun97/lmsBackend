/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.ForumTopic;
import entities.User;
import java.util.List;

/**
 *
 * @author madeline
 */
public class GetForumTopicRsp {

    private List<ForumTopic> forumTopics;
    private User owner;

    public GetForumTopicRsp() {
    }

    public GetForumTopicRsp(List<ForumTopic> forumTopics) {
        this.forumTopics = forumTopics;
    }

    public GetForumTopicRsp(List<ForumTopic> forumTopics, User owner) {
        this.forumTopics = forumTopics;
        this.owner = owner;
    }
    
    public List<ForumTopic> getForumTopics() {
        return forumTopics;
    }

    public void setForumTopics(List<ForumTopic> forumTopics) {
        this.forumTopics = forumTopics;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

}
