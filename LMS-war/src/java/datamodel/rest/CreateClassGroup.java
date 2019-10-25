package datamodel.rest;

import entities.ClassGroup;
import entities.Module;
import entities.User;
import java.sql.Timestamp;
import java.util.List;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author Vixson
 */
public class CreateClassGroup {
    
    //private ClassGroup classGroup;
    
    //private Long classGroupId;
    private String name;
    private Timestamp startTs;
    private Timestamp closeTs;
    //private Module module;
    private Long moduleId;
    private Integer maxMember;
    //private List<User> members;
    
    //private User user;
    
    public CreateClassGroup() {
    }
    
    public CreateClassGroup(String name, Timestamp startTs, Timestamp closeTs, Long moduleId, Integer maxMember) {
        this.name = name;
        this.startTs = startTs;
        this.closeTs = closeTs;
        this.moduleId = moduleId;
        this.maxMember = maxMember;
    }

    /*public CreateClassGroup(ClassGroup classGroup, Long classGroupId, String name, Timestamp startTs, Timestamp closeTs, Module module, Integer maxMember, List<User> members) {
        this.classGroup = classGroup;
        this.classGroupId = classGroupId;
        this.name = name;
        this.startTs = startTs;
        this.closeTs = closeTs;
        this.module = module;
        this.maxMember = maxMember;
        this.members = members;
    }

    public CreateClassGroup(ClassGroup classGroup, Long classGroupId, String name, Timestamp startTs, Timestamp closeTs, Module module, Integer maxMember, List<User> members, User user) {
        this.classGroup = classGroup;
        this.classGroupId = classGroupId;
        this.name = name;
        this.startTs = startTs;
        this.closeTs = closeTs;
        this.module = module;
        this.maxMember = maxMember;
        this.members = members;
        this.user = user;
    }*/
    
    
    
    /*
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
    }*/

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Timestamp getStartTs() {
        return startTs;
    }

    public void setStartTs(Timestamp startTs) {
        this.startTs = startTs;
    }

    public Timestamp getCloseTs() {
        return closeTs;
    }

    public void setCloseTs(Timestamp closeTs) {
        this.closeTs = closeTs;
    }

    /*
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }*/

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Integer getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(Integer maxMember) {
        this.maxMember = maxMember;
    }
    /*
    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }*/



}
