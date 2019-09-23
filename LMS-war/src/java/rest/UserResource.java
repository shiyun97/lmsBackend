/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.MountModuleReq;
import entities.User;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
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

    @GET
    @Path(value = "userLogin")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogin(@QueryParam("username") String username, @QueryParam("password") String password){
        
        Query query = em.createQuery("SELECT u FROM User u WHERE u.username = :username AND u.password = :password");
        User user = (User) query.getSingleResult();
        
        User admin = em.find(User.class, username);
        
        if(admin.getPassword().equals(password)){
            return admin;
        }
        
        if(user != null && !user.isEmpty()){
            return Response.status(Response.Status.OK).entity(user).build();
        } else {
            ErrorRsp = errorRsp = new ErrorRsp(ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).entity(errorRsp).build();
        }
    }
}
