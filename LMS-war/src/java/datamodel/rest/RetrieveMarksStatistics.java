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
public class RetrieveMarksStatistics {
    List<MarksStatistic> items;

    public List<MarksStatistic> getItems() {
        return items;
    }

    public void setItems(List<MarksStatistic> items) {
        this.items = items;
    }

    public RetrieveMarksStatistics(List<MarksStatistic> items) {
        this.items = items;
    }

    public RetrieveMarksStatistics() {
    }
    
}
