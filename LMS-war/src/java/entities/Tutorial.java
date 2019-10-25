/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Asus
 */
@Entity
public class Tutorial implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long tutorialId;
    @Column
    private int maxEnrollment;
    @ManyToOne
    private Venue venue;
    @Column
    private String timing;
    @ManyToMany
    private List<User> studentList;
    @ManyToOne
    private Module module;
    @OneToMany(mappedBy = "tutorial")
    private List<Attendance> attendanceList;

    public Tutorial(Long tutorialId, int maxEnrollment, Venue venue, String timing, List<User> studentList, Module module) {
        this.tutorialId = tutorialId;
        this.maxEnrollment = maxEnrollment;
        this.venue = venue;
        this.timing = timing;
        this.studentList = studentList;
        this.module = module;
    }

    public Tutorial(Long tutorialId, int maxEnrollment, Venue venue, String timing, List<User> studentList, Module module, List<Attendance> attendanceList) {
        this.tutorialId = tutorialId;
        this.maxEnrollment = maxEnrollment;
        this.venue = venue;
        this.timing = timing;
        this.studentList = studentList;
        this.module = module;
        this.attendanceList = attendanceList;
    }

    public Tutorial() {
    }

    public Long getTutorialId() {
        return tutorialId;
    }

    public void setTutorialId(Long tutorialId) {
        this.tutorialId = tutorialId;
    }

    public Venue getVenue() {
        return venue;
    }

    public void setVenue(Venue venue) {
        this.venue = venue;
    }

    public String getTiming() {
        return timing;
    }

    public void setTiming(String timing) {
        this.timing = timing;
    }

    public List<User> getStudentList() {
        return studentList;
    }

    public void setStudentList(List<User> studentList) {
        this.studentList = studentList;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public int getMaxEnrollment() {
        return maxEnrollment;
    }

    public void setMaxEnrollment(int maxEnrollment) {
        this.maxEnrollment = maxEnrollment;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (tutorialId != null ? tutorialId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the tutorialId fields are not set
        if (!(object instanceof Tutorial)) {
            return false;
        }
        Tutorial other = (Tutorial) object;
        if ((this.tutorialId == null && other.tutorialId != null) || (this.tutorialId != null && !this.tutorialId.equals(other.tutorialId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Tutorial[ id=" + tutorialId + " ]";
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }
}
