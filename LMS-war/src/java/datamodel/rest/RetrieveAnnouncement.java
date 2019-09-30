/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Announcement;
import java.util.List;

/**
 *
 * @author Jasmine
 */
public class RetrieveAnnouncement {
    
    List<Announcement> announcements;
    
    
     public RetrieveAnnouncement(List<Announcement> announcements) {
        this.announcements = announcements;
    }
    
    public RetrieveAnnouncement() {
    }

    public List<Announcement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Announcement> announcements) {
        this.announcements = announcements;
    }
    
    
}
