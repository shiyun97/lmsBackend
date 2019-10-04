/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.time.LocalDate;
import java.time.LocalTime;
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

    public ConsultationTimeslot(LocalTime startTs, LocalTime endTs, LocalDate startD, Module module, User booker) {
        this.startTs = startTs;
        this.endTs = endTs;
        //this.endD = endD;
        this.startD = startD;
        this.module = module;
        this.booker = booker;
    }

    public ConsultationTimeslot(Long consultationTsId, LocalTime startTs, LocalTime endTs, LocalDate startD, Module module, User booker) {
        this.consultationTsId = consultationTsId;
        this.startTs = startTs;
        this.endTs = endTs;
        this.startD = startD;
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
    private LocalTime startTs;
    @Column 
    private LocalTime endTs;
    //@Column 
    //private LocalDate endD;
    @Column 
    private LocalDate startD;
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

    public LocalTime getStartTs() {
        return startTs;
    }

    public void setStartTs(LocalTime startTs) {
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
    
    public LocalTime getEndTs() {
        return endTs;
    }

    public void setEndTs(LocalTime endTs) {
        this.endTs = endTs;
    }
    
    /*  public LocalDate getEndD() {
        return endD;
    }

    public void setEndD(LocalDate endD) {
        this.endD = endD;
    } */

    public LocalDate getStartD() {
        return startD;
    }

    public void setStartD(LocalDate startD) {
        this.startD = startD;
    }
    
}
