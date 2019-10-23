/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.ConsultationTimeslot;
import entities.Coursepack;
import entities.Module;
import entities.Question;
import entities.Schedule;
import entities.Survey;
import entities.Tutorial;
import entities.User;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.AccessRightEnum;
import util.GenderEnum;
import util.QuestionTypeEnum;

/**
 *
 * @author Asus
 */
@Singleton
@LocalBean
@Startup
public class DataInitSessionBean {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;

    @PostConstruct
    public void init(){
        Query q = em.createQuery("SELECT u FROM User u");
        if(!q.getResultList().isEmpty()){
            System.out.println("Data already initialized!");
            return;
        }
        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEmail("admin@gmail.com");
        admin.setPassword("password");
        admin.setGender(GenderEnum.Male);
        admin.setAccessRight(AccessRightEnum.Admin);
        admin.setUsername("admin");
        em.persist(admin);
        em.flush();

        User student = new User();
        student.setFirstName("John");
        student.setLastName("Tan");
        student.setEmail("student@gmail.com");
        student.setPassword("password");
        student.setGender(GenderEnum.Male);
        student.setAccessRight(AccessRightEnum.Student);
        student.setUsername("student");
        em.persist(student);
        em.flush();

        User teacher = new User();
        teacher.setFirstName("Alice");
        teacher.setLastName("Tan");
        teacher.setEmail("teacher@gmail.com");
        teacher.setPassword("password");
        teacher.setGender(GenderEnum.Female);
        teacher.setAccessRight(AccessRightEnum.Teacher);
        teacher.setUsername("teacher");
        em.persist(teacher);
        em.flush();

        User extStu = new User();
        extStu.setFirstName("Jane");
        extStu.setLastName("Doe");
        extStu.setEmail("public@gmail.com");
        extStu.setPassword("password");
        extStu.setGender(GenderEnum.Female);
        extStu.setAccessRight(AccessRightEnum.Public);
        extStu.setUsername("public");
        em.persist(extStu);
        em.flush();
        
        User student2 = new User();
        student2.setFirstName("Mark");
        student2.setLastName("Hamilton");
        student2.setEmail("student2@gmail.com");
        student2.setPassword("password");
        student2.setGender(GenderEnum.Male);
        student2.setAccessRight(AccessRightEnum.Student);
        student2.setUsername("student2");
        em.persist(student2);
        em.flush();

        Module m1 = new Module();
        m1.setCode("CS1010");
        m1.setDescription("CS1010 Mod Desc");
        m1.setExamTime(new Timestamp(2019-1900, 11, 29, 9, 0, 0, 0));
        m1.setHasExam(true);
        m1.setCreditUnit(4);
        m1.setExamVenue("MPSH 1");
        m1.setMaxEnrollment(30);
        m1.setYearOffered("2019/2020");
        m1.setTitle("Programming Methodology");
        m1.setSemesterOffered(1);
        m1.setLectureDetails("Monday 12:00 - 14:00");
        m1.setAssignedTeacher(teacher);
        m1.setFaculty("School of Computing");
        m1.setDepartment("Department of Computer Science");
        teacher.getTeacherModuleList().add(m1);
        em.persist(m1);
        em.flush();

        Module m2 = new Module();
        m2.setCode("CS2040");
        m2.setDescription("CS2040 Mod Desc");
        m2.setExamTime(new Timestamp(2019-1900, 11, 30, 9, 0, 0, 0));
        m2.setHasExam(true);
        m2.setCreditUnit(4);
        m2.setExamVenue("MPSH 1");
        m2.setMaxEnrollment(30);
        m2.setYearOffered("2019/2020");
        m2.setTitle("Programming Methodology 2");
        m2.setSemesterOffered(1);
        m2.setLectureDetails("Tuesday 12:00 - 14:00");
        m2.setAssignedTeacher(teacher);
        m2.setFaculty("School of Computing");
        m2.setDepartment("Department of Computer Science");
        teacher.getTeacherModuleList().add(m2);
        em.persist(m2);
        em.flush();

        Module m3 = new Module();
        m3.setCode("IS4103");
        m3.setDescription("IS4103 Mod Desc");
        m3.setExamTime(new Timestamp(2020-1900, 4, 29, 13, 0, 0, 0));
        m3.setHasExam(true);
        m3.setCreditUnit(8);
        m3.setExamVenue("MPSH 1");
        m3.setMaxEnrollment(30);
        m3.setYearOffered("2019/2020");
        m3.setTitle("Capstone");
        m3.setSemesterOffered(1);
        m3.setLectureDetails("Wednesday 12:00 - 14:00");
        m3.setAssignedTeacher(teacher);
        m3.setFaculty("School of Computing");
        m3.setDepartment("Department of Information Systems and Analytics");
        teacher.getTeacherModuleList().add(m3);
        em.persist(m3);
        em.flush();

        Module m4 = new Module();
        m4.setCode("IS4103");
        m4.setDescription("IS4103 Mod Desc");
        m4.setExamTime(new Timestamp(2020-1900, 12, 1, 9, 0, 0, 0));
        m4.setHasExam(true);
        m4.setCreditUnit(8);
        m4.setExamVenue("MPSH 1");
        m4.setMaxEnrollment(30);
        m4.setYearOffered("2020/2021");
        m4.setTitle("Capstone");
        m4.setSemesterOffered(1);
        m4.setLectureDetails("Wednesday 12:00 - 14:00");
        m4.setAssignedTeacher(teacher);
        m4.setFaculty("School of Computing");
        m4.setDepartment("Department of Information Systems and Analytics");
        teacher.getTeacherModuleList().add(m4);
        em.persist(m4);
        em.flush();
        
        student2.getStudentModuleList().add(m1);
        m1.getStudentList().add(student2);

        Tutorial t1 = new Tutorial();
        t1.setModule(m1);
        t1.setMaxEnrollment(10);
        t1.setVenue("SR1");
        t1.setTiming("Thursday 12:00 - 13:00");
        m1.getTutorials().add(t1);
        em.persist(t1);
        em.flush();

        Tutorial t2 = new Tutorial();
        t2.setModule(m1);
        t2.setMaxEnrollment(10);
        t2.setVenue("SR1");
        t2.setTiming("Thursday 13:00 - 14:00");
        m1.getTutorials().add(t2);
        em.persist(t2);
        em.flush();

        Tutorial t3 = new Tutorial();
        t3.setModule(m1);
        t3.setMaxEnrollment(10);
        t3.setVenue("SR1");
        t3.setTiming("Thursday 14:00 - 15:00");
        m1.getTutorials().add(t3);
        em.persist(t3);
        em.flush();

        Tutorial t4 = new Tutorial();
        t4.setModule(m2);
        t4.setMaxEnrollment(10);
        t4.setVenue("Tutorial Room 4");
        t4.setTiming("Friday 09:00 - 10:00");
        m2.getTutorials().add(t4);
        em.persist(t4);
        em.flush();

        Tutorial t5 = new Tutorial();
        t5.setModule(m2);
        t5.setMaxEnrollment(10);
        t5.setVenue("Tutorial Room 4");
        t5.setTiming("Friday 13:00 - 14:00");
        m2.getTutorials().add(t5);
        em.persist(t5);
        em.flush();

        Tutorial t6 = new Tutorial();
        t6.setModule(m2);
        t6.setMaxEnrollment(10);
        t6.setVenue("Tutorial Room 4");
        t6.setTiming("Friday 10:00 - 11:00");
        m2.getTutorials().add(t6);
        em.persist(t6);
        em.flush();

        Tutorial t7 = new Tutorial();
        t7.setModule(m3);
        t7.setMaxEnrollment(10);
        t7.setVenue("Tutorial Room 2");
        t7.setTiming("Friday 10:00 - 11:00");
        m3.getTutorials().add(t7);
        em.persist(t7);
        em.flush();

        Tutorial t8 = new Tutorial();
        t8.setModule(m3);
        t8.setMaxEnrollment(10);
        t8.setVenue("Tutorial Room 2");
        t8.setTiming("Friday 11:00 - 12:00");
        m3.getTutorials().add(t8);
        em.persist(t8);
        em.flush();

        Tutorial t9 = new Tutorial();
        t9.setModule(m3);
        t9.setMaxEnrollment(10);
        t9.setVenue("Tutorial Room 2");
        t9.setTiming("Thursday 11:00 - 12:00");
        m3.getTutorials().add(t9);
        em.persist(t9);
        em.flush();

        student2.getTutorials().add(t1);
        t1.getStudentList().add(student2);
        
        Schedule schedule = new Schedule();
        schedule.setSemester(1);
        schedule.setYear("2019/2020");
        schedule.setModuleRound1StartDate(new Date(2019-1900, 8, 20));
        schedule.setModuleRound1EndDate(new Date(2019-1900, 8, 25));
        schedule.setModuleRound2StartDate(new Date(2019-1900, 8, 29));
        schedule.setModuleRound2EndDate(new Date(2019-1900, 9, 5));
        schedule.setModuleRound3StartDate(new Date(2019-1900, 9, 20));
        schedule.setModuleRound3EndDate(new Date(2019-1900, 12, 5));
        schedule.setTutorialRound1StartDate(new Date(2019-1900, 9, 1));
        schedule.setTutorialRound1EndDate(new Date(2019-1900, 9, 8));
        schedule.setTutorialRound2StartDate(new Date(2019-1900, 9, 20));
        schedule.setTutorialRound2EndDate(new Date(2019-1900, 12, 1));
        em.persist(schedule);
        em.flush();
        
        ConsultationTimeslot c1 = new ConsultationTimeslot();
        c1.setBooker(student2);
        c1.setEndTs(LocalTime.parse("11:30:00"));
        c1.setModule(m4);
        c1.setStartD(LocalDate.parse("2019-10-28"));
        c1.setStartTs(LocalTime.parse("10:30:00"));
        em.persist(c1);
        em.flush();
        
        ConsultationTimeslot c2 = new ConsultationTimeslot();
        c1.setEndTs(LocalTime.parse("11:30:00"));
        c1.setModule(m4);
        c1.setStartD(LocalDate.parse("2019-09-29"));
        c1.setStartTs(LocalTime.parse("10:30:00"));
        em.persist(c2);
        em.flush();
        m1.getConsultationList().add(c1);

        em.flush();
        
        Coursepack cp1 = new Coursepack();
        cp1.setAssignedTeacher(teacher);
        cp1.setCategory("Computer Science");
        cp1.setCode("CS1000");
        cp1.setDescription("Learn C Programming and unlock doors to careers in computer engineering");
        cp1.setPrice(500.00);
        cp1.setRating(5.00);
        cp1.setPublished(false);
        cp1.setTeacherBackground("Graduated with Master degree in Computer Science from NUS");
        cp1.setTitle("C Programming");
        teacher.getTeacherCoursepackList().add(cp1);
        em.persist(cp1);
        em.flush();
        
        Coursepack cp2 = new Coursepack();
        cp2.setAssignedTeacher(teacher);
        cp2.setCategory("Business Management");
        cp2.setCode("BM1000");
        cp2.setDescription("Master the essentials of managing a successful business");
        cp2.setPrice(300.00);
        cp2.setRating(4.00);
        cp2.setPublished(false);
        cp2.setTeacherBackground("With more than 20 years of teaching experiences");
        cp2.setTitle("Operations Management");
        teacher.getTeacherCoursepackList().add(cp2);
        em.persist(cp2);
        em.flush();
        
        Coursepack cp3 = new Coursepack();
        cp3.setAssignedTeacher(teacher);
        cp3.setCategory("Engineering");
        cp3.setCode("EG1000");
        cp3.setDescription("This course will introduce you to the Design Thinking process and illustrate best practices for each step along the way.");
        cp3.setPrice(200.00);
        cp3.setRating(5.00);
        cp3.setPublished(false);
        cp3.setTeacherBackground("Microsoft developer");
        cp3.setTitle("Introduction to Design Thinking");
        teacher.getTeacherCoursepackList().add(cp3);
        em.persist(cp3);
        em.flush();
        
        createSurvey(m1);
        createSurvey(m2);
        createSurvey(m3);
        createSurvey(m4);
    }
    
