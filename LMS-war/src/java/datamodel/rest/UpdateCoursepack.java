/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;
import entities.Coursepack;
import entities.User;
import java.sql.Timestamp;
import java.util.Date;

/**
 *
 * @author Jasmine
 */
public class UpdateCoursepack {
    private Coursepack coursepack;
    
    private Long coursepackId;
    private String code;
    private String title;
    private String description;
    private String category;
    private Double price;
    private Timestamp startDate;
    private Double rating;
    private String teacherBackground;
    
    private User user;
    private Long userId;
    private String email;
    private String password;


    public UpdateCoursepack(){
        
    }
    
    public UpdateCoursepack(Coursepack coursepack, Long coursepackId, String code, String title, String description, String category, Double price, Timestamp startDate, Double rating, String teacherBackground,User user, Long userId, String email, String password ) {
        this.coursepack = coursepack;
        this.coursepackId = coursepackId;
        this.code = code;
        this.title = title;
        this.description = description;
        this.category = category;
        this.price = price;
        this.startDate = startDate;
        this.rating = rating;
        this.teacherBackground = teacherBackground;
        this.user = user;
        this.userId = userId;
        this.email = email;
        this.password = password;
    }

    public Coursepack getCoursepack() {
        return coursepack;
    }

    public void setCoursepack(Coursepack coursepack) {
        this.coursepack = coursepack;
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

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Double getPrice() {
        return price;
    }

    public void setPrice(Double price) {
        this.price = price;
    }

    public Timestamp getStartDate() {
        return startDate;
    }

    public void setStartDate(Timestamp startDate) {
        this.startDate = startDate;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
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
    
    
    
}
