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
    private boolean order;
    
    @ManyToOne
    private Coursepack coursepack;
    @OneToMany (mappedBy = "lessonOrder")
    private List<File> fileList;
    @OneToMany (mappedBy = "lessonOrder")
    private List<Quiz> quizList;
    @OneToOne
    private Outlines outlines; 

    public LessonOrder(){
        
    }
    
    
    public LessonOrder(Long lessonOrderId, boolean order, Coursepack coursepack, List<File> fileList, List<Quiz> quizList, Outlines outlines) {
        this.lessonOrderId = lessonOrderId;
        this.order = order;
        this.coursepack = coursepack;
        this.fileList = fileList;
        this.quizList = quizList;
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

    public boolean isOrder() {
        return order;
    }

    public void setOrder(boolean order) {
        this.order = order;
    }

    public Coursepack getCoursepack() {
        return coursepack;
    }

    public void setCoursepack(Coursepack coursepack) {
        this.coursepack = coursepack;
    }

    public List<File> getFileList() {
        return fileList;
    }

    public void setFileList(List<File> fileList) {
        this.fileList = fileList;
    }

    public List<Quiz> getQuizList() {
        return quizList;
    }

    public void setQuizList(List<Quiz> quizList) {
        this.quizList = quizList;
    }

    public Outlines getOutlines() {
        return outlines;
    }

    public void setOutlines(Outlines outlines) {
        this.outlines = outlines;
    }
    
    
    
    
    
    
}
