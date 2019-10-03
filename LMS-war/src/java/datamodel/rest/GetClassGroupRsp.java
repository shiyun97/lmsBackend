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
    private List<Integer> currentEnrollment;

    public GetClassGroupRsp() {
    }

    public GetClassGroupRsp(List<ClassGroup> classGroupList, List<Integer> currentEnrollment) {
        this.classGroupList = classGroupList;
        this.currentEnrollment = currentEnrollment;
    }
    
    public List<ClassGroup> getClassGroupList() {
        return classGroupList;
    }

    public void setClassGroupList(List<ClassGroup> classGroupList) {
        this.classGroupList = classGroupList;
    }
    
    public List<Integer> getCurrentEnrollment() {
        return currentEnrollment;
    }

    public void setCurrentEnrollment(List<Integer> currentEnrollment) {
        this.currentEnrollment = currentEnrollment;
    }

}
