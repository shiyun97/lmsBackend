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
public class RetrieveCoursepackStatistics {
    List<CoursepackStatistic> items;
    private int totalEnrolled;
    private double totalRevenue;

    public List<CoursepackStatistic> getItems() {
        return items;
    }

    public void setItems(List<CoursepackStatistic> items) {
        this.items = items;
    }

    public RetrieveCoursepackStatistics() {
    }

    public RetrieveCoursepackStatistics(List<CoursepackStatistic> items) {
        this.items = items;
    }

    public RetrieveCoursepackStatistics(List<CoursepackStatistic> items, int totalEnrolled, double totalRevenue) {
        this.items = items;
        this.totalEnrolled = totalEnrolled;
        this.totalRevenue = totalRevenue;
    }

    public double getTotalRevenue() {
        return totalRevenue;
    }

    public void setTotalRevenue(double totalRevenue) {
        this.totalRevenue = totalRevenue;
    }

    public int getTotalEnrolled() {
        return totalEnrolled;
    }

    public void setTotalEnrolled(int totalEnrolled) {
        this.totalEnrolled = totalEnrolled;
    }
    
}
