/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.ErrorRsp;
import datamodel.rest.RetrieveEnrolledStudentModulesRsp;
import datamodel.rest.RetrieveModule;
import ejb.AcademicYearSessionBean;
import entities.Module;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
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
 * 
 */
@Path("module")
@Stateless
public class ModuleResource {
    
    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;


    @Path("retrieveAllModules")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllModules(){
        
        try{
            Query query = em.createQuery("SELECT m FROM Module m ");
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
                                    m.getSemesterOffered(), m.getYearOffered(),
                                    m.getCreditUnit(), m.getGrade(), m.getMaxEnrollment(), 
                                    null, null, null, null, null, null, null, null, null, null,
                                    teacherCopy, null, null, null, m.isHasExam(), m.getExamTime(), m.getExamVenue(),
                                    m.getLectureDetails(), m.getDepartment(), m.getFaculty()));
                }
                return Response.status(Status.OK).entity(resp).build();
            }
        } catch (Exception e){
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();            
        }
    }
    
    @Path("retrieveTeacherModules/{userId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveTeacherModules(@PathParam("userId") Long userId){
        
        // Verify user
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response
                    .status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access right for this function!"))
                    .build();
        }
        
        try{
            Query query = em.createQuery("SELECT m FROM Module m WHERE m.assignedTeacher.userId= :userId ");
            query.setParameter("userId", userId);
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
                                    m.getSemesterOffered(), m.getYearOffered(),
                                    m.getCreditUnit(), m.getGrade(), m.getMaxEnrollment(), 
                                    null, null, null, null, null, null, null, null, null, null,
                                    teacherCopy, null, null, null, m.isHasExam(), m.getExamTime(), m.getExamVenue(),
                                    m.getLectureDetails(), m.getDepartment(), m.getFaculty()));
                }
                return Response.status(Status.OK).entity(resp).build();
            }
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();            
        }
    }

}
