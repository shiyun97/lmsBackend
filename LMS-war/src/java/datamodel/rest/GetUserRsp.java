/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.User;
import java.util.List;

/**
 *
 * @author Vixson
 */
public class GetUserRsp {
    
    private List<User> userList;

    public GetUserRsp() {
    }

    public GetUserRsp(List<User> userList) {
        this.userList = userList;
    }
    
    public List<User> getUserList() {
        return userList;
    }

    public void setUserList(List<User> userList) {
        this.userList = userList;
    }
    
}
