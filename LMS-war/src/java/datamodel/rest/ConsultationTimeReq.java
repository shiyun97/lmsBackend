/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

/**
 *
 * @author Vixson
 */
public class ConsultationTimeReq {
    private String startTime;
    private String endTime;
    private String startDate;

    public ConsultationTimeReq() {
    }

    public ConsultationTimeReq(String startTime, String endTime, String startDate) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.startDate = startDate;
    }

    public String getStartTime() {
        return startTime;
    }

    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    public String getEndTime() {
        return endTime;
    }

    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }      
}
