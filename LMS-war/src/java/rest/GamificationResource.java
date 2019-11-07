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
import entities.Coursepack;
import entities.Folder;
import entities.LessonOrder;
import entities.Outlines;
import entities.User;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

/**
 *
 * @author Vixson
 */
@Stateless
@Path("Gamification")
public class GamificationResource {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;

    @Context
    private UriInfo context;
    @Context
    private ServletContext servletContext;

    SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");

    public GamificationResource() {
    }
//CERTIFICATION METHODS

    @POST
    @Path(value = "createCertification")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCertification(CreateCertification createCertification) {
        try {
            Certification certification = new Certification();
            certification.setTitle(createCertification.getTitle());
            certification.setDescription(createCertification.getDescription());
            em.persist(certification);
            em.flush();
            Certification certificationCopy = new Certification(certification.getId(), certification.getTitle(),
                    certification.getDescription(), null, null);
            return Response.status(Response.Status.OK).entity(certificationCopy).build();
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
                List<Coursepack> coursepackList = c.getCoursepackList();
                List<Coursepack> coursepackListCopy = new ArrayList<>();
                if (coursepackList != null || !coursepackList.isEmpty()) {
                    for (Coursepack cp : coursepackList) {
                        coursepackListCopy.add((new Coursepack(cp.getCoursepackId(), cp.getCode(), cp.getTitle(),
                                cp.getDescription(), null, null, null, null, null, null,
                                null, null, null, null, null, null, null, null)));
                    }
                }
                rsp.getCertificationList().add(new Certification(
                        c.getId(), c.getTitle(), c.getDescription(), null, coursepackListCopy));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getCertification")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getCertification(@QueryParam("certificationId") Long certificationId) {
        try {
            Certification certification = em.find(Certification.class, certificationId);
            if (certification == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No certification found")).build();
            }
            List<Coursepack> coursepackList = certification.getCoursepackList();
            List<Coursepack> coursepackListCopy = new ArrayList<>();
            if (coursepackList != null || !coursepackList.isEmpty()) {
                for (Coursepack cp : coursepackList) {
                    coursepackListCopy.add((new Coursepack(cp.getCoursepackId(), cp.getCode(), cp.getTitle(),
                            cp.getDescription(), null, null, null, null, null, null,
                            null, null, null, null, null, null, null, null)));
                }
            }
            Certification certificationCopy = new Certification(certification.getId(), certification.getTitle(),
                    certification.getDescription(), null, coursepackListCopy);
            return Response.status(Response.Status.OK).entity(certificationCopy).build();
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
            em.merge(certification);
            em.flush();
            Certification certificationCopy = new Certification(certification.getId(), certification.getTitle(), certification.getDescription(),
                    null, null);
            return Response.status(Response.Status.OK).entity(certificationCopy).build();
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
            /*List<Coursepack> coursepackList = certification.getCoursepackList();
            if (coursepackList == null || coursepackList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No coursepacks assigned")).build();
            }
            for (Coursepack c : coursepackList) {
                certification.getCoursepackList().remove(c);
            }*/
            em.remove(certification);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @PUT
    @Path("assignCoursepackToCert")
    @Produces(MediaType.APPLICATION_JSON)
    public Response assignCoursepackToCert(@QueryParam("certificationId") Long certificationId, @QueryParam("coursepackId") Long coursepackId) {
        try {
            Certification certification = em.find(Certification.class, certificationId);
            if (certification == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No certification found")).build();
            }
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);
            if (coursepack == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Coursepack does not exist").build();
            }
            certification.getCoursepackList().add(coursepack);
            List<Coursepack> coursepackList = certification.getCoursepackList();
            List<Coursepack> coursepackListCopy = new ArrayList<>();
            if (coursepackList != null || !coursepackList.isEmpty()) {
                for (Coursepack c : coursepackList) {
                    coursepackListCopy.add((new Coursepack(c.getCoursepackId(), c.getCode(), c.getTitle(),
                            c.getDescription(), null, null, null, null, null, null,
                            null, null, null, null, null, null, null, null)));
                }
            }
            Certification certificationCopy = new Certification(certification.getId(), certification.getTitle(), certification.getDescription(),
                    null, coursepackListCopy);
            return Response.status(Response.Status.OK).entity(certificationCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @PUT
    @Path("removeCoursepackFromCert")
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeCoursepackFromCert(@QueryParam("certificationId") Long certificationId, @QueryParam("coursepackId") Long coursepackId) {
        try {
            Certification certification = em.find(Certification.class, certificationId);
            if (certification == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No certification found")).build();
            }
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);
            if (coursepack == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Coursepack does not exist").build();
            }
            certification.getCoursepackList().remove(coursepack);
            List<Coursepack> coursepackList = certification.getCoursepackList();
            List<Coursepack> coursepackListCopy = new ArrayList<>();
            if (coursepackList != null || !coursepackList.isEmpty()) {
                for (Coursepack c : coursepackList) {
                    coursepackListCopy.add((new Coursepack(c.getCoursepackId(), c.getCode(), c.getTitle(),
                            c.getDescription(), null, null, null, null, null, null,
                            null, null, null, null, null, null, null, null)));
                }
            }
            Certification certificationCopy = new Certification(certification.getId(), certification.getTitle(), certification.getDescription(),
                    null, coursepackListCopy);
            return Response.status(Response.Status.OK).entity(certificationCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    /*@PUT
    @Path("attainCertification")
    @Produces(MediaType.APPLICATION_JSON)
    public Response attainCertification(@QueryParam("userId") Long userId, @QueryParam("certificationId") Long certificationId) {
        try {
            Date dateNow = new Date();
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No user found")).build();
            }
            Certification certification = em.find(Certification.class, certificationId);
            if (certification == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No certification found")).build();
            }
            List<Coursepack> userCoursepackList = user.getPublicUserCompletedCoursepackList();
            if (userCoursepackList == null || userCoursepackList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No coursepacks completed")).build();
            }
            List<Coursepack> certCoursepackList = certification.getCoursepackList();
            if (certCoursepackList == null || certCoursepackList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Certification does not include this coursepack")).build();
            }
            for (Coursepack ucp : userCoursepackList) {
                for (Coursepack ccp : certCoursepackList) {
                    if (!ucp.equals(ccp)) {
                        return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ErrorRsp("No matching coursepack")).build();
                    }
                    certification.setDateAchieved(dateNow);
                    user.getCertificationList().add(certification);
                }
            }
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }*/

    @GET
    @Path("getAllUserCertifications")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUserCertifications(@QueryParam("userId") Long userId) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No user found")).build();
            }
            List<Certification> certificationList = user.getCertificationList();
            if (certificationList == null || certificationList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No certification found")).build();
            }
            GetCertificationRsp rsp = new GetCertificationRsp(new ArrayList<>());
            for (Certification c : certificationList) {
                List<Coursepack> coursepackList = c.getCoursepackList();
                List<Coursepack> coursepackListCopy = new ArrayList<>();
                if (coursepackList != null || !coursepackList.isEmpty()) {
                    for (Coursepack cp : coursepackList) {
                        coursepackListCopy.add((new Coursepack(cp.getCoursepackId(), cp.getCode(), cp.getTitle(),
                                cp.getDescription(), null, null, null, null, null, null,
                                null, null, null, null, null, null, null, null)));
                    }
                }
                rsp.getCertificationList().add(new Certification(
                        c.getId(), c.getTitle(), c.getDescription(), c.getDateAchieved(), coursepackListCopy));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getUserCertification")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserCertification(@QueryParam("userId") Long userId, @QueryParam("certificationId") Long certificationId) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No user found")).build();
            }
            Certification certification = em.find(Certification.class, certificationId);
            if (certification == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No certification found")).build();
            }
            List<Coursepack> coursepackList = certification.getCoursepackList();
            List<Coursepack> coursepackListCopy = new ArrayList<>();
            if (coursepackList != null || !coursepackList.isEmpty()) {
                for (Coursepack cp : coursepackList) {
                    coursepackListCopy.add((new Coursepack(cp.getCoursepackId(), cp.getCode(), cp.getTitle(),
                            cp.getDescription(), null, null, null, null, null, null,
                            null, null, null, null, null, null, null, null)));
                }
            }
            Certification certificationcopy = new Certification(certification.getId(), certification.getTitle(),
                    certification.getDescription(), certification.getDateAchieved(), coursepackListCopy);
            return Response.status(Response.Status.OK).entity(certificationcopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

//BADGE METHODS
    @POST
    @Path("uploadBadge")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response uploadBadge(@FormDataParam("file") InputStream uploadedFileInputStream,
            @FormDataParam("file") FormDataContentDisposition uploadedFileDetails) {
        /*if (type.equals("document") && folderId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Require folderId!")).build();
        }*/
        try {
            System.err.println("********** FileResource.upload()");
            String outputFilePath = servletContext.getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + uploadedFileDetails.getFileName();
            File file = new File(outputFilePath);
            FileOutputStream fileOutputStream = new FileOutputStream(file);

            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            while (true) {
                a = uploadedFileInputStream.read(buffer);

                if (a < 0) {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            uploadedFileInputStream.close();

            // create new badge entity
            entities.Badge newFile = new entities.Badge();
            newFile.setTitle(uploadedFileDetails.getFileName());
            newFile.setLocation(outputFilePath);
            Timestamp timestamp = new Timestamp(System.currentTimeMillis());
            newFile.setCreatedDt(timestamp);

            em.persist(newFile);
            em.flush();

            return Response.status(Response.Status.OK).entity(outputFilePath).build();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp("Badge not found error")).build();
        } catch (IOException ex) {
            ex.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp("file IO exception error")).build();
        }
    }

    @POST
    @Path("uploadMultipleBadges")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response uploadMultipleBadges(@FormDataParam("file") FormDataBodyPart body,
            @QueryParam("folderId") Long folderId) {
        /*if (type.equals("document") && folderId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Require folderId!")).build();
        }*/
        try {
            System.out.println(body);
            for (BodyPart part : body.getParent().getBodyParts()) {
                InputStream inputStream = part.getEntityAs(InputStream.class);
                ContentDisposition meta = part.getContentDisposition();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String outputFilePath = servletContext.getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + timestamp.getTime() + "_" + meta.getFileName();
                // do upload
                java.io.File file = new java.io.File(outputFilePath);
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                int a;
                int BUFFER_SIZE = 8192;
                byte[] buffer = new byte[BUFFER_SIZE];

                while (true) {
                    a = inputStream.read(buffer);

                    if (a < 0) {
                        break;
                    }

                    fileOutputStream.write(buffer, 0, a);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
                inputStream.close();

                // create new badge entity
                entities.Badge newFile = new entities.Badge();
                newFile.setTitle(meta.getFileName());
                newFile.setLocation(outputFilePath);
                newFile.setCreatedDt(timestamp);

                em.persist(newFile);
                em.flush();

            }
            return Response.status(Response.Status.OK).entity("ok").build();
        } catch (FileNotFoundException ex) {
            ex.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp("Badge not found error")).build();
        } catch (IOException ex) {
            ex.printStackTrace();

            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp("File IO exception error")).build();
        }
    }

    @GET
    @Path("getAllBadges")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllBadges() {
        try {
            Query query = em.createQuery("select b from Badge b");
            List<entities.Badge> badgeList = query.getResultList();
            if (badgeList == null || badgeList.size() == 0) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No badge found")).build();
            }
            List<entities.Badge> rsp = new ArrayList<>();
            for (entities.Badge b : badgeList) {
                    rsp.add(new Badge(
                            b.getId(), b.getTitle(), b.getDescription(), b.getDateAchieved(),
                            b.getLocation(), b.getCreatedDt()));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAllUserBadges")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllUserBadges(@QueryParam("userId") Long userId) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No user found")).build();
            }
            List<entities.Badge> badgeList = user.getBadgeList();
            if (badgeList == null || badgeList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No badge found")).build();
            }
            List<entities.Badge> rsp = new ArrayList<>();
            for (entities.Badge b : badgeList) {
                    rsp.add(new Badge(
                            b.getId(), b.getTitle(), b.getDescription(), b.getDateAchieved(),
                            b.getLocation(), b.getCreatedDt()));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @Path(value = "deleteBadge")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteBadge(@QueryParam("badgeId") Long badgeId) {
        try {
            entities.Badge badge = em.find(entities.Badge.class, badgeId);
            if (badge == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Badge does not exist!")).build();
            }
            em.detach(badge);

            File actualFile = new File(badge.getLocation());
            actualFile.delete();
            em.merge(badge);
            em.flush();

            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @PUT
    @Path("attainCompleteFiveAssessmentBadge")
    @Produces(MediaType.APPLICATION_JSON)
    public Response attainCompleteFiveAssessmentBadge(@QueryParam("userId") Long userId, @QueryParam("badgeId") Long badgeId) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No user found")).build();
            }
            entities.Badge badge = em.find(entities.Badge.class, badgeId);
            if (badge == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No badge found")).build();
            }
            if (user.getQuizCompleted() >= 5) {
                user.getBadgeList().add(badge);
            }
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @PUT
    @Path("attainCompleteFirstCoursepackBadge")
    @Produces(MediaType.APPLICATION_JSON)
    public Response attainCompleteFirstCoursepackBadge(@QueryParam("userId") Long userId, @QueryParam("badgeId") Long badgeId) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No user found")).build();
            }
            entities.Badge badge = em.find(entities.Badge.class, badgeId);
            if (badge == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No badge found")).build();
            }
            if(user.getPublicUserCompletedCoursepackList().isEmpty() || user.getCoursepackCompleted() == 0 ||user.getPublicUserCompletedCoursepackList() == null){
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ErrorRsp("No coursepack completed")).build();
            }
            user.getBadgeList().add(badge);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    /*@POST
    @Path(value = "createBadge")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createBadge(CreateBadge createBadge
    ) {
        try {
            Badge badge = new Badge();
            badge.setTitle(createBadge.getTitle());
            badge.setDescription(createBadge.getDescription());
            em.persist(badge);
            em.flush();
            Badge badgeCopy = new Badge(badge.getId(), badge.getTitle(),
                    badge.getDescription(), null);
            return Response.status(Response.Status.OK).entity(badgeCopy).build();
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
                        b.getId(), b.getTitle(), b.getDescription(), b.getDateAchieved(), b.getType(),
                        b.getLocation(), b.getCreatedDt(), b.getIsDelete()));
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
    public Response updateBadge(UpdateBadge updateBadge,
            @QueryParam("badgeId") Long badgeId
    ) {
        try {
            Badge badge = em.find(Badge.class, badgeId);
            if (badge == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No badge found")).build();
            }
            badge.setTitle(updateBadge.getTitle());
            badge.setDescription(updateBadge.getDescription());
            em.merge(badge);
            em.flush();
            Badge badgeCopy = new Badge(badge.getId(), badge.getTitle(), badge.getDescription(), badge.getCriteria(), null, null);
            return Response.status(Response.Status.OK).entity(badgeCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @Path(value = "deleteBadge")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBadge(@QueryParam("badgeId") Long badgeId
    ) {
        try {
            Badge badge = em.find(Badge.class, badgeId);
            if (badge == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No badge found")).build();
            }
            List<User> userList = badge.getUserList();
            if (userList != null || !userList.isEmpty()) {
                for (User u : userList) {
                    if (u.getBadgeList() != null || !u.getBadgeList().isEmpty()) {
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

    @PUT
    @Path("attainBadge")
    @Produces(MediaType.APPLICATION_JSON)
    public Response attainBadge(@QueryParam("userId") Long userId,
            @QueryParam("badgeId") Long badgeId
    ) {
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
            if (user.getQuizCompleted() == 5) {
                user.getBadgeList().add(badge);
                badge.getUserList().add(user);
            }
            if (user.getCoursepackCompleted() == 5) {
                user.getBadgeList().add(badge);
                badge.getUserList().add(user);
            }
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }*/
}
