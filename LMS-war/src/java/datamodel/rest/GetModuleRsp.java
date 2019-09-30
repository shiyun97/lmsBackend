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
 * @author Vixson
 */
public class GetModuleRsp {
    
    private List<Module> module;

    public GetModuleRsp() {
    }

    public GetModuleRsp(List<Module> module) {
        this.module = module;
    }

    public List<Module> getModule() {
        return module;
    }

    public void setModule(List<Module> module) {
        this.module = module;
    }
    
}
