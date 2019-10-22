/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Outlines;
import java.util.List;

/**
 *
 * @author Jasmine
 */
public class GetOutlineRsp {
    private List<Outlines> outlines;

    public GetOutlineRsp(){
        
    }
    
    public GetOutlineRsp(List<Outlines> outlines) {
        this.outlines = outlines;
    }

    public List<Outlines> getOutlines() {
        return outlines;
    }

    public void setOutlines(List<Outlines> outlines) {
        this.outlines = outlines;
    }
    
    
    
}
