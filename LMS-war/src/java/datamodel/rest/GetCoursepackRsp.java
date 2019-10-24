/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Coursepack;
import java.util.List;

/**
 *
 * 
 */
public class GetCoursepackRsp {
    
    private List<Coursepack> coursepack;

    public GetCoursepackRsp() {
      
    }
    public GetCoursepackRsp(List<Coursepack> coursepack) {
        this.coursepack = coursepack;
    }
    
    
    
    /*private List <Coursepack> coursepack;

    public GetCoursepackRsp(){
        
    }
    
    public GetCoursepackRsp(List<Coursepack> coursepack) {
        this.coursepack = coursepack;
    }

    public List<Coursepack> getCoursepack() {
        return coursepack;
    }

    public void setCoursepack(List<Coursepack> coursepack) {
        this.coursepack = coursepack;
    }*/

    public List<Coursepack> getCoursepack() {
        return coursepack;
    }

    public void setCoursepack(List<Coursepack> coursepack) {
        this.coursepack = coursepack;
    }
    
    
}
