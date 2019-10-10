/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.ErrorRsp;
import datamodel.rest.RetrieveAnnouncement;
import entities.Announcement;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.AccessRightEnum;

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
            Query query = em.createQuery("SELECT a FROM Announcement a ");
            List<Announcement> announcements = query.getResultList();
            RetrieveAnnouncement re = new RetrieveAnnouncement();
            re.setAnnouncements(new ArrayList<>());
            if(announcements.isEmpty()|| announcements.get(0) == null){
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No available announcements")).build();
            } else {
                for (Announcement a: announcements){
                    User student = a.getOwner();
                    User studentCopy = new User(student.getFirstName(), student.getLastName(), student.getEmail(),
                            student.getUsername(), null, student.getGender(), student.getAccessRight(),
                            null, null, null, null, null, null, null);
                    re.getAnnouncements().add(
                            new Announcement(a.getAnnoucementId(), a.getTitle(), a.getDescription(), 
                                        a.getCreateTs(), a.getUpdateTs(), a.getSystemWide(), a.getModule(), studentCopy));
                }
                return Response.status(Status.OK).entity(re).build();
            }
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();            
        }
    }
    
    @Path("retrieveTeacherAnnouncements/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTeacherAnnouncements(@PathParam("userId") Long userId){
        
        // Verify user
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response
                    .status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access right for this function!"))
                    .build();
        }
        
        
        try{
            Query query = em.createQuery("SELECT a FROM Announcement a WHERE a.owner.userId= :userId ");
            query.setParameter("userId", userId);
            List<Announcement> announcements = query.getResultList();
            RetrieveAnnouncement re = new RetrieveAnnouncement(new ArrayList<>());
            if(announcements.isEmpty()|| announcements.get(0) == null){
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("User has no announcement")).build();
            } else {
                for (Announcement a: announcements){
                    User student = a.getOwner();
                    User studentCopy = new User(student.getFirstName(), student.getLastName(), student.getEmail(),
                            student.getUsername(), null, student.getGender(), student.getAccessRight(),
                            null, null, null, null, null, null, null);
                    re.getAnnouncements().add(
                            new Announcement(a.getAnnoucementId(), a.getTitle(), a.getDescription(), 
                                        a.getCreateTs(), a.getUpdateTs(), a.getSystemWide(), a.getModule(), studentCopy));
                }
                return Response.status(Status.OK).entity(re).build();
            }
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();            
        }
    }
    
    
    
    
    
}

