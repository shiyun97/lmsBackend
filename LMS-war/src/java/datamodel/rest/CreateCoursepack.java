/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Coursepack;
import entities.LessonOrder;
import entities.Outlines;
import entities.User;
import java.sql.Timestamp;
import java.util.List;

/**
 *
 * 
 */
public class CreateCoursepack {
    
    private Long coursepackId;
    private String code;
    private String title;
    private String description;
    private Long categoryId;
    private String imageLocation;
    private Double price;
    private Boolean published;
    private String teacherBackground;
    private List<String> outlines;
    private User assignedTeacher;
    
    private User user; 
    private Long userId;
    private String email;
    private String password;

    public CreateCoursepack(){
        
    }

    public CreateCoursepack(Long coursepackId, String code, String title, String description, Double price, Boolean published, String teacherBackground,List<String> outlines, User assignedTeacher, User user, Long userId, String email, String password) {
        this.coursepackId = coursepackId;
        this.code = code;
        this.title = title;
        this.description = description;
        this.price = price;
        this.published = published;
        this.teacherBackground = teacherBackground;
        this.outlines = outlines;
        this.assignedTeacher = assignedTeacher;
        
        this.user = user;
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public CreateCoursepack(Long coursepackId, String code, String title, String description, Long categoryId, String imageLocation, Double price, Boolean published, String teacherBackground, List<String> outlines, User assignedTeacher, User user, Long userId, String email, String password) {
        this.coursepackId = coursepackId;
        this.code = code;
        this.title = title;
        this.description = description;
        this.categoryId = categoryId;
        this.imageLocation = imageLocation;
        this.price = price;
        this.published = published;
        this.teacherBackground = teacherBackground;
        this.outlines = outlines;
        this.assignedTeacher = assignedTeacher;
        this.user = user;
        this.userId = userId;
        this.email = email;
        this.password = password;
    }
    
    
    public Long getCoursepackId() {
        return coursepackId;
    }

    public void setCoursepackId(Long coursepackId) {
        this.coursepackId = coursepackId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Long categoryId) {
        this.categoryId = categoryId;
    }

    public String getImageLocation() {
        return imageLocation;
    }

    public void setImageLocation(String imageLocation) {
        this.imageLocation = imageLocation;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Boolean getPublished() {
        return published;
    }

    public void setPublished(Boolean published) {
        this.published = published;
    }

    

    public String getTeacherBackground() {
        return teacherBackground;
    }

    public void setTeacherBackground(String teacherBackground) {
        this.teacherBackground = teacherBackground;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    /*public Outlines getOutlineList() {
        return outlineList;
    }

    public void setOutlineList(Outlines outlineList) {
        this.outlineList = outlineList;
    }

    public Long getOutlineId() {
        return outlineId;
    }

    public void setOutlineId(Long outlineId) {
        this.outlineId = outlineId;
    }

    public List<LessonOrder> getLessonOrderList() {
        return lessonOrderList;
    }

    public void setLessonOrderList(List<LessonOrder> lessonOrderList) {
        this.lessonOrderList = lessonOrderList;
    }

    public String getOutlineName() {
        return outlineName;
    }

    public void setOutlineName(String outlineName) {
        this.outlineName = outlineName;
    }*/

    public List<String> getOutlines() {
        return outlines;
    }

    public void setOutlines(List<String> outlines) {
        this.outlines = outlines;
    }

    public User getAssignedTeacher() {
        return assignedTeacher;
    }

    public void setAssignedTeacher(User assignedTeacher) {
        this.assignedTeacher = assignedTeacher;
    }
    
}
