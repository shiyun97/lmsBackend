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
public class CreateAppealRqst {
    private String reason;
    private Long moduleId;
    private Long oldTutorialId;
    private Long newTutorialId;
    private String type;
    private Long userId;

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getReason() {
        return reason;
    }

    public void setReason(String reason) {
        this.reason = reason;
    }

    public Long getModuleId() {
        return moduleId;
    }

    public void setModuleId(Long moduleId) {
        this.moduleId = moduleId;
    }

    public Long getOldTutorialId() {
        return oldTutorialId;
    }

    public void setOldTutorialId(Long oldTutorialId) {
        this.oldTutorialId = oldTutorialId;
    }

    public Long getNewTutorialId() {
        return newTutorialId;
    }

    public void setNewTutorialId(Long newTutorialId) {
        this.newTutorialId = newTutorialId;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
    
    
}
