/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.AnswerStatistic;
import datamodel.rest.AttendanceStatistic;
import datamodel.rest.ErrorRsp;
import datamodel.rest.ForumTopicStatistic;
import datamodel.rest.GetUserRsp;
import datamodel.rest.MarksStatistic;
import datamodel.rest.RetrieveAttendanceStatistics;
import datamodel.rest.RetrieveBarAnalytics;
import datamodel.rest.RetrieveForumAnalytics;
import datamodel.rest.RetrieveListBarAnalytics;
import datamodel.rest.RetrieveMarksStatistics;
import ejb.AcademicYearSessionBean;
import entities.Attendance;
import entities.Consultation;
import entities.ConsultationTimeslot;
import entities.ForumPost;
import entities.ForumTopic;
import entities.GradeEntry;
import entities.GradeItem;
import entities.Module;
import entities.Quiz;
import entities.QuizAttempt;
import entities.Tutorial;
import entities.User;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import util.AccessRightEnum;

/**
 *
 * @author Asus
 */
@Path("analytics")
@Stateless
public class LearningAnalyticsResource {
    
    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;
    
    @GET
    @Path("retrieveGradeItemAnalytics")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveGradeItemAnalytics(@QueryParam("userId") Long userId, @QueryParam("moduleId") Long moduleId){
        User user = em.find(User.class, userId);
        if(user == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User doesn't exist!")).build();
        }

        Module module = em.find(Module.class, moduleId);
        if(module == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist!")).build();
        }
        
        if(module.getAssignedTeacher() != user && !module.getStudentList().contains(user)){
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        AccessRightEnum ar = user.getAccessRight();
        
        try{
            RetrieveMarksStatistics resp = new RetrieveMarksStatistics(new ArrayList<>());
            
            for(GradeItem gi: module.getGradeItemList()){
                if(gi.getPublish() || ar == AccessRightEnum.Teacher){
                    ArrayList<Double> marks  = new ArrayList<>(gi.getGradeEntries().size());
                    double total = 0.0;
                    MarksStatistic ms = new MarksStatistic();
                    for (GradeEntry ge : gi.getGradeEntries()) {
                        marks.add(ge.getMarks());
                        total += ge.getMarks();
                        if(ar == AccessRightEnum.Student && ge.getStudent() == user){
                            ms.setStudentMarks(ge.getMarks());
                        }
                    }
                    Collections.sort(marks);
                    ms.setTitle(gi.getTitle());
                    ms.setGradeItemId(gi.getGradeItemId());
                    ms.setMin(marks.get(0));
                    ms.setMax(marks.get(marks.size()-1));
                    ms.setMean(total/marks.size());
                    if(marks.size()%2==1){
                        ms.setMedian(marks.get(marks.size()/2));
                    } else {
                        ms.setMedian( ( marks.get(marks.size()/2) + marks.get( (marks.size()/2) - 1) ) /2);
                    }
                    double rank25 = 1.0*marks.size()/4;
                    double rank75 = 0.75*marks.size();
                    int rank25r = (int) Math.round(rank25);
                    int rank75r = (int) Math.round(rank75);
                    
                    if(rank25-rank25r == 0){
                        ms.setTwentyfifth(marks.get(rank25r));
                    } else {
                        ms.setTwentyfifth(marks.get(rank25r) + (rank25-rank25r)*(marks.get(rank25r+1) - marks.get(rank25r)) );
                    }
                    
                    if(rank75-rank75r == 0){
                        ms.setSeventyfifth(marks.get(rank75r));
                    } else {
                        ms.setSeventyfifth(marks.get(rank75r) + (rank75-rank75r)*(marks.get(rank75r+1) - marks.get(rank75r)));
                    }
                    
                    resp.getItems().add(ms);
                }
            }
            
            return Response.status(Response.Status.OK).entity(resp).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @GET
    @Path("retrieveQuizAnalytics")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveQuizAnalytics(@QueryParam("userId") Long userId, @QueryParam("moduleId") Long moduleId){
        User user = em.find(User.class, userId);
        if(user == null){
            return Response.status(Response.Status.FORBIDDEN).entity(new ErrorRsp("User doesn't exist!")).build();
        }

        Module module = em.find(Module.class, moduleId);
        if(module == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist!")).build();
        }
        
        if(module.getAssignedTeacher() != user && !module.getStudentList().contains(user)){
            return Response.status(Response.Status.FORBIDDEN)
                    .entity(new ErrorRsp("User doesn't have access to this function"))
                    .build();
        }
        
        AccessRightEnum ar = user.getAccessRight();
        
        try{
            RetrieveMarksStatistics resp = new RetrieveMarksStatistics(new ArrayList<>());
            
            for(Quiz q: module.getQuizList()){
                if(!q.getQuizAttemptList().isEmpty() && (q.isPublishAnswer() || ar == AccessRightEnum.Teacher)){
                    ArrayList<Double> marks  = new ArrayList<>(q.getQuizAttemptList().size());
                    double total = 0.0;
                    MarksStatistic ms = new MarksStatistic();
                    for (QuizAttempt qa : q.getQuizAttemptList()) {
                        marks.add(qa.getTotalMarks());
                        total += qa.getTotalMarks();
                        if(ar == AccessRightEnum.Student && qa.getQuizTaker() == user){
                            ms.setStudentMarks(qa.getTotalMarks());
                        }
                    }
                    Collections.sort(marks);
                    ms.setTitle(q.getTitle());
                    ms.setQuizId(q.getQuizId());
                    ms.setMin(marks.get(0));
                    ms.setMax(marks.get(marks.size()-1));
                    ms.setMean(total/marks.size());
                    if(marks.size()%2==1){
                        ms.setMedian(marks.get(marks.size()/2));
                    } else {
                        ms.setMedian( ( marks.get(marks.size()/2) + marks.get( (marks.size()/2) - 1) ) /2);
                    }
                    double rank25 = 1.0*marks.size()/4;
                    double rank75 = 0.75*marks.size();
                    int rank25r = (int) Math.round(rank25);
                    int rank75r = (int) Math.round(rank75);
                    
                    if(rank25-rank25r == 0){
                        ms.setTwentyfifth(marks.get(rank25r));
                    } else {
                        ms.setTwentyfifth(marks.get(rank25r) + (rank25-rank25r)*(marks.get(rank25r+1) - marks.get(rank25r)) );
                    }
                    
                    if(rank75-rank75r == 0){
                        ms.setSeventyfifth(marks.get(rank75r));
                    } else {
                        ms.setSeventyfifth(marks.get(rank75r) + (rank75-rank75r)*(marks.get(rank75r+1) - marks.get(rank75r)));
                    }
                    
                    resp.getItems().add(ms);
                }
            }
            
            return Response.status(Response.Status.OK).entity(resp).build();
        } catch (Exception e){
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @GET
    @Path("retrieveAttendanceAnalytics")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAttendanceAnalytics(@QueryParam("moduleId") Long moduleId){
        Module module = em.find(Module.class, moduleId);
        if(module == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist!")).build();
        }
        
        HashMap<Date, Integer> weekAtt = new HashMap<>();
        for(Attendance a: module.getAttandanceList()){
            long timeInMs = a.getStartTs().getTime();
            int day = a.getStartTs().getDay();
            long startOfWeek = timeInMs - (24*3600*1000* day);
            Date dateOfWeek = new Date(startOfWeek);
            dateOfWeek.setHours(0);
            dateOfWeek.setMinutes(0);
            dateOfWeek.setSeconds(0);
            weekAtt.put(dateOfWeek, weekAtt.getOrDefault(dateOfWeek, 0) + 1);
        }
        
        HashMap<Date, Integer> tutorialAtt = new HashMap<>();
        for(Tutorial t: module.getTutorials()){
            for(Attendance a: t.getAttendanceList()){
                long timeInMs = a.getStartTs().getTime();
                int day = a.getStartTs().getDay();
                long startOfWeek = timeInMs - (24*3600*1000* day);
                Date dateOfWeek = new Date(startOfWeek);
                dateOfWeek.setHours(0);
                dateOfWeek.setMinutes(0);
                dateOfWeek.setSeconds(0);
                tutorialAtt.put(dateOfWeek, tutorialAtt.getOrDefault(dateOfWeek, 0) + 1);
            }
        }
        
        RetrieveAttendanceStatistics resp = new RetrieveAttendanceStatistics(new ArrayList<>());
        
        int classSize = module.getStudentList().size();
        
        Iterator it = weekAtt.entrySet().iterator();
        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            AttendanceStatistic as = new AttendanceStatistic();
            as.setStartDate( (Date) pair.getKey());
            as.setPresentLecture((int) pair.getValue());
            as.setAbsentLecture(classSize - (int) pair.getValue());
            if(tutorialAtt.containsKey((Date) pair.getKey())){
                as.setPresentTutorial(tutorialAtt.get((Date) pair.getKey()));
                as.setAbsentTutorial(classSize - tutorialAtt.get((Date) pair.getKey()));
            } else {
                as.setPresentTutorial(0);
                as.setAbsentTutorial(classSize);
            }
            resp.getItems().add(as);
            it.remove(); // avoids a ConcurrentModificationException
        }
        
        it = tutorialAtt.entrySet().iterator();
        while (it.hasNext()){
            Map.Entry pair = (Map.Entry)it.next();
            if(!weekAtt.containsKey((Date) pair.getKey())){
                AttendanceStatistic as = new AttendanceStatistic();
                as.setPresentLecture(0);
                as.setAbsentLecture(classSize);
                as.setPresentTutorial((int) pair.getValue());
                as.setAbsentTutorial(classSize - (int) pair.getValue());
                resp.getItems().add(as);
            }
            it.remove(); // avoids a ConcurrentModificationException
        }
        
        return Response.status(Response.Status.OK).entity(resp).build();
    }
    
    @GET
    @Path("retrieveBarAnalytics")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveBarAnalytics(@QueryParam("moduleId") Long moduleId){
        Module module = em.find(Module.class, moduleId);
        if(module == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist!")).build();
        }
        
        RetrieveBarAnalytics resp = new RetrieveBarAnalytics();
        resp.setClassSize(module.getStudentList().size());
        
        // Find last attendance
        if(!module.getAttandanceList().isEmpty()){
            Attendance last = module.getAttandanceList().get(0);
            for(Attendance a: module.getAttandanceList()){
                if(a.getStartTs().after(last.getStartTs())){
                    last = a;
                }
            }
            resp.setLectureAttendance(last.getAttendees().size());
        } else {
            resp.setLectureAttendance(0);
        }
        
        // Consultation
        resp.setTotalConsultations(module.getConsultationList().size());
        for(ConsultationTimeslot c: module.getConsultationList()){
            if(c.getBooker() != null){
                resp.setBookedConsultations(resp.getBookedConsultations() + 1);
            }
        }
        
        // Quiz
        if(module.getQuizList().isEmpty()){
            resp.setQuizAttempts(0);
        } else {
            Quiz quiz = module.getQuizList().get(0);

            if(quiz.getOpeningDate().after(new Date())){ // make sure starting quiz is in the past.
                for(Quiz q: module.getQuizList()){
                    if(q.getOpeningDate().before(new Date())){
                        quiz = q;
                        break;
                    }
                }
            }
            for(Quiz q: module.getQuizList()){ // find latest quiz
                if(q.getOpeningDate().before(new Date()) && q.getOpeningDate().after(quiz.getOpeningDate())){
                    quiz = q;
                }
            }

            // Count number of different student attempting the quiz
            HashSet<User> set = new HashSet<>();
            for(QuizAttempt qa: quiz.getQuizAttemptList()){
                if(!set.contains(qa.getQuizTaker())){
                    set.add(qa.getQuizTaker());
                }
            }
            resp.setQuizAttempts(set.size());
        }
        
        HashSet<User> set = new HashSet<>();
        for(ForumTopic ft: module.getForumTopicList()){
            for(ForumPost thread: ft.getThreads()){
                if(!set.contains(thread.getOwner())){
                    set.add(thread.getOwner());
                }
                
                for(ForumPost comment: thread.getComments()){
                    if(!set.contains(comment.getOwner())){
                        set.add(comment.getOwner());
                    }
                }
                
                for(ForumPost reply: thread.getReplies()){
                    if(!set.contains(reply.getOwner())){
                        set.add(reply.getOwner());
                    }
                    for(ForumPost comment: reply.getComments()){
                        if(!set.contains(comment.getOwner())){
                            set.add(comment.getOwner());
                        }
                    }
                }
            }
        }
        
        resp.setForumContributions(set.size());
        
        return Response.status(Response.Status.OK).entity(resp).build();
    }
    
    @GET
    @Path("retrieveForumAnalytics")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveForumAnalytics(@QueryParam("moduleId") Long moduleId){
        Module module = em.find(Module.class, moduleId);
        if(module == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist!")).build();
        }
        
        RetrieveForumAnalytics resp = new RetrieveForumAnalytics(new ArrayList<>());
        
        for(ForumTopic ft: module.getForumTopicList()){
            ForumTopicStatistic fts = new ForumTopicStatistic();
            fts.setTitle(ft.getTitle());
            fts.setThreads(ft.getThreads().size());
            
            int replies = 0;
            int comments = 0;
            for(ForumPost thread: ft.getThreads()){
                comments += thread.getComments().size();
                replies += thread.getReplies().size();
                
                for(ForumPost reply: thread.getReplies()){
                    comments += reply.getComments().size();
                }
            }
            
            fts.setReplies(replies);
            fts.setComments(comments);
            
            resp.getItems().add(fts);
        }
        
        return Response.status(Response.Status.OK).entity(resp).build();
    }
    
    @GET
    @Path("retrieveListBarAnalytics")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveListBarAnalytics(@QueryParam("userId") Long userId){
        
        User user = em.find(User.class, userId);
        if(user == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User doesn't exist!")).build();
        }
        
        if(user.getAccessRight() != AccessRightEnum.Teacher){
            return Response.status(Response.Status.FORBIDDEN).entity(new ErrorRsp("User doesn't has access to this function")).build();
        }
        
        try{
            RetrieveListBarAnalytics resp = new RetrieveListBarAnalytics(new ArrayList<>());
            
            Query query = em.createQuery("SELECT DISTINCT m FROM User u join u.teacherModuleList m "
                    + "WHERE m.semesterOffered = :semester AND m.yearOffered = :year AND u.userId = :userId");
            query.setParameter("semester", AcademicYearSessionBean.getSemester());
            query.setParameter("year", AcademicYearSessionBean.getYear());
            query.setParameter("userId", userId);
            List<Module> modules = query.getResultList();

            if(!modules.isEmpty() && modules.get(0) != null){
                for(Module module: modules){
                    RetrieveBarAnalytics rba = new RetrieveBarAnalytics();
                    rba.setModuleCode(module.getCode());
                    rba.setModuleTitle(module.getTitle());
                    rba.setModuleId(module.getModuleId());
                    rba.setClassSize(module.getStudentList().size());

                    // Find last attendance
                    if(!module.getAttandanceList().isEmpty()){
                        Attendance last = module.getAttandanceList().get(0);
                        for(Attendance a: module.getAttandanceList()){
                            if(a.getStartTs().after(last.getStartTs())){
                                last = a;
                            }
                        }
                        rba.setLectureAttendance(last.getAttendees().size());
                    } else {
                        rba.setLectureAttendance(0);
                    }


                    // Consultation
                    rba.setTotalConsultations(module.getConsultationList().size());
                    for(ConsultationTimeslot c: module.getConsultationList()){
                        if(c.getBooker() != null){
                            rba.setBookedConsultations(rba.getBookedConsultations() + 1);
                        }
                    }

                    // Quiz
                    if(module.getQuizList().isEmpty()){
                        rba.setQuizAttempts(0);
                    } else {
                        Quiz quiz = module.getQuizList().get(0);

                        if(quiz.getOpeningDate().after(new Date())){ // make sure starting quiz is in the past.
                            for(Quiz q: module.getQuizList()){
                                if(q.getOpeningDate().before(new Date())){
                                    quiz = q;
                                    break;
                                }
                            }
                        }
                        for(Quiz q: module.getQuizList()){ // find latest quiz
                            if(q.getOpeningDate().before(new Date()) && q.getOpeningDate().after(quiz.getOpeningDate())){
                                quiz = q;
                            }
                        }

                        // Count number of different student attempting the quiz
                        HashSet<User> set = new HashSet<>();
                        for(QuizAttempt qa: quiz.getQuizAttemptList()){
                            if(!set.contains(qa.getQuizTaker())){
                                set.add(qa.getQuizTaker());
                            }
                        }
                        rba.setQuizAttempts(set.size());
                    }

                    HashSet<User> set = new HashSet<>();
                    for(ForumTopic ft: module.getForumTopicList()){
                        for(ForumPost thread: ft.getThreads()){
                            if(!set.contains(thread.getOwner())){
                                set.add(thread.getOwner());
                            }

                            for(ForumPost comment: thread.getComments()){
                                if(!set.contains(comment.getOwner())){
                                    set.add(comment.getOwner());
                                }
                            }

                            for(ForumPost reply: thread.getReplies()){
                                if(!set.contains(reply.getOwner())){
                                    set.add(reply.getOwner());
                                }
                                for(ForumPost comment: reply.getComments()){
                                    if(!set.contains(comment.getOwner())){
                                        set.add(comment.getOwner());
                                    }
                                }
                            }
                        }
                    }

                    rba.setForumContributions(set.size());
                    resp.getBars().add(rba);
                }
            }
        
            return Response.status(Response.Status.OK).entity(resp).build();
        } catch (Exception e){
           e.printStackTrace();
           return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
       }
    }
    
    @GET
    @Path("retrieveBottomStudents")
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveBottomStudents(@QueryParam("moduleId") Long moduleId){
        Module module = em.find(Module.class, moduleId);
        if(module == null){
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module doesn't exist!")).build();
        }
        
        HashSet<User> bottoms = new HashSet<>();
        bottoms.addAll(module.getStudentList());
        
        for(GradeItem gi: module.getGradeItemList()){
            ArrayList<Double> marks  = new ArrayList<>(gi.getGradeEntries().size());
            for (GradeEntry ge : gi.getGradeEntries()) {
                marks.add(ge.getMarks());
            }
            Collections.sort(marks);
            double rank25 = 1.0*marks.size()/4;
            int rank25r = (int) Math.round(rank25);

            double twentyfifth;
            if(rank25-rank25r == 0){
                twentyfifth = marks.get(rank25r);
            } else {
                twentyfifth = marks.get(rank25r) + (rank25-rank25r)*(marks.get(rank25r+1) - marks.get(rank25r));
            }
            
            for (GradeEntry ge : gi.getGradeEntries()) {
                if(ge.getMarks() > twentyfifth){
                    bottoms.remove(ge.getStudent());
                }
            }
        }
        
        return Response.status(Response.Status.OK).entity(new GetUserRsp(new ArrayList<>(bottoms))).build();
    }
}
