/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import entities.Module;
import entities.User;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * 
 */
@Path("module")
@Stateless
public class ModuleResource {
    
    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;
        
      
    //View module
    @GET
    @Path(value = "retrieveAllModules")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllModules(){
        
        try{
            
            Query query = em.createQuery("Select m FROM Module m ");
            List<Module> modules = (List<Module>) query.getResultList();
            if(modules != null && !modules.isEmpty()){
                 return Response.status(Response.Status.OK).entity(modules).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No module found").build();
            }

        }catch (Exception e){
           e.printStackTrace();
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
       }
       
    }
    
    //View module by moduleId
    @GET
    @Path(value = "retrieveModules/{moduleId}")
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveModuleByModuleId(@PathParam("moduleId") Long moduleId) {
        
        try{
            
            Module module = em.find(Module.class, moduleId);
            
            if(module == null){
                return Response.status(Response.Status.BAD_REQUEST).entity("Module Not Exists!").build();
            }

            if(module != null){
                 return Response.status(Response.Status.OK).entity(module).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No module found").build();
            }

        }catch (Exception e){
           e.printStackTrace();
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
       }
       
    }
    
    //View module by userId
    @GET
    @Path(value = "retrieveModulesForUser/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveModuleByUserId(@PathParam("userId") Long userId) {
        
        try{
            
            if(em.find(User.class, userId) == null){
               return Response.status(Response.Status.BAD_REQUEST).entity("User Not Exists!").build(); 
            }
            
            Query query = em.createQuery("SELECT m FROM Module m WHERE m.owner = :userId");
            List<Module> modules = (List<Module>) query.getResultList();
            if(modules != null && !modules.isEmpty()){
                return Response.status(Response.Status.OK).entity(modules).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No module for this user").build();
            }
            
        }catch (Exception e){
           e.printStackTrace();
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
       }
       
    }
    
    //Update module by userId 
    @PUT
    @Path(value = "updateModuleForUser/{userId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateModuleByUserId(@PathParam("userId") Long userId, @PathParam("moduleId") Long moduleId) {
        
        try{
            
            if(em.find(User.class, userId) == null){
               return Response.status(Response.Status.BAD_REQUEST).entity("User Not Exists!").build(); 
            }
            
            Query query = em.createQuery("SELECT m FROM Module m WHERE m.owner = :userId");
            
            Module m = (Module)query.getSingleResult();

            if(m != null){
              
                m.setAnnoucementList(null);
                m.setAttandanceList(null);
                m.setClassGroupList(null);
                m.setConsultationList(null);
                m.setFeedbackList(null);
                m.setFolderList(null);
                m.setForumPostList(null);
                m.setGrade(null);
                m.setGradeItemList(null);
                m.setLessonPlanList(null);
                m.setGradeItemList(null);
                m.setLessonPlanList(null);
                m.setMaxEnrollment(null);
                m.setQuizList(null);
               
                em.merge(m);
                em.flush();

                return Response.status(Response.Status.OK).entity(m).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No module for this user").build();
            }
            
        }catch (Exception e){
           e.printStackTrace();
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(e).build();
       }
       
    }
    
    
}
