/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.AnswerStatistic;
import datamodel.rest.ChoiceModel;
import datamodel.rest.CreateGradeItemRqst;
import datamodel.rest.CreateQuizAttemptRqst;
import datamodel.rest.EnterMarksRqst;
import datamodel.rest.ErrorRsp;
import datamodel.rest.PageModel;
import datamodel.rest.QuestionAttemptModel;
import datamodel.rest.QuestionModel;
import datamodel.rest.QuestionStatistic;
import datamodel.rest.QuizRqst;
import datamodel.rest.QuizRsp;
import datamodel.rest.RetrieveGradeEntriesRsp;
import datamodel.rest.RetrieveGradeItemsRsp;
import datamodel.rest.RetrieveQuestionAttemptsRsp;
import datamodel.rest.RetrieveQuizAttemptsRsp;
import datamodel.rest.RetrieveQuizzesResp;
import datamodel.rest.RetrieveSurveyStatistics;
import entities.Badge;
import entities.Certification;
import entities.Coursepack;
import entities.GradeEntry;
import entities.GradeItem;
import entities.LessonOrder;
import entities.Module;
import entities.Outlines;
import entities.Question;
import entities.QuestionAttempt;
import entities.Quiz;
import entities.QuizAttempt;
import entities.Survey;
import entities.SurveyAttempt;
import entities.User;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
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
    public Response createModuleQuiz(QuizRqst rqst, @QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Module module = em.find(Module.class, rqst.getModuleId());
        if (module == null) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist")).build();
        } else if (module.getAssignedTeacher() != user) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this module"))
                    .build();
        }

        try {
            Quiz quiz = new Quiz();
            quiz.setTitle(rqst.getTitle());
            quiz.setDescription(rqst.getDescription());
            quiz.setQuizType(rqst.getQuizType());
            quiz.setQuestionsOrder(rqst.getQuestionsOrder());
            quiz.setMaxTimeToFinish(rqst.getMaxTimeToFinish());
            quiz.setNoOfAttempts(rqst.getNoOfAttempts());
            quiz.setModule(module);
            quiz.setPublish(rqst.isPublish());
            quiz.setQuestionList(new ArrayList<Question>());
            // Parse Date
            Date openingDate = dateFormatter.parse(rqst.getOpeningDate());
            Date closingDate = dateFormatter.parse(rqst.getClosingDate());

            // Verify date
            if (!openingDate.before(closingDate)) {
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Opening date is not before closing date")).build();
            }

            quiz.setOpeningDate(openingDate);
            quiz.setClosingDate(closingDate);

            List<QuestionModel> questionsRqst = rqst.getQuestions();

            double total = 0;
            int count = 1;
            for (QuestionModel qm : questionsRqst) {
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
                question.setCorrectAnswer(qm.getCorrectAnswer());

                if (question.getType() == QuestionTypeEnum.radiogroup) {
                    for (ChoiceModel choice : qm.getChoices()) {
                        question.getChoices().add(choice.getText());
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

        } catch (ParseException pe) {
            pe.printStackTrace();
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Opening or closing date isn't in proper format")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("updateModuleQuiz")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateModuleQuiz(QuizRqst rqst, @QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Quiz quiz = em.find(Quiz.class, rqst.getQuizId());
        if (quiz == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID doesn't exist")).build();
        }

        if (quiz.getModule().getAssignedTeacher() != user) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        boolean attempted = !quiz.getQuizAttemptList().isEmpty();

        try {
            // Basic Config
            quiz.setTitle(rqst.getTitle());
            quiz.setDescription(rqst.getDescription());
            quiz.setQuizType(rqst.getQuizType());
            quiz.setQuestionsOrder(rqst.getQuestionsOrder());
            quiz.setMaxTimeToFinish(rqst.getMaxTimeToFinish());
            quiz.setNoOfAttempts(rqst.getNoOfAttempts());
            quiz.setPublish(rqst.isPublish());
            // Parse Date
            Date openingDate = dateFormatter.parse(rqst.getOpeningDate());
            Date closingDate = dateFormatter.parse(rqst.getClosingDate());

            // Verify date
            if (!openingDate.before(closingDate)) {
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Opening date is not before closing date")).build();
            }

            quiz.setOpeningDate(openingDate);
            quiz.setClosingDate(closingDate);

            return Response.status(Status.OK).build();

        } catch (ParseException pe) {
            pe.printStackTrace();
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Opening or closing date isn't in proper format")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("createQuestion")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createQuestion(QuestionModel qm, @QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Quiz quiz = em.find(Quiz.class, qm.getQuizId());
        if (quiz == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID doesn't exist")).build();
        }

        if (quiz.getModule().getAssignedTeacher() != user) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        try {
            Question question = new Question();
            question.setTitle(qm.getTitle());
            question.setLevel(qm.getLevel());
            question.setNumber(quiz.getQuestionList().size() + 1);
            question.setPoints(qm.getPoints());
            question.setExplanation(qm.getExplanation());
            question.setType(qm.getType());
            question.setIsRequired(qm.getIsRequired());
            question.setChoices(new ArrayList<>());
            question.setCorrectAnswer(qm.getCorrectAnswer());

            if (question.getType() == QuestionTypeEnum.radiogroup) {
                for (ChoiceModel choice : qm.getChoices()) {
                    question.getChoices().add(choice.getText());
                    if (qm.getCorrectAnswer().equals(choice.getValue())) {
                        question.setCorrectAnswer(choice.getText());
                    }
                }
            }

            em.persist(question);
            em.flush();

            quiz.getQuestionList().add(question);
            quiz.setMaxMarks(quiz.getMaxMarks() + question.getPoints());
            return Response.status(Status.OK).entity(question).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("updateQuestion")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateQuestion(QuestionModel qm, @QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Quiz quiz = em.find(Quiz.class, qm.getQuizId());
        if (quiz == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID doesn't exist")).build();
        }

        if (quiz.getModule().getAssignedTeacher() != user) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        boolean attempted = !quiz.getQuizAttemptList().isEmpty();

        try {
            Question question = em.find(Question.class, qm.getQuestionId());

            if (question == null) {
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Question with the given ID doesn't exist")).build();
            }
            double pointDiff = qm.getPoints() - question.getPoints();
            if (attempted) {
                Question oldQ = question;
                quiz.getQuestionList().remove(oldQ);

                question = new Question();
                question.setTitle(qm.getTitle());
                question.setLevel(qm.getLevel());
                question.setNumber(oldQ.getNumber());
                question.setPoints(qm.getPoints());
                question.setExplanation(qm.getExplanation());
                question.setType(qm.getType());
                question.setIsRequired(qm.getIsRequired());
                question.setChoices(new ArrayList<>());
                question.setCorrectAnswer(qm.getCorrectAnswer());

                em.persist(question);
                quiz.getQuestionList().add(question);
                em.flush();
            } else {
                question.setTitle(qm.getTitle());
                question.setLevel(qm.getLevel());
                question.setPoints(qm.getPoints());
                question.setExplanation(qm.getExplanation());
                question.setType(qm.getType());
                question.setIsRequired(qm.getIsRequired());
                question.setChoices(new ArrayList<>());
                question.setCorrectAnswer(qm.getCorrectAnswer());
            }

            quiz.setMaxMarks(quiz.getMaxMarks() + pointDiff);

            if (question.getType() == QuestionTypeEnum.radiogroup) {
                for (ChoiceModel choice : qm.getChoices()) {
                    question.getChoices().add(choice.getText());
                    if (qm.getCorrectAnswer().equals(choice.getValue())) {
                        question.setCorrectAnswer(choice.getText());
                    }
                }
            }

            return Response.status(Status.OK).entity(question).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("deleteQuestion")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteQuestion(@QueryParam("userId") Long userId, @QueryParam("quizId") Long quizId, @QueryParam("questionId") Long questionId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Quiz quiz = em.find(Quiz.class, quizId);
        if (quiz == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID doesn't exist")).build();
        }

        if (quiz.getModule().getAssignedTeacher() != user) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        boolean attempted = !quiz.getQuizAttemptList().isEmpty();

        try {
            Question question = em.find(Question.class, questionId);

            if (question == null) {
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Question with the given ID doesn't exist")).build();
            }

            // Reduce the number of other questions after this question
            for (Question q : quiz.getQuestionList()) {
                if (q.getNumber() > question.getNumber()) {
                    q.setNumber(q.getNumber() - 1);
                }
            }

            quiz.setMaxMarks(quiz.getMaxMarks() - question.getPoints());
            quiz.getQuestionList().remove(question);
            if (!attempted) {
                em.remove(question);
            }

            return Response.status(Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("createQuizAttempt")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createQuizAttempt(@QueryParam("userId") Long userId, CreateQuizAttemptRqst rqst) {
        Date now = new Date();

        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Student) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Quiz quiz = em.find(Quiz.class, rqst.getQuizId());
        if (quiz == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID doesn't exist")).build();
        }

        if (quiz.getClosingDate().before(now) || quiz.getOpeningDate().after(now)) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Quiz is closed")).build();
        }

        // Check attempts
        int count = 0;
        for (QuizAttempt qa : quiz.getQuizAttemptList()) {
            if (qa.getQuizTaker() == user) {
                if (++count >= quiz.getNoOfAttempts()) {
                    return Response.status(Status.BAD_REQUEST).entity("Student has reached the maximum number of attempts").build();
                }
            }
        }

        try {
            QuizAttempt qa = new QuizAttempt();
            qa.setCreateTs(now);
            qa.setQuiz(quiz);
            qa.setQuizTaker(user);
            quiz.getQuizAttemptList().add(qa);
            qa.setQuestionAttemptList(new ArrayList<>());

            double totalMarks = 0.0;
            for (QuestionAttemptModel queA : rqst.getQuestionAttempts()) {
                Question que = em.find(Question.class, queA.getQuestionId());
                if (que == null) {
                    return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Question not found")).build();
                }

                QuestionAttempt queAToPersist = new QuestionAttempt();
                queAToPersist.setAnswer(queA.getAnswer());
                queAToPersist.setQuestion(que);
                if (que.getType() == QuestionTypeEnum.radiogroup && queA.getAnswer().equals(que.getCorrectAnswer())) {
                    queAToPersist.setMarks(que.getPoints());
                    totalMarks += que.getPoints();
                } else {
                    queAToPersist.setMarks(0.0);
                }

                em.persist(queAToPersist);
                System.out.println(qa.getQuestionAttemptList());
                qa.getQuestionAttemptList().add(queAToPersist);

            }
            em.persist(qa);

            qa.setTotalMarks(totalMarks);
            em.flush();

            return Response.status(Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("publishQuizAnswer")
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishQuizAnswer(@QueryParam("userId") Long userId, @QueryParam("quizId") Long quizId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Quiz quiz = em.find(Quiz.class, quizId);
        if (quiz == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID doesn't exist")).build();
        }

        quiz.setPublishAnswer(true);

        return Response.status(Status.OK).build();
    }

    @DELETE
    @Path("deleteModuleQuiz")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteModuleQuiz(@QueryParam("userId") Long userId, @QueryParam("quizId") Long quizId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Quiz quiz = em.find(Quiz.class, quizId);
        if (quiz == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID doesn't exist")).build();
        }

        if (quiz.getModule() == null) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Quiz is not for a module")).build();
        }

        if (quiz.getQuizAttemptList().isEmpty()) {
            quiz.getModule().getQuizList().remove(quiz);
            em.remove(quiz);
            return Response.status(Status.OK).build();
        } else {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Quiz has been attempted before, can't be deleted.")).build();
        }
    }

    @GET
    @Path("retrieveAllModuleQuiz/{moduleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllModuleQuiz(@PathParam("moduleId") Long moduleId, @QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() == AccessRightEnum.Admin || user.getAccessRight() == AccessRightEnum.Public) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        AccessRightEnum ar = user.getAccessRight();

        Module module = em.find(Module.class, moduleId);
        if (module == null) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist")).build();
        } else if (module.getAssignedTeacher() != user && !user.getStudentModuleList().contains(module)) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this module"))
                    .build();
        }

        try {
            List<Quiz> quizzes = new ArrayList<>();

            for (Quiz q : module.getQuizList()) {
                if (ar == AccessRightEnum.Teacher || q.isPublish()) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @GET
    @Path("retrieveModuleQuizNotInGradebook/{moduleId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllModuleQuizNotInGradebook(@PathParam("moduleId") Long moduleId, @QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Module module = em.find(Module.class, moduleId);
        if (module == null) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist")).build();
        } else if (module.getAssignedTeacher() != user && !user.getStudentModuleList().contains(module)) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this module"))
                    .build();
        }

        try {
            List<Quiz> quizzes = new ArrayList<>();

            for (Quiz q : module.getQuizList()) {
                if (!q.isGradeitemCreated()) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @GET
    @Path("retrieveModuleQuiz/{quizId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveModuleQuiz(@PathParam("quizId") Long quizId, @QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() == AccessRightEnum.Admin || user.getAccessRight() == AccessRightEnum.Public) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        AccessRightEnum ar = user.getAccessRight();

        try {
            Quiz q = em.find(Quiz.class, quizId);

            if (q == null) {
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID is not found")).build();
            }

            Module module = q.getModule();
            if (module == null) {
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist")).build();
            } else if (module.getAssignedTeacher() != user && !user.getStudentModuleList().contains(module)) {
                return Response.status(Status.FORBIDDEN)
                        .entity(new ErrorRsp("User doesn't have access to this module"))
                        .build();
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
            newQ.setReachedMaxAttempt(false);
            int count = 0;
            for (QuizAttempt qa : q.getQuizAttemptList()) {
                if (qa.getQuizTaker() == user) {
                    if (++count >= q.getNoOfAttempts()) {
                        newQ.setReachedMaxAttempt(true);
                        break;
                    }
                }
            }

            return Response.status(Status.OK).entity(newQ).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }

    }

    @GET
    @Path("retrieveAllQuizAttempts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllQuizAttempts(@QueryParam("userId") Long userId, @QueryParam("quizId") Long quizId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() == AccessRightEnum.Admin || user.getAccessRight() == AccessRightEnum.Public) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        AccessRightEnum ar = user.getAccessRight();

        Quiz q = em.find(Quiz.class, quizId);
        if (q == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID is not found")).build();
        }

        if (q.getQuizAttemptList().isEmpty()) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No attempts yet for this quiz")).build();
        }

        RetrieveQuizAttemptsRsp resp = new RetrieveQuizAttemptsRsp(new ArrayList<>());

        for (QuizAttempt qa : q.getQuizAttemptList()) {
            if (ar == AccessRightEnum.Teacher || qa.getQuizTaker() == user) {
                QuizAttempt qaToReturn = new QuizAttempt();
                qaToReturn.setCreateTs(qa.getCreateTs());
                qaToReturn.setQuizAttemptId(qa.getQuizAttemptId());
                qaToReturn.setTotalMarks(qa.getTotalMarks());
                qaToReturn.setQuestionAttemptList(qa.getQuestionAttemptList());

                User stu = new User();
                stu.setUserId(qa.getQuizTaker().getUserId());
                stu.setFirstName(qa.getQuizTaker().getFirstName());
                stu.setLastName(qa.getQuizTaker().getLastName());
                stu.setUsername(qa.getQuizTaker().getUsername());

                resp.getQuizAttempts().add(qaToReturn);
            }
        }

        if (resp.getQuizAttempts().isEmpty()) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No attempts yet for this quiz")).build();
        }

        return Response.status(Status.OK).entity(resp).build();
    }

    @GET
    @Path("retrieveQuestionAttempts")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveQuestionAttempts(@QueryParam("userId") Long userId, @QueryParam("quizAttemptId") Long quizAttemptId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() == AccessRightEnum.Admin || user.getAccessRight() == AccessRightEnum.Public) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        AccessRightEnum ar = user.getAccessRight();

        QuizAttempt qa = em.find(QuizAttempt.class, quizAttemptId);
        if (qa == null || (ar == AccessRightEnum.Student && qa.getQuizTaker() != user)) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("QuizAttempt is not found")).build();
        }

        return Response.status(Status.OK).entity(new RetrieveQuestionAttemptsRsp(qa.getQuestionAttemptList())).build();
    }

    @POST
    @Path("enterQuestionMarks")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response enterQuestionMarks(@QueryParam("userId") Long userId, @QueryParam("questionAttemptId") Long questionAttemptId, @QueryParam("marks") Double marks) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        QuestionAttempt queA = em.find(QuestionAttempt.class, questionAttemptId);
        if (queA == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("QuestionAttempt is not found")).build();
        }

        if (queA.getQuestion().getPoints() < marks) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Marks given exceeds the maximum point for the question")).build();
        }

        try {
            queA.setMarks(marks);

            Query q = em.createQuery("SELECT qa FROM QuizAttempt qa join qa.questionAttemptList que WHERE que.questionAttemptId = :id");
            q.setParameter("id", questionAttemptId);
            QuizAttempt qa = (QuizAttempt) q.getSingleResult();
            double total = 0.0;
            for (QuestionAttempt que : qa.getQuestionAttemptList()) {
                total += que.getMarks();
            }

            qa.setTotalMarks(total);

            return Response.status(Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("createGradeItem")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGradeItem(CreateGradeItemRqst rqst, @QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Module module = em.find(Module.class, rqst.getModuleId());
        if (module == null) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist")).build();
        } else if (module.getAssignedTeacher() != user) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this module"))
                    .build();
        }

        try {
            GradeItem gradeItem = new GradeItem();
            gradeItem.setTitle(rqst.getTitle());
            gradeItem.setDescription(rqst.getDescription());
            gradeItem.setMaxMarks(rqst.getMaxMarks());
            gradeItem.setModule(module);
            module.getGradeItemList().add(gradeItem);

            em.persist(gradeItem);
            em.flush();

            // Add grade entry for every student in the module
            for (User stu : module.getStudentList()) {
                GradeEntry gradeEntry = new GradeEntry();
                gradeEntry.setGradeItem(gradeItem);
                gradeEntry.setStudent(stu);
                gradeItem.getGradeEntries().add(gradeEntry);

                em.persist(gradeEntry);
                em.flush();
            }

            return Response.status(Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("updateGradeItem")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response updateGradeItem(@QueryParam("userId") Long userId, CreateGradeItemRqst rqst) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        GradeItem gradeItem = em.find(GradeItem.class, rqst.getGradeItemId());
        if (gradeItem == null) {
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
    public Response retrieveGradeItems(@QueryParam("userId") Long userId, @PathParam("moduleId") Long moduleId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Module module = em.find(Module.class, moduleId);
        if (module == null) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist")).build();
        } else if (module.getAssignedTeacher() != user) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this module"))
                    .build();
        }

        try {
            RetrieveGradeItemsRsp resp = new RetrieveGradeItemsRsp(new ArrayList<>());
            for (GradeItem gradeItem : module.getGradeItemList()) {
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

            if (resp.getGradeItems().isEmpty()) {
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No grade items yet for this module")).build();
            }

            return Response.status(Status.OK).entity(resp).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("deleteGradeItem")
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteGradeItem(@QueryParam("userId") Long userId, @QueryParam("gradeItemId") Long gradeItemId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        GradeItem gradeItem = em.find(GradeItem.class, gradeItemId);
        if (gradeItem == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("GradeItem is not found!")).build();
        } else if (gradeItem.getModule().getAssignedTeacher() != user) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        for (GradeEntry ge : gradeItem.getGradeEntries()) {
            ge.setStudent(null);
            em.remove(ge);
        }

        em.remove(gradeItem);
        em.flush();

        return Response.status(Status.OK).build();
    }

    @GET
    @Path("retrieveGradeEntries")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveGradeEntries(@QueryParam("userId") Long userId, @QueryParam("gradeItemId") Long gradeItemId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        GradeItem gradeItem = em.find(GradeItem.class, gradeItemId);
        if (gradeItem == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("GradeItem is not found!")).build();
        } else if (gradeItem.getModule().getAssignedTeacher() != user) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        try {
            RetrieveGradeEntriesRsp resp = new RetrieveGradeEntriesRsp(new ArrayList<>());

            for (GradeEntry ge : gradeItem.getGradeEntries()) {
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
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @GET
    @Path("retrieveStudentGrades")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveStudentGrades(@QueryParam("userId") Long userId, @QueryParam("moduleId") Long moduleId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Student) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Module module = em.find(Module.class, moduleId);
        if (module == null) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist")).build();
        } else if (!module.getStudentList().contains(user)) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this module"))
                    .build();
        }

        try {
            Query q = em.createQuery("SELECT DISTINCT gi FROM Module m join m.gradeItemList gi join gi.gradeEntries ge WHERE gi.publish = :publish AND m.moduleId = :moduleId");
            q.setParameter("moduleId", moduleId);
            q.setParameter("publish", true);

            List<GradeItem> gradeItems = q.getResultList();

            if (gradeItems == null || gradeItems.isEmpty()) {
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No grade entries for the student")).build();
            }

            RetrieveGradeItemsRsp resp = new RetrieveGradeItemsRsp(new ArrayList<>());
            for (GradeItem gradeItem : gradeItems) {
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
                giToReturn.setGradeEntries(new ArrayList<>());
                for (GradeEntry ge : gradeItem.getGradeEntries()) {
                    if (ge.getStudent() == user) {
                        GradeEntry geToReturn = new GradeEntry();
                        geToReturn.setGradeEntryId(ge.getGradeEntryId());
                        geToReturn.setMarks(ge.getMarks());
                        geToReturn.setRemarks(ge.getRemarks());

                        giToReturn.getGradeEntries().add(geToReturn);
                    }
                }

                resp.getGradeItems().add(giToReturn);
            }

            if (resp.getGradeItems().isEmpty()) {
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No grade entries for the student")).build();
            }

            return Response.status(Status.OK).entity(resp).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("enterGradeMarks")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response enterGradeMarks(@QueryParam("userId") Long userId, EnterMarksRqst rqst) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        GradeEntry gradeEntry = em.find(GradeEntry.class, rqst.getGradeEntryId());
        if (gradeEntry == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("GradeEntry is not found")).build();
        }

        try {
            Query q = em.createQuery("SELECT gi FROM User u join u.teacherModuleList m join m.gradeItemList gi join gi.gradeEntries ge WHERE ge.gradeEntryId=:id");

            q.setParameter("id", rqst.getGradeEntryId());

            GradeItem gi = (GradeItem) q.getSingleResult();

            if (gi.getMaxMarks() < rqst.getMarks()) {
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Marks given is bigger than maxMarks of the grade item")).build();
            }

            gradeEntry.setMarks(rqst.getMarks());
            gradeEntry.setRemarks(rqst.getRemarks());

            return Response.status(Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("publishGrade")
    @Produces(MediaType.APPLICATION_JSON)
    public Response publishGrade(@QueryParam("userId") Long userId, @QueryParam("gradeItemId") Long gradeItemId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        GradeItem gradeItem = em.find(GradeItem.class, gradeItemId);
        if (gradeItem == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("GradeItem not found")).build();
        }

        gradeItem.setPublish(true);

        return Response.status(Status.OK).build();
    }

    @POST
    @Path("unpublishGrade")
    @Produces(MediaType.APPLICATION_JSON)
    public Response unpublishGrade(@QueryParam("userId") Long userId, @QueryParam("gradeItemId") Long gradeItemId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        GradeItem gradeItem = em.find(GradeItem.class, gradeItemId);
        if (gradeItem == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("GradeItem not found")).build();
        }

        gradeItem.setPublish(false);

        return Response.status(Status.OK).build();
    }

    // There are different types on converting a quiz results to gradeItem.
    // 1. best : by best marks
    // 2. first : first attempt
    // 3. last : last attempt
    @POST
    @Path("createGradeItemFromQuiz")
    @Produces(MediaType.APPLICATION_JSON)
    public Response createGradeItemFromQuiz(@QueryParam("userId") Long userId, @QueryParam("quizId") Long quizId, CreateGradeItemRqst rqst, @QueryParam("type") String type) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Module module = em.find(Module.class, rqst.getModuleId());
        if (module == null) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist")).build();
        } else if (module.getAssignedTeacher() != user) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this module"))
                    .build();
        }

        Quiz quiz = em.find(Quiz.class, quizId);
        if (quiz == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz is not found")).build();
        }

        if (!module.getQuizList().contains(quiz)) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Quiz is not part of the module")).build();
        }

//        if(quiz.getClosingDate().after(new Date())){
//            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Quiz hasn't closed yet!")).build();
//        }
        try {
            GradeItem gradeItem = new GradeItem();
            gradeItem.setTitle(rqst.getTitle());
            gradeItem.setDescription(rqst.getDescription());
            gradeItem.setModule(module);
            gradeItem.setMaxMarks(quiz.getMaxMarks());
            gradeItem.setGradeEntries(new ArrayList<>());
            module.getGradeItemList().add(gradeItem);
            em.persist(gradeItem);

            HashMap<User, Double> marks = new HashMap<>();
            if (type.toLowerCase().contains("best")) {
                for (QuizAttempt qa : quiz.getQuizAttemptList()) {
                    if (!marks.containsKey(qa.getQuizTaker()) || marks.get(qa.getQuizTaker()) < qa.getTotalMarks()) {
                        marks.put(qa.getQuizTaker(), qa.getTotalMarks());
                    }
                }
            } else if (type.toLowerCase().contains("last")) {
                HashMap<User, Date> times = new HashMap<>();
                for (QuizAttempt qa : quiz.getQuizAttemptList()) {
                    if (!marks.containsKey(qa.getQuizTaker()) || times.get(qa.getQuizTaker()).before(qa.getCreateTs())) {
                        times.put(user, qa.getCreateTs());
                        marks.put(qa.getQuizTaker(), qa.getTotalMarks());
                    }
                }
            } else if (type.toLowerCase().contains("first")) {
                HashMap<User, Date> times = new HashMap<>();
                for (QuizAttempt qa : quiz.getQuizAttemptList()) {
                    if (!marks.containsKey(qa.getQuizTaker()) || times.get(qa.getQuizTaker()).after(qa.getCreateTs())) {
                        times.put(user, qa.getCreateTs());
                        marks.put(qa.getQuizTaker(), qa.getTotalMarks());
                    }
                }
            }

            for (User u : module.getStudentList()) {
                GradeEntry gradeEntry = new GradeEntry();
                gradeEntry.setGradeItem(gradeItem);
                gradeEntry.setStudent(u);
                gradeEntry.setMarks(marks.getOrDefault(u, 0.0));
                gradeItem.getGradeEntries().add(gradeEntry);

                em.persist(gradeEntry);
                em.flush();
            }

            quiz.setGradeitemCreated(true);

            return Response.status(Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("createCoursepackQuiz")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createCoursepackQuiz(QuizRqst rqst, @QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Coursepack cp = em.find(Coursepack.class, rqst.getCoursepackId());
        if (cp == null) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Coursepack is not found!")).build();
        } else if (cp.getAssignedTeacher() != user) {
            return Response.status(Status.FORBIDDEN).entity(new ErrorRsp("User doesn't have access to this coursepack")).build();
        }

        try {
            Quiz quiz = new Quiz();
            quiz.setTitle(rqst.getTitle());
            quiz.setDescription(rqst.getDescription());
            quiz.setQuizType(rqst.getQuizType());
            quiz.setQuestionsOrder(rqst.getQuestionsOrder());
//            quiz.setMaxTimeToFinish(rqst.getMaxTimeToFinish());
            quiz.setNoOfAttempts(rqst.getNoOfAttempts());
//            quiz.setCoursepack(cp);
            quiz.setPublish(rqst.isPublish());
            quiz.setQuestionList(new ArrayList<Question>());
            // Parse Date
//            Date openingDate = dateFormatter.parse(rqst.getOpeningDate());
//            Date closingDate = dateFormatter.parse(rqst.getClosingDate());

            // Verify date
//            if(!openingDate.before(closingDate)){
//                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Opening date is not before closing date")).build();
//            }
//            quiz.setOpeningDate(openingDate);
//            quiz.setClosingDate(closingDate);
            List<QuestionModel> questionsRqst = rqst.getQuestions();

            double total = 0;
            int count = 1;
            for (QuestionModel qm : questionsRqst) {
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
                question.setCorrectAnswer(qm.getCorrectAnswer());

                if (question.getType() == QuestionTypeEnum.radiogroup) {
                    for (ChoiceModel choice : qm.getChoices()) {
                        question.getChoices().add(choice.getText());
                    }
                }

                em.persist(question);
                em.flush();

                quiz.getQuestionList().add(question);
            }

            cp.getQuizList().add(quiz);
            em.persist(quiz);
            em.flush();

            quiz.setMaxMarks(total);

            return Response.status(Status.OK).entity(quiz.getQuizId()).build();

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("createCoursepackQuizAttempt")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createCoursepackQuizAttempt(@QueryParam("userId") Long userId, CreateQuizAttemptRqst rqst) {
        Date now = new Date();

        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() == AccessRightEnum.Teacher || user.getAccessRight() == AccessRightEnum.Admin) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Quiz quiz = em.find(Quiz.class, rqst.getQuizId());
        if (quiz == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID doesn't exist")).build();
        }

//        if(quiz.getClosingDate().before(now) || quiz.getOpeningDate().after(now)){
//            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Quiz is closed")).build();
//        }
//
        // Check attempts
        // Only one attempt
        for (QuizAttempt qa : quiz.getQuizAttemptList()) {
            if (qa.getQuizTaker() == user) {
                return Response.status(Status.BAD_REQUEST).entity("Student has reached the maximum number of attempts").build();
            }
        }

        try {
            QuizAttempt qa = new QuizAttempt();
            qa.setCreateTs(now);
            qa.setQuiz(quiz);
            qa.setQuizTaker(user);
            quiz.getQuizAttemptList().add(qa);
            qa.setQuestionAttemptList(new ArrayList<>());
            em.persist(qa);

            double totalMarks = 0.0;
            for (QuestionAttemptModel queA : rqst.getQuestionAttempts()) {
                Question que = em.find(Question.class, queA.getQuestionId());
                if (que == null) {
                    return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Question not found")).build();
                }

                QuestionAttempt queAToPersist = new QuestionAttempt();
                queAToPersist.setAnswer(queA.getAnswer());
                queAToPersist.setQuestion(que);
                if (que.getType() == QuestionTypeEnum.radiogroup && queA.getAnswer().equals(que.getCorrectAnswer())) {
                    queAToPersist.setMarks(que.getPoints());
                    totalMarks += que.getPoints();
                } else {
                    queAToPersist.setMarks(0.0);
                }

                em.persist(queAToPersist);
                System.out.println(qa.getQuestionAttemptList());
                qa.getQuestionAttemptList().add(queAToPersist);

            }
            qa.setTotalMarks(totalMarks);
            em.flush();

            return Response.status(Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @GET
    @Path("retrieveCoursepackQuiz/{quizId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveCoursepackQuiz(@PathParam("quizId") Long quizId, @QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() == AccessRightEnum.Admin || user.getAccessRight() == AccessRightEnum.Public) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        AccessRightEnum ar = user.getAccessRight();

        try {
            Quiz q = em.find(Quiz.class, quizId);

            if (q == null) {
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID is not found")).build();
            }

            QuizRsp newQ = new QuizRsp();
            newQ.setQuizId(q.getQuizId());
//            newQ.setOpeningDate(dateFormatter.format(q.getOpeningDate()));
//            newQ.setClosingDate(dateFormatter.format(q.getClosingDate()));
            newQ.setQuizType(q.getQuizType());
            newQ.setMaxMarks(q.getMaxMarks());
            newQ.setDescription(q.getDescription());
            newQ.setTitle(q.getTitle());
//            newQ.setMaxTimeToFinish(q.getMaxTimeToFinish());
            newQ.setPublish(q.isPublish());
//            newQ.setNoOfAttempts(q.getNoOfAttempts());
            newQ.setQuestionsOrder(q.getQuestionsOrder());
            newQ.setPublishAnswer(q.isPublishAnswer());
            newQ.getPages().add(new PageModel(q.getQuestionList(), "page1"));
            newQ.setReachedMaxAttempt(false);
            for (QuizAttempt qa : q.getQuizAttemptList()) {
                if (qa.getQuizTaker() == user) {
                    newQ.setReachedMaxAttempt(true);
                }
            }

            return Response.status(Status.OK).entity(newQ).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @GET
    @Path("retrieveAllCoursepackQuiz/{coursepackId}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllCoursepackQuiz(@PathParam("coursepackId") Long coursepackId, @QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        AccessRightEnum ar = user.getAccessRight();

        Coursepack cp = em.find(Coursepack.class, coursepackId);
        if (cp == null) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Coursepack is not found!")).build();
        } else if (cp.getAssignedTeacher() != user) {
            return Response.status(Status.FORBIDDEN).entity(new ErrorRsp("User doesn't have access to this coursepack")).build();
        }

        try {
            List<Quiz> quizzes = new ArrayList<>();

            for (Quiz q : cp.getQuizList()) {
                if (ar == AccessRightEnum.Teacher || q.isPublish()) {
                    Quiz newQ = new Quiz();
                    newQ.setQuizId(q.getQuizId());
//                    newQ.setOpeningDate(q.getOpeningDate());
//                    newQ.setClosingDate(q.getClosingDate());
                    newQ.setQuizType(q.getQuizType());
                    newQ.setMaxMarks(q.getMaxMarks());
                    newQ.setDescription(q.getDescription());
                    newQ.setTitle(q.getTitle());
//                    newQ.setMaxTimeToFinish(q.getMaxTimeToFinish());
                    newQ.setPublish(q.isPublish());
//                    newQ.setNoOfAttempts(q.getNoOfAttempts());
                    newQ.setQuestionsOrder(q.getQuestionsOrder());
                    newQ.setPublishAnswer(q.isPublishAnswer());
                    quizzes.add(newQ);
                }
            }

            return Response.status(Status.OK).entity(new RetrieveQuizzesResp(quizzes)).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("updateCoursepackQuiz")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateCoursepackQuiz(QuizRqst rqst, @QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Quiz quiz = em.find(Quiz.class, rqst.getQuizId());
        if (quiz == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID doesn't exist")).build();
        }

        if (quiz.getLessonOrder().getOutlines().getCoursepack().getAssignedTeacher() != user) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        boolean attempted = !quiz.getQuizAttemptList().isEmpty();

        try {
            // Basic Config
            quiz.setTitle(rqst.getTitle());
            quiz.setDescription(rqst.getDescription());
            quiz.setQuizType(rqst.getQuizType());
            quiz.setQuestionsOrder(rqst.getQuestionsOrder());
            quiz.setMaxTimeToFinish(rqst.getMaxTimeToFinish());
            quiz.setNoOfAttempts(rqst.getNoOfAttempts());
            quiz.setPublish(rqst.isPublish());
            // Parse Date
            Date openingDate = dateFormatter.parse(rqst.getOpeningDate());
            Date closingDate = dateFormatter.parse(rqst.getClosingDate());

            // Verify date
            if (!openingDate.before(closingDate)) {
                return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Opening date is not before closing date")).build();
            }

            quiz.setOpeningDate(openingDate);
            quiz.setClosingDate(closingDate);

            return Response.status(Status.OK).build();

        } catch (ParseException pe) {
            pe.printStackTrace();
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Opening or closing date isn't in proper format")).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("createQuestionCoursepack")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createQuestionCoursepack(QuestionModel qm, @QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Quiz quiz = em.find(Quiz.class, qm.getQuizId());
        if (quiz == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID doesn't exist")).build();
        }

//        if(quiz.getLessonOrder().getOutlines().getCoursepack().getAssignedTeacher() != user){
//            return Response.status(Status.FORBIDDEN)
//                    .entity(new ErrorRsp("User doesn't have access to this function"))
//                    .build();
//        }
        try {
            Question question = new Question();
            question.setTitle(qm.getTitle());
            question.setLevel(qm.getLevel());
            question.setNumber(quiz.getQuestionList().size() + 1);
            question.setPoints(qm.getPoints());
            question.setExplanation(qm.getExplanation());
            question.setType(qm.getType());
            question.setIsRequired(qm.getIsRequired());
            question.setChoices(new ArrayList<>());
            question.setCorrectAnswer(qm.getCorrectAnswer());

            if (question.getType() == QuestionTypeEnum.radiogroup) {
                for (ChoiceModel choice : qm.getChoices()) {
                    question.getChoices().add(choice.getText());
                    if (qm.getCorrectAnswer().equals(choice.getValue())) {
                        question.setCorrectAnswer(choice.getText());
                    }
                }
            }

            em.persist(question);
            em.flush();

            quiz.getQuestionList().add(question);
            quiz.setMaxMarks(quiz.getMaxMarks() + question.getPoints());
            return Response.status(Status.OK).entity(question).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("updateQuestionCoursepack")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateQuestionCoursepack(QuestionModel qm, @QueryParam("userId") Long userId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Quiz quiz = em.find(Quiz.class, qm.getQuizId());
        if (quiz == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID doesn't exist")).build();
        }

//        if(quiz.getLessonOrder().getOutlines().getCoursepack().getAssignedTeacher() != user){
//            return Response.status(Status.FORBIDDEN)
//                    .entity(new ErrorRsp("User doesn't have access to this function"))
//                    .build();
//        }
        boolean attempted = !quiz.getQuizAttemptList().isEmpty();

        try {
            Question question = em.find(Question.class, qm.getQuestionId());

            if (question == null) {
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Question with the given ID doesn't exist")).build();
            }
            double pointDiff = qm.getPoints() - question.getPoints();
            if (attempted) {
                Question oldQ = question;
                quiz.getQuestionList().remove(oldQ);

                question = new Question();
                question.setTitle(qm.getTitle());
                question.setLevel(qm.getLevel());
                question.setNumber(oldQ.getNumber());
                question.setPoints(qm.getPoints());
                question.setExplanation(qm.getExplanation());
                question.setType(qm.getType());
                question.setIsRequired(qm.getIsRequired());
                question.setChoices(new ArrayList<>());
                question.setCorrectAnswer(qm.getCorrectAnswer());

                em.persist(question);
                quiz.getQuestionList().add(question);
                em.flush();
            } else {
                question.setTitle(qm.getTitle());
                question.setLevel(qm.getLevel());
                question.setPoints(qm.getPoints());
                question.setExplanation(qm.getExplanation());
                question.setType(qm.getType());
                question.setIsRequired(qm.getIsRequired());
                question.setChoices(new ArrayList<>());
                question.setCorrectAnswer(qm.getCorrectAnswer());
            }

            quiz.setMaxMarks(quiz.getMaxMarks() + pointDiff);

            if (question.getType() == QuestionTypeEnum.radiogroup) {
                for (ChoiceModel choice : qm.getChoices()) {
                    question.getChoices().add(choice.getText());
                    if (qm.getCorrectAnswer().equals(choice.getValue())) {
                        question.setCorrectAnswer(choice.getText());
                    }
                }
            }

            return Response.status(Status.OK).entity(question).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @DELETE
    @Path("deleteQuestionCoursepack")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response deleteQuestionCoursepack(@QueryParam("userId") Long userId, @QueryParam("quizId") Long quizId, @QueryParam("questionId") Long questionId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() != AccessRightEnum.Teacher) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Quiz quiz = em.find(Quiz.class, quizId);
        if (quiz == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID doesn't exist")).build();
        }

//        if(quiz.getLessonOrder().getOutlines().getCoursepack().getAssignedTeacher() != user){
//            return Response.status(Status.FORBIDDEN)
//                    .entity(new ErrorRsp("User doesn't have access to this function"))
//                    .build();
//        }
        boolean attempted = !quiz.getQuizAttemptList().isEmpty();

        try {
            Question question = em.find(Question.class, questionId);

            if (question == null) {
                return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Question with the given ID doesn't exist")).build();
            }

            // Reduce the number of other questions after this question
            for (Question q : quiz.getQuestionList()) {
                if (q.getNumber() > question.getNumber()) {
                    q.setNumber(q.getNumber() - 1);
                }
            }

            quiz.setMaxMarks(quiz.getMaxMarks() - question.getPoints());
            quiz.getQuestionList().remove(question);
            if (!attempted) {
                em.remove(question);
            }

            return Response.status(Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @POST
    @Path("completeCoursepackQuiz")
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response completeCoursepackQuiz(@QueryParam("userId") Long userId, @QueryParam("quizId") Long quizId) {
        User user = em.find(User.class, userId);
        if (user == null || user.getAccessRight() == AccessRightEnum.Teacher || user.getAccessRight() == AccessRightEnum.Admin) {
            return Response.status(Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }

        Quiz quiz = em.find(Quiz.class, quizId);
        if (quiz == null) {
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID not found!")).build();
        } else if (quiz.getLessonOrder() == null) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("Quiz is not part of a coursepack")).build();
        }

        if (!quiz.getLessonOrder().getOutlines().getCoursepack().getPublicUserList().contains(user)) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("User is not enrolled in this coursepack")).build();
        }

        if (quiz.getLessonOrder().getPublicUserList().contains(user)) {
            return Response.status(Status.BAD_REQUEST).entity(new ErrorRsp("User already finished this lesson order")).build();
        }
        
        Coursepack coursepack = quiz.getLessonOrder().getOutlines().getCoursepack();
        if(coursepack == null){
            return Response.status(Status.NOT_FOUND).entity(new ErrorRsp("No coursepack found")).build();
        }
        quiz.getLessonOrder().getPublicUserList().add(user);
        completeCoursepack(user, coursepack);
        user.setQuizCompleted(+1);
        rewardCompleteFiveAssessmentBadge(user);
        rewardCompleteTenAssessmentBadge(user);
        rewardCompleteTwentyAssessmentBadge(user);
        return Response.status(Status.OK).build();
    }

    @GET
    @Path("retrieveQuizStatistics")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveQuizStatistics(@QueryParam("quizId") Long quizId) {
        Quiz quiz = em.find(Quiz.class, quizId);
        if (quiz == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Quiz with the given ID not found!")).build();
        }

        if (quiz.getQuizAttemptList().isEmpty()) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("No attempts yet for this quiz!")).build();
        }

        try {
            RetrieveSurveyStatistics resp = new RetrieveSurveyStatistics(new ArrayList<>());
            resp.setTitle(quiz.getTitle());
            resp.setDescription(quiz.getDescription());
            resp.setQuizId(quiz.getQuizId());
            resp.setAttempts(quiz.getQuizAttemptList().size());

            for (Question q : quiz.getQuestionList()) {
                if (q.getType() == QuestionTypeEnum.radiogroup) {
                    QuestionStatistic qs = new QuestionStatistic(q.getQuestionId(), q.getTitle(), new ArrayList<>());

                    HashMap<String, Integer> count = new HashMap<>(); // Answer : Attempt
                    for (QuizAttempt sa : quiz.getQuizAttemptList()) {
                        for (QuestionAttempt qa : sa.getQuestionAttemptList()) {
                            if (qa.getQuestion() == q) {
                                count.put(qa.getAnswer(), count.getOrDefault(qa.getAnswer(), 0) + 1);
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
                        Map.Entry pair = (Map.Entry) it.next();
                        AnswerStatistic as = new AnswerStatistic();
                        as.setAnswer((String) pair.getKey());
                        as.setCount((int) pair.getValue());
                        qs.getAnswers().add(as);
                        it.remove(); // avoids a ConcurrentModificationException
                    }
                    resp.getQuestions().add(qs);
                } else {
                    resp.getQuestions().add(new QuestionStatistic(q.getQuestionId(), q.getTitle(), null));
                }
            }

            return Response.status(Response.Status.OK).entity(resp).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    public boolean completeCoursepack(User user, Coursepack coursepack) {
        List<Outlines> outlinesList = coursepack.getOutlineList();
        if (outlinesList == null || outlinesList.isEmpty()) {
            System.out.println("No coursepack outline");
            return false;
        }
        for (Outlines o : outlinesList) {
            List<LessonOrder> lessonOrderList = o.getLessonOrder();
            if (lessonOrderList == null || lessonOrderList.isEmpty()) {
                System.out.println("no lesson order");
                for (LessonOrder l : lessonOrderList) {
                    if (!l.getPublicUserList().contains(user)) {
                        return false;
                    }
                }
            }
            user.getPublicUserCompletedCoursepackList().add(coursepack);
            user.setCoursepackCompleted(+1);
            rewardCertification(user, coursepack);
            rewardCompleteFirstCoursepackBadge(user);
            rewardCompleteThreeCoursepackBadge(user);
        }
        return true;
    }

    public boolean rewardCertification(User user, Coursepack coursepack) {
        Query query = em.createQuery("select c from Certification c join c.coursepackList cp where cp = :coursepack");
        query.setParameter("coursepack", coursepack);
        Certification certification = (Certification) query.getSingleResult();
        if (certification == null) {
            System.out.println("No certifcation found");
            return false;
        }
        if(user.getCertificationList().contains(certification)){
            System.out.println("Certification has been attained");
            return false;
        }
        List<Coursepack> coursepackList = certification.getCoursepackList();
        if (coursepackList == null || coursepackList.isEmpty()) {
            System.out.println("No coursepack in certifcation criteria");
            return false;
        }
        for (Coursepack cp : coursepackList) {
            if (!user.getPublicUserCompletedCoursepackList().contains(cp)) {
                System.out.println("Certification criteria not completed");
                return false;
            }
        }
        Date currentDate = new Date();
        certification.setDateAchieved(currentDate);
        user.getCertificationList().add(certification);
        return true;
    }

    public boolean rewardCompleteFiveAssessmentBadge(User user) {
        Query query = em.createQuery("select b from Badge b where b.title =: title");
        query.setParameter("title", "5quiz.JPG");
        Badge badge = (Badge) query.getSingleResult();
        if(user.getBadgeList().contains(badge)){
            System.out.println("Badge has been attained");
            return false;
        }
        if (user.getQuizCompleted() == 5) {
            user.getBadgeList().add(badge);
            return true;
        }
        return false;
    }

    public boolean rewardCompleteTenAssessmentBadge(User user) {
        Query query = em.createQuery("select b from Badge b where b.title =: title");
        query.setParameter("title", "10quiz.JPG");
        Badge badge = (Badge) query.getSingleResult();
        if(user.getBadgeList().contains(badge)){
            System.out.println("Badge has been attained");
            return false;
        }
        if (user.getQuizCompleted() == 5) {
            user.getBadgeList().add(badge);
            return true;
        }
        return false;
    }

    public boolean rewardCompleteTwentyAssessmentBadge(User user) {
        Query query = em.createQuery("select b from Badge b where b.title =: title");
        query.setParameter("title", "20quiz.JPG");
        Badge badge = (Badge) query.getSingleResult();
        if(user.getBadgeList().contains(badge)){
            System.out.println("Badge has been attained");
            return false;
        }
        if (user.getQuizCompleted() == 5) {
            user.getBadgeList().add(badge);
            return true;
        }
        return false;
    }

    public boolean rewardCompleteFirstCoursepackBadge(User user) {
        Query query = em.createQuery("select b from Badge b where b.title =: title");
        query.setParameter("title", "1coursepack.JPG");
        Badge badge = (Badge) query.getSingleResult();
        if(user.getBadgeList().contains(badge)){
            System.out.println("Badge has been attained");
            return false;
        }
        if (user.getCoursepackCompleted() == 1) {
            user.getBadgeList().add(badge);
            return true;
        }
        return false;
    }

    public boolean rewardCompleteThreeCoursepackBadge(User user) {
        Query query = em.createQuery("select b from Badge b where b.title =: title");
        query.setParameter("title", "3coursepack.JPG");
        Badge badge = (Badge) query.getSingleResult();
        if(user.getBadgeList().contains(badge)){
            System.out.println("Badge has been attained");
            return false;
        }
        if (user.getCoursepackCompleted() == 1) {
            user.getBadgeList().add(badge);
            return true;
        }
        return false;
    }
}
