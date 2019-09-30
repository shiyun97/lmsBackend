/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.User;

/**
 *
 * @author Vixson
 */
public class CheckUserLogin {
    private User user;
    
    private Long userId;
    private String username;
    private String password;
    
    private String email;

    public CheckUserLogin() {
    }

    public CheckUserLogin(User user, Long userId, String username, String password, String email) {
        this.user = user;
        this.userId = userId;
        this.username = username;
        this.password = password;
        this.email = email;
    }

    public CheckUserLogin(User user) {
        this.user = user;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    
    
}
