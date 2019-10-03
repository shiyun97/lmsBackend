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
 * 
 */
public class RetrieveModule {
    
    private Module module;
   
    public RetrieveModule(){
        
    }
    
     public RetrieveModule(Module modules){
        
         this.module = module;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
    
    
    
}
