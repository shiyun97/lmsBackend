/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Feedback;
import java.util.List;

/**
 *
 * @author Asus
 */
public class RetrieveAllFeedbacksForModuleRsp {
    List<Feedback> feedbacks;

    public RetrieveAllFeedbacksForModuleRsp() {
    }

    public RetrieveAllFeedbacksForModuleRsp(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }

    public List<Feedback> getFeedbacks() {
        return feedbacks;
    }

    public void setFeedbacks(List<Feedback> feedbacks) {
        this.feedbacks = feedbacks;
    }
    
    
}
