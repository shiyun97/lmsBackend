/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.CreateAnnoucement;
import datamodel.rest.ErrorRsp;
import datamodel.rest.GetAnnoucementRsp;
import datamodel.rest.UpdateAnnoucement;
import ejb.SendMailSSL;
import entities.Annoucement;
import entities.Module;
import entities.User;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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

/**
 *
 * @author Vixson
 */
@Stateless
@Path("Annoucement")
public class AnnoucementResource {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public AnnoucementResource() {
    }

    private SendMailSSL sendMail = new SendMailSSL();

    /*@POST
    @Path(value = "createAnnoucement/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAnnoucement(CreateAnnoucement createAnnoucement, @PathParam("id") Long moduleId) {
        try {
            Date createdDate = formatter.parse(createAnnoucement.getCreatedDate());
            Date lastUpdatedDate = formatter.parse(createAnnoucement.getLastUpdatedDate());
            Date startDate = formatter.parse(createAnnoucement.getStartDate());
            Date endDate = formatter.parse(createAnnoucement.getEndDate());

            Module module = em.find(Module.class, moduleId);

            Annoucement annoucement = new Annoucement();
            annoucement.setTitle(createAnnoucement.getTitle());
            annoucement.setContent(createAnnoucement.getContent());
            annoucement.setCreatedDate(createdDate);
            annoucement.setLastUpdatedDate(createdDate);
            annoucement.setStartDate(startDate);
            annoucement.setEndDate(endDate);
            annoucement.setPublish(createAnnoucement.getPublish());
            annoucement.setEmailNotification(createAnnoucement.getEmailNotification());
            //annoucement.setModule(createAnnoucement.getModule());
            annoucement.setModule(module);
            annoucement.setOwner(createAnnoucement.getOwner());
            em.persist(annoucement);
            em.flush();
            module.getAnnoucementList().add(annoucement);

            Module moduleCopy = new Module(module.getModuleId(), module.getCode(), module.getTitle(), module.getDescription(),
                    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                    module.isHasExam(), module.getExamTime(), module.getExamVenue(), module.getLectureDetails(), module.getDepartment(),
                    module.getFaculty());
            Annoucement annoucementCopy = new Annoucement(annoucement.getAnnoucementId(), annoucement.getTitle(),
                    annoucement.getContent(), createdDate, lastUpdatedDate, startDate, endDate,
                    annoucement.getPublish(), annoucement.getEmailNotification(), moduleCopy, null);
            
            Query admin = em.createQuery("select u from User u where u.accessRight = :accessRight");
            admin.setParameter("accessRight", AccessRightEnum.Admin);
            User user = (User) admin.getSingleResult();
            
            List<String> address = new ArrayList<>();
            Query query = em.createQuery("select u from User u");
            List<User> userList = query.getResultList();
            if(userList == null || userList.isEmpty()){
                return Response.status(Response.Status.NOT_FOUND).entity("No user found").build();
            }
            for(User u : userList){
                address.add(u.getEmail());
            }
            sendMail.send(user.getEmail(), user.getPassword(), address, annoucementCopy.getTitle(), annoucementCopy.getContent());
            //sendMail.send("ad1234567min@gmail.com", "password!234%", "ykwvix@gmail.com", annoucementCopy.getTitle(), annoucementCopy.getContent());
            return Response.status(Response.Status.OK).entity(annoucementCopy).build();
            //EmailSessionBean.sendEmail(annoucement.getOwner().getEmail(), annoucement.getTitle(), annoucement.getOwner().getUsername());
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }*/
    @POST
    @Path(value = "createModuleAnnoucement/{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createModuleAnnoucement(CreateAnnoucement createAnnoucement, @PathParam("id") Long moduleId, @QueryParam("userId") Long userId) {
        try {
            Date createdDate = new Date();
            //Date createdDate = formatter.parse(createAnnoucement.getCreatedDate());
            //Date lastUpdatedDate = formatter.parse(createAnnoucement.getLastUpdatedDate());
            Date startDate = formatter.parse(createAnnoucement.getStartDate());
            Date endDate = formatter.parse(createAnnoucement.getEndDate());

            Module module = em.find(Module.class, moduleId);
            User user = em.find(User.class, userId);
            
            if(startDate.before(createdDate) && endDate.before(createdDate) && endDate.before(startDate)) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Invalid date")).build();
            }

            Annoucement annoucement = new Annoucement();
            annoucement.setTitle(createAnnoucement.getTitle());
            annoucement.setContent(createAnnoucement.getContent());
            annoucement.setCreatedDate(createdDate);
            annoucement.setLastUpdatedDate(createdDate);
            annoucement.setStartDate(startDate);
            annoucement.setEndDate(endDate);
            //annoucement.setPublish(createAnnoucement.getPublish());
            annoucement.setEmailNotification(createAnnoucement.getEmailNotification());
            //annoucement.setModule(createAnnoucement.getModule());
            annoucement.setModule(module);
            annoucement.setOwner(user);
            em.persist(annoucement);
            em.flush();
            module.getAnnoucementList().add(annoucement);

            Module moduleCopy = new Module(module.getModuleId(), module.getCode(), module.getTitle(), module.getDescription(),
                    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                    module.isHasExam(), module.getExamTime(), module.getExamVenue(), module.getLectureDetails(), module.getDepartment(),
                    module.getFaculty());
            Annoucement annoucementCopy = new Annoucement(annoucement.getAnnoucementId(), annoucement.getTitle(),
                    annoucement.getContent(), createdDate, createdDate, startDate, endDate,
                    null, annoucement.getEmailNotification(), moduleCopy, null);

            if (createAnnoucement.getEmailNotification() == true) {
                List<String> address = new ArrayList<>();
                Query query = em.createQuery("select u from User u");
                List<User> userList = query.getResultList();
                if (userList == null || userList.isEmpty()) {
                    return Response.status(Response.Status.NOT_FOUND).entity("No user found").build();
                }
                for (User u : userList) {
                    if ((module.getStudentList().contains(u) || module.getAssignedTeacher().getUserId() == userId) && !u.getAccessRight().equals(AccessRightEnum.Admin)) {
                        address.add(u.getEmail());
                    }
                }
                sendMail.send(user.getEmail(), user.getPassword(), address, annoucementCopy.getTitle(), annoucementCopy.getContent());
            }

            //sendMail.send("ad1234567min@gmail.com", "password!234%", "ykwvix@gmail.com", annoucementCopy.getTitle(), annoucementCopy.getContent());
            return Response.status(Response.Status.OK).entity(annoucementCopy).build();
            //EmailSessionBean.sendEmail(annoucement.getOwner().getEmail(), annoucement.getTitle(), annoucement.getOwner().getUsername());
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @POST
    @Path(value = "createSystemAnnoucement")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createSystemAnnoucement(CreateAnnoucement createAnnoucement, @QueryParam("userId") Long userId) {
        try {
            Date createdDate = new Date();
            //Date createdDate = formatter.parse(createAnnoucement.getCreatedDate());
            //Date lastUpdatedDate = formatter.parse(createAnnoucement.getLastUpdatedDate());
            Date startDate = formatter.parse(createAnnoucement.getStartDate());
            Date endDate = formatter.parse(createAnnoucement.getEndDate());

            User user = em.find(User.class, userId);
            
            if(startDate.before(createdDate) && endDate.before(createdDate) && endDate.before(startDate)) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Invalid date")).build();
            }

            Annoucement annoucement = new Annoucement();
            annoucement.setTitle(createAnnoucement.getTitle());
            annoucement.setContent(createAnnoucement.getContent());
            annoucement.setCreatedDate(createdDate);
            annoucement.setLastUpdatedDate(createdDate);
            annoucement.setStartDate(startDate);
            annoucement.setEndDate(endDate);
            //annoucement.setPublish(createAnnoucement.getPublish());
            annoucement.setEmailNotification(createAnnoucement.getEmailNotification());
            //annoucement.setModule(createAnnoucement.getModule());
            //annoucement.setModule(module);
            annoucement.setOwner(user);
            em.persist(annoucement);
            em.flush();
            Annoucement annoucementCopy = new Annoucement(annoucement.getAnnoucementId(), annoucement.getTitle(),
                    annoucement.getContent(), createdDate, createdDate, startDate, endDate,
                    null, annoucement.getEmailNotification(), null, null);

            if (createAnnoucement.getEmailNotification() == true) {
                List<String> address = new ArrayList<>();
                Query query = em.createQuery("select u from User u");
                List<User> userList = query.getResultList();
                if (userList == null || userList.isEmpty()) {
                    return Response.status(Response.Status.NOT_FOUND).entity("No user found").build();
                }
                for (User u : userList) {
                    if (u.getAccessRight() != Public) {
                        address.add(u.getEmail());
                    }
                }
                sendMail.send(user.getEmail(), user.getPassword(), address, annoucementCopy.getTitle(), annoucementCopy.getContent());
            }
            return Response.status(Response.Status.OK).entity(annoucementCopy).build();
            //EmailSessionBean.sendEmail(annoucement.getOwner().getEmail(), annoucement.getTitle(), annoucement.getOwner().getUsername());
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAllAnnoucement")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAnnoucement() {
        try {
            Query query = em.createQuery("select a from Annoucement a");
            List<Annoucement> annoucementList = query.getResultList();
            if (annoucementList == null || annoucementList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No annoucement found").build();
            }
            GetAnnoucementRsp rsp = new GetAnnoucementRsp(new ArrayList<>());
            for (Annoucement a : annoucementList) {
                Module module = a.getModule();
                if (module != null) {
                    Module moduleCopy = new Module(module.getModuleId(), module.getCode(), module.getTitle(), module.getDescription(),
                            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                            module.isHasExam(), module.getExamTime(), module.getExamVenue(), module.getLectureDetails(), module.getDepartment(),
                            module.getFaculty());
                    rsp.getAnnoucementList().add(
                            new Annoucement(a.getAnnoucementId(), a.getTitle(),
                                    a.getContent(), a.getCreatedDate(), a.getLastUpdatedDate(),
                                    a.getStartDate(), a.getEndDate(), a.getPublish(), a.getEmailNotification(),
                                    moduleCopy, null));
                }
                rsp.getAnnoucementList().add(
                        new Annoucement(a.getAnnoucementId(), a.getTitle(),
                                a.getContent(), a.getCreatedDate(), a.getLastUpdatedDate(),
                                a.getStartDate(), a.getEndDate(), a.getPublish(), a.getEmailNotification(),
                                null, null));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAnnoucement")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnnoucement(@QueryParam("annoucementId") Long annoucementId) {
        try {
            Annoucement annoucement = em.find(Annoucement.class, annoucementId);
            if (annoucement == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No annoucement found").build();
            }
            Module module = annoucement.getModule();
            if (module != null) {
                Module moduleCopy = new Module(module.getModuleId(), module.getCode(), module.getTitle(), module.getDescription(),
                        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                        module.isHasExam(), module.getExamTime(), module.getExamVenue(), module.getLectureDetails(), module.getDepartment(),
                        module.getFaculty());
                Annoucement annoucementCopy = new Annoucement(annoucement.getAnnoucementId(), annoucement.getTitle(),
                        annoucement.getContent(), annoucement.getCreatedDate(), annoucement.getLastUpdatedDate(),
                        annoucement.getStartDate(), annoucement.getEndDate(), annoucement.getPublish(),
                        annoucement.getEmailNotification(),
                        moduleCopy, null);
                return Response.status(Response.Status.OK).entity(annoucementCopy).build();
            }
            Annoucement annoucementCopy = new Annoucement(annoucement.getAnnoucementId(), annoucement.getTitle(),
                    annoucement.getContent(), annoucement.getCreatedDate(), annoucement.getLastUpdatedDate(),
                    annoucement.getStartDate(), annoucement.getEndDate(), annoucement.getPublish(),
                    annoucement.getEmailNotification(),
                    null, null);
            return Response.status(Response.Status.OK).entity(annoucementCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAllActiveAnnoucements")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllActiveAnnoucements(@QueryParam("moduleId") Long moduleId) {
        try {
            Module module = em.find(Module.class, moduleId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }
            GetAnnoucementRsp rsp = new GetAnnoucementRsp(new ArrayList<>());
            List<Annoucement> annoucementList = module.getAnnoucementList();
            if (annoucementList == null || annoucementList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not have annoucement").build();
            }
            Module moduleCopy = new Module(module.getModuleId(), module.getCode(), module.getTitle(), module.getDescription(),
                    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                    module.isHasExam(), module.getExamTime(), module.getExamVenue(), module.getLectureDetails(), module.getDepartment(),
                    module.getFaculty());
            Date now = new Date();
            for (Annoucement a : annoucementList) {
                if (a.getStartDate().before(now) && a.getEndDate().after(now)) {
                    rsp.getAnnoucementList().add(
                            new Annoucement(a.getAnnoucementId(), a.getTitle(),
                                    a.getContent(), a.getCreatedDate(), a.getLastUpdatedDate(),
                                    a.getStartDate(), a.getEndDate(), a.getPublish(),
                                    a.getEmailNotification(),
                                    moduleCopy, null));
                }
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAllActiveSystemAnnoucement")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllActiveSystemAnnoucement() {
        try {
            Query query = em.createQuery("select a from Annoucement a");
            List<Annoucement> annoucementList = query.getResultList();
            if (annoucementList == null || annoucementList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No annoucement found").build();
            }
            GetAnnoucementRsp rsp = new GetAnnoucementRsp(new ArrayList<>());
            Date now = new Date();
            for (Annoucement a : annoucementList) {
                if (a.getStartDate().before(now) && a.getEndDate().after(now)) {
                    rsp.getAnnoucementList().add(
                            new Annoucement(a.getAnnoucementId(), a.getTitle(),
                                    a.getContent(), a.getCreatedDate(), a.getLastUpdatedDate(),
                                    a.getStartDate(), a.getEndDate(), a.getPublish(), a.getEmailNotification(),
                                    null, null));
                }
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAllExpiredAnnoucements")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllExpiredAnnoucements(@QueryParam("moduleId") Long moduleId) {
        try {
            Module module = em.find(Module.class, moduleId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }
            GetAnnoucementRsp rsp = new GetAnnoucementRsp(new ArrayList<>());
            List<Annoucement> annoucementList = module.getAnnoucementList();
            if (annoucementList == null || annoucementList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not have annoucement").build();
            }
            Module moduleCopy = new Module(module.getModuleId(), module.getCode(), module.getTitle(), module.getDescription(),
                    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                    module.isHasExam(), module.getExamTime(), module.getExamVenue(), module.getLectureDetails(), module.getDepartment(),
                    module.getFaculty());
            Date now = new Date();
            for (Annoucement a : annoucementList) {
                if (a.getEndDate().before(now)) {
                    rsp.getAnnoucementList().add(
                            new Annoucement(a.getAnnoucementId(), a.getTitle(),
                                    a.getContent(), a.getCreatedDate(), a.getLastUpdatedDate(),
                                    a.getStartDate(), a.getEndDate(), a.getPublish(),
                                    a.getEmailNotification(),
                                    moduleCopy, null));
                }
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAllExpiredSystemAnnoucement")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllExpiredSystemAnnoucement() {
        try {
            Query query = em.createQuery("select a from Annoucement a");
            List<Annoucement> annoucementList = query.getResultList();
            if (annoucementList == null || annoucementList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No annoucement found").build();
            }
            GetAnnoucementRsp rsp = new GetAnnoucementRsp(new ArrayList<>());
            Date now = new Date();
            for (Annoucement a : annoucementList) {
                if (a.getEndDate().before(now)) {
                    rsp.getAnnoucementList().add(
                            new Annoucement(a.getAnnoucementId(), a.getTitle(),
                                    a.getContent(), a.getCreatedDate(), a.getLastUpdatedDate(),
                                    a.getStartDate(), a.getEndDate(), a.getPublish(), a.getEmailNotification(),
                                    null, null));
                }
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAllUpcomingAnnoucements")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUpcomingAnnoucements(@QueryParam("moduleId") Long moduleId) {
        try {
            Module module = em.find(Module.class, moduleId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }
            GetAnnoucementRsp rsp = new GetAnnoucementRsp(new ArrayList<>());
            List<Annoucement> annoucementList = module.getAnnoucementList();
            if (annoucementList == null || annoucementList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not have annoucement").build();
            }
            Module moduleCopy = new Module(module.getModuleId(), module.getCode(), module.getTitle(), module.getDescription(),
                    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                    module.isHasExam(), module.getExamTime(), module.getExamVenue(), module.getLectureDetails(), module.getDepartment(),
                    module.getFaculty());
            Date now = new Date();
            for (Annoucement a : annoucementList) {
                if (a.getStartDate().after(now)) {
                    rsp.getAnnoucementList().add(
                            new Annoucement(a.getAnnoucementId(), a.getTitle(),
                                    a.getContent(), a.getCreatedDate(), a.getLastUpdatedDate(),
                                    a.getStartDate(), a.getEndDate(), a.getPublish(),
                                    a.getEmailNotification(),
                                    moduleCopy, null));
                }
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAllUpcomingSystemAnnoucement")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUpcomingSystemAnnoucement() {
        try {
            Query query = em.createQuery("select a from Annoucement a");
            List<Annoucement> annoucementList = query.getResultList();
            if (annoucementList == null || annoucementList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No annoucement found").build();
            }
            GetAnnoucementRsp rsp = new GetAnnoucementRsp(new ArrayList<>());
            Date now = new Date();
            for (Annoucement a : annoucementList) {
                if (a.getStartDate().after(now)) {
                    rsp.getAnnoucementList().add(
                            new Annoucement(a.getAnnoucementId(), a.getTitle(),
                                    a.getContent(), a.getCreatedDate(), a.getLastUpdatedDate(),
                                    a.getStartDate(), a.getEndDate(), a.getPublish(), a.getEmailNotification(),
                                    null, null));
                }
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @PUT
    @Path(value = "updateSystemAnnoucement")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateSystemAnnoucement(UpdateAnnoucement updateAnnoucement, @QueryParam("annoucementId") Long annoucementId, @QueryParam("userId") Long userId) {
        try {
            Annoucement annoucement = em.find(Annoucement.class, annoucementId);
            if (annoucement == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No annoucement found").build();
            }
            //Date createdDate = formatter.parse(updateAnnoucement.getCreatedDate());
            Date updateDate = new Date();
            //String lastUpdatedDate = formatter.format(date);
            Date startDate = formatter.parse(updateAnnoucement.getStartDate());
            Date endDate = formatter.parse(updateAnnoucement.getEndDate());
            
            if(startDate.before(updateDate) && endDate.before(updateDate) && endDate.before(startDate)) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Invalid date")).build();
            }

            User user = em.find(User.class, userId);

            annoucement.setTitle(updateAnnoucement.getTitle());
            annoucement.setContent(updateAnnoucement.getContent());
            //annoucement.setCreatedDate(createdDate);
            annoucement.setLastUpdatedDate(updateDate);
            annoucement.setStartDate(startDate);
            annoucement.setEndDate(endDate);
            annoucement.setPublish(updateAnnoucement.getPublish());
            annoucement.setEmailNotification(updateAnnoucement.getEmailNotification());
            //annoucement.setModule(updateAnnoucement.getModule());
            annoucement.setOwner(user);
            em.merge(annoucement);
            em.flush();
            Annoucement annoucementCopy = new Annoucement(annoucement.getAnnoucementId(), annoucement.getTitle(),
                    annoucement.getContent(), annoucement.getCreatedDate(), updateDate, startDate, endDate,
                    annoucement.getPublish(), annoucement.getEmailNotification(), null, null);

            if (updateAnnoucement.getEmailNotification() == true) {
                List<String> address = new ArrayList<>();
                Query query = em.createQuery("select u from User u");
                List<User> userList = query.getResultList();
                if (userList == null || userList.isEmpty()) {
                    return Response.status(Response.Status.NOT_FOUND).entity("No user found").build();
                }
                for (User u : userList) {
                    if (u.getAccessRight() != Public) {
                        address.add(u.getEmail());
                    }
                }
                sendMail.send(user.getEmail(), user.getPassword(), address, annoucementCopy.getTitle(), annoucementCopy.getContent());
            }
            return Response.status(Response.Status.OK).entity(annoucementCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @PUT
    @Path(value = "updateModuleAnnoucement")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateModuleAnnoucement(UpdateAnnoucement updateAnnoucement, @QueryParam("moduleId") Long moduleId, @QueryParam("annoucementId") Long annoucementId, @QueryParam("userId") Long userId) {
        try {
            Annoucement annoucement = em.find(Annoucement.class, annoucementId);
            if (annoucement == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No annoucement found").build();
            }

            Date updateDate = new Date();
            //Date lastUpdatedDate = formatter.parse(updateAnnoucement.getLastUpdatedDate());
            Date startDate = formatter.parse(updateAnnoucement.getStartDate());
            Date endDate = formatter.parse(updateAnnoucement.getEndDate());
            
            if(startDate.before(updateDate) && endDate.before(updateDate) && endDate.before(startDate)) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Invalid date")).build();
            }

            Module module = em.find(Module.class, moduleId);
            User user = em.find(User.class, userId);

            annoucement.setTitle(updateAnnoucement.getTitle());
            annoucement.setContent(updateAnnoucement.getContent());
            //annoucement.setCreatedDate(createdDate);
            annoucement.setLastUpdatedDate(updateDate);
            annoucement.setStartDate(startDate);
            annoucement.setEndDate(endDate);
            annoucement.setPublish(updateAnnoucement.getPublish());
            annoucement.setEmailNotification(updateAnnoucement.getEmailNotification());
            annoucement.setModule(module);
            annoucement.setOwner(updateAnnoucement.getOwner());
            em.merge(annoucement);
            em.flush();

            Module moduleCopy = new Module(module.getModuleId(), module.getCode(), module.getTitle(), module.getDescription(),
                    null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                    module.isHasExam(), module.getExamTime(), module.getExamVenue(), module.getLectureDetails(), module.getDepartment(),
                    module.getFaculty());
            Annoucement annoucementCopy = new Annoucement(annoucement.getAnnoucementId(), annoucement.getTitle(),
                    annoucement.getContent(), annoucement.getCreatedDate(), updateDate, startDate, endDate,
                    annoucement.getPublish(), annoucement.getEmailNotification(), moduleCopy, null);

            if (updateAnnoucement.getEmailNotification() == true) {
                List<String> address = new ArrayList<>();
                Query query = em.createQuery("select u from User u");
                List<User> userList = query.getResultList();
                if (userList == null || userList.isEmpty()) {
                    return Response.status(Response.Status.NOT_FOUND).entity("No user found").build();
                }
                for (User u : userList) {
                    if ((module.getStudentList().contains(u) || module.getAssignedTeacher().getUserId() == userId) && !u.getAccessRight().equals(AccessRightEnum.Admin)) {
                        address.add(u.getEmail());
                    }
                }
                sendMail.send(user.getEmail(), user.getPassword(), address, annoucementCopy.getTitle(), annoucementCopy.getContent());
            }
            return Response.status(Response.Status.OK).entity(annoucementCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @Path(value = "deleteAnnoucement")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAnnoucement(@QueryParam("annoucementId") Long annoucementId) {
        try {
            Annoucement annoucement = em.find(Annoucement.class, annoucementId);
            if (annoucement == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No annoucement found").build();
            }
            //annoucement.getModule().getAnnoucementList().remove(annoucement);
            if (annoucement.getModule() != null) {
                Module module = em.find(Module.class, annoucement.getModule().getModuleId());
                module.getAnnoucementList().remove(annoucement);
                em.merge(module);
                annoucement.setModule(null);
            }
            
            annoucement.setOwner(null);
            em.remove(annoucement);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }
}
