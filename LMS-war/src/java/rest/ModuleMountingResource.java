/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.MountModuleReq;
import datamodel.rest.UpdateModule;
import datamodel.rest.CheckUserLogin;
import entities.Module;
import entities.User;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.DELETE;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static util.AccessRightEnum.Admin;
import util.exception.InputDataValidationException;
import util.exception.ModuleNotFoundException;

/**
 *
 * @author Vixson
 */
@Stateless
@Path("User")
public class ModuleMountingResource {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;

    public ModuleMountingResource() {
    }

    public boolean isLogin(User user) {
        user = em.find(User.class, user.getId());
        if (user != null) {
            return true;
        }
        return false;
    }

    public User userLogin(String username, String password) {
        User user = em.find(User.class, username);
        if (user != null) {
            if (user.getPassword().equals(password)) {
                return user;
            }
        }
        throw new NotFoundException("Username does not exist");
    }

    @PUT
    @Path(value = "mountModule")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response mountModule(MountModuleReq mountModuleReq) {

        if (isLogin(mountModuleReq.getUser()) == true && mountModuleReq.getUser().getAccessRight() == Admin) {

            try {
                Module module = new Module();
                module.setCode(mountModuleReq.getCode());
                module.setTitle(mountModuleReq.getTitle());
                module.setDescription(mountModuleReq.getDescription());
                module.setSemesterOffered(mountModuleReq.getSemesterOffered());
                module.setYearOffered(mountModuleReq.getYearOffered());
                module.setCreditUnit(mountModuleReq.getCreditUnit());
                module.setMaxEnrollment(mountModuleReq.getMaxEnrollment());
                module.setHasExam(mountModuleReq.isHasExam());
                module.setExamTime(mountModuleReq.getExamTime());
                module.setExamVenue(mountModuleReq.getExamVenue());
                module.setAssignedTeacher(mountModuleReq.getAssignedTeacher());

                module.setAnnoucementList(null);
                module.setAttandanceList(null);
                module.setClassGroupList(null);
                module.setConsultationList(null);
                module.setFeedback(null);
                module.setFolderList(null);
                module.setForumPostList(null);
                module.setGrade(null);
                module.setGradeItemList(null);
                module.setLessonPlanList(null);
                module.setPublicUserList(null);
                module.setQuizList(null);
                module.setStudentList(null);

                em.persist(module);
                em.flush();

                return Response.status(Response.Status.OK).entity(module).build();
            } catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @Path(value = "getModule/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModuleById(@PathParam("id") Long moduleId) {
        try {
            if (em.find(Module.class, moduleId) == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Module does not exist").build();
            }

            Query query = em.createQuery("select m from Module m where m.module = :moduleId");
            Module module = (Module) query.getSingleResult();
            return Response.status(Response.Status.OK).entity(module).build();

        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path(value = "getAllModule")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllModule() {
        try {
            Query query = em.createQuery("select m from Module m");
            List<Module> moduleList = query.getResultList();

            if (moduleList != null && !moduleList.isEmpty()) {
                return Response.status(Response.Status.OK).entity(moduleList).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity("No module found").build();
            }
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path(value = "removeModule/{code}")
    @DELETE
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeModule(@QueryParam("code") String code, CheckUserLogin checkUserLogin) {

        if (isLogin(checkUserLogin.getUser()) == true && checkUserLogin.getUser().getAccessRight() == Admin) {

            try {
                if (em.find(Module.class, code) == null) {
                    return Response.status(Response.Status.BAD_REQUEST).entity("Module does not exist").build();
                }

                Query query = em.createQuery("select m from Module m where m.code = :code");
                Module module = (Module) query.getSingleResult();

                if (module != null) {
                    em.remove(module);
                    return Response.status(Response.Status.OK).entity(query).build();
                }
                return Response.status(Response.Status.NOT_FOUND).entity("No module found").build();

            } catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @Path(value = "updateModule")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateModule(UpdateModule updateModule) {

        if (isLogin(updateModule.getUser()) == true && updateModule.getUser().getAccessRight() == Admin) {

            try {

                Long moduleId = updateModule.getModuleId();
                Module module = em.find(Module.class, moduleId);
                //em.createQuery("select m from Module m where m.moduleId = :moduleId");
                //Module module = (Module) query.getSingleResult();

                if (module != null) {
                    module.setCode(updateModule.getCode());
                    module.setTitle(updateModule.getTitle());
                    module.setDescription(updateModule.getDescription());
                    module.setSemesterOffered(updateModule.getSemesterOffered());
                    module.setYearOffered(updateModule.getYearOffered());
                    module.setCreditUnit(updateModule.getCreditUnit());
                    module.setMaxEnrollment(updateModule.getMaxEnrollment());
                    module.setAssignedTeacher(updateModule.getAssignedTeacher());
                    module.setHasExam(updateModule.isHasExam());
                    module.setExamTime(updateModule.getExamTime());
                    module.setExamVenue(updateModule.getExamVenue());

                    em.merge(module);
                    em.flush();

                    return Response.status(Response.Status.OK).entity(module).build();
                }
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
                /**
                 * }catch(ModuleNotFoundException | InputDataValidationException
                 * ex) { return
                 * Response.status(Response.Status.BAD_REQUEST).entity("Module
                 * does not exist").build(); *
                 */
            } catch (Exception ex) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
            }
        }
        return Response.status(Response.Status.UNAUTHORIZED).build();
    }
}
