/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.CreateNewFeedback;
import datamodel.rest.CreateQuizAttemptRqst;
import datamodel.rest.CreateRatingRqst;
import datamodel.rest.CreateSurveyAttemptRqst;
import datamodel.rest.ErrorRsp;
import datamodel.rest.QuestionAttemptModel;
import datamodel.rest.RetrieveAllFeedbacksForModuleRsp;
import datamodel.rest.RetrieveRatingsRsp;
import datamodel.rest.RetrieveSurveys;
import entities.Coursepack;
import entities.Feedback;
import entities.Module;
import entities.Question;
import entities.QuestionAttempt;
import entities.Quiz;
import entities.QuizAttempt;
import entities.Rating;
import entities.Survey;
import entities.SurveyAttempt;
import entities.User;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
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
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.AccessRightEnum;
import util.QuestionTypeEnum;

/**
 *
 * @author Asus
 */
@Path("feedback")
@Stateless
public class FeedbackResource {
    
    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;
    
    @Path("retrieveAllFeedbackForModule/{moduleId}")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllFeedbackForModule(@PathParam("moduleId") Long moduleId){
        System.out.println("retrieveAllFeedbackForModule");
        System.out.println(em);
        try{
            Module mod = em.find(Module.class, moduleId);
            if(mod == null){
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exists!")).build();
            }

            
            List<Feedback> feedbacks = mod.getFeedbackList();
            if(feedbacks != null && !feedbacks.isEmpty()){
                return Response.status(Response.Status.OK).entity(new RetrieveAllFeedbacksForModuleRsp(feedbacks)).build();
            } else {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No feedback for this module")).build();
            }
       } catch (Exception e){
           e.printStackTrace();
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
       }
    }
    
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createFeedback(CreateNewFeedback createNewFeedback){
        try{
            User user = em.find(User.class, createNewFeedback.getUserId());
            if(user == null){
                return Response.status(Response.Status.BAD_REQUEST).entity("User doesn't exist!").build();
            }
            
            Module module = em.find(Module.class, createNewFeedback.getModuleId());
            if(module == null){
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist!")).build();
            }
            
            if(!module.getStudentList().contains(user)){
                return Response.status(Response.Status.FORBIDDEN).entity(new ErrorRsp("Student isn't enrolled in this module!")).build();
            }
            
            Feedback feedback = new Feedback();
            feedback.setFeedback(createNewFeedback.getFeedback());
            // Set current time
            feedback.setCreateTs(new Timestamp(new Date().getTime()));
            
            module.getFeedbackList().add(feedback);
            
            em.persist(feedback);
            em.flush();
            
            return Response.status(Response.Status.OK).build();
        } catch(Exception e){
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @POST
    @Path("createSurveyAttempt")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createSurveyAttempt(@QueryParam("userId") Long userId, CreateSurveyAttemptRqst rqst){
        Date now = new Date();
        
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Student){
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        Survey survey = em.find(Survey.class, rqst.getSurveyId());
        if(survey == null){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Survey with the given ID doesn't exist")).build();
        }
        
        if(survey.getClosingDate().before(now) || survey.getOpeningDate().after(now)){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Survey is closed")).build();
        }
        
        // Check attempts
        for(SurveyAttempt sa: survey.getSurveyAttemptList()){
            if(sa.getSurveyTaker() == user){
                return Response.status(Response.Status.BAD_REQUEST).entity("Student has done this survey before").build();
            }
        }
        
        try {
            SurveyAttempt sa = new SurveyAttempt();
            sa.setSurvey(survey);
            sa.setSurveyTaker(user);
            sa.setQuestionAttemptList(new ArrayList<>());
            
            for (QuestionAttemptModel queA: rqst.getQuestionAttempts()){
                Question que = em.find(Question.class, queA.getQuestionId());
                if(que == null){
                    return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Question not found")).build();
                }
                
                QuestionAttempt queAToPersist = new QuestionAttempt();
                queAToPersist.setAnswer(queA.getAnswer());
                queAToPersist.setQuestion(que);
                
                em.persist(queAToPersist);
                sa.getQuestionAttemptList().add(queAToPersist);
                
            }
            em.persist(sa);
            em.flush();
            
            return Response.status(Response.Status.OK).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @GET
    @Path("retrieveSurvey")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSurvey(@QueryParam("userId") Long userId, @QueryParam("surveyId") Long surveyId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        Survey survey = em.find(Survey.class, surveyId);
        if(survey == null){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Survey with the given ID doesn't exist")).build();
        }
        
        try {
            Survey surveyToReturn = new Survey();
            surveyToReturn.setOpeningDate(survey.getOpeningDate());
            surveyToReturn.setClosingDate(survey.getClosingDate());
            surveyToReturn.setSurveyAttemptList(survey.getSurveyAttemptList());
            surveyToReturn.setSurveyId(survey.getSurveyId());
            surveyToReturn.setTitle(survey.getTitle());
            surveyToReturn.setDescription(survey.getDescription());
            
            return Response.status(Response.Status.OK).entity(surveyToReturn).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @GET
    @Path("retrieveAllSurvey")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllSurvey(@QueryParam("userId") Long userId, @QueryParam("moduleId") Long moduleId){
        User user = em.find(User.class, userId);
        if(user == null || (user.getAccessRight() != AccessRightEnum.Teacher && user.getAccessRight() != AccessRightEnum.Student)){
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        AccessRightEnum ar = user.getAccessRight();
        
        Module module = em.find(Module.class, moduleId);
        if(module == null){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Module is not found")).build();
        } else if ( ar == AccessRightEnum.Teacher && module.getAssignedTeacher() != user){
            return Response.status(Response.Status.FORBIDDEN).entity(new ErrorRsp("User doesn't have access to this function")).build();
        } else if (ar == AccessRightEnum.Student && !module.getStudentList().contains(user)){
            return Response.status(Response.Status.FORBIDDEN).entity(new ErrorRsp("User doesn't have access to this function")).build();
        }
        
        try {
            Query q = em.createQuery("SELECT s FROM Survey s WHERE s.module = :module");
            q.setParameter("module", module);
            List<Survey> surveys = q.getResultList();
            
            if(surveys == null || surveys.isEmpty()){
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("There are no surveys for this module")).build();
            }
            
            RetrieveSurveys resp = new RetrieveSurveys(new ArrayList<>());
            
            for(Survey survey: surveys){
                if(ar == AccessRightEnum.Teacher || 
                        (survey.getOpeningDate().before(new Date()) && survey.getClosingDate().after(new Date()))){
                    Survey surveyToReturn = new Survey();
                    surveyToReturn.setOpeningDate(survey.getOpeningDate());
                    surveyToReturn.setClosingDate(survey.getClosingDate());
                    surveyToReturn.setSurveyId(survey.getSurveyId());
                    surveyToReturn.setTitle(survey.getTitle());
                    surveyToReturn.setDescription(survey.getDescription());

                    resp.getSurveys().add(surveyToReturn);
                }
            }
            
            return Response.status(Response.Status.OK).entity(resp).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @POST
    @Path("createRating")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createRating(CreateRatingRqst rqst){
        User user = em.find(User.class, rqst.getUserId());
        if(user == null || (user.getAccessRight() != AccessRightEnum.Student && user.getAccessRight() != AccessRightEnum.Public)){
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        Coursepack cp = em.find(Coursepack.class, rqst.getCoursepackId());
        if(cp == null){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Coursepack with the given ID not found!")).build();
        } else if (!cp.getPublicUserList().contains(user)){
            return Response.status(Response.Status.FORBIDDEN).entity(new ErrorRsp("User is not a student of this coursepack")).build();
        }
        
        try {
            Rating rating = new Rating();
            rating.setRating(rqst.getRating());
            rating.setComment(rqst.getComment());
            
            em.persist(rating);
            cp.getRatingList().add(rating);
            
            cp.setRating(cp.getRating() + rating.getRating() / cp.getRatingList().size());
            em.flush();
            
            return Response.status(Response.Status.OK).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @GET
    @Path("retrieveAllRatings")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllRatings(@QueryParam("coursepackId") Long coursepackId){
        Coursepack cp = em.find(Coursepack.class, coursepackId);
        if(cp == null){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Coursepack with the given ID not found!")).build();
        }
        
        if(cp.getRatingList().isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No ratings yet for this coursepack")).build();
        }
        
        return Response.status(Response.Status.OK).entity(new RetrieveRatingsRsp(cp.getRatingList())).build();
    }
    
    @GET
    @Path("retrieveRating")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveRating(@QueryParam("ratingId") Long ratingId){
        Rating rating = em.find(Rating.class, ratingId);
        if(rating == null){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Rating with the given ID not found!")).build();
        }
        
        return Response.status(Response.Status.OK).entity(rating).build();
    }
}
