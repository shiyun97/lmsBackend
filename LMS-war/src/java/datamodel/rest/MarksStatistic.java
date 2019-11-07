/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

/**
 *
 * @author Asus
 */
public class MarksStatistic {
    Long gradeItemId;
    Long quizId;
    String title;
    double mean;
    double median;
    double max;
    double min;
    double twentyfifth;
    double seventyfifth;
    double studentMarks;

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getGradeItemId() {
        return gradeItemId;
    }

    public void setGradeItemId(Long gradeItemId) {
        this.gradeItemId = gradeItemId;
    }

    public Long getQuizId() {
        return quizId;
    }

    public void setQuizId(Long quizId) {
        this.quizId = quizId;
    }

    public double getMean() {
        return mean;
    }

    public void setMean(double mean) {
        this.mean = mean;
    }

    public double getMedian() {
        return median;
    }

    public void setMedian(double median) {
        this.median = median;
    }

    public double getMax() {
        return max;
    }

    public void setMax(double max) {
        this.max = max;
    }

    public double getMin() {
        return min;
    }

    public void setMin(double min) {
        this.min = min;
    }

    public double getTwentyfifth() {
        return twentyfifth;
    }

    public void setTwentyfifth(double twentyfifth) {
        this.twentyfifth = twentyfifth;
    }

    public double getSeventyfifth() {
        return seventyfifth;
    }

    public void setSeventyfifth(double seventyfifth) {
        this.seventyfifth = seventyfifth;
    }

    public double getStudentMarks() {
        return studentMarks;
    }

    public void setStudentMarks(double studentMarks) {
        this.studentMarks = studentMarks;
    }
    
    
}
