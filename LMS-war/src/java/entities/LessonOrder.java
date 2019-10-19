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
public class LessonOrder implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long lessonOrderId;
    private int order;
    private String name;
    private Boolean type;
    

    @OneToOne
    private File file;
    @OneToOne
    private Quiz quiz;
    @ManyToOne
    private Outlines outlines; 

    public LessonOrder(){
        
    }
    
    
    public LessonOrder(Long lessonOrderId, int order, String name, Boolean type, File file, Quiz quiz, Outlines outlines) {
        this.lessonOrderId = lessonOrderId;
        this.order = order;
        this.name = name;
        this.type = type; 
        this.file = file;
        this.quiz = quiz;
        this.outlines = outlines;
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

    public int getOrder() {
        return order;
    }

    public void setOrder(int order) {
        this.order = order;
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
    
    
    
    
    
    
}
