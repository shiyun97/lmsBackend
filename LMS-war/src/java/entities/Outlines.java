/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Jasmine
 */
@Entity
public class Outlines implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long outlineId;
    private String name;

    @ManyToOne
    private Coursepack coursepack;
    @OneToMany(mappedBy = "outlines")
    private List<LessonOrder> lessonOrder; 

    public Outlines(){
        
    }
    
    public Outlines(Long outlineId, Coursepack coursepack, List<LessonOrder> lessonOrder, String name) {
        this.outlineId = outlineId;
        this.coursepack = coursepack;
        this.lessonOrder = lessonOrder;
        this.name = name;
    }
    
    
    
    
    public Long getOutlineId() {
        return outlineId;
    }

    public void setOutlineId(Long outlineId) {
        this.outlineId = outlineId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (outlineId != null ? outlineId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the outlineId fields are not set
        if (!(object instanceof Outlines)) {
            return false;
        }
        Outlines other = (Outlines) object;
        if ((this.outlineId == null && other.outlineId != null) || (this.outlineId != null && !this.outlineId.equals(other.outlineId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Outlines[ id=" + outlineId + " ]";
    }

    public Coursepack getCoursepack() {
        return coursepack;
    }

    public void setCoursepack(Coursepack coursepack) {
        this.coursepack = coursepack;
    }

    public List<LessonOrder> getLessonOrder() {
        return lessonOrder;
    }

    public void setLessonOrder(List<LessonOrder> lessonOrder) {
        this.lessonOrder = lessonOrder;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
    
}
