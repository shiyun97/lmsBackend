/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.GradeEntry;
import java.util.List;

/**
 *
 * @author Asus
 */
public class RetrieveGradeEntriesRsp {
    List<GradeEntry> gradeEntries;

    public RetrieveGradeEntriesRsp() {
    }

    public RetrieveGradeEntriesRsp(List<GradeEntry> gradeEntries) {
        this.gradeEntries = gradeEntries;
    }

    public List<GradeEntry> getGradeEntries() {
        return gradeEntries;
    }

    public void setGradeEntries(List<GradeEntry> gradeEntries) {
        this.gradeEntries = gradeEntries;
    }
    
    
}
