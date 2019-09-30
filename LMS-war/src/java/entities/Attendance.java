/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import unusedEntities.Student;
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
public class Attendance implements Serializable {

    public Attendance(Integer total, Integer attendedNumber, Integer semester, Timestamp createTs, Timestamp updateTs, Module module, List<User> attendees) {
        this.total = total;
        this.attendedNumber = attendedNumber;
        this.semester = semester;
        this.createTs = createTs;
        this.updateTs = updateTs;
        this.module = module;
        this.attendees = attendees;
    }

    public Attendance() {
    }

    private static long serialVersionUID = 1L;
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
    private Timestamp createTs;
    @Column
    private Timestamp updateTs;
    @ManyToOne
    private Module module;
    @OneToMany
    private List<User> attendees;
            

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (attendanceId != null ? attendanceId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the attendanceId fields are not set
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
        return "entities.studentAttendance[ Id=" + attendanceId + " ]";
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

    public Timestamp getCreateTs() {
        return createTs;
    }

    public void setCreateTs(Timestamp createTs) {
        this.createTs = createTs;
    }

    public Timestamp getUpdateTs() {
        return updateTs;
    }

    public void setUpdateTs(Timestamp updateTs) {
        this.updateTs = updateTs;
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
    
}
