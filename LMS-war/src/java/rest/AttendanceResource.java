/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.CreateAttendance;
import datamodel.rest.ErrorRsp;
import datamodel.rest.GetAttendanceRsp;
import datamodel.rest.GetAttendeesRsp;
import datamodel.rest.GetTutorialRsp;
import datamodel.rest.GetUserRsp;
import datamodel.rest.UpdateAttendance;
import entities.Attendance;
import entities.Module;
import entities.Tutorial;
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
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Vixson
 */
@Stateless
@Path("Attendance")
public class AttendanceResource {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;

    public AttendanceResource() {
    }

    @POST
    @Path(value = "createAttendance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAttendance(CreateAttendance createAttendance, @QueryParam("moduleId") Long moduleId) {
        try {
            Module module = em.find(Module.class, moduleId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }
            Attendance attendance = new Attendance();
            attendance.setStartTs(createAttendance.getStartTs());
            attendance.setEndTs(createAttendance.getEndTs());
            attendance.setDuration(createAttendance.getDuration());
            attendance.setModule(module);
            attendance.setSemester(createAttendance.getSemester());
            attendance.setTotal(createAttendance.getTotal());
            em.persist(attendance);
            em.flush();
            module.getAttandanceList().add(attendance);
            Attendance attendanceCopy = new Attendance(attendance.getAttendanceId(), attendance.getTotal(), attendance.getAttendedNumber(),
                    attendance.getSemester(), attendance.getStartTs(), attendance.getEndTs(),
                    attendance.getDuration(), null, null);
            return Response.status(Response.Status.OK).entity(attendanceCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    /*@POST
    @Path(value = "createAttendance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAttendance(CreateAttendance createAttendance) {
        try {
            Attendance attendance = new Attendance();
            attendance.setStartTs(createAttendance.getStartTs());
            attendance.setEndTs(createAttendance.getEndTs());
            attendance.setDuration(createAttendance.getDuration());
            attendance.setModule(createAttendance.getModule());
            attendance.setSemester(createAttendance.getSemester());
            attendance.setTotal(createAttendance.getTotal());
            em.persist(attendance);
            em.flush();
            Attendance attendanceCopy = new Attendance(attendance.getAttendanceId(), attendance.getTotal(), attendance.getAttendedNumber(),
                    attendance.getSemester(), attendance.getStartTs(), attendance.getEndTs(),
                    attendance.getDuration(), null, null);
            return Response.status(Response.Status.OK).entity(attendanceCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }*/
    @POST
    @Path(value = "createTutorialAttendance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTutorialAttendance(CreateAttendance createAttendance, @QueryParam("tutorialId") Long tutorialId) {
        try {
            Tutorial tutorial = em.find(Tutorial.class, tutorialId);
            if (tutorial == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Tutorial does not exist").build();
            }
            Attendance attendance = new Attendance();
            attendance.setStartTs(createAttendance.getStartTs());
            attendance.setEndTs(createAttendance.getEndTs());
            attendance.setDuration(createAttendance.getDuration());
            //attendance.setModule(createAttendance.getModule());
            attendance.setSemester(createAttendance.getSemester());
            attendance.setTotal(createAttendance.getTotal());
            attendance.setTutorial(tutorial);
            em.persist(attendance);
            em.flush();
            tutorial.getAttendanceList().add(attendance);
            Attendance attendanceCopy = new Attendance(attendance.getAttendanceId(), attendance.getTotal(), attendance.getAttendedNumber(),
                    attendance.getSemester(), attendance.getStartTs(), attendance.getEndTs(),
                    attendance.getDuration(), null, null, null);
            return Response.status(Response.Status.OK).entity(attendanceCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAllAttendance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAttendance(@QueryParam("moduleId") Long moduleId) {
        try {
            Module module = em.find(Module.class, moduleId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }
            List<Attendance> attendanceList = module.getAttandanceList();
            if (attendanceList == null || attendanceList.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Module has no attendance").build();
            } else {
                GetAttendanceRsp rsp = new GetAttendanceRsp(new ArrayList<>());
                //GetUserRsp userRsp = new GetUserRsp(new ArrayList<>());
                for (Attendance a : attendanceList) {
                    List<User> attendees = a.getAttendees();
                    List<User> attendeesCopy = new ArrayList<>();
                    if (attendees != null || !attendees.isEmpty()) {
                        for (User u : attendees) {
                            attendeesCopy.add(
                                    new User(u.getUserId(), u.getFirstName(),
                                            u.getLastName(), u.getEmail(), u.getUsername(),
                                            null, u.getGender(), null, null, null, null, null,
                                            null, null, null, null, null));
                        }
                    }
                    rsp.getAttendanceList().add(
                            new Attendance(a.getAttendanceId(), a.getTotal(),
                                    a.getAttendedNumber(), a.getSemester(),
                                    a.getStartTs(), a.getEndTs(), a.getDuration(),
                                    null, attendeesCopy));
                }
                return Response.status(Response.Status.OK).entity(rsp).build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAllTutorialAttendance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTutorialAttendance(@QueryParam("tutorialId") Long tutorialId) {
        try {
            Tutorial tutorial = em.find(Tutorial.class, tutorialId);
            if (tutorial == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Tutorial does not exist").build();
            }
            List<Attendance> attendanceList = tutorial.getAttendanceList();
            if (attendanceList == null || attendanceList.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Tutorial has no attendance").build();
            } else {
                GetAttendanceRsp rsp = new GetAttendanceRsp(new ArrayList<>());
                for (Attendance a : attendanceList) {
                    List<User> attendees = a.getAttendees();
                    List<User> attendeesCopy = new ArrayList<>();
                    if (attendees != null || !attendees.isEmpty()) {
                        for (User u : attendees) {
                            attendeesCopy.add(
                                    new User(u.getUserId(), u.getFirstName(),
                                            u.getLastName(), u.getEmail(), u.getUsername(),
                                            null, u.getGender(), null, null, null, null, null,
                                            null, null, null, null, null));
                        }
                    }
                    rsp.getAttendanceList().add(
                            new Attendance(a.getAttendanceId(), a.getTotal(),
                                    a.getAttendedNumber(), a.getSemester(),
                                    a.getStartTs(), a.getEndTs(), a.getDuration(),
                                    null, attendeesCopy));
                }
                return Response.status(Response.Status.OK).entity(rsp).build();
            }
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    /*@GET
    @Path("getStudentAttandance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentAttandance(@QueryParam("userId") Long userId) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User does not exist").build();
            }
            List<Attendance> attendanceList = user.getAttendanceList();
            if (attendanceList == null || attendanceList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Student has no attendance").build();
            }
            GetAttendanceRsp rsp = new GetAttendanceRsp(new ArrayList<>());
            for (Attendance a : attendanceList) {
                Module module = a.getModule();
                Module moduleCopy = new Module(module.getModuleId(), module.getCode(), module.getTitle(),
                        null, module.getSemesterOffered(), module.getYearOffered(), module.getCreditUnit(),
                        null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                        null, module.isHasExam(), null, null, null, null, null);
                List<Tutorial> tutorialList = module.getTutorials();
                if (tutorialList == null || tutorialList.isEmpty()) {
                    rsp.getAttendanceList().add(new Attendance(
                            a.getAttendanceId(), a.getTotal(), a.getAttendedNumber(),
                            a.getSemester(), null, null, null, moduleCopy,
                            null, null, null));
                } else {
                    //GetTutorialRsp rspTutorial = new GetTutorialRsp(new ArrayList<>());
                    for (Tutorial t : tutorialList) {
                        if (t.getAttendanceList().contains(a)) {
                            Tutorial tutorialCopy = new Tutorial(t.getTutorialId(), t.getMaxEnrollment(),
                                    t.getVenue(), t.getTiming(), null, null);
                            rsp.getAttendanceList().add(new Attendance(
                                    a.getAttendanceId(), a.getTotal(), a.getAttendedNumber(),
                                    a.getSemester(), null, null, null, moduleCopy,
                                    null, tutorialCopy, null));
                            /*rspTutorial.getTutorials().add(new Tutorial(
                                    t.getTutorialId(), t.getMaxEnrollment(),
                                    t.getVenue(), t.getTiming(), null, null));
                        }
                    }
                    /*Tutorial tutorial = a.getTutorial();
                Tutorial tutorialCopy = new Tutorial(tutorial.getTutorialId(), tutorial.getMaxEnrollment(),
                        tutorial.getVenue(), tutorial.getTiming(), null, null);
                }
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }*/
    @GET
    @Path("getStudentModuleAttandance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentModuleAttandance(@QueryParam("userId") Long userId, @QueryParam("moduleId") Long moduleId) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User does not exist").build();
            }
            Module module = em.find(Module.class, moduleId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }
            List<Attendance> attendanceList = module.getAttandanceList();
            if (attendanceList == null || attendanceList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No attendance found").build();
            }
            GetAttendanceRsp rsp = new GetAttendanceRsp(new ArrayList<>());
            for (Attendance a : attendanceList) {
                if (a.getAttendees().contains(user)) {
                    Module moduleCopy = new Module(module.getModuleId(), module.getCode(), module.getTitle(),
                            null, module.getSemesterOffered(), module.getYearOffered(), module.getCreditUnit(),
                            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                            null, module.isHasExam(), null, null, null, null, null);
                    rsp.getAttendanceList().add(new Attendance(
                            a.getAttendanceId(), a.getTotal(), a.getAttendedNumber(),
                            a.getSemester(), null, null, null, moduleCopy,
                            null, null, null));

                    List<Tutorial> tutorialList = module.getTutorials();
                    if (tutorialList != null || !tutorialList.isEmpty()) {
                        for (Tutorial t : tutorialList) {
                            List<Attendance> tutorialAttendanceList = t.getAttendanceList();
                            if (tutorialAttendanceList != null || !tutorialAttendanceList.isEmpty()) {
                                for (Attendance ta : tutorialAttendanceList) {
                                    if (ta.getAttendees().contains(user)) {
                                        Tutorial tutorialCopy = new Tutorial(t.getTutorialId(), t.getMaxEnrollment(),
                                                t.getVenue(), t.getTiming(), null, null);
                                        rsp.getAttendanceList().add(new Attendance(
                                                ta.getAttendanceId(), ta.getTotal(), ta.getAttendedNumber(),
                                                ta.getSemester(), null, null, null, null,
                                                null, tutorialCopy, null));
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getStudentTutorialAttandance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentTutorialAttandance(@QueryParam("userId") Long userId, @QueryParam("tutorialId") Long tutorialId) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User does not exist").build();
            }
            Tutorial tutorial = em.find(Tutorial.class, tutorialId);
            if (tutorial == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Tutorial does not exist").build();
            }
            List<Attendance> attendanceList = tutorial.getAttendanceList();
            if (attendanceList == null || attendanceList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No attendance found").build();
            }
            GetAttendanceRsp rsp = new GetAttendanceRsp(new ArrayList<>());
            for (Attendance a : attendanceList) {
                if (a.getAttendees().contains(user)) {
                    Tutorial tutorialCopy = new Tutorial(tutorial.getTutorialId(), tutorial.getMaxEnrollment(),
                            tutorial.getVenue(), tutorial.getTiming(), null, null);
                    rsp.getAttendanceList().add(new Attendance(
                            a.getAttendanceId(), a.getTotal(), a.getAttendedNumber(),
                            a.getSemester(), null, null, null, null,
                            null, tutorialCopy, null));
                }
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getStudentOnlyModuleAttandance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentOnlyModuleAttandance(@QueryParam("userId") Long userId, @QueryParam("moduleId") Long moduleId) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User does not exist").build();
            }
            Module module = em.find(Module.class, moduleId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }
            List<Attendance> attendanceList = module.getAttandanceList();
            if (attendanceList == null || attendanceList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No attendance found").build();
            }
            GetAttendanceRsp rsp = new GetAttendanceRsp(new ArrayList<>());
            for (Attendance a : attendanceList) {
                if (a.getAttendees().contains(user)) {
                    Module moduleCopy = new Module(module.getModuleId(), module.getCode(), module.getTitle(),
                            null, module.getSemesterOffered(), module.getYearOffered(), module.getCreditUnit(),
                            null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                            null, module.isHasExam(), null, null, null, null, null);
                    rsp.getAttendanceList().add(new Attendance(
                            a.getAttendanceId(), a.getTotal(), a.getAttendedNumber(),
                            a.getSemester(), null, null, null, moduleCopy,
                            null, null, null));
                }
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getStudentAllModuleAttandance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentAllModuleAttandance(@QueryParam("userId") Long userId) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User does not exist").build();
            }
            List<Module> moduleList = user.getStudentModuleList();
            if (moduleList == null || moduleList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No module enrolled").build();
            }
            GetAttendanceRsp rsp = new GetAttendanceRsp(new ArrayList<>());
            for (Module m : moduleList) {
                List<Attendance> attendanceList = m.getAttandanceList();
                if (attendanceList == null || attendanceList.isEmpty()) {
                    return Response.status(Response.Status.NOT_FOUND).entity("No attendance found").build();
                }
                for (Attendance a : attendanceList) {
                    if (a.getAttendees().contains(user)) {
                        Module moduleCopy = new Module(m.getModuleId(), m.getCode(), m.getTitle(),
                                null, m.getSemesterOffered(), m.getYearOffered(), m.getCreditUnit(),
                                null, null, null, null, null, null, null, null, null, null, null, null, null, null, null,
                                null, m.isHasExam(), null, null, null, null, null);
                        rsp.getAttendanceList().add(new Attendance(
                                a.getAttendanceId(), a.getTotal(), a.getAttendedNumber(),
                                a.getSemester(), null, null, null, moduleCopy,
                                null, null, null));
                    }
                }
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getStudentAllTutorialAttandance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getStudentAllTutorialAttandance(@QueryParam("userId") Long userId) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User does not exist").build();
            }
            List<Tutorial> tutorialList = user.getTutorials();
            if (tutorialList == null || tutorialList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No tutorial enrolled").build();
            }
            GetAttendanceRsp rsp = new GetAttendanceRsp(new ArrayList<>());
            for (Tutorial t : tutorialList) {
                List<Attendance> attendanceList = t.getAttendanceList();
                if (attendanceList == null || attendanceList.isEmpty()) {
                    return Response.status(Response.Status.NOT_FOUND).entity("No attendance found").build();
                }
                for (Attendance a : attendanceList) {
                    if (a.getAttendees().contains(user)) {
                        Tutorial tutorialCopy = new Tutorial(t.getTutorialId(), t.getMaxEnrollment(),
                                t.getVenue(), t.getTiming(), null, null);
                        rsp.getAttendanceList().add(new Attendance(
                                a.getAttendanceId(), a.getTotal(), a.getAttendedNumber(),
                                a.getSemester(), null, null, null, null,
                                null, tutorialCopy, null));
                    }
                }
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    /*@GET
    @Path("getUserAttandance")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserAttandance(@QueryParam("userId") Long userId) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User does not exist").build();
            }
            List<Attendance> attendanceList = user.getAttendanceList();
            if (attendanceList == null || attendanceList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No attendance found").build();
            }
            GetAttendanceRsp rsp = new GetAttendanceRsp(new ArrayList<>());
            for (Attendance a : attendanceList) {
                rsp.getAttendanceList().add(new Attendance(
                        a.getAttendanceId(), a.getTotal(), a.getAttendedNumber(),
                        a.getSemester(), null, null, null, null,
                        null, null, null));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }*/
    @GET
    @Path("getAttendees")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAttendees(@QueryParam("attendanceId") Long attendanceId
    ) {
        try {
            Attendance attendance = em.find(Attendance.class, attendanceId);
            if (attendance == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Attendance not found").build();
            }
            List<User> attendees = attendance.getAttendees();
            if (attendees == null || attendees.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No students attended").build();
            }
            GetAttendeesRsp rsp = new GetAttendeesRsp(new ArrayList<>());
            for (User u : attendees) {
                rsp.getAttendees().add(new User(
                        null, u.getId(), u.getFirstName(), u.getLastName(),
                        u.getEmail(), u.getUsername(), u.getPassword(), u.getGender(),
                        u.getAccessRight(), null, null, null,
                        null, null, null, null));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @PUT
    @Path(value = "updateAttendance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateAttendance(UpdateAttendance updateAttendance,
            @QueryParam("moduleId") Long moduleId,
            @QueryParam("attendanceId") Long attendanceId
    ) {
        try {
            Module module = em.find(Module.class, moduleId);
            Attendance attendance = em.find(Attendance.class, attendanceId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }
            if (attendance == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Attendance not found").build();
            }
            attendance.setAttendedNumber(updateAttendance.getAttendedNumber());
            attendance.setDuration(updateAttendance.getDuration());
            attendance.setStartTs(updateAttendance.getStartTs());
            attendance.setEndTs(updateAttendance.getEndTs());
            attendance.setTotal(updateAttendance.getTotal());
            attendance.setSemester(updateAttendance.getSemester());
            attendance.setModule(module);
            em.merge(attendance);
            Attendance attendanceCopy = new Attendance(attendance.getAttendanceId(), attendance.getTotal(), attendance.getAttendedNumber(),
                    attendance.getSemester(), attendance.getStartTs(), attendance.getEndTs(),
                    attendance.getDuration(), module, null);
            return Response.status(Response.Status.OK).entity(attendanceCopy).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @PUT
    @Path(value = "updateTutorialAttendance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTutorialAttendance(UpdateAttendance updateAttendance,
            @QueryParam("tutorialId") Long tutorialId,
            @QueryParam("attendanceId") Long attendanceId
    ) {
        try {
            Tutorial tutorial = em.find(Tutorial.class, tutorialId);
            Attendance attendance = em.find(Attendance.class, attendanceId);
            if (tutorial == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Tutorial does not exist").build();
            }
            if (attendance == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Attendance not found").build();
            }
            attendance.setAttendedNumber(updateAttendance.getAttendedNumber());
            attendance.setDuration(updateAttendance.getDuration());
            attendance.setStartTs(updateAttendance.getStartTs());
            attendance.setEndTs(updateAttendance.getEndTs());
            attendance.setTotal(updateAttendance.getTotal());
            attendance.setSemester(updateAttendance.getSemester());
            attendance.setTutorial(tutorial);
            em.merge(attendance);
            Attendance attendanceCopy = new Attendance(attendance.getAttendanceId(), attendance.getTotal(), attendance.getAttendedNumber(),
                    attendance.getSemester(), attendance.getStartTs(), attendance.getEndTs(),
                    attendance.getDuration(), null, null, null);
            return Response.status(Response.Status.OK).entity(attendanceCopy).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @PUT
    @Path(value = "addAttendee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addAttendee(@QueryParam("attendanceId") Long attendanceId,
            @QueryParam("userId") Long userId
    ) {
        try {
            Attendance attendance = em.find(Attendance.class, attendanceId);
            if (attendance == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Attendance not found").build();
            }
            List<User> attendees = attendance.getAttendees();
            /*if (attendees == null && attendees.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No students found").build();
            }*/
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("User does not exist").build();
            }
            if (attendees.contains(user)) {
                return Response.status(Response.Status.CONFLICT).entity("Student is already in attendance").build();
            }
            attendees.add(user);
            user.getAttendanceList().add(attendance);
            GetAttendeesRsp rsp = new GetAttendeesRsp(new ArrayList<>());
            for (User u : attendees) {
                rsp.getAttendees().add(new User(
                        u.getFirstName(), u.getLastName(), u.getEmail(), u.getUsername(),
                        null, u.getGender(), null, null, null, null,
                        null, null, null, null));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @PUT
    @Path(value = "addListOfAttendees")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addListOfAttendees(@QueryParam("moduleId") Long moduleId,
            @QueryParam("attendanceId") Long attendanceId
    ) {
        try {
            Module module = em.find(Module.class, moduleId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }
            Attendance attendance = em.find(Attendance.class, attendanceId);
            if (attendance == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Attendance not found").build();
            }
            List<User> attendees = attendance.getAttendees();
            /*if (attendees == null && attendees.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No students found").build();
            }*/
            Query query = em.createQuery("select u from User u where u.accessRight = Student");
            List<User> studentList = query.getResultList();
            if (studentList == null && studentList.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No students found").build();
            }
            for (User u : studentList) {
                if (!attendees.contains(u) && module.getStudentList().contains(u)) {
                    attendees.add(u);
                    u.getAttendanceList().add(attendance);
                }
            }
            GetAttendeesRsp rsp = new GetAttendeesRsp(new ArrayList<>());
            for (User u : attendees) {
                rsp.getAttendees().add(new User(
                        u.getFirstName(), u.getLastName(), u.getEmail(), u.getUsername(),
                        null, u.getGender(), null, null, null, null,
                        null, null, null, null));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @PUT
    @Path(value = "addTutorialListOfAttendees")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response addTutorialListOfAttendees(@QueryParam("tutorialId") Long tutorialId,
            @QueryParam("attendanceId") Long attendanceId
    ) {
        try {
            Tutorial tutorial = em.find(Tutorial.class, tutorialId);
            if (tutorial == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Tutorial does not exist").build();
            }
            Attendance attendance = em.find(Attendance.class, attendanceId);
            if (attendance == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Attendance not found").build();
            }
            List<User> attendees = attendance.getAttendees();
            /*if (attendees == null && attendees.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No students found").build();
            }*/
            Query query = em.createQuery("select u from User u where u.accessRight = Student");
            List<User> studentList = query.getResultList();
            if (studentList == null && studentList.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No students found").build();
            }
            for (User u : studentList) {
                if (!attendees.contains(u) && tutorial.getStudentList().contains(u)) {
                    attendees.add(u);
                    u.getAttendanceList().add(attendance);
                }
            }
            GetAttendeesRsp rsp = new GetAttendeesRsp(new ArrayList<>());
            for (User u : attendees) {
                rsp.getAttendees().add(new User(
                        u.getFirstName(), u.getLastName(), u.getEmail(), u.getUsername(),
                        null, u.getGender(), null, null, null, null,
                        null, null, null, null));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @PUT
    @Path(value = "removeAttendee")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeAttendee(@QueryParam("attendanceId") Long attendanceId,
            @QueryParam("userId") Long userId
    ) {
        try {
            Attendance attendance = em.find(Attendance.class, attendanceId);
            if (attendance == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Attendance not found").build();
            }
            List<User> attendees = attendance.getAttendees();
            if (attendees == null && attendees.isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("No students found").build();
            }
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("User does not exist").build();
            }
            GetAttendeesRsp rsp = new GetAttendeesRsp(new ArrayList<>());
            if (attendees.contains(user)) {
                attendees.remove(user);
                user.getAttendanceList().remove(attendance);
                for (User u : attendees) {
                    rsp.getAttendees().add(new User(
                            u.getFirstName(), u.getLastName(), u.getEmail(), u.getUsername(),
                            null, u.getGender(), null, null, null, null,
                            null, null, null, null));
                }
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @POST
    @Path(value = "signAttendance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signAttendance(@QueryParam("moduleId") Long moduleId,
            @QueryParam("userId") Long userId,
            @QueryParam("attendanceId") Long attendanceId
    ) {
        try {
            Module module = em.find(Module.class, moduleId);
            User user = em.find(User.class, userId);
            Attendance attendance = em.find(Attendance.class, attendanceId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }
            if (attendance == null || !module.getAttandanceList().contains(attendance)) {
                return Response.status(Response.Status.NOT_FOUND).entity("Attendance not found").build();
            }
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User does not exist").build();
            }
            if (!module.getStudentList().contains(user)) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("User does not belong to module").build();
            }
            if (attendance.getAttendees().contains(user)) {
                return Response.status(Response.Status.CONFLICT).entity("Attendance is already recorded").build();
            }
            attendance.getAttendees().add(user);
            user.getAttendanceList().add(attendance);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @POST
    @Path(value = "signTutorialAttendance")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response signTutorialAttendance(@QueryParam("tutorialId") Long tutorialId,
            @QueryParam("userId") Long userId,
            @QueryParam("attendanceId") Long attendanceId
    ) {
        try {
            Tutorial tutorial = em.find(Tutorial.class, tutorialId);
            User user = em.find(User.class, userId);
            Attendance attendance = em.find(Attendance.class, attendanceId);
            if (tutorial == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Tutorial does not exist").build();
            }
            if (attendance == null || tutorial.getAttendanceList().isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Attendance not found").build();
            }
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("User does not exist").build();
            }
            if (!tutorial.getStudentList().contains(user)) {
                return Response.status(Response.Status.NOT_ACCEPTABLE).entity("User does not belong to tutorial").build();
            }
            if (attendance.getAttendees().contains(user)) {
                return Response.status(Response.Status.CONFLICT).entity("Attendance is already recorded").build();
            }
            attendance.getAttendees().add(user);
            user.getAttendanceList().add(attendance);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @Path(value = "deleteAttendance")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAttendance(@QueryParam("moduleId") Long moduleId,
            @QueryParam("attendanceId") Long attendanceId
    ) {
        try {
            Module module = em.find(Module.class, moduleId);
            Attendance attendance = em.find(Attendance.class, attendanceId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }
            if (attendance == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Attendance not found").build();
            }
            List<User> attendees = attendance.getAttendees();
            for (User u : attendees) {
                u.getAttendanceList().remove(attendance);
            }
            module.getAttandanceList().remove(attendance);
            em.remove(attendance);
            return Response.status(Response.Status.OK).entity("Attendance deleted").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @Path(value = "deleteTutorialAttendance")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTutorialAttendance(@QueryParam("tutorialId") Long tutorialId,
            @QueryParam("attendanceId") Long attendanceId
    ) {
        try {
            Tutorial tutorial = em.find(Tutorial.class, tutorialId);
            Attendance attendance = em.find(Attendance.class, attendanceId);
            if (tutorial == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Tutorial does not exist").build();
            }
            if (attendance == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Attendance not found").build();
            }
            List<User> attendees = attendance.getAttendees();
            for (User u : attendees) {
                u.getAttendanceList().remove(attendance);
            }
            tutorial.getAttendanceList().remove(attendance);
            em.remove(attendance);
            return Response.status(Response.Status.OK).entity("Attendance deleted").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }
}
