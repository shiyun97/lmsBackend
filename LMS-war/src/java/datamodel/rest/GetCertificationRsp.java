/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Certification;
import java.util.List;

/**
 *
 * @author Vixson
 */
public class GetCertificationRsp {
    private List<Certification> certificationList;

    public GetCertificationRsp() {
    }

    public GetCertificationRsp(List<Certification> certificationList) {
        this.certificationList = certificationList;
    }

    public List<Certification> getCertificationList() {
        return certificationList;
    }

    public void setCertificationList(List<Certification> certificationList) {
        this.certificationList = certificationList;
    }
    
}
