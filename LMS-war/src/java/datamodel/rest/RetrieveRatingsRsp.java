/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Rating;
import java.util.List;

/**
 *
 * @author Asus
 */
public class RetrieveRatingsRsp {
    List<Rating> ratings;

    public List<Rating> getRatings() {
        return ratings;
    }

    public void setRatings(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public RetrieveRatingsRsp(List<Rating> ratings) {
        this.ratings = ratings;
    }

    public RetrieveRatingsRsp() {
    }
    
    
}
