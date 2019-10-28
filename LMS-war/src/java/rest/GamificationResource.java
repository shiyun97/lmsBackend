/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.CreateBadge;
import datamodel.rest.CreateCertification;
import datamodel.rest.ErrorRsp;
import datamodel.rest.GetBadgeRsp;
import datamodel.rest.GetCertificationRsp;
import datamodel.rest.UpdateBadge;
import datamodel.rest.UpdateCertification;
import entities.Badge;
import entities.Certification;
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
@Path("Gamification")
public class GamificationResource {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public GamificationResource() {
    }

    @POST
    @Path(value = "createBadge")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBadge(CreateBadge createBadge) {
        try {
            Badge badge = new Badge();
            badge.setTitle(createBadge.getTitle());
            badge.setDescription(createBadge.getDescription());
            badge.setCriteria(createBadge.getCriteria());
            em.persist(badge);
            em.flush();
            Badge badgeCopy = new Badge(badge.getId(), badge.getTitle(),
                    badge.getDescription(), badge.getCriteria(), null, null);
            return Response.status(Response.Status.OK).entity(badgeCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @POST
    @Path(value = "createCertification")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCertification(CreateCertification createCertification) {
        try {
            Certification certification = new Certification();
            certification.setTitle(createCertification.getTitle());
            certification.setDescription(createCertification.getDescription());
            certification.setCriteria(createCertification.getCriteria());
            em.persist(certification);
            em.flush();
            Certification certificationCopy = new Certification(certification.getId(), certification.getTitle(),
                    certification.getDescription(), certification.getCriteria(), null, null);
            return Response.status(Response.Status.OK).entity(certificationCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAllBadges")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBadges() {
        try {
            Query query = em.createQuery("select b from Badge b");
            List<Badge> badgeList = query.getResultList();
            if (badgeList == null || badgeList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No badge found")).build();
            }
            GetBadgeRsp rsp = new GetBadgeRsp(new ArrayList<>());
            for (Badge b : badgeList) {
                rsp.getBadgeList().add(new Badge(
                        b.getId(), b.getTitle(), b.getDescription(), b.getCriteria(), null, null));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAllCertifications")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllCertifications() {
        try {
            Query query = em.createQuery("select c from Certification c");
            List<Certification> certificationList = query.getResultList();
            if (certificationList == null || certificationList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No certification found")).build();
            }
            GetCertificationRsp rsp = new GetCertificationRsp(new ArrayList<>());
            for (Certification c : certificationList) {
                rsp.getCertificationList().add(new Certification(
                        c.getId(), c.getTitle(), c.getDescription(), c.getCriteria(), null, null));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @PUT
    @Path("updateBadge")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateBadge(UpdateBadge updateBadge, @QueryParam("badgeId") Long badgeId) {
        try {
            Badge badge = em.find(Badge.class, badgeId);
            if (badge == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No badge found")).build();
            }
            badge.setTitle(updateBadge.getTitle());
            badge.setDescription(updateBadge.getDescription());
            badge.setCriteria(updateBadge.getCriteria());
            em.merge(badge);
            em.flush();
            Badge badgeCopy = new Badge(badge.getId(), badge.getTitle(), badge.getDescription(), badge.getCriteria(), null, null);
            return Response.status(Response.Status.OK).entity(badgeCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @PUT
    @Path("updateCertification")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateCertification(UpdateCertification updateCertification, @QueryParam("certificationId") Long certificationId) {
        try {
            Certification certification = em.find(Certification.class, certificationId);
            if (certification == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No certification found")).build();
            }
            certification.setTitle(updateCertification.getTitle());
            certification.setDescription(updateCertification.getDescription());
            certification.setCriteria(updateCertification.getCriteria());
            em.merge(certification);
            em.flush();
            Certification certificationCopy = new Certification(certification.getId(), certification.getTitle(), certification.getDescription(),
                    certification.getCriteria(), null, null);
            return Response.status(Response.Status.OK).entity(certificationCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @Path(value = "deleteBadge")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBadge(@QueryParam("badgeId") Long badgeId) {
        try {
            Badge badge = em.find(Badge.class, badgeId);
            if (badge == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No badge found")).build();
            }
            List<User> userList = badge.getUserList();
            if (userList != null || !userList.isEmpty()) {
                for(User u : userList){
                    if(u.getBadgeList() != null || !u.getBadgeList().isEmpty()){
                        u.getBadgeList().remove(badge);
                        badge.getUserList().remove(u);
                    }
                }
            }
            em.remove(badge);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @Path(value = "deleteCertification")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteCertification(@QueryParam("certificationId") Long certificationId) {
        try {
            Certification certification = em.find(Certification.class, certificationId);
            if (certification == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No certification found")).build();
            }
            List<User> userList = certification.getUserList();
            if (userList != null || !userList.isEmpty()) {
                for(User u : userList){
                    if(u.getCertificationList()!= null || !u.getCertificationList().isEmpty()){
                        u.getCertificationList().remove(certification);
                        certification.getUserList().remove(u);
                    }
                }
            }
            em.remove(certification);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }
    
    @PUT
    @Path("getBadge")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBadge(@QueryParam("userId") Long userId, @QueryParam("badgeId") Long badgeId) {
        try {
            User user = em.find(User.class, userId);
             if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No user found")).build();
            }
            Badge badge = em.find(Badge.class, badgeId);
            if (badge == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No badge found")).build();
            }
            List<Badge> badgeList = user.getBadgeList();
            if (badgeList == null || badgeList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No badge found")).build();
            }
            if(user.getQuizCompleted() == 5){
                user.getBadgeList().add(badge);
                badge.getUserList().add(user);
            }
            if(user.getCoursepackCompleted() == 5){
                user.getBadgeList().add(badge);
                badge.getUserList().add(user);
            }
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }
    
    @PUT
    @Path("getCertification")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCertification(@QueryParam("userId") Long userId, @QueryParam("certificationId") Long certificationId) {
        try {
            User user = em.find(User.class, userId);
             if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No user found")).build();
            }
            Certification certification = em.find(Certification.class, certificationId);
            if (certification == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No certification found")).build();
            }
            List<Certification> certificationList = user.getCertificationList();
            if (certificationList == null || certificationList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No certification found")).build();
            }
            for(Certification c : certificationList){
                if(c.getId().equals(0) && c.getId().equals(1)){
                    user.getCertificationList().add(c);
                    c.getUserList().add(user);
                }
            }
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }
}
