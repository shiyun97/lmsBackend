/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.File;
import entities.LessonOrder;
import entities.Quiz;

/**
 *
 * @author Jasmine
 */
public class lessonOrderReq {
    
    private LessonOrder lessonOrder;
    private Long lessonOrderId;
    private int number;
    private String name;
    private Boolean type; 
    
    private File file; 
    private Quiz quiz;

    public lessonOrderReq(LessonOrder lessonOrder, Long lessonOrderId, int number, String name, Boolean type, File file, Quiz quiz) {
        this.lessonOrder = lessonOrder;
        this.lessonOrderId = lessonOrderId;
        this.number = number;
        this.name = name;
        this.type = type;
        this.file = file;
        this.quiz = quiz;
    }

    public LessonOrder getLessonOrder() {
        return lessonOrder;
    }

    public void setLessonOrder(LessonOrder lessonOrder) {
        this.lessonOrder = lessonOrder;
    }

    public Long getLessonOrderId() {
        return lessonOrderId;
    }

    public void setLessonOrderId(Long lessonOrderId) {
        this.lessonOrderId = lessonOrderId;
    }

    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getType() {
        return type;
    }

    public void setType(Boolean type) {
        this.type = type;
    }

    public File getFile() {
        return file;
    }

    public void setFile(File file) {
        this.file = file;
    }

    public Quiz getQuiz() {
        return quiz;
    }

    public void setQuiz(Quiz quiz) {
        this.quiz = quiz;
    }
    
    
    
    
    
}
