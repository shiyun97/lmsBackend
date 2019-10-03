/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.MountModuleReq;
import datamodel.rest.UpdateModule;
import datamodel.rest.CheckUserLogin;
import datamodel.rest.GetModuleRsp;
import datamodel.rest.GetTutorialRsp;
import datamodel.rest.GetUserRsp;
import datamodel.rest.MountTutorial;
import datamodel.rest.UpdateModuleTutorial;
import entities.Feedback;
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

/**
 *
 * @author Vixson
 */
@Stateless
@Path("ModuleMounting")
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

    /*module.setAnnoucementList(null);
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
                module.setFeedbackList(null)*/
    @PUT
    @Path(value = "mountModule")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response mountModule(MountModuleReq mountModuleReq) {
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
            module.setAssignedTeacher(mountModuleReq.getUser());
            module.setLectureDetails(mountModuleReq.getLectureDetails());
            em.persist(module);
            em.flush();
            /*Tutorial tutorial = new Tutorial();
            tutorial.setMaxEnrollment(mountModuleReq.getMaxEnrollment());
            tutorial.setVenue(mountModuleReq.getVenue());
            tutorial.setTiming(mountModuleReq.getTiming());
            tutorial.setModule(mountModuleReq.getModule());
            em.persist(tutorial);
            em.flush();
            module.getTutorials().add(tutorial);*/

            return Response.status(Response.Status.OK).entity(module).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @PUT
    @Path(value = "mountTutorial")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response mountTutorial(MountTutorial mountTutorial, @QueryParam("moduleId") Long moduleId) {
        try {
            Module module = em.find(Module.class, moduleId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }
            Tutorial tutorial = new Tutorial();
            tutorial.setMaxEnrollment(mountTutorial.getMaxEnrollment());
            tutorial.setVenue(mountTutorial.getVenue());
            tutorial.setTiming(mountTutorial.getTiming());;
            tutorial.setModule(module);
            em.persist(tutorial);
            em.flush();
            module.getTutorials().add(tutorial);
            Tutorial tutorialCopy = new Tutorial(tutorial.getTutorialId(), tutorial.getMaxEnrollment(),
                    tutorial.getVenue(), tutorial.getTiming(), null, null);
            return Response.status(Response.Status.OK).entity(tutorialCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path(value = "getModule/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModuleById(@PathParam("id") Long moduleId) {
        try {
            Module module = em.find(Module.class, moduleId);

            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }

            User teacher = module.getAssignedTeacher();
            User teacherCopy = new User(null, teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getEmail(),
                    teacher.getUsername(), null, teacher.getGender(), teacher.getAccessRight(),
                    null, null, null, null, null, null, null);

            List<Tutorial> tutorials = new ArrayList<>();
            for (Tutorial t : tutorials) {
                t.getMaxEnrollment();
                t.getVenue();
                t.getTiming();
                t.getStudentList();
                t.getModule();

                tutorials.add(t);
            }

            List<Feedback> feedbackList = new ArrayList<>();
            for (Feedback f : feedbackList) {
                f.getClass();
                f.getCreateTs();
                f.getFeedback();

                feedbackList.add(f);
            }

            Module moduleCopy = new Module(module.getModuleId(), module.getCode(), module.getTitle(),
                    module.getDescription(), module.getSemesterOffered(),
                    module.getYearOffered(), module.getCreditUnit(), null, module.getMaxEnrollment(),
                    null, null, null, null, null, null, null, null, null, null,
                    teacherCopy, null, feedbackList, tutorials, module.isHasExam(),
                    module.getExamTime(), module.getExamVenue(), module.getLectureDetails(), module.getFaculty(),
                    module.getDepartment());

            return Response.status(Response.Status.OK).entity(moduleCopy).build();

        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path(value = "getModuleByCode/{code}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getModuleByCode(@PathParam("code") String code) {
        try {
            Query query = em.createQuery("select m from Module m where m.code = :code");
            query.setParameter("code", code);
            Module module = (Module) query.getSingleResult();

            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }

            User teacher = module.getAssignedTeacher();
            User teacherCopy = new User(null, teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getEmail(),
                    teacher.getUsername(), null, teacher.getGender(), teacher.getAccessRight(),
                    null, null, null, null, null, null, null);

            List<Tutorial> tutorials = new ArrayList<>();
            for (Tutorial t : tutorials) {
                t.getMaxEnrollment();
                t.getVenue();
                t.getTiming();
                t.getStudentList();
                t.getModule();

                tutorials.add(t);
            }

            List<Feedback> feedbackList = new ArrayList<>();
            for (Feedback f : feedbackList) {
                f.getClass();
                f.getCreateTs();
                f.getFeedback();

                feedbackList.add(f);
            }

            Module moduleCopy = new Module(module.getModuleId(), module.getCode(), module.getTitle(),
                    module.getDescription(), module.getSemesterOffered(),
                    module.getYearOffered(), module.getCreditUnit(), null, module.getMaxEnrollment(),
                    null, null, null, null, null, null, null, null, null, null,
                    teacherCopy, null, feedbackList, tutorials, module.isHasExam(),
                    module.getExamTime(), module.getExamVenue(), module.getLectureDetails(), module.getFaculty(),
                    module.getDepartment());

            return Response.status(Response.Status.OK).entity(moduleCopy).build();

        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path(value = "getAllModule")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllModule() {

        try {
            Query query = em.createQuery("select m from Module m");
            List<Module> moduleList = query.getResultList();

            GetModuleRsp rsp = new GetModuleRsp();
            rsp.setModule(new ArrayList<>());
            if (moduleList == null && moduleList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No module found").build();
            } else {
                for (Module module : moduleList) {
                    User teacher = module.getAssignedTeacher();
                    User teacherCopy = new User(null, teacher.getId(), teacher.getFirstName(), teacher.getLastName(), teacher.getEmail(),
                            teacher.getUsername(), null, teacher.getGender(), teacher.getAccessRight(),
                            null, null, null, null, null, null, null);

                    List<Tutorial> tutorials = new ArrayList<>();
                    for (Tutorial t : tutorials) {
                        t.getMaxEnrollment();
                        t.getVenue();
                        t.getTiming();
                        t.getStudentList();
                        t.getModule();
                        tutorials.add(t);
                    }
                    List<Feedback> feedbackList = new ArrayList<>();
                    for (Feedback f : feedbackList) {
                        f.getClass();
                        f.getCreateTs();
                        f.getFeedback();
                        feedbackList.add(f);
                    }
                    rsp.getModule().add(
                            new Module(module.getModuleId(), module.getCode(), module.getTitle(),
                                    module.getDescription(), module.getSemesterOffered(),
                                    module.getYearOffered(), module.getCreditUnit(), null, module.getMaxEnrollment(),
                                    null, null, null, null, null, null, null, null, null, null,
                                    teacherCopy, null, feedbackList, tutorials, module.isHasExam(),
                                    module.getExamTime(), module.getExamVenue(), module.getLectureDetails(), module.getFaculty(),
                                    module.getDepartment()));
                }
                return Response.status(Response.Status.OK).entity(rsp).build();
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path(value = "deleteModule")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteModule(@QueryParam("moduleId") Long moduleId) {

        //if (checkUserLogin.getUser().getAccessRight() == Admin) {
        try {
            Module module = em.find(Module.class, moduleId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No module found").build();

            }
            em.remove(module);

            return Response.status(Response.Status.OK).entity("Module deleted").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        //}
        //return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @Path(value = "deleteModuleByCode")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteModuleByCode(@QueryParam("code") String code, CheckUserLogin checkUserLogin) {

        //if (isLogin(checkUserLogin.getUser()) == true && checkUserLogin.getUser().getAccessRight() == Admin) {
        try {
            Query query = em.createQuery("select m from Module m where m.code = :code");
            query.setParameter("code", code);
            Module module = (Module) query.getSingleResult();

            if (module != null) {
                em.remove(module);
                return Response.status(Response.Status.OK).entity(module).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("No module found").build();

        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        //}
        //return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @Path(value = "updateModule")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateModule(UpdateModule updateModule, @QueryParam("moduleId") Long moduleId) {

        try {

            Module module = em.find(Module.class, moduleId);

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
                module.setLectureDetails(updateModule.getLectureDetails());

                em.merge(module);
                em.flush();

                return Response.status(Response.Status.OK).entity(module).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path(value = "updateModuleWithTutorial")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateModuleWithTutorial(UpdateModule updateModule, @QueryParam("moduleId") Long moduleId) {

        try {

            Module module = em.find(Module.class, moduleId);

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
                module.setLectureDetails(updateModule.getLectureDetails());

                if (module.getTutorials().isEmpty()) {
                    em.merge(module);
                    return Response.status(Response.Status.NOT_FOUND).entity("Module has no tutorial").build();
                }
                List<Tutorial> tutorials = module.getTutorials();
                for (Tutorial t : tutorials) {
                    t.setMaxEnrollment(updateModule.getMaxEnrollment());
                    t.setVenue(updateModule.getVenue());
                    t.setTiming(updateModule.getTiming());
                    t.setStudentList(updateModule.getStudentList());
                    em.merge(t);
                }
                em.flush();
                return Response.status(Response.Status.OK).entity(module).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path(value = "updateModuleDescription/{moduleId}")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateModuleDescription(String description, @PathParam("moduleId") Long moduleId) {

        try {

            Module module = em.find(Module.class, moduleId);

            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }

            module.setDescription(description);

//            em.merge(module);
            em.flush();

            return Response.status(Response.Status.OK).build();

        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(ex.getMessage()).build();
        }
    }

    @Path(value = "updateModuleTutorial")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateModuleTutorial(UpdateModuleTutorial updateModuleTutorial, @QueryParam("moduleId") Long moduleId) {

        try {
            Module module = em.find(Module.class, moduleId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module does not exist").build();
            }
            if (module.getTutorials().isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Module has no tutorial").build();
            }
            List<Tutorial> tutorials = module.getTutorials();
            for (Tutorial t : tutorials) {
                t.setMaxEnrollment(updateModuleTutorial.getMaxEnrollment());
                t.setVenue(updateModuleTutorial.getVenue());
                t.setTiming(updateModuleTutorial.getTiming());
                em.merge(t);
                em.flush();
            }
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path(value = "updateTutorial")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateTutorial(UpdateModuleTutorial updateModuleTutorial, @QueryParam("tutorialId") Long tutorialId) {

        try {
            Tutorial tutorial = em.find(Tutorial.class, tutorialId);
            if (tutorial == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Tutorial does not exist").build();
            }
            tutorial.setMaxEnrollment(updateModuleTutorial.getMaxEnrollment());
            tutorial.setVenue(updateModuleTutorial.getVenue());
            tutorial.setTiming(updateModuleTutorial.getTiming());
            em.merge(tutorial);
            em.flush();

            Tutorial tutorialCopy = new Tutorial(tutorial.getTutorialId(), tutorial.getMaxEnrollment(),
                    tutorial.getVenue(), tutorial.getTiming(), null, null);
            return Response.status(Response.Status.OK).entity(tutorialCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path(value = "getAllTutorialByModule")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllTutorialByModule(@QueryParam("moduleId") Long moduleId) {
        try {
            Module module = em.find(Module.class, moduleId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No module found").build();
            }
            /*Query query = em.createQuery("select t from Tutorial t where t.module = :moduleId");
            query.setParameter("moduleId", moduleId);
            List<Tutorial> tutorials = query.getResultList();*/
            GetTutorialRsp rsp = new GetTutorialRsp(new ArrayList<>(), new ArrayList<>());
            List<Tutorial> tutorials = module.getTutorials();
            if (tutorials == null && tutorials.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No tutorial found").build();
            } else {
                for (Tutorial tutorial : tutorials) {
                    Tutorial temp = em.find(Tutorial.class, tutorial.getTutorialId());
                    int currentEnrollment = temp.getStudentList().size();
                    /*List<User> students = tutorial.getStudentList();
                    for (User s : students) {
                        /*User user = em.find(User.class, userId);
                        if (tutorial.getStudentList().contains(user)) {
                        s.getAccessRight();
                        //s.getClassGroupList();
                        //s.getConsultationTimeslotList();
                        s.getEmail();
                        s.getFirstName();
                        s.getGender();
                        s.getLastName();
                        //s.getQuizAttemptList();
                        //s.getStudentModuleList();
                        //s.getSurveyAttemptList();
                        //s.getTutorials();
                        s.getUsername();
                        students.add(s);
                    }*/
                    //}
                    rsp.getTutorials().add(
                            new Tutorial(tutorial.getTutorialId(), tutorial.getMaxEnrollment(),
                                    tutorial.getVenue(), tutorial.getTiming(), null, null));
                    rsp.getCurrentEnrollment().add(new Integer(currentEnrollment));
                }
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path(value = "getAllStudentByModule")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllStudentByModule(@QueryParam("moduleId") Long moduleId) {
        try {
            Module module = em.find(Module.class, moduleId);
            if (module == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No module found").build();
            }
            GetUserRsp rsp = new GetUserRsp(new ArrayList<>());
            List<User> students = module.getStudentList();
            if (students == null && students.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No students found").build();
            }
            for (User s : students) {
                rsp.getUserList().add(
                        new User(null, s.getUserId(), s.getFirstName(),
                                s.getLastName(), s.getEmail(), s.getUsername(), null,
                                s.getGender(), s.getAccessRight(), null,
                                null, null,
                                null, null, null, null));

            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }
}
