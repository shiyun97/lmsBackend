/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Attendance;
import java.util.List;

/**
 *
 * @author Vixson
 */
public class GetAttendanceRsp {
    private List<Attendance> attendanceList;

    public GetAttendanceRsp() {
    }

    public GetAttendanceRsp(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }

    public List<Attendance> getAttendanceList() {
        return attendanceList;
    }

    public void setAttendanceList(List<Attendance> attendanceList) {
        this.attendanceList = attendanceList;
    }
    
}
