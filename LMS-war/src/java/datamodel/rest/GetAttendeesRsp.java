/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.User;
import java.util.List;

/**
 *
 * @author Vixson
 */
public class GetAttendeesRsp {
    private List<User> attendees;

    public GetAttendeesRsp() {
    }

    public GetAttendeesRsp(List<User> attendees) {
        this.attendees = attendees;
    }

    public List<User> getAttendees() {
        return attendees;
    }

    public void setAttendees(List<User> attendees) {
        this.attendees = attendees;
    }
    
}
