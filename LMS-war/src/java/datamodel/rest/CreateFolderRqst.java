/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import java.sql.Timestamp;
import util.AccessRightEnum;

/**
 *
 * @author yaosh
 */
public class CreateFolderRqst {
    
    private String name;
    private Boolean submission;
    private Timestamp submissionOpenTs;
    private Timestamp submissionCloseTs;
    private AccessRightEnum accessRight;

    public CreateFolderRqst() {
    }

    public CreateFolderRqst(String name, Boolean submission, Timestamp submissionOpenTs, Timestamp submissionCloseTs, AccessRightEnum accessRight) {
        this.name = name;
        this.submission = submission;
        this.submissionOpenTs = submissionOpenTs;
        this.submissionCloseTs = submissionCloseTs;
        this.accessRight = accessRight;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getSubmission() {
        return submission;
    }

    public void setSubmission(Boolean submission) {
        this.submission = submission;
    }

    public Timestamp getSubmissionOpenTs() {
        return submissionOpenTs;
    }

    public void setSubmissionOpenTs(Timestamp submissionOpenTs) {
        this.submissionOpenTs = submissionOpenTs;
    }

    public Timestamp getSubmissionCloseTs() {
        return submissionCloseTs;
    }

    public void setSubmissionCloseTs(Timestamp submissionCloseTs) {
        this.submissionCloseTs = submissionCloseTs;
    }

    public AccessRightEnum getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(AccessRightEnum accessRight) {
        this.accessRight = accessRight;
    }
    
    
}
