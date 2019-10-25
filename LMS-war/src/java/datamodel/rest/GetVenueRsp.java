/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Venue;
import java.util.List;

/**
 *
 * @author Vixson
 */
public class GetVenueRsp {
    private List<Venue> venueList;

    public GetVenueRsp() {
    }

    public GetVenueRsp(List<Venue> venueList) {
        this.venueList = venueList;
    }

    public List<Venue> getVenueList() {
        return venueList;
    }

    public void setVenueList(List<Venue> venueList) {
        this.venueList = venueList;
    }
    
    
}
