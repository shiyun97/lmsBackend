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
public class RetrieveListBarAnalytics {
    List<RetrieveBarAnalytics> bars;

    public List<RetrieveBarAnalytics> getBars() {
        return bars;
    }

    public void setBars(List<RetrieveBarAnalytics> bars) {
        this.bars = bars;
    }

    public RetrieveListBarAnalytics() {
    }

    public RetrieveListBarAnalytics(List<RetrieveBarAnalytics> bars) {
        this.bars = bars;
    }
}
