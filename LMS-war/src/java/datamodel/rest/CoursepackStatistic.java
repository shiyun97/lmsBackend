/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import java.util.Date;

/**
 *
 * @author Asus
 */
public class CoursepackStatistic {
    Date startDate;
    Date endDate;
    private int usersEnrolled;

    public Date getStartDate() {
        return startDate;
    }

    public void setStartDate(Date startDate) {
        this.startDate = startDate;
    }

    public Date getEndDate() {
        return endDate;
    }

    public void setEndDate(Date endDate) {
        this.endDate = endDate;
    }

    public int getUsersEnrolled() {
        return usersEnrolled;
    }

    public void setUsersEnrolled(int usersEnrolled) {
        this.usersEnrolled = usersEnrolled;
    }
}
