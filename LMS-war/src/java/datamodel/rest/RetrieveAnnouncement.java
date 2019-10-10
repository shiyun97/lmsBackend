/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Annoucement;
import java.util.List;

/**
 *
 * @author Jasmine
 */
public class RetrieveAnnouncement {
    
    List<Annoucement> announcements;
    
    
     public RetrieveAnnouncement(List<Annoucement> announcements) {
        this.announcements = announcements;
    }
    
    public RetrieveAnnouncement() {
    }

    public List<Annoucement> getAnnouncements() {
        return announcements;
    }

    public void setAnnouncements(List<Annoucement> announcements) {
        this.announcements = announcements;
    }
    
    
}
