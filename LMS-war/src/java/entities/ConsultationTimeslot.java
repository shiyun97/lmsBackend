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
 * @author Vixson
 */
@Entity
public class ConsultationTimeslot implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long consultationTsId;
    @Column
    private Timestamp startTs;
    @Column 
    private Integer length;
    @Column
    private Boolean booked;
    @ManyToOne
    private Consultation consultation;
    @ManyToOne
    private User booker;
    
    public ConsultationTimeslot(Timestamp startTs, Integer length){
        this.startTs = startTs;
        this.length = length;
    }

    public Long getconsultationTsId() {
        return consultationTsId;
    }

    public void setconsultationTsId(Long consultationTsId) {
        this.consultationTsId = consultationTsId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (consultationTsId != null ? consultationTsId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the consultationTsId fields are not set
        if (!(object instanceof ConsultationTimeslot)) {
            return false;
        }
        ConsultationTimeslot other = (ConsultationTimeslot) object;
        if ((this.consultationTsId == null && other.consultationTsId != null) || (this.consultationTsId != null && !this.consultationTsId.equals(other.consultationTsId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.consultationTimeslot[ Id=" + consultationTsId + " ]";
    }

    public Timestamp getStartTs() {
        return startTs;
    }

    public void setStartTs(Timestamp startTs) {
        this.startTs = startTs;
    }

    public Integer getLength() {
        return length;
    }

    public void setLength(Integer length) {
        this.length = length;
    }

    public Consultation getConsultation() {
        return consultation;
    }

    public void setConsultation(Consultation consultation) {
        this.consultation = consultation;
    }
    
}
