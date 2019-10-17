/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.CreateCoursepack;
import datamodel.rest.GetCoursepackRsp;
import datamodel.rest.UpdateCoursepack;
import datamodel.rest.GetUserRsp;
import entities.Coursepack;
import entities.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * 
 */
@Stateless
@Path("Coursepack")
public class CoursepackResource {
    
    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;
    
    public CoursepackResource(){
        
    }
    
    public boolean isLogin(User user) {
        user = em.find(User.class, user.getId());
        if (user != null) {
            return true;
        }
        return false;
    }
    
    public User userLogin(String username, String password) {
        User user = em.find(User.class, username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new NotFoundException("Username does not exist");
    }
    
    @PUT
    @Path(value = "createCoursepack")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCoursepack(CreateCoursepack createCoursepack){
        try{
            Coursepack coursepack = new Coursepack();
            coursepack.setCode(createCoursepack.getCode());
            coursepack.setTitle(createCoursepack.getTitle());
            coursepack.setDescription(createCoursepack.getDescription());
            coursepack.setCategory(createCoursepack.getCategory());
            coursepack.setPrice(createCoursepack.getPrice());
            coursepack.setStartDate(createCoursepack.getStartDate());
            coursepack.setTeacherBackground(createCoursepack.getTeacherBackground());
            em.persist(coursepack);
            em.flush();
            
            return Response.status(Response.Status.OK).entity(coursepack).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
        
    @Path(value = "deleteCoursepack")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCoursepack(@QueryParam("coursepackId") Long coursepackId){
            
        try{
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);
            if(coursepack == null){
                return Response.status(Response.Status.NOT_FOUND).entity("No coursepack found").build();
            }
            em.remove(coursepack);
                
            return Response.status(Response.Status.OK).entity("Coursepack deleted").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
        
    @Path(value = "deleteCoursepackByCode")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCoursepackByCode(@QueryParam("code") String code){
        try{
            Query query = em.createQuery("select cp from Coursepack cp where cp.code =:code");
            query.setParameter("code", code);
            Coursepack coursepack = (Coursepack) query.getSingleResult();
            
             if (coursepack != null) {
                em.remove(coursepack);
                return Response.status(Response.Status.OK).entity(coursepack).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("No coursepack found").build();

        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
        
    }
    
    
    @Path(value = "updateCoursepack")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCoursepack(UpdateCoursepack updateCoursepack, @QueryParam("coursepackId") Long coursepackId){
        try{
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);
            if(coursepack != null){
                coursepack.setCode(updateCoursepack.getCode());
                coursepack.setTitle(updateCoursepack.getTitle());
                coursepack.setDescription(updateCoursepack.getDescription());
                coursepack.setCategory(updateCoursepack.getCategory());
                coursepack.setPrice(updateCoursepack.getPrice());
                coursepack.setStartDate(updateCoursepack.getStartDate());
                coursepack.setTeacherBackground(updateCoursepack.getTeacherBackground());
                
                em.merge(coursepack);
                em.flush();
                
                return Response.status(Response.Status.OK).entity(coursepack).build();

            }
            return Response.status(Response.Status.NOT_FOUND).entity("Coursepack does not exist").build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @Path(value = "updateCoursepackDescription/{coursepackId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCoursepackDescripton(String description, @PathParam("coursepackId") Long coursepackId){
        try{
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);
            if(coursepack == null){
                return Response.status(Response.Status.NOT_FOUND).entity("Coursepack does not exist").build();
            }
            
            coursepack.setDescription(description);
            em.flush();
            return Response.status(Response.Status.OK).build();
         } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }
    
    @GET 
    @Path(value = "getAllPublicUserByCoursepack")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudentByCoursepack(@QueryParam("coursepackId") Long coursepackId){
        try{
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);
            if(coursepack == null){
                return Response.status(Response.Status.NOT_FOUND).entity("No coursepack found").build();
            }
            GetUserRsp rsp = new GetUserRsp(new ArrayList<>());
            List<User> publicUser = coursepack.getPublicUserList();
            if(publicUser == null && publicUser.isEmpty()){
                return Response.status(Response.Status.NOT_FOUND).entity("No public user found").build();
            }
            
            for(User pu: publicUser){
                rsp.getUserList().add(
                        new User(null, pu.getUserId(), pu.getFirstName(), pu.getLastName(), pu.getEmail(), 
                                pu.getUsername(), null, pu.getGender(), pu.getAccessRight(), null, pu.getQuizAttemptList(),
                                null, null, null, null, pu.getPublicUserModuleList())
                );
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @Path(value = "getCoursepack/{coursepackId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoursepackById(@PathParam("coursepackId") Long coursepackId){
        try{
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);
            if(coursepack == null){
                return Response.status(Response.Status.NOT_FOUND).entity("Coursepack does not exist").build();
            }
           
            
            Coursepack coursepackCopy = new Coursepack(coursepack.getCoursepackId(), coursepack.getCode(), coursepack.getTitle(),
                        coursepack.getDescription(), coursepack.getCategory(), coursepack.getPrice(), coursepack.getStartDate(), null, 
                        coursepack.getTeacherBackground(), null, null, null, null, null, null, null);
            
            return Response.status(Response.Status.OK).entity(coursepackCopy).build();

        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @Path (value = "getCoursepackByCode/{code}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCoursepackByCode(@PathParam("code") String code){
        try{
            Query query = em.createQuery("select cp from Coursepack cp where cp.code =:code");
            query.setParameter("code", code);
            Coursepack coursepack = (Coursepack) query.getSingleResult();
            
            if(coursepack == null){
                return Response.status(Response.Status.NOT_FOUND).entity("Coursepack does not exist").build();
            }
            
            Coursepack coursepackCopy = new Coursepack(coursepack.getCoursepackId(), coursepack.getCode(), coursepack.getTitle(),
                        coursepack.getDescription(), coursepack.getCategory(), coursepack.getPrice(), coursepack.getStartDate(), null, 
                        coursepack.getTeacherBackground(), null, null, null, null, null, null, null);
            
            return Response.status(Response.Status.OK).entity(coursepackCopy).build();

        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
    }
    
    @GET
    @Path(value = "getAllCoursepack")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCoursepack(){
        try{
            Query query = em.createQuery("select cp from Coursepack cp");
            List<Coursepack> coursepackList = query.getResultList();
            
            GetCoursepackRsp rsp = new GetCoursepackRsp();
            rsp.setCoursepack(new ArrayList<>());
            if(coursepackList == null && coursepackList.isEmpty()){
                return Response.status(Response.Status.NOT_FOUND).entity("No coursepack found").build();
            }else{
                for(Coursepack coursepack : coursepackList){
                    rsp.getCoursepack().add(
                            new Coursepack(coursepack.getCoursepackId(), coursepack.getCode(), coursepack.getTitle(),
                                    coursepack.getDescription(), coursepack.getCategory(), coursepack.getPrice(), coursepack.getStartDate(), null, 
                                    coursepack.getTeacherBackground(), null, null, null, null, null, null, null));
                }
                return Response.status(Response.Status.OK).entity(rsp).build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        
    }
    
        
        

    
    
    
    
}
