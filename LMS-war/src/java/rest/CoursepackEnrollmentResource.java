/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.ErrorRsp;
import datamodel.rest.GetCoursepackRsp;
import entities.Cart;
import entities.Coursepack;
import entities.User;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.AccessRightEnum;

/**
 *
 * @author Vixson
 */
@Stateless
@Path("CoursepackEnrollment")
public class CoursepackEnrollmentResource {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;

    public CoursepackEnrollmentResource() {
    }

    @Path("studentEnrollCoursepack")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response studentEnrollCoursepack(@QueryParam("userId") Long userId, @QueryParam("coursepackId") Long coursepackId) {
        try {
            User student = em.find(User.class, userId);
            if (student == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("User not found")).build();
            }
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);
            if (coursepack == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Coursepack not found")).build();
            }
            if (student.getAccessRight() == AccessRightEnum.Student) {
                coursepack.getStudentList().add(student);
                student.getStudentCoursepackList().add(coursepack);
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorRsp("Not allowed")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("publicEnrollCoursepack")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response publicEnrollCoursepack(@QueryParam("userId") Long userId, @QueryParam("coursepackId") Long coursepackId) {
        try {
            User publicUser = em.find(User.class, userId);
            if (publicUser == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("User not found")).build();
            }
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);
            if (coursepack == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Coursepack not found")).build();
            }
            /*if(publicUser.getPayment() == coursepack.getPrice()){
                publicUser.setHasPaid(true);
            }*/
            if (publicUser.getAccessRight() == AccessRightEnum.Public) {
                coursepack.getPublicUserList().add(publicUser);
                publicUser.getPublicUserCoursepackList().add(coursepack);
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorRsp("Not allowed")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("enrollCoursepack")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response enrollCoursepack(@QueryParam("userId") Long userId, @QueryParam("coursepackId") Long coursepackId) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("User not found")).build();
            }
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);
            if (coursepack == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Coursepack not found")).build();
            }
            if (user.getAccessRight() == AccessRightEnum.Student) {
                coursepack.getStudentList().add(user);
                user.getStudentCoursepackList().add(coursepack);
                return Response.status(Response.Status.OK).build();
            }
            /*if(publicUser.getPayment() == coursepack.getPrice()){
                publicUser.setHasPaid(true);
            }*/
            if (user.getAccessRight() == AccessRightEnum.Public) {
                coursepack.getPublicUserList().add(user);
                user.getPublicUserCoursepackList().add(coursepack);
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorRsp("Not allowed")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    
    @Path("findStudentInCoursepack")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response findStudentInCoursepack(@QueryParam("userId") Long userId, @QueryParam("coursepackId") Long coursepackId) {
        try {
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("User not found")).build();
            }
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);
            if (coursepack == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Coursepack not found")).build();
            }
            
            List<Coursepack> studentCoursepacks = new ArrayList<>();
            if (user.getAccessRight() == AccessRightEnum.Student) {
                studentCoursepacks = user.getStudentCoursepackList();
            }
            if (user.getAccessRight() == AccessRightEnum.Public) {
                studentCoursepacks = user.getPublicUserCoursepackList();
            }
            if (studentCoursepacks.contains(coursepack)) {
                return Response.status(Response.Status.OK).build();
            }
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Student not enrolled in coursepack")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    /*@Path("addToCart")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response addToCart(@QueryParam("userId") Long userId, @QueryParam("coursepackId") Long coursepackId) {
        try {
            User publicUser = em.find(User.class, userId);
            if (publicUser == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("User not found")).build();
            }
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);
            if (coursepack == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Coursepack not found")).build();
            }
            /*if(publicUser.getPayment() == coursepack.getPrice()){
                publicUser.setHasPaid(true);
            }
            if (publicUser.getAccessRight() == AccessRightEnum.Public) {
                Cart cart = publicUser.getCart();
                if (cart == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Cart not found")).build();
                }
                if (cart.getCoursepackList().contains(coursepack)) {
                    return Response.status(Response.Status.NOT_ACCEPTABLE).entity(new ErrorRsp("Coursepack already in cart")).build();
                }
                cart.getCoursepackList().add(coursepack);
                return Response.status(Response.Status.OK).entity("Coursepack added to cart").build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorRsp("Not allowed")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("removeFromCart")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeFromCart(@QueryParam("userId") Long userId, @QueryParam("coursepackId") Long coursepackId) {
        try {
            User publicUser = em.find(User.class, userId);
            if (publicUser == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("User not found")).build();
            }
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);
            if (coursepack == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Coursepack not found")).build();
            }
            if (publicUser.getAccessRight() == AccessRightEnum.Public) {
                Cart cart = publicUser.getCart();
                if (cart == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Cart not found")).build();
                }
                if (cart.getCoursepackList() == null || cart.getCoursepackList().isEmpty()) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("No coursepack in cart")).build();
                }
                if (cart.getCoursepackList().contains(coursepack)) {
                    cart.getCoursepackList().remove(coursepack);
                }
                return Response.status(Response.Status.OK).entity("Coursepack removed from cart").build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorRsp("Not allowed")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("removeAllFromCart")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response removeAllFromCart(@QueryParam("userId") Long userId) {
        try {
            User publicUser = em.find(User.class, userId);
            if (publicUser == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("User not found")).build();
            }
            if (publicUser.getAccessRight() == AccessRightEnum.Public) {
                Cart cart = publicUser.getCart();
                if (cart == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Cart not found")).build();
                }
                List<Coursepack> coursepackList = cart.getCoursepackList();
                if (coursepackList == null || coursepackList.isEmpty()) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("No coursepack in cart")).build();
                }
                for (Coursepack c : coursepackList) {
                    if (cart.getCoursepackList().contains(c)) {
                        cart.getCoursepackList().remove(c);
                    }
                }
                return Response.status(Response.Status.OK).entity("Cart emptied").build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorRsp("Not allowed")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("viewCart")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response viewCart(@QueryParam("cartId") Long cartId) {
        try {
            Cart cart = em.find(Cart.class, cartId);
            if (cart == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Cart not found")).build();
            }
            //Query query = em.createQuery("Select c from Coursepack c");
            List<Coursepack> coursepackList = cart.getCoursepackList();
            if (coursepackList == null || coursepackList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("Cart is empty").build();
            }
            GetCoursepackRsp rsp = new GetCoursepackRsp(new ArrayList<>());
            for (Coursepack c : coursepackList) {
                rsp.getCoursepack().add(new Coursepack(
                        c.getCoursepackId(), c.getCode(), c.getTitle(), c.getDescription(),
                        c.getCategory(), c.getPrice(), null, c.getRating(),
                        c.getTeacherBackground(), null, null, null, null, null, null, null, null));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @Path("checkout")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response checkout(@QueryParam("userId") Long userId) {
        try {
            User publicUser = em.find(User.class, userId);
            if (publicUser == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("User not found")).build();
            }
            if (publicUser.getAccessRight() == AccessRightEnum.Public) {
                Cart cart = publicUser.getCart();
                if (cart == null) {
                    return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Cart not found")).build();
                }
                List<Coursepack> coursepackList = cart.getCoursepackList();
                if (coursepackList == null || coursepackList.isEmpty()) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("No coursepack in cart")).build();
                }
                for (Coursepack c : coursepackList) {
                    if (cart.getCoursepackList().contains(c) && c.isIsPaid() == true) {
                        cart.getCoursepackList().remove(c);
                        publicUser.getPublicUserCoursepackList().add(c);
                        c.getPublicUserList().add(publicUser);
                    }
                }
                return Response.status(Response.Status.OK).entity("Checkout successful").build();
            }
            return Response.status(Response.Status.UNAUTHORIZED).entity(new ErrorRsp("Not allowed")).build();
        } catch (Exception e) {
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }*/
}
