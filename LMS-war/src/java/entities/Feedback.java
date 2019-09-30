/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Asus
 */
@Entity
public class Feedback implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long feedbackId;
    
    @Column
    private String feedback;
    @Column
    private Timestamp createTs;
    @ManyToOne
    private Module module;

    public Feedback() {
    }
    
    public Feedback(Long feedbackId, String feedback, Timestamp createTs, Module module) {
        this.feedbackId = feedbackId;
        this.feedback = feedback;
        this.createTs = createTs;
        this.module = module;
    }

    public Feedback(String feedback, Timestamp createTs, Module module) {
        this.feedback = feedback;
        this.createTs = createTs;
        this.module = module;
    }
    
  
    
    public String getFeedback() {
        return feedback;
    }

    public void setFeedback(String feedback) {
        this.feedback = feedback;
    }

    public Timestamp getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Timestamp createTs) {
        this.createTs = createTs;
    }
    
    public Long getFeedbackId() {
        return feedbackId;
    }

    public void setFeedbackId(Long feedbackId) {
        this.feedbackId = feedbackId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (feedbackId != null ? feedbackId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the feedbackId fields are not set
        if (!(object instanceof Feedback)) {
            return false;
        }
        Feedback other = (Feedback) object;
        if ((this.feedbackId == null && other.feedbackId != null) || (this.feedbackId != null && !this.feedbackId.equals(other.feedbackId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Feedback[ id=" + feedbackId + " ]";
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
}