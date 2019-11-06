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
public class RetrieveForumAnalytics {
    List<ForumTopicStatistic> items;

    public List<ForumTopicStatistic> getItems() {
        return items;
    }

    public void setItems(List<ForumTopicStatistic> items) {
        this.items = items;
    }

    public RetrieveForumAnalytics(List<ForumTopicStatistic> items) {
        this.items = items;
    }

    public RetrieveForumAnalytics() {
    }
    
    
}
