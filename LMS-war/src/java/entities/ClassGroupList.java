/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Vixson
 */
@Entity
public class ClassGroupList implements Serializable {

    /*public ClassGroupList(String name, Timestamp startTs, Timestamp closeTs, List<ClassGroup> classGroup, Module module) {
        this.name = name;
        this.startTs = startTs;
        this.closeTs = closeTs;
        this.classGroup = classGroup;
        this.module = module;
    }*/

    public ClassGroupList() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long classGroupListId;
    @Column
    private String name;
    @Column 
    private Timestamp startTs;
    @Column
    private Timestamp closeTs;
    //@OneToMany(mappedBy = "classGroupList")
    //private List<ClassGroup> classGroup;
    @ManyToOne
    private Module module;
    @Column
    private Integer maxMember;
    @ManyToMany
    private List<User> members;

    public ClassGroupList(String name, Timestamp startTs, Timestamp closeTs, Module module, Integer maxMember, List<User> members) {
        this.name = name;
        this.startTs = startTs;
        this.closeTs = closeTs;
        this.module = module;
        this.maxMember = maxMember;
        this.members = members;
    }
    
    
    
    public Long getClassGroupListId() {
        return classGroupListId;
    }

    public void setClassGroupListId(Long classGroupListId) {
        this.classGroupListId = classGroupListId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classGroupListId != null ? classGroupListId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the classGroupListId fields are not set
        if (!(object instanceof ClassGroupList)) {
            return false;
        }
        ClassGroupList other = (ClassGroupList) object;
        if ((this.classGroupListId == null && other.classGroupListId != null) || (this.classGroupListId != null && !this.classGroupListId.equals(other.classGroupListId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ClassGroupList[ Id=" + classGroupListId + " ]";
    }

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
    
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }    

    public Integer getMaxMember() {
        return maxMember;
    }

    public void setMaxMember(Integer maxMember) {
        this.maxMember = maxMember;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
    
}
