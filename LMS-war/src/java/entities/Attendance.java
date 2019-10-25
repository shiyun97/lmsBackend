/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.Date;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;

/**
 *
 * @author Vixson
 */
@Entity
public class Attendance implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long attendanceId;
    @Column
    private Integer total;
    @Column
    private Integer attendedNumber;
    @Column
    private Integer semester;
    @Column
    private Date startTs;
    @Column
    private Date endTs;
    @Column
    private Integer duration;
    @ManyToOne
    private Module module;
    @OneToMany
    private List<User> attendees;
    @ManyToOne
    private Tutorial tutorial;
    @ManyToOne
    private User student;

    public Attendance(Long attendanceId, Integer total, Integer attendedNumber, Integer semester, Date startTs, Date endTs, Integer duration, Module module, List<User> attendees) {
        this.attendanceId = attendanceId;
        this.total = total;
        this.attendedNumber = attendedNumber;
        this.semester = semester;
        this.startTs = startTs;
        this.endTs = endTs;
        this.duration = duration;
        this.module = module;
        this.attendees = attendees;
    }

    public Attendance(Long attendanceId, Integer total, Integer attendedNumber, Integer semester, Date startTs, Date endTs, Integer duration, Module module, List<User> attendees, Tutorial tutorial) {
        this.attendanceId = attendanceId;
        this.total = total;
        this.attendedNumber = attendedNumber;
        this.semester = semester;
        this.startTs = startTs;
        this.endTs = endTs;
        this.duration = duration;
        this.module = module;
        this.attendees = attendees;
        this.tutorial = tutorial;
    }

    public Attendance(Long attendanceId, Integer total, Integer attendedNumber, Integer semester, Date startTs, Date endTs, Integer duration, Module module, List<User> attendees, Tutorial tutorial, User student) {
        this.attendanceId = attendanceId;
        this.total = total;
        this.attendedNumber = attendedNumber;
        this.semester = semester;
        this.startTs = startTs;
        this.endTs = endTs;
        this.duration = duration;
        this.module = module;
        this.attendees = attendees;
        this.tutorial = tutorial;
        this.student = student;
    }

    public Attendance() {
    }
    
    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long id) {
        this.attendanceId = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attendanceId != null ? attendanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Attendance)) {
            return false;
        }
        Attendance other = (Attendance) object;
        if ((this.attendanceId == null && other.attendanceId != null) || (this.attendanceId != null && !this.attendanceId.equals(other.attendanceId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Attendance[ id=" + attendanceId + " ]";
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getAttendedNumber() {
        return attendedNumber;
    }

    public void setAttendedNumber(Integer attendedNumber) {
        this.attendedNumber = attendedNumber;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Date getStartTs() {
        return startTs;
    }

    public void setStartTs(Date startTs) {
        this.startTs = startTs;
    }

    public Date getEndTs() {
        return endTs;
    }

    public void setEndTs(Date endTs) {
        this.endTs = endTs;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<User> attendees) {
        this.attendees = attendees;
    }

    public Tutorial getTutorial() {
        return tutorial;
    }

    public void setTutorial(Tutorial tutorial) {
        this.tutorial = tutorial;
    }

    public User getStudent() {
        return student;
    }

    public void setStudent(User student) {
        this.student = student;
    }
    
}