    public void createSurvey(Module module){
        Survey survey = new Survey();
        survey.setTitle("End-of-Semester Evaluation");
        survey.setDescription("Welcome to the feedback exercise. <br />Please take time to review your learning experience in the past semester and be honest in your replies. <br /><br />Please click on <b>'Next'</b> to proceed with the exercise.");
        survey.setModule(module);
        survey.setOpeningDate(new Date());
        survey.setClosingDate(new Date(2019-1900, 12, 12));
        survey.setQuestionList(new ArrayList<>());
        em.persist(survey);
        em.flush();
        
        Question sq1 = new Question();
        sq1.setHtml("<h5>Quantitative on Content</h5>");
        sq1.setType(QuestionTypeEnum.html);
        em.persist(sq1);
        em.flush();
        survey.getQuestionList().add(sq1);
        
        List<String> choices = new ArrayList<>();
        choices.add("Strongly Disagree");        
        choices.add("Disagree");
        choices.add("Neutral");
        choices.add("Agree");
        choices.add("Strongly Agree");
        
        Question sq2 = new Question();
        sq2.setType(QuestionTypeEnum.radiogroup);
        sq2.setNumber(1);
        sq2.setTitle("The content taught is easy to understand");
        sq2.setIsRequired(true);
        sq2.setChoices(choices);
        em.persist(sq2);
        em.flush();
        survey.getQuestionList().add(sq2);
        
        Question sq3 = new Question();
        sq3.setType(QuestionTypeEnum.radiogroup);
        sq3.setNumber(2);
        sq3.setTitle("The content covered are clearly structured and well-organised");
        sq3.setIsRequired(true);
        sq3.setChoices(choices);
        em.persist(sq3);
        em.flush();
        survey.getQuestionList().add(sq3);
        
        Question sq4 = new Question();
        sq4.setType(QuestionTypeEnum.radiogroup);
        sq4.setNumber(3);
        sq4.setTitle("The content covered are applicable in my field of study");
        sq4.setIsRequired(true);
        sq4.setChoices(choices);
        em.persist(sq4);
        em.flush();
        survey.getQuestionList().add(sq4);
        
        Question sq5 = new Question();
        sq5.setType(QuestionTypeEnum.radiogroup);
        sq5.setNumber(4);
        sq5.setTitle("The content covered are useful");
        sq5.setIsRequired(true);
        sq5.setChoices(choices);
        em.persist(sq5);
        em.flush();
        survey.getQuestionList().add(sq5);
        
        Question sq6 = new Question();
        sq6.setType(QuestionTypeEnum.radiogroup);
        sq6.setNumber(5);
        sq6.setTitle("Grade likely to get for this module");
        sq6.setIsRequired(true);
        sq6.setChoices(choices);
        em.persist(sq6);
        em.flush();
        survey.getQuestionList().add(sq6);
        
        Question sq7 = new Question();
        sq7.setType(QuestionTypeEnum.text);
        sq7.setNumber(6);
        sq7.setTitle("Other comments");
        sq7.setIsRequired(false);
        em.persist(sq7);
        em.flush();
        survey.getQuestionList().add(sq7);
        
        Question sq8 = new Question();
        sq8.setType(QuestionTypeEnum.radiogroup);
        sq8.setNumber(7);
        sq8.setTitle("The teacher has enhanced my thinking ability.");
        sq8.setIsRequired(true);
        sq8.setChoices(choices);
        em.persist(sq8);
        em.flush();
        survey.getQuestionList().add(sq8);
        
        Question sq9 = new Question();
        sq9.setType(QuestionTypeEnum.radiogroup);
        sq9.setNumber(8);
        sq9.setTitle("The teacher provides timely and useful feedback.");
        sq9.setIsRequired(true);
        sq9.setChoices(choices);
        em.persist(sq9);
        em.flush();
        survey.getQuestionList().add(sq9);
        
        Question sq10 = new Question();
        sq10.setType(QuestionTypeEnum.radiogroup);
        sq10.setNumber(9);
        sq10.setTitle("The teacher is approachable for consultation.");
        sq10.setIsRequired(true);
        sq10.setChoices(choices);
        em.persist(sq10);
        em.flush();
        survey.getQuestionList().add(sq10);
        
        Question sq11 = new Question();
        sq11.setType(QuestionTypeEnum.radiogroup);
        sq11.setNumber(10);
        sq11.setTitle("The teacher has increased my interest in the subject.");
        sq11.setIsRequired(true);
        sq11.setChoices(choices);
        em.persist(sq11);
        em.flush();
        survey.getQuestionList().add(sq11);
        
        Question sq12 = new Question();
        sq12.setType(QuestionTypeEnum.radiogroup);
        sq12.setNumber(11);
        sq12.setTitle("The teacher has enhanced my ability to learn independently.");
        sq12.setIsRequired(true);
        sq12.setChoices(choices);
        em.persist(sq12);
        em.flush();
        survey.getQuestionList().add(sq12);
        
        Question sq13 = new Question();
        sq13.setType(QuestionTypeEnum.radiogroup);
        sq13.setNumber(12);
        sq13.setTitle("The teacher encourages me to apply concepts learnt.");
        sq13.setIsRequired(true);
        sq13.setChoices(choices);
        em.persist(sq13);
        em.flush();
        survey.getQuestionList().add(sq13);
        
        Question sq14 = new Question();
        sq14.setType(QuestionTypeEnum.radiogroup);
        sq14.setNumber(13);
        sq14.setTitle("Overall the teacher is effective.");
        sq14.setIsRequired(true);
        sq14.setChoices(choices);
        em.persist(sq14);
        em.flush();
        survey.getQuestionList().add(sq14);
        
        Question sq15 = new Question();
        sq15.setType(QuestionTypeEnum.text);
        sq15.setNumber(14);
        sq15.setTitle("What are the teacher's strengths?");
        sq15.setIsRequired(true);
        em.persist(sq15);
        em.flush();
        survey.getQuestionList().add(sq15);
        
        Question sq16 = new Question();
        sq16.setType(QuestionTypeEnum.text);
        sq16.setNumber(15);
        sq16.setTitle("What are the teacher's strengths?");
        sq16.setIsRequired(true);
        em.persist(sq16);
        em.flush();
        survey.getQuestionList().add(sq16);
        
        Question sq17 = new Question();
        sq17.setType(QuestionTypeEnum.text);
        sq17.setNumber(15);
        sq17.setTitle("What improvements would you suggest to the teacher?");
        sq17.setIsRequired(true);
        em.persist(sq17);
        em.flush();
        survey.getQuestionList().add(sq17);
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
