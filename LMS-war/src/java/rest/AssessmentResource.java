/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.ChoiceModel;
import datamodel.rest.CreateGradeItemRqst;
import datamodel.rest.EnterMarksRqst;
import datamodel.rest.ErrorRsp;
import datamodel.rest.PageModel;
import datamodel.rest.QuestionModel;
import datamodel.rest.QuizRqst;
import datamodel.rest.QuizRsp;
import datamodel.rest.RetrieveGradeEntriesRsp;
import datamodel.rest.RetrieveGradeItemsRsp;
import datamodel.rest.RetrieveQuizzesResp;
import entities.GradeEntry;
import entities.GradeItem;
import entities.Module;
import entities.Question;
import entities.Quiz;
import entities.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import util.AccessRightEnum;
import util.QuestionTypeEnum;

/**
 *
 * @author Asus
 */
@Path("Assessment")
@Stateless
public class AssessmentResource {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;
    
    public SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");
    
    @POST
    @Path("createModuleQuiz")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createModuleQuiz(QuizRqst rqst, @QueryParam("userId") Long userId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        Module module = em.find(Module.class, rqst.getModuleId());
        if(module == null){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist")).build();
        } else if (module.getAssignedTeacher() != user){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this module"))
                    .build();
        }
        
        try{
            Quiz quiz = new Quiz();
            quiz.setTitle(rqst.getTitle());
            quiz.setDescription(rqst.getDescription());
            quiz.setQuizType(rqst.getQuizType());
            quiz.setQuestionsOrder(rqst.getQuestionsOrder());
            quiz.setMaxTimeToFinish(rqst.getMaxTimeToFinish());
            quiz.setNoOfAttempts(rqst.getNoOfAttempts());
            quiz.setModule(module);
            quiz.setQuestionList(new ArrayList<Question>());
            // Parse Date
            Date openingDate = dateFormatter.parse(rqst.getOpeningDate());
            Date closingDate = dateFormatter.parse(rqst.getClosingDate());
            
            // Verify date
            if(!openingDate.before(closingDate)){
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Opening date is not before closing date")).build();
            }
            
            quiz.setOpeningDate(openingDate);
            quiz.setClosingDate(closingDate);
            
            List<QuestionModel> questionsRqst = rqst.getQuestions();
            
            double total = 0;
            int count = 1;
            for(QuestionModel qm: questionsRqst){
                // Count max marks
                total += qm.getPoints();
                
                Question question = new Question();
                question.setTitle(qm.getTitle());
                question.setLevel(qm.getLevel());
                question.setNumber(count++);
                question.setPoints(qm.getPoints());
                question.setExplanation(qm.getExplanation());
                question.setType(qm.getType());
                question.setIsRequired(qm.getIsRequired());
                question.setChoices(new ArrayList<>());
                
                if(question.getType() == QuestionTypeEnum.radiogroup){
                    for (ChoiceModel choice: qm.getChoices()){
                        question.getChoices().add(choice.getText());
                        if(qm.getCorrectAnswer().equals(choice.getValue())){
                            question.setCorrectAnswer(choice.getText());
                        }
                    }
                }
                
                em.persist(question);
                em.flush();
                
                quiz.getQuestionList().add(question);
            }
            
            module.getQuizList().add(quiz);
            em.persist(quiz);
            em.flush();
            
            quiz.setMaxMarks(total);
            
            return Response.status(Status.OK).build();
            
        } catch (ParseException pe){
            pe.printStackTrace();
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Opening or closing date isn't in proper format")).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @POST
    @Path("updateModuleQuiz")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateModuleQuiz(QuizRqst rqst, @QueryParam("userId") Long userId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        Quiz quiz = em.find(Quiz.class, rqst.getQuizId());
        if(quiz == null){
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID doesn't exist")).build();
        }
        
        boolean attempted = !quiz.getQuizAttemptList().isEmpty();
        
        try{
            quiz.setTitle(rqst.getTitle());
            quiz.setDescription(rqst.getDescription());
            quiz.setQuizType(rqst.getQuizType());
            quiz.setQuestionsOrder(rqst.getQuestionsOrder());
            quiz.setMaxTimeToFinish(rqst.getMaxTimeToFinish());
            quiz.setNoOfAttempts(rqst.getNoOfAttempts());
            // Parse Date
            Date openingDate = dateFormatter.parse(rqst.getOpeningDate());
            Date closingDate = dateFormatter.parse(rqst.getClosingDate());
            
            // Verify date
            if(!openingDate.before(closingDate)){
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Opening date is not before closing date")).build();
            }
            
            quiz.setOpeningDate(openingDate);
            quiz.setClosingDate(closingDate);
            
            List<QuestionModel> questionsRqst = rqst.getQuestions();
            
            double total = 0;
            int count = 1;
            for(QuestionModel qm: questionsRqst){
                
                // Search if question has been created before
                Question question = em.find(Question.class, qm.getQuestionId());
                
                if(question == null){
                    question = new Question();
                } else { 
// If not updated, skip
//                    continue;
                    
                    // IF updated remove old question from quiz
                    quiz.getQuestionList().remove(question);
                }
                
                
                
                // Count max marks
                total += qm.getPoints();
                
                question.setNumber(count++);
                question.setPoints(qm.getPoints());
                question.setExplanation(qm.getExplanation());
                question.setType(qm.getType());
                question.setIsRequired(qm.getIsRequired());
                question.setChoices(new ArrayList<>());
                
                if(question.getType() == QuestionTypeEnum.radiogroup){
                    for (ChoiceModel choice: qm.getChoices()){
                        question.getChoices().add(choice.getText());
                        if(qm.getCorrectAnswer().equals(choice.getValue())){
                            question.setCorrectAnswer(choice.getText());
                        }
                    }
                }
                
                em.persist(question);
                em.flush();
                
                quiz.getQuestionList().add(question);
            }
            
            quiz.setMaxMarks(total);
            
            return Response.status(Status.OK).build();
            
        } catch (ParseException pe){
            pe.printStackTrace();
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Opening or closing date isn't in proper format")).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @POST
    @Path("publishQuizAnswer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishQuizAnswer(@QueryParam("userId") Long userId, @QueryParam("quizId") Long quizId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        Quiz quiz = em.find(Quiz.class, quizId);
        if(quiz == null){
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID doesn't exist")).build();
        }
        
        quiz.setPublishAnswer(true);
        
        return Response.status(Status.OK).build();
    }
    
    @DELETE
    @Path("deleteModuleQuiz")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteModuleQuiz(@QueryParam("userId") Long userId, @QueryParam("quizId") Long quizId){
        return null;
    }
    
    @GET
    @Path("retrieveAllModuleQuiz/{moduleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllModuleQuiz(@PathParam("moduleId") Long moduleId, @QueryParam("userId") Long userId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() == AccessRightEnum.Admin || user.getAccessRight() == AccessRightEnum.Public){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        AccessRightEnum ar = user.getAccessRight();
        
        Module module = em.find(Module.class, moduleId);
        if(module == null){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist")).build();
        } else if (module.getAssignedTeacher() != user && !user.getStudentModuleList().contains(module)){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this module"))
                    .build();
        }
        
        try {
            List<Quiz> quizzes = new ArrayList<>();
            
            for(Quiz q: module.getQuizList()){
                if(ar == AccessRightEnum.Teacher || q.isPublish()){
                    Quiz newQ = new Quiz();
                    newQ.setQuizId(q.getQuizId());
                    newQ.setOpeningDate(q.getOpeningDate());
                    newQ.setClosingDate(q.getClosingDate());
                    newQ.setQuizType(q.getQuizType());
                    newQ.setMaxMarks(q.getMaxMarks());
                    newQ.setDescription(q.getDescription());
                    newQ.setTitle(q.getTitle());
                    newQ.setMaxTimeToFinish(q.getMaxTimeToFinish());
                    newQ.setPublish(q.isPublish());
                    newQ.setNoOfAttempts(q.getNoOfAttempts());
                    newQ.setQuestionsOrder(q.getQuestionsOrder());
                    newQ.setPublishAnswer(q.isPublishAnswer());
                    quizzes.add(newQ);
                }
            }
            
            return Response.status(Status.OK).entity(new RetrieveQuizzesResp(quizzes)).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @GET
    @Path("retrieveModuleQuiz/{quizId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveModuleQuiz(@PathParam("quizId") Long quizId, @QueryParam("userId") Long userId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() == AccessRightEnum.Admin || user.getAccessRight() == AccessRightEnum.Public){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        AccessRightEnum ar = user.getAccessRight();
        
        try{
            Quiz q = em.find(Quiz.class, quizId);
            
            if(q == null){
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID is not found")).build();
            }
            
            QuizRsp newQ = new QuizRsp();
            newQ.setQuizId(q.getQuizId());
            newQ.setOpeningDate(dateFormatter.format(q.getOpeningDate()));
            newQ.setClosingDate(dateFormatter.format(q.getClosingDate()));
            newQ.setQuizType(q.getQuizType());
            newQ.setMaxMarks(q.getMaxMarks());
            newQ.setDescription(q.getDescription());
            newQ.setTitle(q.getTitle());
            newQ.setMaxTimeToFinish(q.getMaxTimeToFinish());
            newQ.setPublish(q.isPublish());
            newQ.setNoOfAttempts(q.getNoOfAttempts());
            newQ.setQuestionsOrder(q.getQuestionsOrder());
            newQ.setPublishAnswer(q.isPublishAnswer());
            newQ.getPages().add(new PageModel(q.getQuestionList(), "page1"));
            
            return Response.status(Status.OK).entity(newQ).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
        
    }
    
    @POST
    @Path("createGradeItem")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGradeItem(CreateGradeItemRqst rqst, @QueryParam("userId") Long userId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        Module module = em.find(Module.class, rqst.getModuleId());
        if(module == null){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist")).build();
        } else if (module.getAssignedTeacher() != user){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this module"))
                    .build();
        }
        
        try{
            GradeItem gradeItem = new GradeItem();
            gradeItem.setTitle(rqst.getTitle());
            gradeItem.setDescription(rqst.getDescription());
            gradeItem.setMaxMarks(rqst.getMaxMarks());
            gradeItem.setModule(module);
            module.getGradeItemList().add(gradeItem);
            
            em.persist(gradeItem);
            em.flush();
            
            // Add grade entry for every student in the module
            for (User stu: module.getStudentList()){
                GradeEntry gradeEntry = new GradeEntry();
                gradeEntry.setGradeItem(gradeItem);
                gradeEntry.setStudent(stu);
                gradeItem.getGradeEntries().add(gradeEntry);
                
                em.persist(gradeEntry);
                em.flush();
            }
            
            return Response.status(Status.OK).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @POST
    @Path("updateGradeItem")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGradeItem(@QueryParam("userId") Long userId, CreateGradeItemRqst rqst){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        GradeItem gradeItem = em.find(GradeItem.class, rqst.getGradeItemId());
        if(gradeItem == null){
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("GradeItem is not found!")).build();
        }
        
        gradeItem.setTitle(rqst.getTitle());
        gradeItem.setDescription(rqst.getDescription());
        gradeItem.setMaxMarks(rqst.getMaxMarks());
        
        return Response.status(Status.OK).build();
    }
    
    @GET
    @Path("retrieveGradeItems/{moduleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveGradeItems(@QueryParam("userId") Long userId, @PathParam("moduleId") Long moduleId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        Module module = em.find(Module.class, moduleId);
        if(module == null){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist")).build();
        } else if (module.getAssignedTeacher() != user){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this module"))
                    .build();
        }
        
        try{
            RetrieveGradeItemsRsp resp = new RetrieveGradeItemsRsp(new ArrayList<>());
            for(GradeItem gradeItem: module.getGradeItemList()){
                GradeItem giToReturn = new GradeItem();
                giToReturn.setGradeItemId(gradeItem.getGradeItemId());
                giToReturn.setTitle(gradeItem.getTitle());
                giToReturn.setDescription(gradeItem.getDescription());
                giToReturn.setMaxMarks(gradeItem.getMaxMarks());
                giToReturn.setPublish(gradeItem.getPublish());
                giToReturn.setMean(gradeItem.getMean());
                giToReturn.setMedian(gradeItem.getMedian());
                giToReturn.setSeventyFifth(gradeItem.getSeventyFifth());
                giToReturn.setTwentyFifth(gradeItem.getTwentyFifth());
                
                resp.getGradeItems().add(giToReturn);
            }
            
            if(resp.getGradeItems().isEmpty()){
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No grade items yet for this module")).build();
            }
            
            return Response.status(Status.OK).entity(resp).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @DELETE
    @Path("deleteGradeItem")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGradeItem(@QueryParam("userId") Long userId, @QueryParam("gradeItemId") Long gradeItemId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        GradeItem gradeItem = em.find(GradeItem.class, gradeItemId);
        if(gradeItem == null){
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("GradeItem is not found!")).build();
        } else if (gradeItem.getModule().getAssignedTeacher() != user){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        em.remove(gradeItem);
        em.flush();
        
        return Response.status(Status.OK).build();
    }
    
    @GET
    @Path("retrieveGradeEntries")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveGradeEntries(@QueryParam("userId") Long userId, @QueryParam("gradeItemId") Long gradeItemId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        GradeItem gradeItem = em.find(GradeItem.class, gradeItemId);
        if(gradeItem == null){
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("GradeItem is not found!")).build();
        } else if (gradeItem.getModule().getAssignedTeacher() != user){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        try{
            RetrieveGradeEntriesRsp resp = new RetrieveGradeEntriesRsp(new ArrayList<>());
            
            for(GradeEntry ge: gradeItem.getGradeEntries()){
                GradeEntry geToReturn = new GradeEntry();
                geToReturn.setGradeEntryId(ge.getGradeEntryId());
                geToReturn.setMarks(ge.getMarks());
                geToReturn.setRemarks(ge.getRemarks());
                
                User stu = new User();
                stu.setUserId(ge.getStudent().getUserId());
                stu.setFirstName(ge.getStudent().getFirstName());
                stu.setLastName(ge.getStudent().getLastName());
                stu.setUsername(ge.getStudent().getUsername());
                
                geToReturn.setStudent(stu);
                
                resp.getGradeEntries().add(geToReturn);
            }
            
            return Response.status(Status.OK).entity(resp).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @GET
    @Path("retrieveStudentGrades")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveStudentGrades(@QueryParam("userId") Long userId, @QueryParam("moduleId") Long moduleId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Student){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        Module module = em.find(Module.class, moduleId);
        if(module == null){
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist")).build();
        } else if (!module.getStudentList().contains(user)){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this module"))
                    .build();
        }
        
        try{
            Query q = em.createQuery("SELECT gi FROM Module m join m.gradeItemList gi join gi.gradeEntries ge WHERE gi.publish = :publish AND m.moduleId = :moduleId");
            q.setParameter("moduleId", moduleId);
            q.setParameter("publish", true);
            
            List<GradeItem> gradeItems = q.getResultList();
            
            if(gradeItems == null || gradeItems.isEmpty()){
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No grade entries for the student")).build();
            }
            
            RetrieveGradeItemsRsp resp = new RetrieveGradeItemsRsp(new ArrayList<>());
            for(GradeItem gradeItem: gradeItems){
                GradeItem giToReturn = new GradeItem();
                giToReturn.setGradeItemId(gradeItem.getGradeItemId());
                giToReturn.setTitle(gradeItem.getTitle());
                giToReturn.setDescription(gradeItem.getDescription());
                giToReturn.setMaxMarks(gradeItem.getMaxMarks());
                giToReturn.setPublish(gradeItem.getPublish());
                giToReturn.setMean(gradeItem.getMean());
                giToReturn.setMedian(gradeItem.getMedian());
                giToReturn.setSeventyFifth(gradeItem.getSeventyFifth());
                giToReturn.setTwentyFifth(gradeItem.getTwentyFifth());
                
                for(GradeEntry ge: gradeItem.getGradeEntries()){
                    if(ge.getStudent() == user){
                        GradeEntry geToReturn = new GradeEntry();
                        geToReturn.setGradeEntryId(ge.getGradeEntryId());
                        geToReturn.setMarks(ge.getMarks());
                        geToReturn.setRemarks(ge.getRemarks());

                        giToReturn.getGradeEntries().add(geToReturn);
                    }
                }
                
                resp.getGradeItems().add(giToReturn);
            }
            
            if(resp.getGradeItems().isEmpty()){
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No grade entries for the student")).build();
            }
            
            return Response.status(Status.OK).entity(resp).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @POST
    @Path("enterGradeMarks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response enterGradeMarks(@QueryParam("userId") Long userId, EnterMarksRqst rqst){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        GradeEntry gradeEntry = em.find(GradeEntry.class, rqst.getGradeEntryId());
        if(gradeEntry == null){
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("GradeEntry is not found")).build();
        }
        
        try{
            Query q = em.createQuery("SELECT gi FROM User u join u.teacherModuleList m join m.gradeItemList gi join gi.gradeEntries ge WHERE ge.gradeEntryId=:id");
       
            q.setParameter("id", rqst.getGradeEntryId());

            GradeItem gi = (GradeItem) q.getSingleResult();

            if(gi.getMaxMarks() < rqst.getMarks()){
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Marks given is bigger than maxMarks of the grade item")).build();
            }
            
            gradeEntry.setMarks(rqst.getMarks());
            gradeEntry.setRemarks(rqst.getRemarks());

            return Response.status(Status.OK).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @POST
    @Path("publishGrade")
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishGrade(@QueryParam("userId") Long userId, @QueryParam("gradeItemId") Long gradeItemId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        GradeItem gradeItem = em.find(GradeItem.class, gradeItemId);
        if(gradeItem == null){
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("GradeItem not found")).build();
        }
        
        gradeItem.setPublish(true);
        
        return Response.status(Status.OK).build();
    }
    
    @POST
    @Path("unpublishGrade")
    @Produces(MediaType.APPLICATION_JSON)
    public Response unpublishGrade(@QueryParam("userId") Long userId, @QueryParam("gradeItemId") Long gradeItemId){
        User user = em.find(User.class, userId);
        if(user == null || user.getAccessRight() != AccessRightEnum.Teacher){
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        GradeItem gradeItem = em.find(GradeItem.class, gradeItemId);
        if(gradeItem == null){
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("GradeItem not found")).build();
        }
        
        gradeItem.setPublish(true);
        
        return Response.status(Status.OK).build();
    }
    
    
}
