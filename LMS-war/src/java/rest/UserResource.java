/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.CreateUser;
import datamodel.rest.UpdateUser;
import datamodel.rest.CheckUserLogin;
import datamodel.rest.GetUserRsp;
import ejb.SHAExample;
import ejb.SendMailSSL;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.AccessRightEnum;
import static util.AccessRightEnum.Public;
import util.GenderEnum;
//import util.CryptographicHelper;
//import util.exception.InvalidLoginCredentialException;

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

    private SendMailSSL sendMail = new SendMailSSL();
    private SHAExample hashPassword = new SHAExample();

    public boolean isLogin(User user) {
        user = em.find(User.class, user.getId());
        if (user != null) {
            return true;
        }
        return false;
    }

    /*public User retrieveUser(String attri, String val) {
        String input = "select u from User u where u." + attri + "=:inVal";
        Query query = em.createQuery(input);
        query.setParameter("inVal", val);
        
        try {
            return (User) query.getSingleResult();
        } catch (Exception ex) {
            throw new NotFoundException("User not found");
        }
    }

    public User userlogin(String email, String password) {
        try {
            User user = retrieveUser("email", email);
            String psHash = CryptographicHelper.getInstance().byteArrayToHexString(CryptographicHelper.getInstance().doMD5Hashing(password + user.getSalt()));

            if (user.getPassword().equals(psHash)) {
                return user;
            } else {
                throw new InvalidLoginCredentialException("Username does not exist/ wrong password");
            }
        } catch (Exception ex) {
            throw new NotFoundException("User does not exist");
        }
    }*/
    @PUT
    @Path(value = "createUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createUser(CreateUser createUser) {

        //if (createUser.getUser().getAccessRight() == Admin) {
        long i = 1;
        Query query = em.createQuery("select u from User u where u.userId = :userId");
        query.setParameter("userId", i);
        User admin = (User) query.getSingleResult();
        try {
            User user = new User();
            user.setFirstName(createUser.getFirstName());
            user.setLastName(createUser.getLastName());
            user.setEmail(createUser.getEmail());
            user.setPassword(createUser.getPassword());
            user.setGender(createUser.getGender());
            user.setAccessRight(createUser.getAccessRight());
            user.setUsername(createUser.getUsername());
            em.persist(user);
            em.flush();
            String passwordNow = hashPassword.getPassword(user.getPassword());
            User userCopy = new User(user.getFirstName(), user.getLastName(), user.getEmail(),
                    user.getUsername(), passwordNow, user.getGender(), user.getAccessRight(),
                    null, null, null, null, null, null, null);
            sendMail.sendSingle(admin.getEmail(), admin.getPassword(), userCopy.getEmail(), "Account created: " + userCopy.getEmail(), user.getPassword());
            return Response.status(Response.Status.OK).entity(userCopy).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        //}
        //return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path("getAllUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUser() {
        try {
            Query query = em.createQuery("select u from User u");
            List<User> userList = query.getResultList();

            GetUserRsp rsp = new GetUserRsp(new ArrayList<>());

            if (userList == null && userList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No user found").build();
            } else {
                for (User u : userList) {
                    rsp.getUserList().add(
                            new User(null, u.getId(), u.getFirstName(), u.getLastName(),
                                    u.getEmail(), u.getUsername(), u.getPassword(), u.getGender(),
                                    u.getAccessRight(), null, null, null,
                                    null, null, null, null));
                }
                return Response.status(Response.Status.OK).entity(rsp).build();
            }
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path(value = "getUser/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserById(@PathParam("id") Long userId) {
        try {
            User user = em.find(User.class, userId);

            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("User does not exist").build();
            }

            User userCopy = new User(null, user.getId(), user.getFirstName(), user.getLastName(),
                    user.getEmail(), user.getUsername(), user.getPassword(), user.getGender(),
                    user.getAccessRight(), null, null, null, null, null, null, null);

            return Response.status(Response.Status.OK).entity(userCopy).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path(value = "deleteUser")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteUser(@QueryParam("userId") Long userId) {

        //if (checkUserLogin.getUser().getAccessRight() == Admin) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User does not exist").build();
            }
            em.remove(user);

            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        //}
        //return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @POST
    @Path(value = "updateUser")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateUser(UpdateUser updateUser) {

        //if (updateUser.getUser().getAccessRight() == Admin) {            
        try {
            Long userId = updateUser.getUserId();
            User user = em.find(User.class, userId);
            if (user != null) {
                user.setAccessRight(updateUser.getAccessRight());
                user.setClassGroupList(updateUser.getClassGroupList());
                user.setConsultationTimeslotList(updateUser.getConsultationTimeslotList());
                user.setEmail(updateUser.getEmail());
                user.setUsername(updateUser.getUsername());
                user.setFirstName(updateUser.getFirstName());
                user.setGender(updateUser.getGender());
                user.setLastName(updateUser.getLastName());
                user.setPassword(updateUser.getPassword());
                user.setPublicUserModuleList(updateUser.getPublicUserModuleList());
                user.setQuizAttemptList(updateUser.getQuizAttemptList());
                user.setStudentModuleList(updateUser.getStudentModuleList());
                user.setSurveyAttemptList(updateUser.getSurveyAttemptList());
                user.setTeacherModuleList(updateUser.getTeacherModuleList());
                em.merge(user);
                em.flush();

                /*User userCopy = new User(null,user.getId(),user.getFirstName(),user.getLastName(),
                            user.getEmail(),user.getUsername(),user.getPassword(),
                            user.getGender(),user.getAccessRight(),null,null,
                            null,null,null,null,null);*/
                return Response.status(Response.Status.OK).entity(user).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("User does not exist").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        //}
        //return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @GET
    @Path(value = "userLogin")
    @Produces(MediaType.APPLICATION_JSON)
    public Response userLogin(@QueryParam("email") String email, @QueryParam("password") String password) {
        try {
            Query query = em.createQuery("SELECT u FROM User u WHERE u.email = :email");
            query.setParameter("email", email);
            User user = (User) query.getSingleResult();
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No such user").build();
            }
            if (!user.getPassword().equals(password)) {
                return Response.status(Response.Status.UNAUTHORIZED).entity("Invalid password").build();
            }

            User newU = new User(null, user.getId(), user.getFirstName(), user.getLastName(), user.getEmail(), user.getUsername(), null, user.getGender(), user.getAccessRight(), null, null, null, null, null, null, null);
            return Response.status(Response.Status.OK).entity(new CheckUserLogin(newU)).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @POST
    @Path(value = "publicRegister")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response publicRegister(CreateUser createUser) {
        try {
            User user = new User();
            user.setFirstName(createUser.getFirstName());
            user.setLastName(createUser.getLastName());
            user.setEmail(createUser.getEmail());
            user.setPassword(createUser.getPassword());
            user.setGender(createUser.getGender());
            user.setAccessRight(Public);
            user.setUsername(createUser.getUsername());
            em.persist(user);
            em.flush();

            User userCopy = new User(user.getId(), user.getFirstName(), user.getLastName(),
                    user.getEmail(), user.getUsername(), null, user.getGender(),
                    user.getAccessRight(), null, null, null, null, null, null,
                    null, null, null, null, null, null);
            return Response.status(Response.Status.OK).entity(userCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path(value = "updateAccount")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAccount(UpdateUser updateUser) {
        try {
            Long userId = updateUser.getUserId();
            User user = em.find(User.class, userId);
            if (user != null) {
                user.setEmail(updateUser.getEmail());
                user.setUsername(updateUser.getUsername());
                user.setFirstName(updateUser.getFirstName());
                user.setGender(updateUser.getGender());
                user.setLastName(updateUser.getLastName());
                user.setPassword(updateUser.getPassword());
                em.merge(user);
                em.flush();
                return Response.status(Response.Status.OK).entity(user).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Account does not exist").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
