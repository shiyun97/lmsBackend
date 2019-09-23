/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Module;

/**
 *
 * @author Vixson
 */
public class GetModuleRsp {
    
    private Module module;

    public GetModuleRsp() {
    }

    public GetModuleRsp(Module module) {
        this.module = module;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
    
}
