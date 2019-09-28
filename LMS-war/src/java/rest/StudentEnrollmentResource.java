/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.ErrorRsp;
import datamodel.rest.RetrieveEnrolledStudentModulesRsp;
import datamodel.rest.RetrieveEnrolledStudentTutorialsRsp;
import datamodel.rest.ScheduleRqst;
import ejb.AcademicYearSessionBean;
import entities.Module;
import entities.Schedule;
import entities.Tutorial;
import entities.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
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
import javax.ws.rs.core.Response.Status;
import util.AccessRightEnum;

/**
 *
 * @author Asus
 */
@Path("studentEnrollment")
@Stateless
public class StudentEnrollmentResource {
    
    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;
    
    public SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    public void persist(Object object) {
        em.persist(object);
    }
    
    public boolean isTutorialRoundOpen(){
        Query q = em.createQuery("SELECT s FROM Schedule s WHERE (s.tutorialRound1StartDate <= :now AND s.tutorialRound1EndDate >= :now) "
                + "OR (s.tutorialRound2StartDate <= :now AND s.tutorialRound2EndDate >= :now)");
        q.setParameter("now", new Date());
        try {
            q.getSingleResult();
            return true;
        } catch (NoResultException e){
            return false;
        }
    }
    
    public boolean isModuleRoundOpen(){
        Query q = em.createQuery("SELECT s FROM Schedule s WHERE (s.moduleRound1StartDate <= :now AND s.moduleRound1EndDate >= :now) "
                + "OR (s.moduleRound2StartDate <= :now AND s.moduleRound2EndDate >= :now) "
                + "OR (s.moduleRound3StartDate <= :now AND s.moduleRound3EndDate >= :now)");
        q.setParameter("now", new Date());
        try {
            q.getSingleResult();
            return true;
        } catch (NoResultException e){
            return false;
        }
    }
    
