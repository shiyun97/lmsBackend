/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.CreateUser;
import datamodel.rest.UserLogin;
import entities.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.faces.context.FacesContext;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Vixson
 */
@Stateless
@Path("User")
public class UserResource {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;

    public UserResource() {
    }
    
    @PUT
    @Path(value = "createUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(CreateUser createUser){
        try{
            User user = new User();
            user.setFirstName(createUser.getFirstName());
            user.setLastName(createUser.getLastName());
            user.setEmail(createUser.getEmail());
            user.setUsername(createUser.getUsername());
            user.setPassword(createUser.getPassword());
            user.setGender(createUser.getGender());
            user.setAccessRight(createUser.getAccessRight());
            
            user.setClassGroupList(null);
            user.setConsultationTimeslotList(null);
            //user.setModuleList(null);
            user.setQuizAttemptList(null);
            user.setSurveyAttemptList(null);
            
            em.persist(user);
            em.flush();
            
            return Response.status(Response.Status.OK).entity(user).build();
        } catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GET
    @Path("getAllUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUser(){
        try{
            Query query = em.createQuery("select u from User u");
            List<User> userList = query.getResultList();
            
            if(userList != null && !userList.isEmpty()){
                return Response.status(Response.Status.OK).entity(userList).build(); 
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No user found").build();
                } 
            }catch(Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @GET
    @Path(value = "getUser/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long userId){
        try{
            if(em.find(User.class, userId) == null){
                return Response.status(Response.Status.BAD_REQUEST).entity("User does not exist").build();
            }
            
            Query query = em.createQuery("select u from User u where u.user = :userId");
            User user = (User) query.getSingleResult();
            return Response.status(Response.Status.OK).entity(user).build();
        } catch(Exception ex){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    @DELETE
    @Path(value = "deleteUser/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@PathParam("userId") Long userId){
        try{
            User user = em.find(User.class, userId);
            if (user == null){
                return Response.status(Response.Status.BAD_REQUEST).entity("User does not exist").build();
            }
            em.remove(user);
            
            return Response.status(Response.Status.OK).entity(user).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();           
        }
    }

    @GET
    @Path(value = "userLogin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogin(@QueryParam("username") String username, @QueryParam("password") String password){
        try{
            User user = em.find(User.class, username);
            if(user == null){
                return Response.status(Response.Status.NOT_FOUND).entity("No such username").build();
            }
            if(user.getPassword().equals(password)){
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("isLogin", true);
                FacesContext.getCurrentInstance().getExternalContext().getSessionMap().put("currentUser", user);          
               
            } return Response.status(Response.Status.OK).entity(new UserLogin(user)).build();
            
            /**Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password");
            User user = (User) query.getSingleResult();

            if(user != null){
                return Response.status(Response.Status.OK).entity(new UserLogin(user)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No such user").build();
            } **/
         } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();           
        }
    }
}
