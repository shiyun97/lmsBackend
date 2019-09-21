/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.CreateNewModule;
import datamodel.rest.RetrieveAllModules;
import entities.Module;
import entities.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Jasmine
 */
@Path("module")
@Stateless
public class ModuleResource {
    
    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;
    
    //Create module 
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createModule(CreateNewModule createNewModule){
         try{
            User user = em.find(User.class, createNewModule.getUserId());
            if(user == null){
                return Response.status(Response.Status.BAD_REQUEST).entity("User doesn't exist!").build();
            }
            
            /*
            Module module = em.find(Module.class, createNewFeedback.getModuleId());
            if(module == null){
                return Response.status(Response.Status.BAD_REQUEST).entity("Module doesn't exist!").build();
            }
            
            if(!module.getStudentList().contains(user)){
                return Response.status(Response.Status.FORBIDDEN).entity("Student isn't enrolled in this module!").build();
            }

            */
            
            Module module = new Module();
            //module.setModuleId(createNewModule.getModule());
            //module.setModule(createNewModule.getModule());
           
            module.setOwner(user);

            //module.getFeedbackList().add(feedback);
            
            em.persist(module);
            //em.merge();
            em.flush();
            
            return Response.status(Response.Status.OK).build();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }

    }
        
    /*   
    //View module
    @GET
    @Path(value = "retrieveAllModules")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllModules(){
        
        try{
            if(em.find(Module.class, moduleId == null){
                return Response.status(Response.Status.BAD_REQUEST).entity("Module Not Exists!").build();
            }
            
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
       
    }*/

    
}