    @Path("createSchedule")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSchedule(ScheduleRqst rqst, @QueryParam("userId") Long userId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Admin){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        try{
            // Check if schedule is correct
            Schedule sched = new Schedule();
            sched.setYear(rqst.getYear());
            sched.setSemester(rqst.getSemester());
            sched.setModuleRound1StartDate(dateFormatter.parse(rqst.getModuleRound1StartDate()));
            sched.setModuleRound1EndDate(dateFormatter.parse(rqst.getModuleRound1EndDate()));
            sched.setModuleRound2StartDate(dateFormatter.parse(rqst.getModuleRound2StartDate()));
            sched.setModuleRound2EndDate(dateFormatter.parse(rqst.getModuleRound2EndDate()));
            sched.setModuleRound3StartDate(dateFormatter.parse(rqst.getModuleRound3StartDate()));
            sched.setModuleRound3EndDate(dateFormatter.parse(rqst.getModuleRound3EndDate()));
            sched.setTutorialRound1StartDate(dateFormatter.parse(rqst.getTutorialRound1StartDate()));
            sched.setTutorialRound1EndDate(dateFormatter.parse(rqst.getTutorialRound1EndDate()));
            sched.setTutorialRound2StartDate(dateFormatter.parse(rqst.getTutorialRound2StartDate()));
            sched.setTutorialRound2EndDate(dateFormatter.parse(rqst.getTutorialRound2EndDate()));
            
            if(!sched.getModuleRound1StartDate().before(sched.getModuleRound1EndDate()) ||
                    !sched.getModuleRound2StartDate().before(sched.getModuleRound2EndDate()) ||
                    !sched.getModuleRound3StartDate().before(sched.getModuleRound3EndDate()) ||
                    !sched.getModuleRound1EndDate().before(sched.getModuleRound2StartDate()) || 
                    !sched.getModuleRound2EndDate().before(sched.getModuleRound3StartDate()) ||
                    !sched.getTutorialRound1StartDate().before(sched.getTutorialRound1EndDate()) ||
                    !sched.getTutorialRound2StartDate().before(sched.getTutorialRound2EndDate()) ||
                    !sched.getTutorialRound1EndDate().before(sched.getTutorialRound2StartDate())){
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("The given dates isn't properly set")).build();
            }
            
            em.persist(sched);
            em.flush();
            
            return Response.status(Status.OK).entity(sched).build();
        } catch (ParseException pe){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Dates are not in correct format. It should be in yyyy-MM-ddTHH:mm:ss")).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @Path("updateSchedule")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateSchedule(ScheduleRqst rqst, @QueryParam("userId") Long userId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Admin){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        try{
            Query q = em.createQuery("SELECT s FROM Schedule s WHERE s.semester=:semester AND s.year=:year");
            q.setParameter("semester", rqst.getSemester());
            q.setParameter("year", rqst.getYear());
            Schedule sched = (Schedule) q.getSingleResult();
            
            em.detach(sched);
            
            // Check if schedule is correct
            sched.setYear(rqst.getYear());
            sched.setSemester(rqst.getSemester());
            sched.setModuleRound1StartDate(dateFormatter.parse(rqst.getModuleRound1StartDate()));
            sched.setModuleRound1EndDate(dateFormatter.parse(rqst.getModuleRound1EndDate()));
            sched.setModuleRound2StartDate(dateFormatter.parse(rqst.getModuleRound2StartDate()));
            sched.setModuleRound2EndDate(dateFormatter.parse(rqst.getModuleRound2EndDate()));
            sched.setModuleRound3StartDate(dateFormatter.parse(rqst.getModuleRound3StartDate()));
            sched.setModuleRound3EndDate(dateFormatter.parse(rqst.getModuleRound3EndDate()));
            sched.setTutorialRound1StartDate(dateFormatter.parse(rqst.getTutorialRound1StartDate()));
            sched.setTutorialRound1EndDate(dateFormatter.parse(rqst.getTutorialRound1EndDate()));
            sched.setTutorialRound2StartDate(dateFormatter.parse(rqst.getTutorialRound2StartDate()));
            sched.setTutorialRound2EndDate(dateFormatter.parse(rqst.getTutorialRound2EndDate()));
            
            if(!sched.getModuleRound1StartDate().before(sched.getModuleRound1EndDate()) ||
                    !sched.getModuleRound2StartDate().before(sched.getModuleRound2EndDate()) ||
                    !sched.getModuleRound3StartDate().before(sched.getModuleRound3EndDate()) ||
                    !sched.getModuleRound1EndDate().before(sched.getModuleRound2StartDate()) || 
                    !sched.getModuleRound2EndDate().before(sched.getModuleRound3StartDate()) ||
                    !sched.getTutorialRound1StartDate().before(sched.getTutorialRound1EndDate()) ||
                    !sched.getTutorialRound2StartDate().before(sched.getTutorialRound2EndDate()) ||
                    !sched.getTutorialRound1EndDate().before(sched.getTutorialRound2StartDate())){
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("The given dates isn't properly set")).build();
            }
            
            em.merge(sched);
            em.flush();
            
