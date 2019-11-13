/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.AnswerStatistic;
import datamodel.rest.CreateNewFeedback;
import datamodel.rest.CreateQuizAttemptRqst;
import datamodel.rest.CreateRatingRqst;
import datamodel.rest.CreateSurveyAttemptRqst;
import datamodel.rest.ErrorRsp;
import datamodel.rest.PageModel;
import datamodel.rest.QuestionAttemptModel;
import datamodel.rest.QuestionStatistic;
import datamodel.rest.QuizRsp;
import datamodel.rest.RetrieveAllFeedbacksForModuleRsp;
import datamodel.rest.RetrieveRatingsRsp;
import datamodel.rest.RetrieveSurveyAttempts;
import datamodel.rest.RetrieveSurveyStatistics;
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
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javafx.util.Pair;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
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
import util.QuestionOrderEnum;
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
    
    public SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    
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
            survey.getSurveyAttemptList().add(sa);
            return Response.status(Response.Status.OK).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @GET
    @Path("retrieveSurvey")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSurvey(@QueryParam("userId") Long userId, @QueryParam("moduleId") Long moduleId){
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
            Survey survey = (Survey) q.getSingleResult();
            
            QuizRsp surveyToReturn = new QuizRsp();
            surveyToReturn.setQuizId(survey.getSurveyId());
            surveyToReturn.setOpeningDate(dateFormatter.format(survey.getOpeningDate()));
            surveyToReturn.setClosingDate(dateFormatter.format(survey.getClosingDate()));
            surveyToReturn.setDescription(survey.getDescription());
            surveyToReturn.setTitle(survey.getTitle());
            surveyToReturn.setQuestionsOrder(QuestionOrderEnum.initial);
            surveyToReturn.getPages().add(new PageModel(survey.getQuestionList(), "page1"));
            
            return Response.status(Response.Status.OK).entity(surveyToReturn).build();
        } catch (NoResultException e){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("There are no surveys for this module")).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @GET
    @Path("retrieveSurveyStatistics")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSurveyStatistics(@QueryParam("surveyId") Long surveyId){
        Survey survey = em.find(Survey.class, surveyId);
        if(survey == null){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Survey with the given ID not found!")).build();
        }
        
        if(survey.getSurveyAttemptList().isEmpty()){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No attempts yet for this survey!")).build();
        }
        
        try{
            RetrieveSurveyStatistics resp = new RetrieveSurveyStatistics(new ArrayList<>());
            resp.setTitle(survey.getTitle());
            resp.setDescription(survey.getDescription());
            resp.setSurveyId(survey.getSurveyId());
            resp.setAttempts(survey.getSurveyAttemptList().size());
            
            for(Question q: survey.getQuestionList()){
                if(q.getType() == QuestionTypeEnum.radiogroup){
                    QuestionStatistic qs = new QuestionStatistic(q.getQuestionId(), q.getTitle(), new ArrayList<>());
                    
                    HashMap<String, Integer> count = new HashMap<>(); // Answer : Attempt
                    for(SurveyAttempt sa: survey.getSurveyAttemptList()){
                        for(QuestionAttempt qa: sa.getQuestionAttemptList()){
                            if(qa.getQuestion() == q){
                                if(qa.getAnswer() != null){
                                    count.put(qa.getAnswer(), count.getOrDefault(qa.getAnswer(), 0) + 1);
                                }
                            }
                        }
                    }
                    
                    // For count 0 answers
                    for(String choice: q.getChoices()){
                        if(!count.containsKey(choice)){
                            AnswerStatistic as = new AnswerStatistic();
                            as.setAnswer(choice);
                            as.setCount(0);
                            qs.getAnswers().add(as);
                        }
                    }
                    
                    Iterator it = count.entrySet().iterator();
                    while (it.hasNext()) {
                        Map.Entry pair = (Map.Entry)it.next();
                        AnswerStatistic as = new AnswerStatistic();
                        as.setAnswer((String) pair.getKey());
                        as.setCount((int) pair.getValue());
                        qs.getAnswers().add(as);
                        it.remove(); // avoids a ConcurrentModificationException
                    }
                    
                    
                    resp.getQuestions().add(qs);
                }
            }
            
            return Response.status(Response.Status.OK).entity(resp).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @GET
    @Path("retrieveSurveyAttempts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveSurveyAttempts(@QueryParam("userId") Long userId, @QueryParam("moduleId") Long moduleId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        Module module = em.find(Module.class, moduleId);
        if(module == null){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Module is not found")).build();
        }
        
        try {
            Query q = em.createQuery("SELECT s FROM Survey s WHERE s.module = :module");
            q.setParameter("module", module);
            Survey survey = (Survey) q.getSingleResult();

            if (survey == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Survey with the given ID doesn't exist")).build();
            }

            if (survey.getSurveyAttemptList().isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("There are no attempts yet for this survey")).build();
            }

            RetrieveSurveyAttempts resp = new RetrieveSurveyAttempts(new ArrayList<>());

            for (SurveyAttempt sa : survey.getSurveyAttemptList()) {
                SurveyAttempt saCopy = new SurveyAttempt();
                saCopy.setSurveyAttemptId(sa.getSurveyAttemptId());
                saCopy.setQuestionAttemptList(sa.getQuestionAttemptList());

                User stu = new User();
                stu.setUserId(sa.getSurveyTaker().getUserId());
                stu.setFirstName(sa.getSurveyTaker().getFirstName());
                stu.setLastName(sa.getSurveyTaker().getLastName());
                stu.setUsername(sa.getSurveyTaker().getUsername());

                saCopy.setSurveyTaker(stu);

                resp.getSurveyAttempts().add(saCopy);
            }

            
            return Response.status(Response.Status.OK).entity(resp).build();
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
            Survey survey = (Survey) q.getSingleResult();
            
            if(survey == null){
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("There are no surveys for this module")).build();
            }
            
//            RetrieveSurveys resp = new RetrieveSurveys(new ArrayList<>());
            if(ar == AccessRightEnum.Teacher || 
                    (survey.getOpeningDate().before(new Date()) && survey.getClosingDate().after(new Date()))){
                Survey surveyToReturn = new Survey();
                surveyToReturn.setOpeningDate(survey.getOpeningDate());
                surveyToReturn.setClosingDate(survey.getClosingDate());
                surveyToReturn.setSurveyId(survey.getSurveyId());
                surveyToReturn.setTitle(survey.getTitle());
                surveyToReturn.setDescription(survey.getDescription());
            }
            
            return Response.status(Response.Status.OK).entity(survey).build();
        } catch (NoResultException e){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("There are no surveys for this module")).build();
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
        } /*else if (!cp.getPublicUserList().contains(user)){
            return Response.status(Response.Status.FORBIDDEN).entity(new ErrorRsp("User is not a student of this coursepack")).build();
        }*/
        
        try {
            Rating rating = new Rating();
            rating.setRating(rqst.getRating());
            rating.setComment(rqst.getComment());
            rating.setUser(user);
            
            em.persist(rating);
            cp.getRatingList().add(rating);
            
            int sum = 0;
            for (Rating r : cp.getRatingList()) {
                sum += r.getRating();
            }
            cp.setRating(sum * 1.0 / cp.getRatingList().size());
            //cp.setRating((1.0 * cp.getRating() + rating.getRating()) / cp.getRatingList().size());
            em.flush();
            
            return Response.status(Response.Status.OK).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @PUT
    @Path("editRating")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response editRating(CreateRatingRqst rqst, @QueryParam("ratingId") Long ratingId){
        User user = em.find(User.class, rqst.getUserId());
        if(user == null || (user.getAccessRight() != AccessRightEnum.Student && user.getAccessRight() != AccessRightEnum.Public)){
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        Rating rating = em.find(Rating.class, ratingId);
        if(rating == null){
            return Response.status(Response.Status.NOT_FOUND)
                    .entity(new ErrorRsp("Rating not found"))
                    .build();
        }
        
        Coursepack cp = em.find(Coursepack.class, rqst.getCoursepackId());
        if(cp == null){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Coursepack with the given ID not found!")).build();
        } /*else if (!cp.getPublicUserList().contains(user)){
            return Response.status(Response.Status.FORBIDDEN).entity(new ErrorRsp("User is not a student of this coursepack")).build();
        }*/
        
        try {
            rating.setRating(rqst.getRating());
            rating.setComment(rqst.getComment());
            rating.setUser(user);
            
            em.merge(rating);
            em.flush();
            
            int sum = 0;
            for (Rating r : cp.getRatingList()) {
                sum += r.getRating();
            }
            cp.setRating(sum * 1.0 / cp.getRatingList().size());
            //cp.setRating((1.0 * cp.getRating() + rating.getRating()) / cp.getRatingList().size());
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
        
        RetrieveRatingsRsp rsp = new RetrieveRatingsRsp(new ArrayList<>());
        for (Rating r: cp.getRatingList()){
            Rating rCopy = new Rating();
            rCopy.setRating(r.getRating());
            rCopy.setRatingId(r.getRatingId());
            rCopy.setComment(r.getComment());
            
            User uCopy = new User();
            uCopy.setFirstName(r.getUser().getFirstName());
            uCopy.setLastName(r.getUser().getLastName());
            
            rCopy.setUser(uCopy);
            rsp.getRatings().add(rCopy);
            
            switch(r.getRating()){
                case 1: rsp.setPer1(rsp.getPer1() + 1); break;
                case 2: rsp.setPer2(rsp.getPer2() + 1); break;
                case 3: rsp.setPer3(rsp.getPer3() + 1); break;
                case 4: rsp.setPer4(rsp.getPer4() + 1); break;
                case 5: rsp.setPer5(rsp.getPer5() + 1); break;
            }
        }
        rsp.setPer1(100 * rsp.getPer1() / rsp.getRatings().size());
        rsp.setPer2(100 * rsp.getPer2() / rsp.getRatings().size());
        rsp.setPer3(100 * rsp.getPer3() / rsp.getRatings().size());
        rsp.setPer4(100 * rsp.getPer4() / rsp.getRatings().size());
        rsp.setPer5(100 * rsp.getPer5() / rsp.getRatings().size());
        
        rsp.setAvg(cp.getRating().intValue());
        
        return Response.status(Response.Status.OK).entity(rsp).build();
    }
    
    @GET
    @Path("retrieveRating")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveRating(@QueryParam("ratingId") Long ratingId){
        Rating r = em.find(Rating.class, ratingId);
        if(r == null){
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Rating with the given ID not found!")).build();
        }
        
        Rating rCopy = new Rating();
        rCopy.setRating(r.getRating());
        rCopy.setRatingId(r.getRatingId());
        rCopy.setComment(r.getComment());

        User uCopy = new User();
        uCopy.setFirstName(r.getUser().getFirstName());
        uCopy.setLastName(r.getUser().getLastName());

        rCopy.setUser(uCopy);
        
        return Response.status(Response.Status.OK).entity(rCopy).build();
    }
}
