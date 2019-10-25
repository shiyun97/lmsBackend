/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.ConsultationTimeslot;
import java.util.List;

/**
 *
 * @author Vixson
 */
public class GetConsultationRsp {
    private List<ConsultationTimeslot> consultationTimeslot;

    public GetConsultationRsp() {
    }

    public GetConsultationRsp(List<ConsultationTimeslot> consultationTimeslot) {
        this.consultationTimeslot = consultationTimeslot;
    }

    public List<ConsultationTimeslot> getConsultationTimeslot() {
        return consultationTimeslot;
    }

    public void setConsultationTimeslot(List<ConsultationTimeslot> consultationTimeslot) {
        this.consultationTimeslot = consultationTimeslot;
    }
    
    
}
