/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.validation.constraints.NotNull;
import util.BiddingRoundEnum;

/**
 *
 * @author Asus
 */
@Entity
public class BiddingRound implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long biddingRoundId;
    @Column
    @NotNull
    private BiddingRoundEnum roundType;
    @Column
    private Date startTime;
    @Column
    private Date endTime;

    public Long getBiddingRoundId() {
        return biddingRoundId;
    }

    public void setBiddingRoundId(Long biddingRoundId) {
        this.biddingRoundId = biddingRoundId;
    }

    public BiddingRoundEnum getRoundType() {
        return roundType;
    }

    public void setRoundType(BiddingRoundEnum roundType) {
        this.roundType = roundType;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (biddingRoundId != null ? biddingRoundId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the biddingRoundId fields are not set
        if (!(object instanceof BiddingRound)) {
            return false;
        }
        BiddingRound other = (BiddingRound) object;
        if ((this.biddingRoundId == null && other.biddingRoundId != null) || (this.biddingRoundId != null && !this.biddingRoundId.equals(other.biddingRoundId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.BiddingRound[ id=" + biddingRoundId + " ]";
    }
    
}
