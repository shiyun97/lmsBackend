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
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;

/**
 *
 * @author Vixson
 */
@Entity
public class Consultation implements Serializable {

    public Consultation(String title, String description, List<ConsultationTimeslot> consultationTimeslot, Module module) {
        this.title = title;
        this.description = description;
        this.consultationTimeslot = consultationTimeslot;
        this.module = module;
    }

    public Consultation() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long consultationId;
    @Column
    private String title;
    @Column
    private String description;
    @OneToMany(mappedBy = "consultation")
    private List<ConsultationTimeslot> consultationTimeslot;
    @ManyToOne
    private Module module;
    

    public Long getConsultationId() {
        return consultationId;
    }

    public void setConsultationId(Long consultationId) {
        this.consultationId = consultationId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consultationId != null ? consultationId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the consultationId fields are not set
        if (!(object instanceof Consultation)) {
            return false;
        }
        Consultation other = (Consultation) object;
        if ((this.consultationId == null && other.consultationId != null) || (this.consultationId != null && !this.consultationId.equals(other.consultationId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.consultation[ Id=" + consultationId + " ]";
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

    public List<ConsultationTimeslot> getConsultationTimeslot() {
        return consultationTimeslot;
    }

    public void setConsultationTimeslot(List<ConsultationTimeslot> consultationTimeslot) {
        this.consultationTimeslot = consultationTimeslot;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
}
