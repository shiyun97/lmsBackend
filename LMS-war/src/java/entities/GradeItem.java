/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Vixson
 */
@Entity
public class GradeItem implements Serializable {

    public GradeItem() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long gradeItemId;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private Double maxMarks;
    @Column
    private Double mean;
    @Column
    private Double median;
    @Column
    private Double twentyFifth;
    @Column
    private Double seventyFifth;
    @Column
    private Boolean publish;
    @OneToMany(cascade = CascadeType.REMOVE)
    private List<GradeEntry> gradeEntries;
    @ManyToOne
    private Module module;


    public Long getGradeItemId() {
        return gradeItemId;
    }

    public void setGradeItemId(Long gradeItemId) {
        this.gradeItemId = gradeItemId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (gradeItemId != null ? gradeItemId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the gradeItemId fields are not set
        if (!(object instanceof GradeItem)) {
            return false;
        }
        GradeItem other = (GradeItem) object;
        if ((this.gradeItemId == null && other.gradeItemId != null) || (this.gradeItemId != null && !this.gradeItemId.equals(other.gradeItemId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.GradeItem[ Id=" + gradeItemId + " ]";
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getMean() {
        return mean;
    }

    public void setMean(Double mean) {
        this.mean = mean;
    }

    public Double getMedian() {
        return median;
    }

    public void setMedian(Double median) {
        this.median = median;
    }

    public Double getTwentyFifth() {
        return twentyFifth;
    }

    public void setTwentyFifth(Double twentyFifth) {
        this.twentyFifth = twentyFifth;
    }

    public Double getSeventyFifth() {
        return seventyFifth;
    }

    public void setSeventyFifth(Double seventyFifth) {
        this.seventyFifth = seventyFifth;
    }

    public Boolean getPublish() {
        return publish;
    }

    public void setPublish(Boolean publish) {
        this.publish = publish;
    }

    public List<GradeEntry> getGradeEntries() {
        return gradeEntries;
    }

    public void setGradeEntries(List<GradeEntry> gradeEntries) {
        this.gradeEntries = gradeEntries;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Double getMaxMarks() {
        return maxMarks;
    }

    public void setMaxMarks(Double maxMarks) {
        this.maxMarks = maxMarks;
    }
    
    
}
