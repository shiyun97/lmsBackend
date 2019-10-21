package rest;

import datamodel.rest.ConsultationTimeReq;
import datamodel.rest.ErrorRsp;
import datamodel.rest.GetConsultationRsp;
import datamodel.rest.RetrieveAllConsultationsForModule;
import entities.ConsultationTimeslot;
import entities.Module;
import entities.User;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalTime;
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
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Stateless
@Path("Consultation")
public class ConsultationResource {
    
    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;
    
    public DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
    public DateTimeFormatter timeFormatter = DateTimeFormatter.ofPattern("HH:mm:ss");
    
    @Path("viewAllAvailableConsultation/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllAvailableConsultationTimeslot(@PathParam("moduleId") Long moduleId) {
        try {
            Module mod = em.find(Module.class, moduleId);
            if (mod == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exists!")).build();
            } else {
                /*Query query = em.createQuery("SELECT c FROM ConsultationTimeslot c WHERE c.module = :module "
                        + "AND c.booker is null AND c.startD >=:startD AND c.startTs >=:startTs");
                query.setParameter("module", mod);
                //query.setParameter("booker", null);
                query.setParameter("startD", LocalDate.now());
                query.setParameter("startTs", LocalTime.now());*/
                List<ConsultationTimeslot> consultations = mod.getConsultationList();
                GetConsultationRsp rsp = new GetConsultationRsp(new ArrayList<>());
                //List<ConsultationTimeslot> consultations = query.getResultList();
                if (consultations != null && !consultations.isEmpty()) {
                    //List<ConsultationTimeslot> consultationsCopy = new ArrayList<>();
                    for (ConsultationTimeslot ct : consultations) {
                        if (ct.getBooker() == null && ct.getStartD().isAfter(LocalDate.now()) /*&& (ct.getStartD().isEqual(LocalDate.now())) */                              
                                && (ct.getStartTs().isAfter(LocalTime.now())) /*&& (ct.getStartTs().equals(LocalTime.now()))*/) {
                            rsp.getConsultationTimeslot().add(new ConsultationTimeslot(ct.getconsultationTsId(),
                                    ct.getStartTs(), ct.getEndTs(), ct.getStartD(), null, null));
                        }
                    }
                    return Response.status(Response.Status.OK).entity(rsp).build();
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
    public Response retrieveAllConsultationTimeslot(@QueryParam("moduleId") Long moduleId, @QueryParam("userId") Long userId) {
        System.out.println("retrieveAllConsultationForModule");
        System.out.println(em);
        try {
            Module mod = em.find(Module.class, moduleId);
            User user = em.find(User.class, userId);
            if (mod == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exists!")).build();
            } else if (user.getAccessRight() != AccessRightEnum.Teacher) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Do not have right to view")).build();
            } else {
                RetrieveAllConsultationsForModule rsp = new RetrieveAllConsultationsForModule(new ArrayList<>());
                List<ConsultationTimeslot> timeslots = mod.getConsultationList();
                if (timeslots != null && !timeslots.isEmpty()) {
                    //List<ConsultationTimeslot> timeslotsCopy = new ArrayList<>();
                    for (ConsultationTimeslot ct : timeslots) {
                        rsp.getConsultationTimeslots().add(new ConsultationTimeslot(
                                ct.getconsultationTsId(), ct.getStartTs(), ct.getEndTs(), ct.getStartD(), null, null));
                    }
                    return Response.status(Response.Status.OK).entity(rsp).build();
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
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response dropConsultation(@QueryParam("consultationTimeslotId") Long consultationTimeslotId) {
        try {
            ConsultationTimeslot cs = em.find(ConsultationTimeslot.class, consultationTimeslotId);
            if (cs == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Consultation Not Exists!")).build();
            } else if (cs.getStartTs().compareTo(LocalTime.now()) <= 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("No valid Consultation Exists!")).build();
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
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response bookConsultation(@QueryParam("consultationTimeslotId") Long consultationTimeslotId, @QueryParam("userId") Long userId) {
        try {
            ConsultationTimeslot cs = em.find(ConsultationTimeslot.class, consultationTimeslotId);
            //User user = cs.getBooker();
            User user = em.find(User.class, userId);
            if (cs == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Consultation Not Exists!")).build();
            } else {
                Query q = em.createQuery("Select c FROM ConsultationTimeslot c WHERE c.booker =:booker AND c.startD >=:currentD AND c.startTs>=:currentTs AND c.startD =:startD AND c.startTs=:startTs");
                //q.setParameter("booker", user.getId());
                q.setParameter("booker", user);
                q.setParameter("currentD", LocalDate.now());
                q.setParameter("currentTs", LocalTime.now());
                q.setParameter("startD", cs.getStartD());
                q.setParameter("startTs", cs.getStartTs());
                
                if (q.getResultList().isEmpty()) {
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
    
    @Path("deleteConsultation")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteConsultation(@QueryParam("consultationId") Long consultationId, @QueryParam("userId") Long userId, @QueryParam("moduleId") Long moduleId) {
        try {
            ConsultationTimeslot cs = em.find(ConsultationTimeslot.class, consultationId);
            if (cs == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Timeslot does not exist!")).build();
            } else {
                User user = em.find(User.class, userId);
                Module mod = em.find(Module.class, moduleId);
                
                if (mod == null) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module does not exist!")).build();
                } else if (user.getAccessRight() != AccessRightEnum.Teacher) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You do not have rights to delete consultation!")).build();
                } else if (cs.getBooker() != null) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("This consultation is already booked!")).build();
                } else {
                    mod.getConsultationList().remove(cs);
                    user.getConsultationTimeslotList().remove(cs);
                    em.remove(cs);
                    return Response.status(Response.Status.OK).build();
                }
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @Path("viewConsultationByStudent")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewConsultation(@QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        try {
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User does not exist!")).build();
            } else {
                List<ConsultationTimeslot> cs = user.getConsultationTimeslotList();
                
                if (cs != null && !cs.isEmpty()) {
                    List<ConsultationTimeslot> csCopy = new ArrayList<>();
                    for (ConsultationTimeslot ct : cs) {
                        csCopy.add(new ConsultationTimeslot(
                                ct.getconsultationTsId(), ct.getStartTs(), ct.getEndTs(), ct.getStartD(), null, null));
                    }
                    return Response.status(Response.Status.OK).entity(csCopy).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("No consultation for this module").build();
                }
            }
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
	
	@Path("createConsultation")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createConsultation(ConsultationTimeReq consultationTimeReq, @QueryParam("userId") Long userId, @QueryParam("moduleId") Long moduleId) {
        try {
            User user = em.find(User.class, userId);
            Module mod = em.find(Module.class, moduleId);

            LocalTime startTime = LocalTime.parse(consultationTimeReq.getStartTime(), timeFormatter);
            LocalTime endTime = LocalTime.parse(consultationTimeReq.getEndTime(), timeFormatter);
            LocalDate startDate = LocalDate.parse(consultationTimeReq.getStartDate(), dateFormatter);

            if (mod == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module do not exist !")).build();
            } else if (user.getAccessRight() != AccessRightEnum.Teacher) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You do not have rights to create consultation!")).build();
            } else if (startTime.compareTo(LocalTime.now()) < 0 && startDate.compareTo(LocalDate.now()) < 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Timeslot is invalid!")).build();
            } else if (ChronoUnit.MINUTES.between(startTime, endTime) % 60 != 0) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Invalid input!")).build();
            } else {
                List<ConsultationTimeslot> createdSlot = new ArrayList<>();
                Long numberOfHours = ChronoUnit.HOURS.between(startTime, endTime);
                int noOfHours = Math.toIntExact(numberOfHours);
                Boolean exist = false;
                
                for (int i = 0; i < noOfHours; i++) {
                    LocalTime newStartTime = startTime.plusHours(i);
                    LocalTime newEndTime = newStartTime.plusHours(1);
                    System.out.println(newStartTime.toString());
                    System.out.println(newEndTime.toString());

                    List<ConsultationTimeslot> allSlots = mod.getConsultationList();
                    for (ConsultationTimeslot c : allSlots) {
                    if (c.getStartTs().equals(newStartTime) && (c.getEndTs().equals(newEndTime) && (c.getStartD().equals(startDate)))) {
                        exist = true;
                    }
                    }
                    if (exist == false) {
                        System.out.println("is empty");
                        ConsultationTimeslot newTimeslot = new ConsultationTimeslot();
                        newTimeslot.setStartD(startDate);
                        newTimeslot.setStartTs(newStartTime);
                        newTimeslot.setEndTs(newEndTime);
                        newTimeslot.setModule(mod);

                        mod.getConsultationList().add(newTimeslot);
                        createdSlot.add(newTimeslot);
                        em.persist(newTimeslot);
                        em.flush();
                    }
                    exist = false;
                }
                if (!createdSlot.isEmpty()) {
                    return Response.status(Response.Status.OK).build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("No new slot is created!")).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
}