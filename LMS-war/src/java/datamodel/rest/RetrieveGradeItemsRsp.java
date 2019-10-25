/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.GradeItem;
import java.util.List;

/**
 *
 * @author Asus
 */
public class RetrieveGradeItemsRsp {
    List<GradeItem> gradeItems;

    public List<GradeItem> getGradeItems() {
        return gradeItems;
    }

    public void setGradeItems(List<GradeItem> gradeItems) {
        this.gradeItems = gradeItems;
    }

    public RetrieveGradeItemsRsp(List<GradeItem> gradeItems) {
        this.gradeItems = gradeItems;
    }

    public RetrieveGradeItemsRsp() {
    }
    
    
}
