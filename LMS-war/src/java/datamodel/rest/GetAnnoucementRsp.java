/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.Annoucement;
import java.util.List;

/**
 *
 * @author Vixson
 */
public class GetAnnoucementRsp {
    private List<Annoucement> annoucementList;

    public GetAnnoucementRsp() {
    }

    public GetAnnoucementRsp(List<Annoucement> annoucementList) {
        this.annoucementList = annoucementList;
    }

    public List<Annoucement> getAnnoucementList() {
        return annoucementList;
    }

    public void setAnnoucementList(List<Annoucement> annoucementList) {
        this.annoucementList = annoucementList;
    }
    
}
