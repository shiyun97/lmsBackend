/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

/**
 *
 * @author madeline
 */
public class CreateThreadReq {

    private String title;
    private String message;
    private String type;

    public CreateThreadReq() {
    }

    public CreateThreadReq(String title, String message) {
        this.title = title;
        this.message = message;
    }
    
    public CreateThreadReq(String title, String message, String type) {
        this.title = title;
        this.message = message;
        this.type = type;
    }
    
    public CreateThreadReq(String message){
        this.message = message;
    }
    
    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

}
