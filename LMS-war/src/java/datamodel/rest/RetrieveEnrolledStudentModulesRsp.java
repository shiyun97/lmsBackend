/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Module;
import java.util.List;

/**
 *
 * @author Asus
 */
public class RetrieveEnrolledStudentModulesRsp {
    List<Module> modules;

    public RetrieveEnrolledStudentModulesRsp(List<Module> modules) {
        this.modules = modules;
    }

    public RetrieveEnrolledStudentModulesRsp() {
    }

    public List<Module> getModules() {
        return modules;
    }

    public void setModules(List<Module> modules) {
        this.modules = modules;
    }     
}
