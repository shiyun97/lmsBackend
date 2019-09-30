/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.ErrorRsp;
import datamodel.rest.RetrieveAnnouncement;
import entities.Announcement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

/**
 *
 * @author Jasmine
 */
@Path("announcement")
@Stateless
public class AnnouncementResource {
    
    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;


    @Path("retrieveAllAnnouncements")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllAnnouncements(){
        
        try{
            Query query = em.createQuery("SELECT a FROM Annoucement a ");
            List<Announcement> announcements = query.getResultList();
            RetrieveAnnouncement re = new RetrieveAnnouncement();
            re.setAnnouncements(new ArrayList<>());
            if(announcements.isEmpty()|| announcements.get(0) == null){
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No available announcements")).build();
            } else {
                for (Announcement a: announcements){
                    /*User teacher = m.getAssignedTeacher();
                    User teacherCopy = new User(teacher.getFirstName(), teacher.getLastName(), teacher.getEmail(),
                            teacher.getUsername(), null, teacher.getGender(), teacher.getAccessRight(),
                            null, null, null, null, null, null, null);
                    resp.getModules().add(
                            new Module(m.getModuleId(),m.getCode(), m.getTitle(), m.getDescription(),
                                    m.getFeedback(), m.getSemesterOffered(), m.getYearOffered(),
                                    m.getCreditUnit(), m.getGrade(), m.getMaxEnrollment(), 
                                    null, null, null, null, null, null, null, null, null, null,
                                    teacherCopy, null, null, null, m.isHasExam(), m.getExamTime(), m.getExamVenue(),
                                    m.getLectureDetails()));*/
                }
                return Response.status(Status.OK).entity(re).build();
            }
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();            
        }
    }
}

