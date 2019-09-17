/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.CreateNewFeedback;
import entities.Feedback;
import entities.Module;
import entities.User;
import java.sql.Timestamp;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.naming.Context;
import javax.naming.InitialContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.transaction.UserTransaction;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Asus
 */
@Path("feedback")
public class FeedbackResource {
    
    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;
    
    @Path("retrieveAllFeedbackForModule/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllFeedbackForModule(@PathParam("moduleId") Long moduleId){
        System.out.println("retrieveAllFeedbackForModule");
        System.out.println(em);
        try{
            if(em.find(Module.class, moduleId) == null){
                return Response.status(Response.Status.BAD_REQUEST).entity("Module Not Exists!").build();
            }

            Query query = em.createQuery("SELECT f FROM Feedback f WHERE f.module = :moduleId");
            List<Feedback> feedbacks = (List<Feedback>) query.getResultList();
            if(feedbacks != null && !feedbacks.isEmpty()){
                return Response.status(Response.Status.OK).entity(feedbacks).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No feedback for this module").build();
            }
       } catch (Exception e){
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
       }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFeedback(CreateNewFeedback createNewFeedback){
        try{
            User user = em.find(User.class, createNewFeedback.getUserId());
            if(user == null){
                return Response.status(Response.Status.BAD_REQUEST).entity("User doesn't exist!").build();
            }
            
            Module module = em.find(Module.class, createNewFeedback.getModuleId());
            if(module == null){
                return Response.status(Response.Status.BAD_REQUEST).entity("Module doesn't exist!").build();
            }
            
            if(!module.getStudentList().contains(user)){
                return Response.status(Response.Status.FORBIDDEN).entity("Student isn't enrolled in this module!").build();
            }
            
            Feedback feedback = new Feedback();
            feedback.setFeedback(createNewFeedback.getFeedback());
            feedback.setModule(module);
            // Set current time
            feedback.setCreateTs(new Timestamp(new Date().getTime()));
            
            module.getFeedbackList().add(feedback);
            
            em.persist(feedback);
            em.merge(module);
            em.flush();
            
            return Response.status(Response.Status.OK).build();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
}
