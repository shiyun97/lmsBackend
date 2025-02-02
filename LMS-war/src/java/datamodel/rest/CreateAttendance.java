/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Attendance;
import entities.Module;
import java.util.Date;

/**
 *
 * @author Vixson
 */
public class CreateAttendance {

    private Attendance attendance;
    private Long attendanceId;
    private Integer total;
    private Integer semester;
    private Date startTs;
    private Date endTs;
    private Integer duration;
    private Module module;

    public CreateAttendance() {
    }

    public CreateAttendance(Attendance attendance, Long attendanceId, Integer total, Integer semester, Date startTs, Date endTs, Integer duration, Module module) {
        this.attendance = attendance;
        this.attendanceId = attendanceId;
        this.total = total;
        this.semester = semester;
        this.startTs = startTs;
        this.endTs = endTs;
        this.duration = duration;
        this.module = module;
    }

    public Attendance getAttendance() {
        return attendance;
    }

    public void setAttendance(Attendance attendance) {
        this.attendance = attendance;
    }

    public Long getAttendanceId() {
        return attendanceId;
    }

    public void setAttendanceId(Long attendanceId) {
        this.attendanceId = attendanceId;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public Integer getSemester() {
        return semester;
    }

    public void setSemester(Integer semester) {
        this.semester = semester;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
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
    
}
