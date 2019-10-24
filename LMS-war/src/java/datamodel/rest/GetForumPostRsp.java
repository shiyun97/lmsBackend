/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.ForumPost;
import java.util.List;

/**
 *
 * @author madeline
 */
public class GetForumPostRsp {

    private List<ForumPost> posts;

    public List<ForumPost> getPosts() {
        return posts;
    }

    public GetForumPostRsp() {
    }

    public void setPosts(List<ForumPost> posts) {
        this.posts = posts;
    }

    public GetForumPostRsp(List<ForumPost> posts) {
        this.posts = posts;
    }

}
