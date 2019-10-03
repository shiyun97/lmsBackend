package rest;

import datamodel.rest.ErrorRsp;
import datamodel.rest.RetrieveAllConsultationsForModule;
import entities.ConsultationTimeslot;
import entities.Module;
import entities.User;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Calendar;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.UpdateConsultationReq;

@Stateless
@Path("Consultation")
public class ConsultationResource {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;

    @Path("viewAllAvailableConsultation")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllAvailableConsultationTimeslot(@PathParam("moduleId") Long moduleId) {
        System.out.println("retrieveAllAvailableConsultationForModule");
        System.out.println(em);
        try {
            Module mod = em.find(Module.class, moduleId);
            if (mod == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exists!")).build();
            } else {
                Query query = em.createQuery("SELECT c FROM ConsultationTimeslot c WHERE c.module.moduleId = :moduleId AND c.booker =:booker AND c.startD >=:startD AND c.startTs >=:startTs");
                query.setParameter("moduleId", moduleId);
                query.setParameter("booker", null);
                query.setParameter("startD", LocalDate.now());
                query.setParameter("startTs", LocalTime.now());

                List<ConsultationTimeslot> consultations = (List<ConsultationTimeslot>) query.getResultList();

                if (consultations != null && !consultations.isEmpty()) {
                    return Response.status(Response.Status.OK).entity(consultations).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("No consultation for this module").build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("viewAllConsultationslot")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllConsultationTimeslot(@PathParam("moduleId") Long moduleId) {
        System.out.println("retrieveAllAvailableConsultationForModule");
        System.out.println(em);
        try {
            Module mod = em.find(Module.class, moduleId);
            if (mod == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exists!")).build();
            } else {

                List<ConsultationTimeslot> timeslots = mod.getConsultationList();

                if (timeslots != null && !timeslots.isEmpty()) {
                    return Response.status(Response.Status.OK).entity(new RetrieveAllConsultationsForModule(timeslots)).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("No consultation for this module").build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("dropConsultation")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    public Response dropConsultation(@QueryParam("consultationTimeslotId") Long consultationTimeslotId) {
        try {
            ConsultationTimeslot cs = em.find(ConsultationTimeslot.class, consultationTimeslotId);
            if (cs == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Consultation Not Exists!")).build();
            } else if (cs.getStartTs().compareTo(LocalTime.now()) <= 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("No valid Consultation Not Exists!")).build();
            } else {
                User user = cs.getBooker();
                user.getConsultationTimeslotList().remove(cs);
                cs.setBooker(null);
                return Response.status(Response.Status.OK).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("bookConsultation")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response bookConsultation(@QueryParam("consultationTimeslotId") Long consultationTimeslotId, @QueryParam("userId") Long userId) {
        try {
            ConsultationTimeslot cs = em.find(ConsultationTimeslot.class, consultationTimeslotId);
            User user = cs.getBooker();

            if (cs == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Consultation Not Exists!")).build();
            } else {
                Query q = em.createQuery("Select c FROM ConsultationTimeslot c WHERE c.booker.userId =:booker AND c.startD >=:curentD AND c.startTs>=:currentTs AND c.startD =:startD AND c.startTs=:startTs");
                q.setParameter("booker", user.getId());
                q.setParameter("currentD", LocalDate.now());
                q.setParameter("currentTs", LocalTime.now());
                q.setParameter("startD", cs.getStartD());
                q.setParameter("startTs", cs.getStartTs());

                if (!q.getResultList().isEmpty()) {
                    user.getConsultationTimeslotList().add(cs);
                    cs.setBooker(user);
                    return Response.status(Response.Status.OK).build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Timeslot not valid!")).build();

                }
            }

        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("updateConsultation")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateConsultation(UpdateConsultationReq consultationReq) {
        try {
            Module module = em.find(Module.class, consultationReq.getOldTimeslot().getModule().getModuleId());
            if (module == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Does Not Exists!")).build();
            }

            User user = em.find(User.class, consultationReq.getOldTimeslot().getBooker().getId());
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User Does Not Exists!")).build();
            }

            Query query = em.createQuery("Select cs FROM ConsultationTimeslot cs WHERE cs.consultationTsId = :consultationTsId");
            query.setParameter("consultationTsId", consultationReq.getOldTimeslot().getconsultationTsId());
            ConsultationTimeslot cs = (ConsultationTimeslot) query.getResultList();

            //find available timeslot 
            Query q = em.createQuery("Select cs FROM ConsultationTimeslot cs WHERE cs.module=:module AND cs.booker =:booker AND cs.startD =:startD AND cs.endD =:endD AND cs.startTs=:startTs AND cs.endTs=:endTs AND cs.booker =:booker AND cs.startD >=:currentD AND cs.startTs >=:currentTs");
            q.setParameter("startD", consultationReq.getNewTimeslot().getStartD());
            q.setParameter("endD", consultationReq.getNewTimeslot().getEndD());
            q.setParameter("moduleId", consultationReq.getNewTimeslot().getModule().getModuleId());
            q.setParameter("startTs", consultationReq.getNewTimeslot().getStartTs());
            q.setParameter("endTs", consultationReq.getNewTimeslot().getEndTs());
            q.setParameter("booker", null);
            query.setParameter("currentD", LocalDate.now());
            query.setParameter("currentTs", LocalTime.now());
            ConsultationTimeslot newSlot = (ConsultationTimeslot) q.getResultList();

            if (cs != null && newSlot != null) {
                newSlot.setBooker(user);
                cs.setBooker(null);
                user.getConsultationTimeslotList().remove(consultationReq.getOldTimeslot());
                user.getConsultationTimeslotList().add(consultationReq.getNewTimeslot());
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("There is no consultation to be updated").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
}
