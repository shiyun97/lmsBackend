package datamodel.rest;

import entities.ConsultationTimeslot;
import java.util.List;


public class RetrieveAllConsultationsForModule {

  
  

private List<ConsultationTimeslot> consultationTimeslots;

public RetrieveAllConsultationsForModule(){
    
}

public RetrieveAllConsultationsForModule(List<ConsultationTimeslot> consultationTimeslots){
    this.consultationTimeslots = consultationTimeslots;
}
    
  public List<ConsultationTimeslot> getConsultationTimeslots() {
        return consultationTimeslots;
    }

    public void setConsultationTimeslots(List<ConsultationTimeslot> consultationTimeslots) {
        this.consultationTimeslots = consultationTimeslots;
    }

}
