/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
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

    public ClassGroup(String name, Integer limit, ClassGroupList classGroupList, User members) {
        this.name = name;
        this.limit = limit;
        this.classGroupList = classGroupList;
        this.members = new ArrayList<>();
    }

    public ClassGroup() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long classGroupId;
    @Column
    private String name;
    @Column
    private Integer limit;
    @ManyToOne
    private ClassGroupList classGroupList;
    @ManyToMany
    private List<User> members;
    

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

    public Integer getLimit() {
        return limit;
    }

    public void setLimit(Integer limit) {
        this.limit = limit;
    }

    public ClassGroupList getClassGroupList() {
        return classGroupList;
    }

    public void setClassGroupList(ClassGroupList classGroupList) {
        this.classGroupList = classGroupList;
    }

    public List<User> getMembers() {
        return members;
    }

    public void setMembers(List<User> members) {
        this.members = members;
    }
}
