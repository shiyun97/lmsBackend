/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Date;
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

    public ConsultationTimeslot(Time startTs, Time endTs, Date startD, Date endD, Module module, User booker) {
        this.startTs = startTs;
        this.endTs = endTs;
        this.endD = endD;
        this.startD = startD;
        //this.booked = booked;
        this.module = module;
        this.booker = booker;
    }

    public ConsultationTimeslot() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long consultationTsId;
    @Column
    private Time startTs;
    @Column 
    private Time endTs;
    @Column 
    private Date endD;
    @Column 
    private Date startD;
    //@Column
    //private Boolean booked;
    @ManyToOne
    private Module module;
    @ManyToOne
    private User booker;
    

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

    public Time getStartTs() {
        return startTs;
    }

    public void setStartTs(Time startTs) {
        this.startTs = startTs;
    }

    /*public Boolean getBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }*/

    public User getBooker() {
        return booker;
    }

    public void setBooker(User booker) {
        this.booker = booker;
    }
    
    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
    public Time getEndTs() {
        return endTs;
    }

    public void setEndTs(Time endTs) {
        this.endTs = endTs;
    }
    
      public Date getEndD() {
        return endD;
    }

    public void setEndD(Date endD) {
        this.endD = endD;
    }

    public Date getStartD() {
        return startD;
    }

    public void setStartD(Date startD) {
        this.startD = startD;
    }
    
}
