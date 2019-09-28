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
import javax.persistence.ManyToOne;
import util.AppealStatusEnum;
import util.AppealTypeEnum;

/**
 *
 * @author Asus
 */
@Entity
public class Appeal implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long appealId;
    
    @Column
    private AppealTypeEnum type;
    @Column
    private String reason;
    @Column
    private Date createDate;
    @Column
    private AppealStatusEnum status;
    @Column
    private String resultDetails;
    @ManyToOne
    private Module module;
    @ManyToOne
    private Tutorial oldTutorial;
    @ManyToOne
    private Tutorial newTutorial;
    @ManyToOne
    private User student;
    @ManyToOne
    private User admin;

    public Appeal(Long appealId, AppealTypeEnum type, String reason, Date createDate, AppealStatusEnum status, Module module, Tutorial oldTutorial, Tutorial newTutorial, User student, User admin, String resultDetails) {
        this.appealId = appealId;
        this.type = type;
        this.reason = reason;
        this.createDate = createDate;
        this.status = status;
        this.module = module;
        this.oldTutorial = oldTutorial;
        this.newTutorial = newTutorial;
        this.student = student;
        this.admin = admin;
        this.resultDetails = resultDetails;
    }

    public Appeal() {
    }

    public String getResultDetails() {
        return resultDetails;
    }

    public void setResultDetails(String resultDetails) {
        this.resultDetails = resultDetails;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }

    public User getAdmin() {
        return admin;
    }

    public void setAdmin(User admin) {
        this.admin = admin;
    }

    public AppealTypeEnum getType() {
        return type;
    }

    public void setType(AppealTypeEnum type) {
        this.type = type;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public AppealStatusEnum getStatus() {
        return status;
    }

    public void setStatus(AppealStatusEnum status) {
        this.status = status;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Tutorial getOldTutorial() {
        return oldTutorial;
    }

    public void setOldTutorial(Tutorial oldTutorial) {
        this.oldTutorial = oldTutorial;
    }

    public Tutorial getNewTutorial() {
        return newTutorial;
    }

    public void setNewTutorial(Tutorial newTutorial) {
        this.newTutorial = newTutorial;
    }

    public Long getAppealId() {
        return appealId;
    }

    public void setAppealId(Long appealId) {
        this.appealId = appealId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (appealId != null ? appealId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the appealId fields are not set
        if (!(object instanceof Appeal)) {
            return false;
        }
        Appeal other = (Appeal) object;
        if ((this.appealId == null && other.appealId != null) || (this.appealId != null && !this.appealId.equals(other.appealId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Appeal[ id=" + appealId + " ]";
    }
    
}
