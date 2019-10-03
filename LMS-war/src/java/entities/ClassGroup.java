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
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;

/**
 *
 * @author Vixson
 */
@Entity
public class ClassGroup implements Serializable {

    public ClassGroup() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long classGroupId;
    @Column
    private String name;
    @Column 
    private Timestamp startTs;
    @Column
    private Timestamp closeTs;
    @ManyToOne
    private Module module;
    @Column
    private Integer maxMember;
    @ManyToMany
    private List<User> members;
    
    /*@Column
    private String name;
    @Column
    private Integer maxMember;
    @ManyToOne
    private ClassGroupList classGroupList;
    @ManyToMany
    private List<User> members;*/

    public ClassGroup(Long classGroupId, String name, Timestamp startTs, Timestamp closeTs, Module module, Integer maxMember, List<User> members) {
        this.classGroupId = classGroupId;
        this.name = name;
        this.startTs = startTs;
        this.closeTs = closeTs;
        this.module = module;
        this.maxMember = maxMember;
        this.members = members;
    }
    
    

    public Long getClassGroupId() {
        return classGroupId;
    }

    public void setClassGroupId(Long classGroupId) {
        this.classGroupId = classGroupId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (classGroupId != null ? classGroupId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the classGroupId fields are not set
        if (!(object instanceof ClassGroup)) {
            return false;
        }
        ClassGroup other = (ClassGroup) object;
        if ((this.classGroupId == null && other.classGroupId != null) || (this.classGroupId != null && !this.classGroupId.equals(other.classGroupId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.ClassGroup[ Id=" + classGroupId + " ]";
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
