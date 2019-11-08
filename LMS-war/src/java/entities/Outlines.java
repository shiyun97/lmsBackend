/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Jasmine
 */
@Entity
public class Outlines implements Serializable, Comparable<Outlines> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long outlineId;
    @Column
    private String name;
    @Column
    private int number; 

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    @ManyToOne
    private Coursepack coursepack;
    @OneToMany(mappedBy = "outlines", cascade = CascadeType.REMOVE)
    private List<LessonOrder> lessonOrder; 

    public Outlines(){
        lessonOrder = new ArrayList<>();
    }
    
    public Outlines(Long outlineId, Coursepack coursepack, List<LessonOrder> lessonOrder, String name, int number) {
        this();
        this.outlineId = outlineId;
        this.coursepack = coursepack;
        this.lessonOrder = lessonOrder;
        this.name = name;
        this.number = number; 
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
    
    public int compareTo(Outlines o){
        return this.getNumber() - o.getNumber();
    }
    
    
}
