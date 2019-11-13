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
public class CompleteCoursepackRsp {
    private boolean completeCoursepack;
    private boolean unlockBadge;
    private boolean unlockCertificate;

    public boolean isUnlockCertificate() {
        return unlockCertificate;
    }

    public void setUnlockCertificate(boolean unlockCertificate) {
        this.unlockCertificate = unlockCertificate;
    }

    public boolean isCompleteCoursepack() {
        return completeCoursepack;
    }

    public void setCompleteCoursepack(boolean completeCoursepack) {
        this.completeCoursepack = completeCoursepack;
    }

    public boolean isUnlockBadge() {
        return unlockBadge;
    }

    public void setUnlockBadge(boolean unlockBadge) {
        this.unlockBadge = unlockBadge;
    }
}
