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
import javax.persistence.OneToMany;

/**
 *
 * @author Vixson
 */
@Entity
public class ClassGroupList implements Serializable {

    public ClassGroupList(String name, Timestamp startTs, Timestamp closeTs, List<ClassGroup> classGroup) {
        this.name = name;
        this.startTs = startTs;
        this.closeTs = closeTs;
        this.classGroup = classGroup;
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
    @OneToMany(mappedBy = "classGroupList")
    private List<ClassGroup> classGroup;
    
    
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

    public List<ClassGroup> getClassGroup() {
        return classGroup;
    }

    public void setClassGroup(List<ClassGroup> classGroup) {
        this.classGroup = classGroup;
    }
    
}
