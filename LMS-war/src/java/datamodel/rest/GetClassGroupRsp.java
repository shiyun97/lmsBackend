/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.ClassGroup;
import java.util.List;

/**
 *
 * @author Vixson
 */
public class GetClassGroupRsp {
    
    private List<ClassGroup> classGroupList;

    public GetClassGroupRsp() {
    }

    public GetClassGroupRsp(List<ClassGroup> classGroupList) {
        this.classGroupList = classGroupList;
    }
    
    public List<ClassGroup> getUserList() {
        return classGroupList;
    }

    public void setUserList(List<ClassGroup> classGroupList) {
        this.classGroupList = classGroupList;
    }
    
}
