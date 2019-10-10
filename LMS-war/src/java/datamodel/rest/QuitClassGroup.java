/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.ClassGroup;
import entities.Module;
import entities.User;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * @author Vixson
 */
public class QuitClassGroup {
    private ClassGroup classGroup;
    
    private Long classGroupId;

    private User user;
    private Long userId;
    private List<User> members;

    public QuitClassGroup(ClassGroup classGroup, Long classGroupId, User user, Long userId, List<User> members) {
        this.classGroup = classGroup;
        this.classGroupId = classGroupId;
        this.user = user;
        this.userId = userId;
        this.members = members;
    }

    

    public ClassGroup getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(ClassGroup classGroup) {
        this.classGroup = classGroup;
    }

    public Long getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(Long classGroupId) {
        this.classGroupId = classGroupId;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
    
    
}
