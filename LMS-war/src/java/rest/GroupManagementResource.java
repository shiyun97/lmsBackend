/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.CheckUserLogin;
import datamodel.rest.CreateClassGroup;
import datamodel.rest.GetClassGroupRsp;
import datamodel.rest.JoinClassGroup;
import datamodel.rest.QuitClassGroup;
import datamodel.rest.UpdateClassGroup;
import datamodel.rest.ViewClassGroup;
import entities.ClassGroup;
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
import javax.ws.rs.DELETE;
import javax.ws.rs.NotFoundException;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import static util.AccessRightEnum.Teacher;

/**
 *
 * @author Vixson
 */
@Stateless
@Path("ManageGroup")
public class GroupManagementResource {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;

    public GroupManagementResource() {
    }

    public boolean isLogin(User user) {
        user = em.find(User.class, user.getId());
        if (user != null) {
            return true;
        }
        return false;
    }

    @PUT
    @Path(value = "createClassGroup")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createClassGroup(CreateClassGroup createClassGroup) {

        //if (createClassGroup.getUser().getAccessRight() == Teacher) {
        try {
            ClassGroup classGroup = new ClassGroup();
            classGroup.setMaxMember(createClassGroup.getMaxMember());
            classGroup.setStartTs(createClassGroup.getStartTs());
            classGroup.setCloseTs(createClassGroup.getCloseTs());
            classGroup.setModule(createClassGroup.getModule());

            classGroup.setName(createClassGroup.getName());

            em.persist(classGroup);
            em.flush();

            return Response.status(Response.Status.OK).entity(classGroup).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        //}
        //return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @Path(value = "getClassGroup/{id}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getClassGroupById(@PathParam("id") Long classGroupId) {
        try {
            ClassGroup classGroup = em.find(ClassGroup.class, classGroupId);
            if (classGroup == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Group does not exist").build();
            }
            List<User> members = new ArrayList<>();
            for (User u : members) {
                u.getEmail();
                u.getFirstName();
                u.getGender();
                u.getId();
                u.getLastName();
                members.add(u);
            }
            ClassGroup classGroupCopy = new ClassGroup(classGroup.getClassGroupId(), classGroup.getName(),
                    classGroup.getStartTs(), classGroup.getCloseTs(), classGroup.getModule(),
                    classGroup.getMaxMember(), members);

            return Response.status(Response.Status.OK).entity(classGroupCopy).build();

        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @GET
    @Path(value = "getAllClassGroup")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllClassGroup() {
        try {
            Query query = em.createQuery("select g from ClassGroup g");
            List<ClassGroup> classGroupList = query.getResultList();

            GetClassGroupRsp rsp = new GetClassGroupRsp(new ArrayList<>());

            if (classGroupList == null && classGroupList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No group found").build();

            }
            List<User> members = new ArrayList<>();
            for (User u : members) {
                u.getEmail();
                u.getFirstName();
                u.getGender();
                u.getId();
                u.getLastName();
                members.add(u);
            }
            for (ClassGroup c : classGroupList) {
                rsp.getUserList().add(new ClassGroup(c.getClassGroupId(), c.getName(),
                        c.getStartTs(), c.getCloseTs(), c.getModule(), c.getMaxMember(),
                        members));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @DELETE
    @Path(value = "deleteGroup")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGroup(@QueryParam("classGroupId") Long classGroupId
    ) {

        //if (checkUserLogin.getUser().getAccessRight() == Admin) {
        try {
            ClassGroup classGroup = em.find(ClassGroup.class, classGroupId);
            if (classGroup == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No group found").build();
            }
            em.remove(classGroup);

            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        //}
        //return Response.status(Response.Status.UNAUTHORIZED).build();
    }

    @Path(value = "updateClassGroup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateClassGroup(UpdateClassGroup updateClassGroup, @QueryParam("classGroupId") Long classGroupId) {

        //if (updateClassGroup.getUser().getAccessRight() == Teacher) {
        try {
            ClassGroup classGroup = em.find(ClassGroup.class, classGroupId);

            if (classGroup != null) {
                classGroup.setMaxMember(updateClassGroup.getMaxMember());
                classGroup.setStartTs(updateClassGroup.getStartTs());
                classGroup.setCloseTs(updateClassGroup.getCloseTs());
                classGroup.setModule(updateClassGroup.getModule());
                classGroup.setName(updateClassGroup.getName());

                if (classGroup.getMembers().isEmpty()) {
                    return Response.status(Response.Status.NOT_FOUND).entity("Group has no members").build();
                }
                List<User> members = classGroup.getMembers();
                for (User u : members) {
                    u.setId(updateClassGroup.getUserId());
                }

                em.merge(classGroup);
                em.flush();

                return Response.status(Response.Status.OK).entity(classGroup).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity("Group does not exist").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
        //}
        //return Response.status(Response.Status.UNAUTHORIZED).build();
    }

//FOR THE STUDENTS    
//RESTFUL    
    @Path(value = "joinClassGroup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response joinClassGroup(JoinClassGroup joinClassGroup
    ) {
        try {
            ClassGroup classGroup = em.find(ClassGroup.class, joinClassGroup.getClassGroupId());

            if (classGroup == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Group does not exist").build();
            }

            if (classGroup.getMembers().size() == classGroup.getMaxMember()) {
                return Response.status(Response.Status.BAD_REQUEST).entity("Group is full").build();
            }
            User user = em.find(User.class, joinClassGroup.getUserId());
            classGroup.getMembers().add(user);

            return Response.status(Response.Status.OK).entity("You have joined the group").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path(value = "quitClassGroup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response quitClassGroup(QuitClassGroup quitClassGroup
    ) {
        try {

            ClassGroup classGroup = em.find(ClassGroup.class, quitClassGroup.getClassGroupId());
            if (classGroup == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Group does not exist").build();
            }

            User user = em.find(User.class, quitClassGroup.getUserId());

            classGroup.getMembers().remove(user);
            em.merge(classGroup);

            return Response.status(Response.Status.OK).entity("You have quit the group").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

    @Path(value = "viewClassGroup")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewClassGroup(ViewClassGroup viewClassGroup
    ) {
        try {
            ClassGroup classGroup = em.find(ClassGroup.class, viewClassGroup.getClassGroupId());

            if (classGroup == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("Group does not exist").build();
            }

            Query query = em.createQuery("select u from User u where u.classGroupList = :classGroupId");
            query.setParameter("classGroupId", viewClassGroup.getClassGroupId());
            List<User> memberList = (List<User>) query.getResultList();

            for (int x = 0; x < memberList.size(); x++) {
                if (viewClassGroup.getUser().equals(classGroup.getMembers().get(x))) {
                    return Response.status(Response.Status.OK).entity(memberList).build();
                }
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity("Not allowed").build();
        } catch (Exception ex) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).build();
        }
    }

//NORMAL
    /*public void joinClassGroup(ClassGroup classGroup, User user) throws GroupIsFullException {
        classGroup = em.find(ClassGroup.class, classGroup.getClassGroupId());

        if (classGroup.getMembers().size() < classGroup.getMaxMember()) {
            classGroup.getMembers().add(user);

            em.merge(classGroup);
            //return(classGroup);      
        } else {
            throw new GroupIsFullException("Group is full");
        }
    }

    public void quitClassGroup(ClassGroup classGroup, User user) {
        classGroup = em.find(ClassGroup.class, classGroup.getClassGroupId());

        classGroup.getMembers().remove(user);
        em.merge(classGroup);

        //return(classGroup);
    }

    public List<User> viewClassGroup(ClassGroup classGroup, User user) throws UnathorisedException {
        classGroup = em.find(ClassGroup.class, classGroup.getClassGroupId());

        Query query = em.createQuery("select u from User u where u.classGroupList = :classGroupId");
        List<User> memberList = (List<User>) query.getResultList();

        for (int x = 0; x < memberList.size(); x++) {
            if (user.equals(classGroup.getMembers().get(x))) {
                //Query query = em.createQuery("select u from User u where u.classGroup = :classGroupId");
                //List<User> memberList = (List<User>) query.getResultList();
                return (memberList);
            }
        }
        throw new UnathorisedException("You do not belong to this group");
    }*/
}