            return Response.status(Status.OK).entity(sched).build();
        } catch (ParseException pe){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Dates are not in correct format. It should be in yyyy-MM-ddTHH:mm:ss")).build();
        } catch (NoResultException n){
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No schedule exists with the given year and semester")).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @Path("getScheduleDetails")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getScheduleDetails(@QueryParam("semester") int semester, @QueryParam("year") String year){
        System.out.println("GET SCHEDULE");
        try{
            Query q = em.createQuery("SELECT s FROM Schedule s WHERE s.semester=:semester AND s.year=:year");
            q.setParameter("semester", semester);
            q.setParameter("year", year);
            Schedule sched = (Schedule) q.getSingleResult();
            
            return Response.status(Status.OK).entity(sched).build();
        } catch (NoResultException n){
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No schedule exists with the given year and semester")).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @Path("getCurrentScheduleDetails")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getScheduleDetails(){
        System.out.println("GET SCHEDULE");
        try{
            Query q = em.createQuery("SELECT s FROM Schedule s WHERE s.semester=:semester AND s.year=:year");
            q.setParameter("semester", AcademicYearSessionBean.getSemester());
            q.setParameter("year", AcademicYearSessionBean.getYear());
            Schedule sched = (Schedule) q.getSingleResult();
            
            return Response.status(Status.OK).entity(sched).build();
        } catch (NoResultException n){
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Schedule for this semester hasn't been set")).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
//    @Path("retrieveStudentEnrollmentStatus")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response retrieveStudentEnrollmentStatus(){
//        RetrieveStudentEnrollmentSessionStatusRsp resp = new RetrieveStudentEnrollmentSessionStatusRsp();
//        
//        try{
//            Query q = em.createQuery("SELECT br FROM BiddingRound br WHERE br.roundType=:roundType "
//                    + "AND br.startTime <= :currentTime AND br.endTime > :currentTime");
//            q.setParameter("currentTime", new Date());
//            q.setParameter("roundType", BiddingRoundEnum.Module);
//            BiddingRound result = (BiddingRound) q.getSingleResult();
//            resp.setModuleRoundOpen(true);
//        } catch (NoResultException e){
//            resp.setModuleRoundOpen(false);
//        }
//        
//        try{
//            Query q = em.createQuery("SELECT br FROM BiddingRound br WHERE br.roundType=:roundType "
//                    + "AND br.startTime <= :currentTime AND br.endTime > :currentTime");
//            q.setParameter("currentTime", new Date());
//            q.setParameter("roundType", BiddingRoundEnum.Tutorial);
//            BiddingRound result = (BiddingRound) q.getSingleResult();
//            resp.setTutorialRoundOpen(true);
//        } catch (NoResultException e){
//            resp.setTutorialRoundOpen(false);
//        }
//        
//        return Response.status(Status.OK).entity(resp).build();
//    }
    
//    @Path("retrieveFutureBiddingRounds")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response retrieveFutureBiddingRounds(){
//        Query q = em.createQuery("SELECT br FROM BiddingRound br WHERE br.startTime > :currentTime");
//        q.setParameter("currentTime", new Date());
//        List<BiddingRound> rounds = q.getResultList();
//        
//        if(rounds.isEmpty()){
//            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No future bidding rounds available")).build();
//        } else {
//            return Response.status(Status.OK).entity(new RetrieveBiddingRoundsRsp(rounds)).build();
//        }
//    }
//    
//    @Path("retrieveAllBiddingRounds")
//    @GET
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response retrieveAllBiddingRounds(){
//        Query q = em.createQuery("SELECT br FROM BiddingRound br");
//        List<BiddingRound> rounds = q.getResultList();
//        
//        if(rounds.isEmpty()){
//            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No future bidding rounds available")).build();
//        } else {
//            return Response.status(Status.OK).entity(new RetrieveBiddingRoundsRsp(rounds)).build();
//        }
//    }
    
//    @Path("setModuleRound")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response createModuleRound(@QueryParam("startDate") String startDateStr, 
//            @QueryParam("endDate") String endDateStr, @QueryParam("userId") Long userId){
////        Timestamp format in JSON should be yyyy-[m]m-[d]d hh:mm:ss[.f...]
//
//        try{
//            Date startDate = dateFormatter.parse(startDateStr);
//            Date endDate = dateFormatter.parse(endDateStr);
//            
//            System.out.println(startDate + " " + endDate);
//            
//            // Check If User has access
//            User user = em.find(User.class, userId);
//            if(user == null || user.getAccessRight() != AccessRightEnum.Admin){
//                return Response.status(Status.FORBIDDEN)
//                        .entity(new ErrorRsp("User doesn't have access to this function"))
//                        .build();
//            }
//            
//            //
//            if(startDate.after(endDate) || startDate.before(new Date())){
//                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Illegal date inputs")).build();
//            }
//            
//            // Check if there's another round at the same time
//            Query q = em.createQuery("SELECT br FROM BiddingRound br WHERE br.roundType=:roundType "
//                    + "AND ((br.startTime <= :startTime AND br.endTime > :startTime) OR "
//                    + "(br.startTime < :endTime AND br.endTime >= :startTime))");
//            q.setParameter("startTime", startDate);
//            q.setParameter("endTime", endDate);
//            q.setParameter("roundType", BiddingRoundEnum.Module);
//            List<Object> result = q.getResultList();
//            if(!result.isEmpty() && result.get(0) != null){
//                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("There's another round at the same time")).build();
//            }
//             
//            BiddingRound round = new BiddingRound();
//            round.setStartTime(startDate);
//            round.setEndTime(endDate);
//            round.setRoundType(BiddingRoundEnum.Module);
//            em.persist(round);
//            em.flush();
//            
//            return Response.status(Status.OK).build();
//             
//        } catch (Exception e){
//            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
//        }
//    }
//    
//    @Path("deleteRound")
//    @DELETE
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response deleteRound(@QueryParam("userId") Long userId, @QueryParam("roundId") Long roundId){
//        // Check If User has access
//        User user = em.find(User.class, userId);
//        if(user == null || user.getAccessRight() != AccessRightEnum.Admin){
//            return Response.status(Status.FORBIDDEN)
//                    .entity(new ErrorRsp("User doesn't have access to this function"))
//                    .build();
//        }
//            
//        try{
//            BiddingRound round = em.find(BiddingRound.class, roundId);
//            if(round == null){
//                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No round with the given id")).build();
//            }
//            em.remove(round);
//            em.flush();
//            return Response.status(Status.OK).build();
//        } catch (Exception e){
//            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
//        }
//    }
//    
//    @Path("setTutorialRound")
//    @PUT
//    @Produces(MediaType.APPLICATION_JSON)
//    public Response createTutorialRound(@QueryParam("startDate") String startDateStr, 
//            @QueryParam("endDate") String endDateStr, @QueryParam("userId") Long userId){
////        Timestamp format in JSON should be yyyy-[m]m-[d]d hh:mm:ss[.f...]
//
//        try{
//            Date startDate = dateFormatter.parse(startDateStr);
//            Date endDate = dateFormatter.parse(endDateStr);
//            
//            // Check If User has access
//            User user = em.find(User.class, userId);
//            if(user == null || user.getAccessRight() != AccessRightEnum.Admin){
//                return Response.status(Status.FORBIDDEN)
//                        .entity(new ErrorRsp("User doesn't have access to this function"))
//                        .build();
//            }
//            
//            if(startDate.after(endDate) || startDate.before(new Date())){
//                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Illegal date inputs")).build();
//            }
//            
//            // Check if there's another round at the same time
//            Query q = em.createQuery("SELECT br FROM BiddingRound br WHERE br.roundType=:roundType "
//                    + "AND ((br.startTime <= :startTime AND br.endTime > :startTime) OR "
//                    + "(br.startTime < :endTime AND br.endTime >= :startTime))");
//            q.setParameter("startTime", startDate);
//            q.setParameter("endTime", endDate);
//            q.setParameter("roundType", BiddingRoundEnum.Tutorial);
//            List<Object> result = q.getResultList();
//            if(!result.isEmpty() && result.get(0) != null){
//                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("There's another round at the same time")).build();
//            }
//             
//            BiddingRound round = new BiddingRound();
//            round.setStartTime(startDate);
//            round.setEndTime(endDate);
//            round.setRoundType(BiddingRoundEnum.Tutorial);
//            em.persist(round);
//            em.flush();
//            
//            return Response.status(Status.OK).build();
//        } catch (Exception e){
//            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
//        }
//    }
    
    @Path("enrollModule")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response enrollModule(@QueryParam("userId") Long userId, @QueryParam("moduleId") Long moduleId){
        // Check session open or not
        if(!isModuleRoundOpen()){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module enrollment is closed")).build();
        }
        
        // Verify user
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Student){
            return Response
                    .status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access right for this function!"))
                    .build();
        }
        
        // Check the number of credits the student has registered for
        
        try {
            Module module = em.find(Module.class, moduleId);
            if(module == null){
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Module Not Found")).build();
            } else if (module.getStudentList().contains(user)){
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Student already enrolled in the module")).build();
            } else if(!module.getYearOffered().equals(AcademicYearSessionBean.getYear())
                    || module.getSemesterOffered() != AcademicYearSessionBean.getSemester()){
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module isn't offered this semester")).build();
            }else if(module.getMaxEnrollment() <= module.getStudentList().size()){
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module already full")).build();
            } else {
                module.getStudentList().add(user);
                user.getStudentModuleList().add(module);
                em.flush();
            }
            return Response.status(Status.OK).build();
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @Path("enrollModuleAdmin")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response enrollModule(@QueryParam("userId") Long userId, @QueryParam("moduleId") Long moduleId, @QueryParam("adminId") Long adminId){
        // Check session open or not
        if(!isModuleRoundOpen()){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module enrollment is closed")).build();
        }
        
        // Verify user
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Student){
            return Response
                    .status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("Student with the given id doesn't exist!"))
                    .build();
        }
        
        User admin = em.find(User.class, userId);
        if(admin == null || admin.getAccessRight() != AccessRightEnum.Admin){
            return Response
                    .status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access right for this function!"))
                    .build();
        }
        
        // Check the number of credits the student has registered for
        
        try {
            Module module = em.find(Module.class, moduleId);
            if(module == null){
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Module Not Found")).build();
            } else if (module.getStudentList().contains(user)){
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Student already enrolled in the module")).build();
            } else if(!module.getYearOffered().equals(AcademicYearSessionBean.getYear())
                    || module.getSemesterOffered() != AcademicYearSessionBean.getSemester()){
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module isn't offered this semester")).build();
            }else if(module.getMaxEnrollment() <= module.getStudentList().size()){
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module already full")).build();
            } else {
                module.getStudentList().add(user);
                user.getStudentModuleList().add(module);
                em.flush();
            }
            return Response.status(Status.OK).build();
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @Path("dropModule")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response dropModule(@QueryParam("userId") Long userId, @QueryParam("moduleId") Long moduleId){
        // Verify user
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Student){
            return Response
                    .status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access right for this function!"))
                    .build();
        }
        
        try{
            Module module = em.find(Module.class, moduleId);
            if(module == null){
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Module Not Found")).build();
            } else if(!module.getStudentList().contains(user)){
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Student isn't enrolled in the module")).build();
            } else {
                module.getStudentList().remove(user);
                user.getStudentModuleList().remove(module);
                em.flush();
            }
            
            return Response.status(Status.OK).build();
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build(); 
        }
    }
    
    @Path("retrieveStudentModules/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveStudentModules(@PathParam("userId") Long userId){
        
        // Verify user
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Student){
            return Response
                    .status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access right for this function!"))
                    .build();
        }
        
        try{
            Query query = em.createQuery("SELECT m FROM Module m join m.studentList stu "
                    + "WHERE m.semesterOffered = :semester AND m.yearOffered = :year");
            query.setParameter("semester", AcademicYearSessionBean.getSemester());
            query.setParameter("year", AcademicYearSessionBean.getYear());
//            query.setParameter("userId", userId);
            List<Module> modules = query.getResultList();
            RetrieveEnrolledStudentModulesRsp resp = new RetrieveEnrolledStudentModulesRsp(new ArrayList<>());
            if(modules.isEmpty()|| modules.get(0) == null){
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("User has no enrolled modules")).build();
            } else {
                for (Module m: modules){
                    User teacher = m.getAssignedTeacher();
                    User teacherCopy = new User(teacher.getFirstName(), teacher.getLastName(), teacher.getEmail(),
                            teacher.getUsername(), null, teacher.getGender(), teacher.getAccessRight(),
                            null, null, null, null, null, null, null);
                    resp.getModules().add(
                            new Module(m.getModuleId(),m.getCode(), m.getTitle(), m.getDescription(),
                                    m.getFeedback(), m.getSemesterOffered(), m.getYearOffered(),
                                    m.getCreditUnit(), m.getGrade(), m.getMaxEnrollment(), 
                                    null, null, null, null, null, null, null, null, null, null,
                                    teacherCopy, null, null, null, m.isHasExam(), m.getExamTime(), m.getExamVenue(),
                                    m.getLectureDetails()));
                }
                return Response.status(Status.OK).entity(resp).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();            
        }
    }
    
    @Path("retrieveAvailableModules")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAvailableModules(){
        
        try{
            Query query = em.createQuery("SELECT m FROM Module m "
                    + "WHERE m.semesterOffered = :semester AND m.yearOffered = :year");
            query.setParameter("semester", AcademicYearSessionBean.getSemester());
            query.setParameter("year", AcademicYearSessionBean.getYear());
            List<Module> modules = query.getResultList();
            RetrieveEnrolledStudentModulesRsp resp = new RetrieveEnrolledStudentModulesRsp();
            resp.setModules(new ArrayList<>());
            if(modules.isEmpty()|| modules.get(0) == null){
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No available modules")).build();
            } else {
                for (Module m: modules){
                    User teacher = m.getAssignedTeacher();
                    User teacherCopy = new User(teacher.getFirstName(), teacher.getLastName(), teacher.getEmail(),
                            teacher.getUsername(), null, teacher.getGender(), teacher.getAccessRight(),
                            null, null, null, null, null, null, null);
                    resp.getModules().add(
                            new Module(m.getModuleId(),m.getCode(), m.getTitle(), m.getDescription(),
                                    m.getFeedback(), m.getSemesterOffered(), m.getYearOffered(),
                                    m.getCreditUnit(), m.getGrade(), m.getMaxEnrollment(), 
                                    null, null, null, null, null, null, null, null, null, null,
                                    teacherCopy, null, null, null, m.isHasExam(), m.getExamTime(), m.getExamVenue(),
                                    m.getLectureDetails()));
                }
                return Response.status(Status.OK).entity(resp).build();
            }
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();            
        }
    }
    
    @Path("enrollTutorial")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response enrollTutorial(@QueryParam("userId") Long userId, @QueryParam("tutorialId") Long tutorialId){

        // Check session open or not
        if(!isTutorialRoundOpen()){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Tutorial enrollment is closed")).build();
        }
        
        // Verify user
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Student){
            return Response
                    .status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access right for this function!"))
                    .build();
        }
        
        // Check if student enrolled in the module of the tutorial.
        Tutorial tutorial = em.find(Tutorial.class, tutorialId);
        if(tutorial == null){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Tutorial not found!")).build();
        }
        System.out.println(tutorial);
        if(!tutorial.getModule().getStudentList().contains(user)){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Student not enrolled in the module yet")).build();
        }
        
        if(!tutorial.getModule().getYearOffered().equals(AcademicYearSessionBean.getYear())
                || tutorial.getModule().getSemesterOffered() != AcademicYearSessionBean.getSemester()){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("This tutorial isn't offered this semester")).build();
        }
        
        if(tutorial.getStudentList().contains(user)){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Student has already been enrolled to this tutorial before")).build();
        }
        
        try{
            if(tutorial.getMaxEnrollment() > tutorial.getStudentList().size()){
                tutorial.getStudentList().add(user);
                user.getTutorials().add(tutorial);
                em.flush();
                Module m = tutorial.getModule();
                Module mCopy = new Module(m.getModuleId(),m.getCode(), m.getTitle(), m.getDescription(),
                                    m.getFeedback(), m.getSemesterOffered(), m.getYearOffered(),
                                    m.getCreditUnit(), m.getGrade(), m.getMaxEnrollment(), 
                                    null, null, null, null, null, null, null, null, null, null,
                                    null, null, null, null, m.isHasExam(), m.getExamTime(), m.getExamVenue(),
                                    m.getLectureDetails());
                Tutorial t = new Tutorial(tutorial.getTutorialId(), tutorial.getMaxEnrollment(), tutorial.getVenue(), tutorial.getTiming(), null, mCopy);
                return Response.status(Status.OK).entity(t).build();
            } else {
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Tutorial slot already full!")).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build(); 
        }
    }
    
    @Path("dropTutorial")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response dropTutorial(@QueryParam("userId") Long userId, @QueryParam("tutorialId") Long tutorialId){
        // Verify user
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Student){
            return Response
                    .status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access right for this function!"))
                    .build();
        }
        
        Tutorial tutorial = em.find(Tutorial.class, tutorialId);
        if(tutorial == null){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Tutorial not found!")).build();
        }
        
        try{
            user.getTutorials().remove(tutorial);
            tutorial.getStudentList().remove(user);
            em.flush();
            return Response.status(Status.OK).build();
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build(); 
        }
    }
    
    @Path("retrieveStudentTutorials/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveStudentTutorials(@PathParam("userId") Long userId){
        // Verify user
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Student){
            return Response
                    .status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access right for this function!"))
                    .build();
        }
        
        try{
            RetrieveEnrolledStudentTutorialsRsp resp = new RetrieveEnrolledStudentTutorialsRsp(new ArrayList<>());
            for(Tutorial t: user.getTutorials()){
                if(t.getModule().getYearOffered().equals(AcademicYearSessionBean.getYear()) 
                        && t.getModule().getSemesterOffered() == AcademicYearSessionBean.getSemester()){
                    Module m = t.getModule();
                    Module mCopy = new Module(m.getModuleId(),m.getCode(), m.getTitle(), m.getDescription(),
                                    m.getFeedback(), m.getSemesterOffered(), m.getYearOffered(),
                                    m.getCreditUnit(), m.getGrade(), m.getMaxEnrollment(), 
                                    null, null, null, null, null, null, null, null, null, null,
                                    null, null, null, null, m.isHasExam(), m.getExamTime(), m.getExamVenue(),
                                    m.getLectureDetails());
                    Tutorial tCopy = new Tutorial(t.getTutorialId(), t.getMaxEnrollment(), t.getVenue(), t.getTiming(), null, mCopy);
                    resp.getTutorials().add(tCopy);
                } 
            }
            if(resp.getTutorials().isEmpty()){
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Student isn't enrolled in any tutorials yet")).build();
            }
            return Response.status(Status.OK).entity(resp).build();
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build(); 
        }
    }
    
    @Path("retrieveTutorialsToBid/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTutorialsToBid(@PathParam("userId") Long userId){
        // Verify user
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Student){
            return Response
                    .status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access right for this function!"))
                    .build();
        }
        
        try{
            RetrieveEnrolledStudentTutorialsRsp resp = new RetrieveEnrolledStudentTutorialsRsp(new ArrayList<>());
            for(Module m: user.getStudentModuleList()){
                Module mCopy = new Module(m.getModuleId(),m.getCode(), m.getTitle(), m.getDescription(),
                                        m.getFeedback(), m.getSemesterOffered(), m.getYearOffered(),
                                        m.getCreditUnit(), m.getGrade(), m.getMaxEnrollment(), 
                                        null, null, null, null, null, null, null, null, null, null,
                                        null, null, null, null, m.isHasExam(), m.getExamTime(), m.getExamVenue(),
                                        m.getLectureDetails());
                if(m.getYearOffered().equals(AcademicYearSessionBean.getYear()) && m.getSemesterOffered() == AcademicYearSessionBean.getSemester()){
                    for(Tutorial t: m.getTutorials()){

                        Tutorial tCopy = new Tutorial(t.getTutorialId(), t.getMaxEnrollment(), t.getVenue(), t.getTiming(), null, mCopy);
                        resp.getTutorials().add(tCopy);
                    }
                }
            }
            return Response.status(Status.OK).entity(resp).build();
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build(); 
        }
    }
}
