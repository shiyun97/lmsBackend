/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

/**
 *
 * @author Asus
 */
public class AppealRoundRsp {
    private Boolean isOpen;

    public Boolean getIsOpen() {
        return isOpen;
    }

    public void setIsOpen(boolean isOpen) {
        this.isOpen = isOpen;
    }

    public AppealRoundRsp(boolean isOpen) {
        this.isOpen = isOpen;
    }
    
    
}
