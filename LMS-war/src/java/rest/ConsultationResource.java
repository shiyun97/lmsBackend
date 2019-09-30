package rest;

import datamodel.rest.ErrorRsp;
import entities.ConsultationTimeslot;
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
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Stateless
@Path("Consultation")
public class ConsultationResource {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;

    @Path("viewAllAvailableConsultation/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllAvailableConsultationTimeslot(@PathParam("moduleId") Long moduleId) {
        System.out.println("retrieveAllAvailableConsultationForModule");
        System.out.println(em);
        try {
            if (em.find(Module.class, moduleId) == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exists!")).build();
            }

            Query query = em.createQuery("SELECT c FROM ConsultationTimeslot c WHERE c.module = :moduleId AND c.booker =:booker");
            query.setParameter("moduleId", moduleId);
            query.setParameter("booker", null);

            List<ConsultationTimeslot> consultations = (List<ConsultationTimeslot>) query.getResultList();
            if (consultations != null && !consultations.isEmpty()) {
                return Response.status(Response.Status.OK).entity(consultations).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No consultation for this module").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("viewAllConsultationslot/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllConsultationTimeslot(@PathParam("moduleId") Long moduleId) {
        System.out.println("retrieveAllAvailableConsultationForModule");
        System.out.println(em);
        try {
            if (em.find(Module.class, moduleId) == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exists!")).build();
            }

            Query query = em.createQuery("SELECT c FROM ConsultationTimeslot c WHERE c.module = :moduleId");
            query.setParameter("moduleId", moduleId);

            List<ConsultationTimeslot> consultations = (List<ConsultationTimeslot>) query.getResultList();
            if (consultations != null && !consultations.isEmpty()) {
                return Response.status(Response.Status.OK).entity(consultations).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No consultation for this module").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("createConsultation/{createNewConsultation}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createConsultation(ConsultationTimeslot createNewConsultation) {

        try {
            Module module = em.find(Module.class, createNewConsultation.getModule().getModuleId());
            if (module == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exists!")).build();
            }

            Query query = em.createQuery("Select cs from ConsultationTimeslot cs WHERE cs.module = :moduleId AND cs.startD =:startD AND cs.endD =:endD AND cs.startTs = :startTs AND cs.endTs =:endTs");
            query.setParameter("moduleId", createNewConsultation.getModule().getModuleId());
            query.setParameter("startD", createNewConsultation.getStartD());
            query.setParameter("endD", createNewConsultation.getEndD());
            query.setParameter("startTs", createNewConsultation.getStartTs());
            query.setParameter("endTs", createNewConsultation.getEndTs());

            if (query.getResultList() != null) {
                ConsultationTimeslot cs = new ConsultationTimeslot();
                cs.setEndD(createNewConsultation.getEndD());
                cs.setStartD(createNewConsultation.getStartD());
                cs.setStartTs(createNewConsultation.getStartTs());
                cs.setEndTs(createNewConsultation.getEndTs());
                module.getConsultationList().add(cs);
                em.persist(cs);
                em.flush();
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Consultation Timeslot is already created!")).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("bookConsultation/{bookConsultation}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response bookConsultation(ConsultationTimeslot bookConsultation) {
        try {
            Module module = em.find(Module.class, bookConsultation.getModule().getModuleId());
            if (module == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exists!")).build();
            }

            User user = em.find(User.class, bookConsultation.getBooker().getId());
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User Not Exists!")).build();
            }

            Query query = em.createQuery("Select cs from ConsultationTimeslot cs WHERE cs.module =:moduleId AND cs.booker=:booker AND cs.startD =:startD AND cs.endD =:endD AND cs.startTs =:startTs AND cs.endTs =:endTs");
            query.setParameter("startD", bookConsultation.getStartD());
            query.setParameter("endD", bookConsultation.getEndD());
            query.setParameter("moduleId", bookConsultation.getModule().getModuleId());
            query.setParameter("startTs", bookConsultation.getStartTs());
            query.setParameter("endTs", bookConsultation.getEndTs());
            query.setParameter("booker", null);

            if (query.getResultList() != null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("There are no available timeslots").build();
            } else {
                ConsultationTimeslot cs = (ConsultationTimeslot) query.getResultList();
                cs.setBooker(bookConsultation.getBooker());
                user.getConsultationTimeslotList().add(cs);
                return Response.status(Response.Status.OK).build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("dropConsultation/{consultationTimeslot}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response dropConsultation(ConsultationTimeslot consultationTimeslot) {
        try {

            Module module = em.find(Module.class, consultationTimeslot.getModule().getModuleId());
            if (module == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exists!")).build();
            }

            User user = em.find(User.class, consultationTimeslot.getBooker().getId());
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User Not Exists!")).build();
            }

            Query q = em.createQuery("Select c FROM ConsultationTimeslot c WHERE c.consultationTsId = :id");
            q.setParameter("id", consultationTimeslot.getconsultationTsId());

            ConsultationTimeslot cs = (ConsultationTimeslot) q.getResultList();
            if (cs != null) {
                user.getConsultationTimeslotList().remove(cs);
                cs.setBooker(null);
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("There is no timeslot to be deleted").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("updateConsultation/{consultationTimeslot}")
    @GET
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateConsultation(ConsultationTimeslot newTimeslot, ConsultationTimeslot oldTimeslot) {
        try {
            Module module = em.find(Module.class, oldTimeslot.getModule().getModuleId());
            if (module == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Does Not Exists!")).build();
            }

            User user = em.find(User.class, oldTimeslot.getBooker().getId());
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User Does Not Exists!")).build();
            }

            Query query = em.createQuery("Select cs FROM ConsultationTimeslot cs WHERE cs.consultationTsId = :consultationTsId");
            query.setParameter("consultationTsId", oldTimeslot.getconsultationTsId());
            ConsultationTimeslot cs = (ConsultationTimeslot) query.getResultList();

            //find available timeslot 
            Query q = em.createQuery("Select cs FROM ConsultationTimeslot cs WHERE cs.module=:module AND cs.booker =:booker AND cs.startD =:startD AND cs.endD =:endD AND cs.startTs=:startTs AND cs.endTs=:endTs AND cs.booker =:booker");
            q.setParameter("startD", newTimeslot.getStartD());
            q.setParameter("endD", newTimeslot.getEndD());
            q.setParameter("moduleId", newTimeslot.getModule().getModuleId());
            q.setParameter("startTs", newTimeslot.getStartTs());
            q.setParameter("endTs", newTimeslot.getEndTs());
            q.setParameter("booker", null);
            ConsultationTimeslot newSlot = (ConsultationTimeslot) q.getResultList();

            if (cs != null && newSlot != null) {
                newSlot.setBooker(user);
                cs.setBooker(null);
                user.getConsultationTimeslotList().remove(oldTimeslot);
                user.getConsultationTimeslotList().add(newTimeslot);
                return Response.status(Response.Status.OK).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("There is no consultation to be updated").build();
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

}
