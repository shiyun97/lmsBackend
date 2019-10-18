/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.ForumTopic;
import java.util.List;

/**
 *
 * @author madeline
 */
public class GetForumTopicRsp {

    private List<ForumTopic> forumTopics;

    public GetForumTopicRsp() {
    }

    public GetForumTopicRsp(List<ForumTopic> forumTopics) {
        this.forumTopics = forumTopics;
    }

    public List<ForumTopic> getForumTopics() {
        return forumTopics;
    }

    public void setForumTopics(List<ForumTopic> forumTopics) {
        this.forumTopics = forumTopics;
    }

}
