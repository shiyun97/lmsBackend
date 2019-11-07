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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Transient;
import util.LessonOrderStatusEnum;

/**
 *
 * @author Jasmine
 */
@Entity
public class LessonOrder implements Serializable, Comparable<LessonOrder> {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lessonOrderId;
    @Column
    private int number;
    @Column
    private String name;
    @Column
    private Boolean type;
    

    @OneToOne
    private File file;
    @OneToOne
    private Quiz quiz;
    @ManyToOne
    private Outlines outlines; 
    @OneToMany
    private List<User> publicUserList;
    @Transient
    private LessonOrderStatusEnum status; // Status of a lesson order; not persisted
    

    public LessonOrder(){
        publicUserList = new ArrayList<>();
    }
    
    
    public LessonOrder(Long lessonOrderId, int number, String name, Boolean type, File file, Quiz quiz, Outlines outlines, List<User> publicUserList) {
        this();
        this.lessonOrderId = lessonOrderId;
        this.number = number;
        this.name = name;
        this.type = type; 
        this.file = file;
        this.quiz = quiz;
        this.outlines = outlines;
        this.publicUserList = publicUserList;
    }
    
    
    

    public Long getLessonOrderId() {
        return lessonOrderId;
    }

    public void setLessonOrderId(Long lessonOrderId) {
        this.lessonOrderId = lessonOrderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (lessonOrderId != null ? lessonOrderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the lessonOrderId fields are not set
        if (!(object instanceof LessonOrder)) {
            return false;
        }
        LessonOrder other = (LessonOrder) object;
        if ((this.lessonOrderId == null && other.lessonOrderId != null) || (this.lessonOrderId != null && !this.lessonOrderId.equals(other.lessonOrderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.LessonOrder[ id=" + lessonOrderId + " ]";
    }


    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }


   

    public Outlines getOutlines() {
        return outlines;
    }

    public void setOutlines(Outlines outlines) {
        this.outlines = outlines;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public List<User> getPublicUserList() {
        return publicUserList;
    }

    public void setPublicUserList(List<User> publicUserList) {
        this.publicUserList = publicUserList;
    }

    public LessonOrderStatusEnum getStatus() {
        return status;
    }

    public void setStatus(LessonOrderStatusEnum status) {
        this.status = status;
    }
    
    public int compareTo(LessonOrder o){
        if(o.getOutlines().compareTo(this.getOutlines()) == 0){
            return this.getNumber() - o.getNumber();
        }
        
        return this.getOutlines().compareTo(o.getOutlines());
    }
    
    
    
    
}
