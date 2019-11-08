/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author yaosh
 */
@Entity
public class Category implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long categoryId;
    @Column
    private String name;
    @OneToMany(mappedBy = "category")
    private List<Coursepack> coursepackList;

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (categoryId != null ? categoryId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Category)) {
            return false;
        }
        Category other = (Category) object;
        if ((this.categoryId == null && other.categoryId != null) || (this.categoryId != null && !this.categoryId.equals(other.categoryId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Category[ id=" + categoryId + " ]";
    }

    public Category() {
    }

    public Category(Long categoryId, String name, List<Coursepack> coursepackList) {
        this.categoryId = categoryId;
        this.name = name;
        this.coursepackList = coursepackList;
    }

    public Category(Long categoryId, String name) {
        this.categoryId = categoryId;
        this.name = name;
    }
    

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Coursepack> getCoursepackList() {
        return coursepackList;
    }

    public void setCoursepackList(List<Coursepack> coursepackList) {
        this.coursepackList = coursepackList;
    }
    
}
