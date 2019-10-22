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
    int avg;
    int per1;
    int per2;
    int per3;
    int per4;
    int per5;

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

    public int getAvg() {
        return avg;
    }

    public void setAvg(int avg) {
        this.avg = avg;
    }

    public int getPer1() {
        return per1;
    }

    public void setPer1(int per1) {
        this.per1 = per1;
    }

    public int getPer2() {
        return per2;
    }

    public void setPer2(int per2) {
        this.per2 = per2;
    }

    public int getPer3() {
        return per3;
    }

    public void setPer3(int per3) {
        this.per3 = per3;
    }

    public int getPer4() {
        return per4;
    }

    public void setPer4(int per4) {
        this.per4 = per4;
    }

    public int getPer5() {
        return per5;
    }

    public void setPer5(int per5) {
        this.per5 = per5;
    }
    
    
}
