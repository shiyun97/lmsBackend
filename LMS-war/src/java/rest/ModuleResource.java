/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.MountModuleReq;
import datamodel.rest.UpdateModule;
import entities.Module;
import entities.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.exception.InputDataValidationException;
import util.exception.ModuleNotFoundException;

/**
 *
 * @author Vixson
 */
@Stateless
@Path("User")
public class ModuleResource {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;

    public ModuleResource() {
    }

    @PUT
    @Path(value = "mountModule")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response mountModule(MountModuleReq mountModuleReq){
        try{
            Module module = new Module();
            module.setCode(mountModuleReq.getCode());
            module.setTitle(mountModuleReq.getTitle());
            module.setDescription(mountModuleReq.getDescription());
            module.setSemesterOffered(mountModuleReq.getSemesterOffered());
            module.setCreditUnit(mountModuleReq.getCreditUnit());
            module.setMaxEnrollment(mountModuleReq.getMaxEnrollment());
            module.setHasExam(mountModuleReq.isHasExam());
            module.setExamTime(mountModuleReq.getExamTime());
            module.setExamVenue(mountModuleReq.getExamVenue());
            module.setAnnoucementList(null);
            module.setAttandanceList(null);
            module.setClassGroupList(null);
            module.setConsultationList(null);
            module.setFeedback(null);
            module.setFolderList(null);
            module.setForumPostList(null);
            module.setGrade(null);
            module.setGradeItemList(null);
            module.setLessonPlanList(null);
            module.se
            

            em.persist(module);
            em.flush();

            return Response.status(Response.Status.OK).entity(module).build();
        } catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @Path("getModule/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModuleById(@PathParam("id") Long moduleId){
        try{
            if(em.find(Module.class, moduleId) == null){
                return Response.status(Response.Status.BAD_REQUEST).entity("Module does not exist").build();
            }
            
        Query query = em.createQuery("select m from Module m where m.module = :moduleId");
        Module module = (Module) query.getSingleResult();
        return Response.status(Response.Status.OK).entity(module).build();
        
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @Path("getAllModule")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllModule(){
        try{
            Query query = em.createQuery("select m from Module m");
            List<Module> moduleList = query.getResultList();
            
            if(moduleList != null && !moduleList.isEmpty()){
            return Response.status(Response.Status.OK).entity(moduleList).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No module found").build();
            }
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @Path("removeModule/{code}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeModule(@QueryParam("code") String code){
        try{
            if(em.find(Module.class, code) == null){
                return Response.status(Response.Status.BAD_REQUEST).entity("Module does not exist").build();
            }
            
            Query query = em.createQuery("select m from Module m where m.code = :code");
            Module module = (Module) query.getSingleResult();
            
            if(module != null){
            em.remove(module);
            }         
            return Response.status(Response.Status.OK).entity(query).build();
            
        } catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();           
        }
    }
    
    @Path("updateModule")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateModule(UpdateModule updateModule){
        try{
            Module module = updateModule.getModule();
            
            if(module != null){
            module.setCode(updateModule.getCode());
            module.setTitle(updateModule.getTitle());
            module.setDescription(updateModule.getDescription());
            module.setSemesterOffered(updateModule.getSemesterOffered());
            module.setCreditUnit(updateModule.getCreditUnit());
            module.setMaxEnrollment(updateModule.getMaxEnrollment());
            
            em.merge(module);
            em.flush();
            }
            return Response.status(Response.Status.OK).entity(module).build();
        /**}catch(ModuleNotFoundException | InputDataValidationException ex) {
            return Response.status(Response.Status.BAD_REQUEST).entity("Module does not exist").build();   **/        
        }catch(Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();           
        }
    }
}
