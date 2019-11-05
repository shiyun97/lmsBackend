/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import java.util.List;

/**
 *
 * @author Asus
 */
public class RetrieveAttendanceStatistics {
    List<AttendanceStatistic> items;

    public List<AttendanceStatistic> getItems() {
        return items;
    }

    public void setItems(List<AttendanceStatistic> items) {
        this.items = items;
    }

    public RetrieveAttendanceStatistics() {
    }

    public RetrieveAttendanceStatistics(List<AttendanceStatistic> items) {
        this.items = items;
    }
    
}
