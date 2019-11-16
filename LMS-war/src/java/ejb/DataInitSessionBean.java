/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Annoucement;
import entities.Attendance;
import entities.Category;
import entities.Certification;
import entities.ConsultationTimeslot;
import entities.Coursepack;
import entities.ForumPost;
import entities.ForumTopic;
import entities.GradeEntry;
import entities.GradeItem;
import entities.Module;
import entities.Outlines;
import entities.Question;
import entities.QuestionAttempt;
import entities.Quiz;
import entities.QuizAttempt;
import entities.Schedule;
import entities.Survey;
import entities.SurveyAttempt;
import entities.Tutorial;
import entities.User;
import entities.Venue;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.AccessRightEnum;
import util.GenderEnum;
import util.QuestionOrderEnum;
import util.QuestionTypeEnum;
import util.QuizTypeEnum;

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
    public void init() {
        Query q = em.createQuery("SELECT u FROM User u");
        if (!q.getResultList().isEmpty()) {
            System.out.println("Data already initialized!");
            return;
        }

        User admin = new User();
        admin.setFirstName("Admin");
        admin.setLastName("Admin");
        admin.setEmail("ad1234567min@gmail.com");
        admin.setPassword("password!234%");
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

        User student2 = new User();
        student2.setFirstName("Mark");
        student2.setLastName("Hamilton");
        student2.setEmail("student2@gmail.com");
        student2.setPassword("password");
        student2.setGender(GenderEnum.Male);
        student2.setAccessRight(AccessRightEnum.Student);
        student2.setUsername("student2");
        em.persist(student2);

        User student3 = new User();
        student3.setFirstName("Mary");
        student3.setLastName("Lee");
        student3.setEmail("student3@gmail.com");
        student3.setPassword("password");
        student3.setGender(GenderEnum.Female);
        student3.setAccessRight(AccessRightEnum.Student);
        student3.setUsername("student3");
        em.persist(student3);
        em.flush();

        User student4 = new User();
        student4.setFirstName("Bob");
        student4.setLastName("Lim");
        student4.setEmail("student4@gmail.com");
        student4.setPassword("password");
        student4.setGender(GenderEnum.Male);
        student4.setAccessRight(AccessRightEnum.Student);
        student4.setUsername("student4");
        em.persist(student4);
        em.flush();

        User student5 = new User();
        student5.setFirstName("Mark");
        student5.setLastName("Lee");
        student5.setEmail("student5@gmail.com");
        student5.setPassword("password");
        student5.setGender(GenderEnum.Female);
        student5.setAccessRight(AccessRightEnum.Student);
        student5.setUsername("student5");
        em.persist(student5);
        em.flush();

        User student6 = new User();
        student6.setFirstName("Marcus");
        student6.setLastName("Chua");
        student6.setEmail("student6@gmail.com");
        student6.setPassword("password");
        student6.setGender(GenderEnum.Male);
        student6.setAccessRight(AccessRightEnum.Student);
        student6.setUsername("student6");
        em.persist(student6);
        em.flush();

        User student7 = new User();
        student7.setFirstName("Josephine");
        student7.setLastName("Tan");
        student7.setEmail("student7@gmail.com");
        student7.setPassword("password");
        student7.setGender(GenderEnum.Female);
        student7.setAccessRight(AccessRightEnum.Student);
        student7.setUsername("student7");
        em.persist(student7);
        em.flush();

        User student8 = new User();
        student8.setFirstName("Da");
        student8.setLastName("Wei");
        student8.setEmail("student8@gmail.com");
        student8.setPassword("password");
        student8.setGender(GenderEnum.Male);
        student8.setAccessRight(AccessRightEnum.Student);
        student8.setUsername("student8");
        em.persist(student8);
        em.flush();

        User student9 = new User();
        student9.setFirstName("Rose");
        student9.setLastName("May");
        student9.setEmail("student9@gmail.com");
        student9.setPassword("password");
        student9.setGender(GenderEnum.Female);
        student9.setAccessRight(AccessRightEnum.Student);
        student9.setUsername("student9");
        em.persist(student9);
        em.flush();

        User student10 = new User();
        student10.setFirstName("Jane");
        student10.setLastName("Loaer");
        student10.setEmail("student10@gmail.com");
        student10.setPassword("password");
        student10.setGender(GenderEnum.Female);
        student10.setAccessRight(AccessRightEnum.Student);
        student10.setUsername("student10");
        em.persist(student10);
        em.flush();

        User student11 = new User();
        student11.setFirstName("Kate");
        student11.setLastName("Low");
        student11.setEmail("student11@gmail.com");
        student11.setPassword("password");
        student11.setGender(GenderEnum.Female);
        student11.setAccessRight(AccessRightEnum.Student);
        student11.setUsername("student11");
        em.persist(student11);
        em.flush();

        List<User> studentList = new ArrayList<>();
        studentList.add(student);
        studentList.add(student2);
        studentList.add(student3);
        studentList.add(student4);
        studentList.add(student5);
        studentList.add(student6);
        studentList.add(student7);
        studentList.add(student8);
        studentList.add(student9);
        studentList.add(student10);
        studentList.add(student11);

        List<User> studentList2 = new ArrayList<>();
        studentList2.add(student11);

        User teacher = new User();
        teacher.setFirstName("Alice");
        teacher.setLastName("Tan");
        teacher.setEmail("tea1234567cher@gmail.com");
        teacher.setPassword("password!234%");
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

        User extStu2 = new User();
        extStu2.setFirstName("May");
        extStu2.setLastName("Doe");
        extStu2.setEmail("public2@gmail.com");
        extStu2.setPassword("password");
        extStu2.setGender(GenderEnum.Female);
        extStu2.setAccessRight(AccessRightEnum.Public);
        extStu2.setUsername("public");
        em.persist(extStu2);
        em.flush();

        User extStu3 = new User();
        extStu3.setFirstName("April");
        extStu3.setLastName("Doe");
        extStu3.setEmail("public3@gmail.com");
        extStu3.setPassword("password");
        extStu3.setGender(GenderEnum.Female);
        extStu3.setAccessRight(AccessRightEnum.Public);
        extStu3.setUsername("public");
        em.persist(extStu3);
        em.flush();

        Venue v1 = new Venue();
        v1.setName("MPSH2");
        em.persist(v1);
        em.flush();

        Venue v2 = new Venue();
        v2.setName("COM1-12");
        em.persist(v2);
        em.flush();

        Venue v3 = new Venue();
        v3.setName("AS7-24");
        em.persist(v3);
        em.flush();

        Venue v4 = new Venue();
        v4.setName("COM1-21");
        em.persist(v4);
        em.flush();

        Module m1 = new Module();
        m1.setCode("CS1010");
        m1.setDescription("CS1010 Mod Desc");
        m1.setExamTime(new Timestamp(2019 - 1900, 11, 29, 9, 0, 0, 0));
        m1.setHasExam(true);
        m1.setCreditUnit(4);
        m1.setExamVenue(v1);
        m1.setMaxEnrollment(30);
        m1.setYearOffered("2019/2020");
        m1.setTitle("Programming Methodology");
        m1.setSemesterOffered(1);
        m1.setLectureDetails("Monday 12:00 - 14:00");
        m1.setAssignedTeacher(teacher);
        m1.setStudentList(studentList);
        m1.setFaculty("School of Computing");
        m1.setDepartment("Department of Computer Science");
        teacher.getTeacherModuleList().add(m1);
        student.getStudentModuleList().add(m1);
        //student2.getStudentModuleList().add(m1);
        student3.getStudentModuleList().add(m1);
        student4.getStudentModuleList().add(m1);
        student5.getStudentModuleList().add(m1);
        student6.getStudentModuleList().add(m1);
        student7.getStudentModuleList().add(m1);
        student8.getStudentModuleList().add(m1);
        student9.getStudentModuleList().add(m1);
        student10.getStudentModuleList().add(m1);
        student11.getStudentModuleList().add(m1);
        em.persist(m1);
        em.flush();
        createQuiz(m1);
        createGradeItem(m1);

        Module m2 = new Module();
        m2.setCode("CS2040");
        m2.setDescription("CS2040 Mod Desc");
        m2.setExamTime(new Timestamp(2019 - 1900, 11, 30, 9, 0, 0, 0));
        m2.setHasExam(true);
        m2.setCreditUnit(4);
        m2.setExamVenue(v1);
        m2.setMaxEnrollment(30);
        m2.setYearOffered("2019/2020");
        m2.setTitle("Programming Methodology 2");
        m2.setSemesterOffered(1);
        m2.setLectureDetails("Tuesday 12:00 - 14:00");
        m2.setAssignedTeacher(teacher);
        m2.setFaculty("School of Computing");
        m2.setDepartment("Department of Computer Science");
        teacher.getTeacherModuleList().add(m2);
        m2.setStudentList(studentList2);
        student11.getStudentModuleList().add(m2);
        em.persist(m2);
        em.flush();
        createQuiz(m2);
        createGradeItem(m2);

        Module m3 = new Module();
        m3.setCode("IS4103");
        m3.setDescription("IS4103 Mod Desc");
        m3.setExamTime(new Timestamp(2020 - 1900, 4, 29, 13, 0, 0, 0));
        m3.setHasExam(true);
        m3.setCreditUnit(8);
        m3.setExamVenue(v2);
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
        createQuiz(m3);
        createGradeItem(m3);

        Module m4 = new Module();
        m4.setCode("IS4103");
        m4.setDescription("IS4103 Mod Desc");
        m4.setExamTime(new Timestamp(2020 - 1900, 12, 1, 9, 0, 0, 0));
        m4.setHasExam(true);
        m4.setCreditUnit(8);
        m4.setExamVenue(v2);
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

        List<Module> moduleList = new ArrayList<>();
        moduleList.add(m1);
        moduleList.add(m2);
        em.flush();

        List<Module> moduleList2 = new ArrayList<>();
        moduleList2.add(m3);
        moduleList2.add(m4);
        em.flush();

        student2.getStudentModuleList().add(m1);
        //m1.getStudentList().add(student2);

        Tutorial t1 = new Tutorial();
        t1.setModule(m1);
        t1.setMaxEnrollment(10);
        t1.setVenue(v3);
        t1.setTiming("Thursday 12:00 - 13:00");
        t1.setModule(m1);
        t1.setStudentList(studentList);
        //t1.getStudentList().add(student);
        //t1.getStudentList().add(student2);
        //t1.getStudentList().add(student3);
        student.getTutorials().add(t1);
        student2.getTutorials().add(t1);
        student3.getTutorials().add(t1);
        student4.getTutorials().add(t1);
        student5.getTutorials().add(t1);
        student6.getTutorials().add(t1);
        student7.getTutorials().add(t1);
        student8.getTutorials().add(t1);
        student9.getTutorials().add(t1);
        student10.getTutorials().add(t1);
        m1.getTutorials().add(t1);
        em.persist(t1);
        em.flush();

        Tutorial t2 = new Tutorial();
        t2.setModule(m1);
        t2.setMaxEnrollment(10);
        t2.setVenue(v3);
        t2.setTiming("Thursday 13:00 - 14:00");
        m1.getTutorials().add(t2);
        t2.setStudentList(studentList2);
        student11.getTutorials().add(t2);
        em.persist(t2);
        em.flush();

        Tutorial t3 = new Tutorial();
        t3.setModule(m1);
        t3.setMaxEnrollment(10);
        t3.setVenue(v3);
        t3.setTiming("Thursday 14:00 - 15:00");
        m1.getTutorials().add(t3);
        em.persist(t3);
        em.flush();

        Tutorial t4 = new Tutorial();
        t4.setModule(m2);
        t4.setMaxEnrollment(10);
        t4.setVenue(v3);
        t4.setTiming("Friday 09:00 - 10:00");
        m2.getTutorials().add(t4);
        em.persist(t4);
        em.flush();

        Tutorial t5 = new Tutorial();
        t5.setModule(m2);
        t5.setMaxEnrollment(10);
        t5.setVenue(v3);
        t5.setTiming("Friday 13:00 - 14:00");
        m2.getTutorials().add(t5);
        em.persist(t5);
        em.flush();

        Tutorial t6 = new Tutorial();
        t6.setModule(m2);
        t6.setMaxEnrollment(10);
        t6.setVenue(v4);
        t6.setTiming("Friday 10:00 - 11:00");
        m2.getTutorials().add(t6);
        em.persist(t6);
        em.flush();

        Tutorial t7 = new Tutorial();
        t7.setModule(m3);
        t7.setMaxEnrollment(10);
        t7.setVenue(v4);
        t7.setTiming("Friday 10:00 - 11:00");
        m3.getTutorials().add(t7);
        em.persist(t7);
        em.flush();

        Tutorial t8 = new Tutorial();
        t8.setModule(m3);
        t8.setMaxEnrollment(10);
        t8.setVenue(v4);
        t8.setTiming("Friday 11:00 - 12:00");
        m3.getTutorials().add(t8);
        em.persist(t8);
        em.flush();

        Tutorial t9 = new Tutorial();
        t9.setModule(m3);
        t9.setMaxEnrollment(10);
        t9.setVenue(v4);
        t9.setTiming("Thursday 11:00 - 12:00");
        m3.getTutorials().add(t9);
        em.persist(t9);
        em.flush();

        List<Tutorial> tutorialList = new ArrayList<>();
        tutorialList.add(t1);
        tutorialList.add(t2);
        tutorialList.add(t3);
        tutorialList.add(t4);
        tutorialList.add(t5);
        em.flush();

        List<Tutorial> tutorialList2 = new ArrayList<>();
        tutorialList2.add(t6);
        tutorialList2.add(t7);
        tutorialList2.add(t8);
        tutorialList2.add(t9);
        em.flush();

        //student2.getTutorials().add(t1);
        //t1.getStudentList().add(student2);
        Schedule schedule = new Schedule();
        schedule.setSemester(1);
        schedule.setYear("2019/2020");
        schedule.setModuleRound1StartDate(new Date(2019 - 1900, 8, 20));
        schedule.setModuleRound1EndDate(new Date(2019 - 1900, 8, 25));
        schedule.setModuleRound2StartDate(new Date(2019 - 1900, 8, 29));
        schedule.setModuleRound2EndDate(new Date(2019 - 1900, 9, 5));
        schedule.setModuleRound3StartDate(new Date(2019 - 1900, 9, 20));
        schedule.setModuleRound3EndDate(new Date(2019 - 1900, 12, 5));
        schedule.setTutorialRound1StartDate(new Date(2019 - 1900, 9, 1));
        schedule.setTutorialRound1EndDate(new Date(2019 - 1900, 9, 8));
        schedule.setTutorialRound2StartDate(new Date(2019 - 1900, 9, 20));
        schedule.setTutorialRound2EndDate(new Date(2019 - 1900, 12, 1));
        em.persist(schedule);
        em.flush();

        ConsultationTimeslot c1 = new ConsultationTimeslot();
        c1.setBooker(student2);
        c1.setEndTs(LocalTime.parse("11:00:00"));
        c1.setModule(m4);
        c1.setStartD(LocalDate.parse("2019-10-28"));
        c1.setStartTs(LocalTime.parse("10:00:00"));
        em.persist(c1);
        em.flush();
        m4.getConsultationList().add(c1);

        ConsultationTimeslot c2 = new ConsultationTimeslot();
        c2.setEndTs(LocalTime.parse("13:00:00"));
        c2.setModule(m4);
        c2.setStartD(LocalDate.parse("2019-10-28"));
        c2.setStartTs(LocalTime.parse("12:00:00"));
        em.persist(c2);
        em.flush();
        m4.getConsultationList().add(c2);

        m1.getConsultationList().add(c1);
        em.flush();

        List<User> testMissingStudents = new ArrayList<>();
        testMissingStudents.add(student);
        testMissingStudents.add(student2);
        testMissingStudents.add(student3);
        testMissingStudents.add(student4);
        testMissingStudents.add(student5);

        Attendance a1 = new Attendance();
        a1.setDuration(5);
        a1.setEndTs(new Timestamp(2019 - 1900, 9, 20, 13, 11, 0, 0));
        a1.setModule(m1);
        a1.setSemester(1);
        a1.setStartTs(new Timestamp(2019 - 1900, 9, 20, 13, 10, 0, 0));
        a1.setTotal(11);
        //a1.setTutorial(t1);
        //t1.getAttendanceList().add(a1);
        a1.setAttendees(studentList);
        //a1.getAttendees().add(student);
        a1.setAttendedNumber(11);
        student.getAttendanceList().add(a1);
        student2.getAttendanceList().add(a1);
        student3.getAttendanceList().add(a1);
        student4.getAttendanceList().add(a1);
        student5.getAttendanceList().add(a1);
        student6.getAttendanceList().add(a1);
        student7.getAttendanceList().add(a1);
        student8.getAttendanceList().add(a1);
        student9.getAttendanceList().add(a1);
        student10.getAttendanceList().add(a1);
        student11.getAttendanceList().add(a1);
        m1.getAttandanceList().add(a1);
        em.persist(a1);
        em.flush();

        Attendance a2 = new Attendance();
        a2.setDuration(5);
        a2.setEndTs(new Timestamp(2019 - 1900, 9, 27, 13, 11, 0, 0));
        //a1.setModule(m1);
        //m1.getAttandanceList().add(a1);
        a2.setSemester(1);
        a2.setStartTs(new Timestamp(2019 - 1900, 9, 27, 13, 10, 0, 0));
        a2.setTotal(11);
        a2.setTutorial(t1);
        a2.setAttendees(studentList);
        //a1.getAttendees().add(student);
        a2.setAttendedNumber(11);
        student.getAttendanceList().add(a2);
        student2.getAttendanceList().add(a2);
        student3.getAttendanceList().add(a2);
        student4.getAttendanceList().add(a2);
        student5.getAttendanceList().add(a2);
        student6.getAttendanceList().add(a2);
        student7.getAttendanceList().add(a2);
        student8.getAttendanceList().add(a2);
        student9.getAttendanceList().add(a2);
        student10.getAttendanceList().add(a2);
        student11.getAttendanceList().add(a2);
        t1.getAttendanceList().add(a2);
        em.persist(a2);
        em.flush();

        Attendance a3 = new Attendance();
        a3.setDuration(5);
        a3.setEndTs(new Timestamp(2019 - 1900, 10, 10, 13, 11, 0, 0));
        a3.setSemester(1);
        a3.setStartTs(new Timestamp(2019 - 1900, 10, 10, 13, 10, 0, 0));
        a3.setTotal(11);
        a3.setTutorial(t1);
        a3.setAttendees(testMissingStudents);
        a3.setAttendedNumber(5);
        student.getAttendanceList().add(a3);
        student2.getAttendanceList().add(a3);
        student3.getAttendanceList().add(a3);
        student4.getAttendanceList().add(a3);
        student5.getAttendanceList().add(a3);
        m1.getAttandanceList().add(a3);
        em.persist(a3);
        em.flush();

        Attendance a4 = new Attendance();
        a4.setDuration(5);
        a4.setEndTs(new Timestamp(2019 - 1900, 10, 17, 13, 11, 0, 0));
        a4.setSemester(1);
        a4.setStartTs(new Timestamp(2019 - 1900, 10, 17, 13, 10, 0, 0));
        a4.setTotal(11);
        a4.setTutorial(t1);
        a4.setAttendees(testMissingStudents);
        a4.setAttendedNumber(5);
        student.getAttendanceList().add(a4);
        student2.getAttendanceList().add(a4);
        student3.getAttendanceList().add(a4);
        student4.getAttendanceList().add(a4);
        student5.getAttendanceList().add(a4);
        t1.getAttendanceList().add(a4);
        em.persist(a4);
        em.flush();

        List<Attendance> attendanceList = new ArrayList<>();
        attendanceList.add(a1);
        attendanceList.add(a2);

        ConsultationTimeslot c3 = new ConsultationTimeslot();
        c3.setBooker(student);
        c3.setEndTs(LocalTime.parse("11:00:00"));
        c3.setModule(m4);
        c3.setStartD(LocalDate.parse("2019-10-29"));
        c3.setStartTs(LocalTime.parse("10:00:00"));
        em.persist(c3);
        em.flush();
        m4.getConsultationList().add(c3);

        Category cat1 = new Category();
        cat1.setName("Development");
        em.persist(cat1);
        em.flush();

        Category cat2 = new Category();
        cat2.setName("IT & Software");
        em.persist(cat2);
        em.flush();

        Category cat3 = new Category();
        cat3.setName("Business");
        em.persist(cat3);
        em.flush();

        Category cat4 = new Category();
        cat4.setName("Design");
        em.persist(cat4);
        em.flush();

        Category cat5 = new Category();
        cat5.setName("Marketing");
        em.persist(cat5);
        em.flush();

        Category cat6 = new Category();
        cat6.setName("Engineering");
        em.persist(cat6);
        em.flush();

        Category cat7 = new Category();
        cat7.setName("Math");
        em.persist(cat7);
        em.flush();

        Category cat8 = new Category();
        cat8.setName("Science");
        em.persist(cat8);
        em.flush();

        Category cat9 = new Category();
        cat9.setName("Social Science");
        em.persist(cat9);
        em.flush();

        Category cat10 = new Category();
        cat10.setName("Language");
        em.persist(cat10);
        em.flush();

        Coursepack cp1 = new Coursepack();
        cp1.setAssignedTeacher(teacher);
        cp1.setCategory(cat2);
        cat2.getCoursepackList().add(cp1);
        cp1.setCode("CS1000");
        cp1.setImageLocation("data:image/png;base64,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");
        cp1.setDescription("Learn C Programming and unlock doors to careers in computer engineering");
        cp1.setPrice(50.00);
        cp1.setRating(0.00);
        cp1.setPublished(false);
        cp1.setTeacherBackground("Graduated with Master degree in Computer Science from NUS");
        cp1.setTitle("Introduction to C Programming");
        teacher.getTeacherCoursepackList().add(cp1);
        em.persist(cp1);
        em.flush();
        createOutline(cp1);
        createQuiz(cp1);

        Coursepack cp2 = new Coursepack();
        cp2.setAssignedTeacher(teacher);
        cp2.setCategory(cat3);
        cat3.getCoursepackList().add(cp2);
        cp2.setCode("BM1000");
        cp2.setImageLocation("https://eduadvisor.my/wp-content/uploads/2015/05/Feature-Header-Business-01-2.png");
        cp2.setDescription("Master the essentials of managing a successful business");
        cp2.setPrice(80.00);
        cp2.setRating(0.00);
        cp2.setPublished(false);
        cp2.setTeacherBackground("With more than 20 years of teaching experiences");
        cp2.setTitle("Operations Management");
        teacher.getTeacherCoursepackList().add(cp2);
        em.persist(cp2);
        em.flush();
        createOutline(cp2);
        createQuiz(cp2);

        Coursepack cp3 = new Coursepack();
        cp3.setAssignedTeacher(teacher);
        cp3.setCategory(cat6);
        cat6.getCoursepackList().add(cp3);
        cp3.setCode("EG1000");
        cp3.setImageLocation("https://miro.medium.com/max/4186/1*4THiK_g7e8NrzfOks6fYsQ.png");
        cp3.setDescription("This course will introduce you to the Design Thinking process and illustrate best practices for each step along the way.");
        cp3.setPrice(50.00);
        cp3.setRating(0.00);
        cp3.setPublished(false);
        cp3.setTeacherBackground("Microsoft developer");
        cp3.setTitle("Introduction to Design Thinking");
        teacher.getTeacherCoursepackList().add(cp3);
        em.persist(cp3);
        em.flush();
        createOutline(cp3);
        createQuiz(cp3);

        Coursepack cp4 = new Coursepack();
        cp4.setAssignedTeacher(teacher);
        cp4.setCategory(cat1);
        cat1.getCoursepackList().add(cp4);
        cp4.setCode("DE1000");
        cp4.setImageLocation("https://cdn.uconnectlabs.com/wp-content/uploads/sites/5/2017/12/20170301155447.jpg");
        cp4.setDescription("This course will introduce you to learning web development - HTML, CSS, JS, Node and More!");
        cp4.setPrice(80.00);
        cp4.setRating(0.00);
        cp4.setPublished(false);
        cp4.setTeacherBackground("I am a developer with a passion in teaching!");
        cp4.setTitle("Introduction to Web Development");
        teacher.getTeacherCoursepackList().add(cp4);
        em.persist(cp4);
        em.flush();
        createOutline(cp4);
        createQuiz(cp4);

        Coursepack cp5 = new Coursepack();
        cp5.setAssignedTeacher(teacher);
        cp5.setCategory(cat1);
        cat1.getCoursepackList().add(cp5);
        cp5.setCode("DE1010");
        cp5.setImageLocation("https://brainstation-23.com/wp-content/uploads/2019/07/React-for-Web-Development.png");
        cp5.setDescription("This course will introduce you to learning about modern development stack, React!");
        cp5.setPrice(80.00);
        cp5.setRating(0.00);
        cp5.setPublished(true);
        cp5.setTeacherBackground("I am a developer with a passion in teaching!");
        cp5.setTitle("Introduction to Modern React");
        teacher.getTeacherCoursepackList().add(cp5);
        em.persist(cp5);
        em.flush();
        createOutline(cp5);
        createQuiz(cp5);

        Coursepack cp6 = new Coursepack();
        cp6.setAssignedTeacher(teacher);
        cp6.setCategory(cat1);
        cat1.getCoursepackList().add(cp6);
        cp6.setCode("DE1060");
        cp6.setImageLocation("https://miro.medium.com/max/2530/1*Nj-vU3k0XlhfAn2yqxq49g.jpeg");
        cp6.setDescription("This course will introduce you to learning about using Angular to develop web applications!");
        cp6.setPrice(80.00);
        cp6.setRating(0.00);
        cp6.setPublished(true);
        cp6.setTeacherBackground("I am a developer with a passion in teaching!");
        cp6.setTitle("Introduction to Angular 4");
        teacher.getTeacherCoursepackList().add(cp6);
        em.persist(cp6);
        em.flush();
        createOutline(cp6);
        createQuiz(cp6);

        Coursepack cp7 = new Coursepack();
        cp7.setAssignedTeacher(teacher);
        cp7.setCategory(cat1);
        cat1.getCoursepackList().add(cp7);
        cp7.setCode("DE2000");
        cp7.setImageLocation("https://icon-library.net/images/no-image-available-icon/no-image-available-icon-6.jpg");
        cp7.setDescription("This course will introduce you to learning about using HTML and CSS with Wordpress!");
        cp7.setPrice(60.00);
        cp7.setRating(0.00);
        cp7.setPublished(true);
        cp7.setTeacherBackground("I am a developer with a passion in teaching!");
        cp7.setTitle("Guide to HTML, CSS, Wordpress");
        teacher.getTeacherCoursepackList().add(cp7);
        em.persist(cp7);
        em.flush();
        createOutline(cp7);
        createQuiz(cp7);

        Coursepack cp8 = new Coursepack();
        cp8.setAssignedTeacher(teacher);
        cp8.setCategory(cat1);
        cat1.getCoursepackList().add(cp8);
        cp8.setCode("DE2010");
        cp8.setImageLocation("https://icon-library.net/images/no-image-available-icon/no-image-available-icon-6.jpg");
        cp8.setDescription("This course will introduce you to learning modern JavaScript");
        cp8.setPrice(50.00);
        cp8.setRating(0.00);
        cp8.setPublished(true);
        cp8.setTeacherBackground("I am a developer with a passion in teaching!");
        cp8.setTitle("Learn Modern JavaScript");
        teacher.getTeacherCoursepackList().add(cp8);
        em.persist(cp8);
        em.flush();
        createOutline(cp8);
        createQuiz(cp8);

        Coursepack cp9 = new Coursepack();
        cp9.setAssignedTeacher(teacher);
        cp9.setCategory(cat1);
        cat1.getCoursepackList().add(cp9);
        cp9.setCode("DE1015");
        cp9.setImageLocation("https://icon-library.net/images/no-image-available-icon/no-image-available-icon-6.jpg");
        cp9.setDescription("This course will introduce you to developing web spplications with PHP");
        cp9.setPrice(60.00);
        cp9.setRating(0.00);
        cp9.setPublished(true);
        cp9.setTeacherBackground("I am a developer with a passion in teaching!");
        cp9.setTitle("Introduction to PHP");
        teacher.getTeacherCoursepackList().add(cp9);
        em.persist(cp9);
        em.flush();
        createOutline(cp9);
        createQuiz(cp9);

        ForumTopic topic1 = new ForumTopic();
        topic1.setModule(m1);
        topic1.setDescription("Please post your group name and team members (no more than 5 per team).");
        topic1.setTitle("Group Formation");
        em.persist(topic1);
        em.flush();
        m1.getForumTopicList().add(topic1);

        ForumTopic topic2 = new ForumTopic();
        topic2.setModule(m1);
        topic2.setDescription("This topic heading is only used for disseminating important information.");
        topic2.setTitle("Course Administration");
        em.persist(topic2);
        em.flush();
        m1.getForumTopicList().add(topic2);

        ForumTopic topic3 = new ForumTopic();
        topic3.setCoursepack(cp1);
        topic3.setDescription("Please use this topic heading to ask any general questions about the module.");
        topic3.setTitle("General Enquiries");
        em.persist(topic3);
        em.flush();
        cp1.getForumTopicList().add(topic3);

        ForumTopic topic4 = new ForumTopic();
        topic4.setModule(m1);
        topic4.setDescription("Please use this topic heading to ask any questions relating to assignment 6.");
        topic4.setTitle("Assignment 6");
        em.persist(topic4);
        em.flush();
        m1.getForumTopicList().add(topic4);

        ForumPost thread1 = new ForumPost();
        thread1.setCreateTs(LocalDateTime.now());
        thread1.setUpdateTs(LocalDateTime.now());
        thread1.setMessage("Alice, Tom, John, Marcus and Peter");
        thread1.setOwner(student);
        thread1.setTitle("Grouping");
        thread1.setTopic(topic1);
        thread1.setThreadStarter(Boolean.TRUE);
        em.persist(thread1);
        em.flush();
        topic1.getThreads().add(thread1);

        ForumPost thread2 = new ForumPost();
        thread2.setCreateTs(LocalDateTime.now());
        thread2.setUpdateTs(LocalDateTime.now());
        thread2.setMessage("I'm learning javascript and to practice traversing the DOM I have created a method that returns an array of parent elements based on the 'nodeName'. I have done this so that I could select all of a certain element (that is a parent of another) and style them or change them, etc.");
        thread2.setOwner(extStu);
        thread2.setTitle("Accessing all elements in array");
        thread2.setTopic(topic3);
        thread2.setThreadStarter(Boolean.TRUE);
        em.persist(thread2);
        em.flush();
        topic3.getThreads().add(thread2);

        ForumPost comment1 = new ForumPost();
        comment1.setCreateTs(LocalDateTime.now());
        comment1.setUpdateTs(LocalDateTime.now());
        comment1.setMessage("No, you are doing to have to use a method.");
        comment1.setOwner(student2);
        comment1.setThreadStarter(Boolean.FALSE);
        comment1.setType("comment");
        comment1.setParentOfComments(thread2);
        em.persist(comment1);
        em.flush();
        thread2.getComments().add(comment1);

        ForumPost reply1 = new ForumPost();
        reply1.setTitle("Re:Accessing all elements in array");
        reply1.setCreateTs(LocalDateTime.now());
        reply1.setUpdateTs(LocalDateTime.now());
        reply1.setMessage("Probably the simplest way is to use arrays API.\n"
                + "If you can use ES6, it would look like so: parents.forEach(parent => parent.style.background = \"black\")");
        reply1.setOwner(teacher);
        reply1.setThreadStarter(Boolean.FALSE);
        reply1.setType("reply");
        reply1.setParentOfReply(thread2);
        em.persist(reply1);
        em.flush();
        thread2.getReplies().add(reply1);

        ForumPost comment2 = new ForumPost();
        comment2.setParentOfComments(reply1);
        comment2.setCreateTs(LocalDateTime.now());
        comment2.setMessage("Thanks for this answer. I think what I was trying to achieve is much more "
                + "complex than I had initially thought and much beyond my current capabilities. "
                + "I appreciate this answer but I was wanted to change the function rather than changing "
                + "how to access the elements once the function returned it's array. If that makes sense?!");
        comment2.setCreateTs(LocalDateTime.now());
        comment2.setUpdateTs(LocalDateTime.now());
        comment2.setOwner(extStu);
        comment2.setType("comment");
        comment2.setThreadStarter(Boolean.FALSE);
        em.persist(comment2);
        em.flush();
        reply1.getComments().add(comment2);

        ForumPost thread3 = new ForumPost();
        thread3.setCreateTs(LocalDateTime.now());
        thread3.setUpdateTs(LocalDateTime.now());
        thread3.setMessage("I was unable to attempt the quiz as there isn’t any link for me to click to start quiz");
        thread3.setOwner(student);
        thread3.setTitle("Unable to attempt quiz");
        thread3.setTopic(topic3);
        thread3.setThreadStarter(Boolean.TRUE);
        em.persist(thread3);
        em.flush();
        topic3.getThreads().add(thread3);

        ForumPost comment3 = new ForumPost();
        comment3.setCreateTs(LocalDateTime.now());
        comment3.setUpdateTs(LocalDateTime.now());
        comment3.setMessage("I am not having any problem with this. I see a link there.");
        comment3.setOwner(student2);
        comment3.setThreadStarter(Boolean.FALSE);
        comment3.setType("comment");
        comment3.setParentOfComments(thread3);
        em.persist(comment3);
        em.flush();
        thread3.getComments().add(comment3);

        ForumPost reply2 = new ForumPost();
        reply2.setTitle("Re:Unable to attempt quiz");
        reply2.setCreateTs(LocalDateTime.now());
        reply2.setUpdateTs(LocalDateTime.now());
        reply2.setMessage("The issue has since been rectified by the LumiNUS steam. Could you try again? The deadline for the current quiz is also extended by 1 day, to 23.59pm Monday. ");
        reply2.setOwner(teacher);
        reply2.setThreadStarter(Boolean.FALSE);
        reply2.setType("reply");
        reply2.setParentOfReply(thread3);
        em.persist(reply2);
        em.flush();
        thread3.getReplies().add(reply2);

        ForumPost comment4 = new ForumPost();
        comment4.setCreateTs(LocalDateTime.now());
        comment4.setUpdateTs(LocalDateTime.now());
        comment4.setMessage("Yes, I am now able to attempt the quiz. Thank you.");
        comment4.setOwner(student);
        comment4.setThreadStarter(Boolean.FALSE);
        comment4.setType("comment");
        em.persist(comment4);
        em.flush();
        reply2.getComments().add(comment4);

        ForumPost thread4 = new ForumPost();
        thread4.setCreateTs(LocalDateTime.now());
        thread4.setUpdateTs(LocalDateTime.now());
        thread4.setMessage("Dear Profs/TAs, \n" + "\n" + "1. May I clarify if we are using a vector calculation to calculate the shortest distance? Meaning using dist = sqrt[ (x1 - x2)^2 + (y1 - y2) ^2 ] \n"
                + "\n" + "    Or is the shortest distance based on vertical and horizontal travel only?\n" + "\n"
                + "2. Could there be a typo here?  Shouldn't it be dist = sqrt ( 30^2 ) = 30? How is the distance from the house to Stores 0 and 2 distance sqrt(30)? sqrt(30) is smaller than 10 anyway.");
        thread4.setOwner(student);
        thread4.setTitle("PDMap: Calculating (shortest) distance");
        thread4.setTopic(topic4);
        thread4.setThreadStarter(Boolean.TRUE);
        em.persist(thread4);
        em.flush();
        topic4.getThreads().add(thread4);

        ForumPost reply3 = new ForumPost();
        reply3.setTitle("Re:PDMap: Calculating (shortest) distance");
        reply3.setCreateTs(LocalDateTime.now());
        reply3.setUpdateTs(LocalDateTime.now());
        reply3.setMessage("1. Take euclidean distance. (your formula is correct)\n" + "\n" + "2. Yes there is a typo. Distance to 0 and 2 pizza stores are 30. Not sqrt(30)");
        reply3.setOwner(teacher);
        reply3.setThreadStarter(Boolean.FALSE);
        reply3.setType("reply");
        reply3.setParentOfReply(thread4);
        em.persist(reply3);
        em.flush();
        thread4.getReplies().add(reply3);

        ForumPost comment5 = new ForumPost();
        comment5.setCreateTs(LocalDateTime.now());
        comment5.setUpdateTs(LocalDateTime.now());
        comment5.setMessage("The assignment instruction was:\n" + "\n" + "\"Since we are all using integer arithmetic, the distance computation must be exact.\"\n" + "\n"
                + "May I check how are we supposed to get integer distance if we're using euclidian distance to calculate it? Do I just not use square root in the formula? Or do we convert a float to an integer? But doing so will lose accuracy?\n"
                + "\n" + "Kindly advise please, thank you!");
        comment5.setOwner(student);
        comment5.setThreadStarter(Boolean.FALSE);
        comment5.setType("comment");
        comment5.setParentOfComments(reply3);
        em.persist(comment5);
        em.flush();
        reply3.getComments().add(comment5);

        createSurvey(m1);
        createSurvey(m2);
        createSurvey(m3);
        createSurvey(m4);

        Annoucement an1 = new Annoucement();
        an1.setTitle("abc");
        an1.setContent("learn you abc");
        an1.setCreatedDate(new Date(2019 - 1900, 10, 22, 11, 0));
        an1.setLastUpdatedDate(new Date(2019 - 1900, 10, 22, 11, 0));
        an1.setStartDate(new Date(2019 - 1900, 10, 22, 12, 0));
        an1.setEndDate(new Date(2019 - 1900, 10, 24, 12, 0));
        an1.setPublish(true);
        an1.setEmailNotification(true);
        an1.setOwner(admin);
        em.persist(an1);
        em.flush();

        Annoucement an2 = new Annoucement();
        an2.setTitle("def");
        an2.setContent("learn your def");
        an2.setCreatedDate(new Date(2019 - 1900, 10, 22, 10, 0));
        an2.setLastUpdatedDate(new Date(2019 - 1900, 10, 22, 10, 0));
        an2.setStartDate(new Date(2019 - 1900, 10, 22, 11, 0));
        an2.setEndDate(new Date(2019 - 1900, 10, 24, 23, 0));
        an2.setPublish(true);
        an2.setEmailNotification(true);
        an2.setModule(m1);
        an2.setOwner(teacher);
        em.persist(an2);
        em.flush();

        Annoucement an3 = new Annoucement();
        an3.setTitle("ghi");
        an3.setContent("learn your ghi");
        an3.setCreatedDate(new Date(2019 - 1900, 10, 24, 10, 0));
        an3.setLastUpdatedDate(new Date(2019 - 1900, 10, 24, 10, 0));
        an3.setStartDate(new Date(2019 - 1900, 10, 25, 11, 0));
        an3.setEndDate(new Date(2019 - 1900, 10, 28, 23, 0));
        an3.setPublish(true);
        an3.setEmailNotification(true);
        an3.setOwner(admin);
        em.persist(an3);
        em.flush();

        Annoucement an4 = new Annoucement();
        an4.setTitle("jkl");
        an4.setContent("learn your jkl");
        an4.setCreatedDate(new Date(2019 - 1900, 10, 24, 10, 0));
        an4.setLastUpdatedDate(new Date(2019 - 1900, 10, 24, 10, 0));
        an4.setStartDate(new Date(2019 - 1900, 10, 25, 11, 0));
        an4.setEndDate(new Date(2019 - 1900, 10, 28, 23, 0));
        an4.setPublish(true);
        an4.setEmailNotification(true);
        an4.setModule(m1);
        an4.setOwner(teacher);
        em.persist(an4);
        em.flush();

        Annoucement an5 = new Annoucement();
        an5.setTitle("expired 1");
        an5.setContent("expired 1");
        an5.setCreatedDate(new Date(2019 - 1900, 01, 01, 01, 0));
        an5.setLastUpdatedDate(new Date(2019 - 1900, 01, 01, 01, 0));
        an5.setStartDate(new Date(2019 - 1900, 02, 02, 02, 0));
        an5.setEndDate(new Date(2019 - 1900, 03, 03, 03, 0));
        an5.setPublish(true);
        an5.setEmailNotification(false);
        an5.setOwner(admin);
        em.persist(an5);
        em.flush();

        Annoucement an6 = new Annoucement();
        an6.setTitle("expired 2");
        an6.setContent("expired 2");
        an6.setCreatedDate(new Date(2019 - 1900, 01, 01, 01, 0));
        an6.setLastUpdatedDate(new Date(2019 - 1900, 01, 01, 01, 0));
        an6.setStartDate(new Date(2019 - 1900, 02, 02, 02, 0));
        an6.setEndDate(new Date(2019 - 1900, 03, 03, 03, 0));
        an6.setPublish(true);
        an6.setEmailNotification(false);
        an6.setModule(m1);
        an6.setOwner(teacher);
        em.persist(an6);
        em.flush();

        List<Coursepack> coursepackList = new ArrayList<>();
        coursepackList.add(cp1);
        coursepackList.add(cp2);
        coursepackList.add(cp3);

        Certification cer1 = new Certification();
        cer1.setTitle("IT Genius");
        cer1.setDescription("You good");
        cer1.setCoursepackList(coursepackList);
        em.persist(cer1);
        em.flush();
    }

    public void createOutline(Coursepack coursepack) {
        Outlines outline1 = new Outlines();
        outline1.setName("Topic 1");
        outline1.setNumber(1);
        outline1.setCoursepack(coursepack);
        em.persist(outline1);
        coursepack.getOutlineList().add(outline1);

        Outlines outline2 = new Outlines();
        outline2.setName("Topic 2");
        outline2.setNumber(2);
        outline2.setCoursepack(coursepack);
        em.persist(outline2);
        coursepack.getOutlineList().add(outline2);

        Outlines outline3 = new Outlines();
        outline3.setName("Topic 3");
        outline3.setNumber(3);
        outline3.setCoursepack(coursepack);
        em.persist(outline3);
        em.flush();
        coursepack.getOutlineList().add(outline3);
    }

    public void createSurvey(Module module) {
        Survey survey = new Survey();
        survey.setTitle("End-of-Semester Evaluation");
        survey.setDescription("Welcome to the feedback exercise. <br />Please take time to review your learning experience in the past semester and be honest in your replies. <br /><br />Please click on <b>'Next'</b> to proceed with the exercise.");
        survey.setModule(module);
        survey.setOpeningDate(new Date());
        survey.setClosingDate(new Date(2019 - 1900, 12, 12));
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

        List<String> grades = new ArrayList<>();
        grades.add("A+");
        grades.add("A");
        grades.add("A-");
        grades.add("B+");
        grades.add("B");
        grades.add("B-");
        grades.add("C+");
        grades.add("C");
        grades.add("D+");
        grades.add("D");
        grades.add("F");

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
        sq6.setChoices(grades);
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
        sq8.setHtml("<h5>Quantitative on Content</h5>");
        sq8.setType(QuestionTypeEnum.html);
        em.persist(sq8);
        em.flush();
        survey.getQuestionList().add(sq8);

        Question sq9 = new Question();
        sq9.setType(QuestionTypeEnum.radiogroup);
        sq9.setNumber(7);
        sq9.setTitle("The teacher has enhanced my thinking ability.");
        sq9.setIsRequired(false);
        sq9.setChoices(choices);
        em.persist(sq9);
        em.flush();
        survey.getQuestionList().add(sq9);

        Question sq10 = new Question();
        sq10.setType(QuestionTypeEnum.radiogroup);
        sq10.setNumber(8);
        sq10.setTitle("The teacher provides timely and useful feedback.");
        sq10.setIsRequired(false);
        sq10.setChoices(choices);
        em.persist(sq10);
        em.flush();
        survey.getQuestionList().add(sq10);

        Question sq11 = new Question();
        sq11.setType(QuestionTypeEnum.radiogroup);
        sq11.setNumber(9);
        sq11.setTitle("The teacher is approachable for consultation.");
        sq11.setIsRequired(false);
        sq11.setChoices(choices);
        em.persist(sq11);
        em.flush();
        survey.getQuestionList().add(sq11);

        Question sq12 = new Question();
        sq12.setType(QuestionTypeEnum.radiogroup);
        sq12.setNumber(10);
        sq12.setTitle("The teacher has increased my interest in the subject.");
        sq12.setIsRequired(false);
        sq12.setChoices(choices);
        em.persist(sq12);
        em.flush();
        survey.getQuestionList().add(sq12);

        Question sq13 = new Question();
        sq13.setType(QuestionTypeEnum.radiogroup);
        sq13.setNumber(11);
        sq13.setTitle("The teacher has enhanced my ability to learn independently.");
        sq13.setIsRequired(false);
        sq13.setChoices(choices);
        em.persist(sq13);
        em.flush();
        survey.getQuestionList().add(sq13);

        Question sq14 = new Question();
        sq14.setType(QuestionTypeEnum.radiogroup);
        sq14.setNumber(12);
        sq14.setTitle("The teacher encourages me to apply concepts learnt.");
        sq14.setIsRequired(false);
        sq14.setChoices(choices);
        em.persist(sq14);
        em.flush();
        survey.getQuestionList().add(sq14);

        Question sq15 = new Question();
        sq15.setType(QuestionTypeEnum.radiogroup);
        sq15.setNumber(13);
        sq15.setTitle("Overall the teacher is effective.");
        sq15.setIsRequired(false);
        sq15.setChoices(choices);
        em.persist(sq15);
        em.flush();
        survey.getQuestionList().add(sq15);

        Question sq16 = new Question();
        sq16.setType(QuestionTypeEnum.text);
        sq16.setNumber(14);
        sq16.setTitle("What are the teacher's strengths?");
        sq16.setIsRequired(false);
        em.persist(sq16);
        em.flush();
        survey.getQuestionList().add(sq16);

        Question sq17 = new Question();
        sq17.setType(QuestionTypeEnum.text);
        sq17.setNumber(15);
        sq17.setTitle("What are the teacher's weaknesses?");
        sq17.setIsRequired(false);
        em.persist(sq17);
        em.flush();
        survey.getQuestionList().add(sq17);

        Question sq18 = new Question();
        sq18.setType(QuestionTypeEnum.text);
        sq18.setNumber(15);
        sq18.setTitle("What improvements would you suggest to the teacher?");
        sq18.setIsRequired(false);
        em.persist(sq18);
        em.flush();
        survey.getQuestionList().add(sq18);
        
        createSurveyAttempts(survey);
    }
    
    public void createSurveyAttempts(Survey survey){
        Random random = new Random();
        for(User u: survey.getModule().getStudentList()){
            SurveyAttempt sa = new SurveyAttempt();
            sa.setSurvey(survey);
            sa.setSurveyTaker(u);
            sa.setQuestionAttemptList(new ArrayList<>());
            em.persist(sa);
            for(Question que: survey.getQuestionList()){
                if(que.getType() == QuestionTypeEnum.radiogroup){
                    QuestionAttempt qa = new QuestionAttempt();
                    qa.setAnswer(que.getChoices().get(random.nextInt(que.getChoices().size())));
                    qa.setQuestion(que);
                    em.persist(qa);
                    sa.getQuestionAttemptList().add(qa);
                }
            }
        }
    }

    public void createQuiz(Module module) {
        Quiz quiz = new Quiz();
        quiz.setOpeningDate(new Date(2019 - 1900, 11, 1));
        quiz.setClosingDate(new Date(2019 - 1900, 12, 5));
        quiz.setTitle("Quiz 1");
        quiz.setDescription("This quiz will cover all the topics discussed in the first 3 lectures of this module.");
        quiz.setQuizType(QuizTypeEnum.normal);
        quiz.setQuestionsOrder(QuestionOrderEnum.initial);
        quiz.setPublish(true);
        quiz.setPublishAnswer(true);
        quiz.setQuestionList(new ArrayList<>());
        quiz.setMaxMarks(5.0);

        Question q1 = new Question();
        q1.setTitle("Who is the creator of this quiz?");
        q1.setChoices(new ArrayList<>());
        q1.getChoices().add("Kristo");
        q1.getChoices().add("Vixson");
        q1.getChoices().add("Both A and B");
        q1.getChoices().add("No correct options");
        q1.setCorrectAnswer("Kristo");
        q1.setPoints(1.0);
        q1.setNumber(1);
        q1.setType(QuestionTypeEnum.radiogroup);
        q1.setIsRequired(Boolean.TRUE);
        q1.setExplanation("The correct answer can be check on the Git history.");
        quiz.getQuestionList().add(q1);
        em.persist(q1);

        Question q2 = new Question();
        q2.setTitle("When is this quiz created?");
        q2.setChoices(new ArrayList<>());
        q2.getChoices().add("Last night");
        q2.getChoices().add("Last week");
        q2.getChoices().add("Two weeks ago");
        q2.getChoices().add("Last weekend");
        q2.setCorrectAnswer("Last night");
        q2.setPoints(1.0);
        q2.setNumber(2);
        q2.setType(QuestionTypeEnum.radiogroup);
        q2.setIsRequired(Boolean.TRUE);
        q2.setExplanation("This quiz is created last night");
        quiz.getQuestionList().add(q2);
        em.persist(q2);

        Question q3 = new Question();
        q3.setTitle("How many people are in a Capstone group?");
        q3.setChoices(new ArrayList<>());
        q3.getChoices().add("4");
        q3.getChoices().add("5");
        q3.getChoices().add("6");
        q3.getChoices().add("7");
        q3.getChoices().add("All answers are correct");
        q3.setCorrectAnswer("All answers are correct");
        q3.setPoints(1.0);
        q3.setNumber(3);
        q3.setType(QuestionTypeEnum.radiogroup);
        q3.setIsRequired(Boolean.TRUE);
        q3.setExplanation("Capstone groups can range from 4-7 people");
        quiz.getQuestionList().add(q3);
        em.persist(q3);

        Question q4 = new Question();
        q4.setTitle("Who should you vote in STePS?");
        q4.setChoices(new ArrayList<>());
        q4.getChoices().add("Team A");
        q4.getChoices().add("Team B");
        q4.getChoices().add("Team C");
        q4.getChoices().add("FlipIt - TT02");
        q4.setCorrectAnswer("FlipIt - TT02");
        q4.setPoints(1.0);
        q4.setNumber(4);
        q4.setType(QuestionTypeEnum.radiogroup);
        q4.setIsRequired(Boolean.TRUE);
        q4.setExplanation("flipIt provides a holistic learning management platform for all universities.");
        quiz.getQuestionList().add(q4);
        em.persist(q4);

        Question q5 = new Question();
        q5.setTitle("What score should we get for this module?");
        q5.setChoices(new ArrayList<>());
        q5.getChoices().add("A+");
        q5.getChoices().add("A");
        q5.getChoices().add("A-");
        q5.getChoices().add("B+ or lower");
        q5.setCorrectAnswer("A+");
        q5.setPoints(1.0);
        q5.setNumber(5);
        q5.setType(QuestionTypeEnum.radiogroup);
        q5.setIsRequired(Boolean.TRUE);
        q5.setExplanation("Our team works hard through night and day and deserves it.");
        quiz.getQuestionList().add(q5);
        em.persist(q5);

        em.persist(quiz);
        module.getQuizList().add(quiz);
        quiz.setModule(module);
        createQuizAttempt(quiz);

        Quiz quiz2 = new Quiz();
        quiz2.setOpeningDate(new Date(2019 - 1900, 11, 10));
        quiz2.setClosingDate(new Date(2019 - 1900, 12, 5));
        quiz2.setTitle("Quiz 2");
        quiz2.setDescription("This quiz will test your algebra skills.");
        quiz2.setQuizType(QuizTypeEnum.normal);
        quiz2.setQuestionsOrder(QuestionOrderEnum.initial);
        quiz2.setPublish(true);
        quiz2.setPublishAnswer(true);
        quiz2.setQuestionList(new ArrayList<>());
        quiz2.setMaxMarks(5.0);

        q1 = new Question();
        q1.setTitle("Find x if x + 5 = 2");
        q1.setChoices(new ArrayList<>());
        q1.getChoices().add("2");
        q1.getChoices().add("3");
        q1.getChoices().add("-3");
        q1.getChoices().add("-2");
        q1.setCorrectAnswer("-3");
        q1.setPoints(1.0);
        q1.setNumber(1);
        q1.setType(QuestionTypeEnum.radiogroup);
        q1.setIsRequired(Boolean.TRUE);
        q1.setExplanation("x = 2 - 5 = -3");
        quiz2.getQuestionList().add(q1);
        em.persist(q1);

        q2 = new Question();
        q2.setTitle("Find x if 10x + 1 = 51");
        q2.setChoices(new ArrayList<>());
        q2.getChoices().add("5");
        q2.getChoices().add("4.9");
        q2.getChoices().add("5.1");
        q2.getChoices().add("4");
        q2.setCorrectAnswer("5");
        q2.setPoints(1.0);
        q2.setNumber(2);
        q2.setType(QuestionTypeEnum.radiogroup);
        q2.setIsRequired(Boolean.TRUE);
        q2.setExplanation("x = (51 - 1)/10");
        quiz2.getQuestionList().add(q2);
        em.persist(q2);

        q3 = new Question();
        q3.setTitle("y = 3x + 2. What is the value of y if x is 2");
        q3.setChoices(new ArrayList<>());
        q3.getChoices().add("2");
        q3.getChoices().add("3");
        q3.getChoices().add("4");
        q3.getChoices().add("8");
        q3.getChoices().add("11");
        q3.setCorrectAnswer("8");
        q3.setPoints(1.0);
        q3.setNumber(3);
        q3.setType(QuestionTypeEnum.radiogroup);
        q3.setIsRequired(Boolean.TRUE);
        q3.setExplanation("y = 3*2 + 2 = 8");
        quiz2.getQuestionList().add(q3);
        em.persist(q3);

        q4 = new Question();
        q4.setTitle("What is the distance from (1,1) to (2,1)");
        q4.setChoices(new ArrayList<>());
        q4.getChoices().add("1");
        q4.getChoices().add("2");
        q4.getChoices().add("3");
        q4.getChoices().add("0");
        q4.setCorrectAnswer("1");
        q4.setPoints(1.0);
        q4.setNumber(4);
        q4.setType(QuestionTypeEnum.radiogroup);
        q4.setIsRequired(Boolean.TRUE);
        q4.setExplanation("Distance is 1");
        quiz2.getQuestionList().add(q4);
        em.persist(q4);

        q5 = new Question();
        q5.setTitle("If x + y = 2 and x - y = 1. The value of x and y respectively is");
        q5.setChoices(new ArrayList<>());
        q5.getChoices().add("1.5, 3");
        q5.getChoices().add("1.5, 0.5");
        q5.getChoices().add("1, 0.5");
        q5.getChoices().add("1, 2");
        q5.setCorrectAnswer("1.5, 0.5");
        q5.setPoints(1.0);
        q5.setNumber(5);
        q5.setType(QuestionTypeEnum.radiogroup);
        q5.setIsRequired(Boolean.TRUE);
        q5.setExplanation("Our team works hard through night and day and deserves it.");
        quiz2.getQuestionList().add(q5);
        em.persist(q5);

        em.persist(quiz2);
        module.getQuizList().add(quiz2);
        quiz2.setModule(module);
        createQuizAttempt(quiz2);
    }

    public void createQuiz(Coursepack coursepack) {
        Quiz quiz = new Quiz();
        quiz.setOpeningDate(new Date(2019 - 1900, 11, 1));
        quiz.setClosingDate(new Date(2019 - 1900, 12, 5));
        quiz.setTitle("Quiz 1");
        quiz.setDescription("This quiz will cover all the topics discussed in the first 3 lectures of this module.");
        quiz.setQuizType(QuizTypeEnum.normal);
        quiz.setQuestionsOrder(QuestionOrderEnum.initial);
        quiz.setQuestionList(new ArrayList<>());
        quiz.setMaxMarks(5.0);

        Question q1 = new Question();
        q1.setTitle("Who is the creator of this quiz?");
        q1.setChoices(new ArrayList<>());
        q1.getChoices().add("Kristo");
        q1.getChoices().add("Vixson");
        q1.getChoices().add("Both A and B");
        q1.getChoices().add("No correct options");
        q1.setCorrectAnswer("Kristo");
        q1.setPoints(1.0);
        q1.setNumber(1);
        q1.setType(QuestionTypeEnum.radiogroup);
        q1.setIsRequired(Boolean.TRUE);
        q1.setExplanation("The correct answer can be check on the Git history.");
        quiz.getQuestionList().add(q1);
        em.persist(q1);

        Question q2 = new Question();
        q2.setTitle("When is this quiz created?");
        q2.setChoices(new ArrayList<>());
        q2.getChoices().add("Last night");
        q2.getChoices().add("Last week");
        q2.getChoices().add("Two weeks ago");
        q2.getChoices().add("Last weekend");
        q2.setCorrectAnswer("Last night");
        q2.setPoints(1.0);
        q2.setNumber(2);
        q2.setType(QuestionTypeEnum.radiogroup);
        q2.setIsRequired(Boolean.TRUE);
        q2.setExplanation("This quiz is created last night");
        quiz.getQuestionList().add(q2);
        em.persist(q2);

        Question q3 = new Question();
        q3.setTitle("How many people are in a Capstone group?");
        q3.setChoices(new ArrayList<>());
        q3.getChoices().add("4");
        q3.getChoices().add("5");
        q3.getChoices().add("6");
        q3.getChoices().add("7");
        q3.getChoices().add("All answers are correct");
        q3.setCorrectAnswer("All answers are correct");
        q3.setPoints(1.0);
        q3.setNumber(3);
        q3.setType(QuestionTypeEnum.radiogroup);
        q3.setIsRequired(Boolean.TRUE);
        q3.setExplanation("Capstone groups can range from 4-7 people");
        quiz.getQuestionList().add(q3);
        em.persist(q3);

        Question q4 = new Question();
        q4.setTitle("Who should you vote in STePS?");
        q4.setChoices(new ArrayList<>());
        q4.getChoices().add("Team A");
        q4.getChoices().add("Team B");
        q4.getChoices().add("Team C");
        q4.getChoices().add("FlipIt - TT02");
        q4.setCorrectAnswer("FlipIt - TT02");
        q4.setPoints(1.0);
        q4.setNumber(4);
        q4.setType(QuestionTypeEnum.radiogroup);
        q4.setIsRequired(Boolean.TRUE);
        q4.setExplanation("flipIt provides a holistic learning management platform for all universities.");
        quiz.getQuestionList().add(q4);
        em.persist(q4);

        Question q5 = new Question();
        q5.setTitle("What score should we get for this module?");
        q5.setChoices(new ArrayList<>());
        q5.getChoices().add("A+");
        q5.getChoices().add("A");
        q5.getChoices().add("A-");
        q5.getChoices().add("B+ or lower");
        q5.setCorrectAnswer("A+");
        q5.setPoints(1.0);
        q5.setNumber(5);
        q5.setType(QuestionTypeEnum.radiogroup);
        q5.setIsRequired(Boolean.TRUE);
        q5.setExplanation("Our team works hard through night and day and deserves it.");
        quiz.getQuestionList().add(q5);
        em.persist(q5);

        em.persist(quiz);
        coursepack.getQuizList().add(quiz);

        Quiz quiz2 = new Quiz();
        quiz2.setOpeningDate(new Date(2019 - 1900, 11, 10));
        quiz2.setClosingDate(new Date(2019 - 1900, 12, 5));
        quiz2.setTitle("Quiz 2");
        quiz2.setDescription("This quiz will test your algebra skills.");
        quiz2.setQuizType(QuizTypeEnum.normal);
        quiz2.setQuestionsOrder(QuestionOrderEnum.initial);
        quiz2.setQuestionList(new ArrayList<>());
        quiz2.setMaxMarks(5.0);

        q1 = new Question();
        q1.setTitle("Find x if x + 5 = 2");
        q1.setChoices(new ArrayList<>());
        q1.getChoices().add("2");
        q1.getChoices().add("3");
        q1.getChoices().add("-3");
        q1.getChoices().add("-2");
        q1.setCorrectAnswer("-3");
        q1.setPoints(1.0);
        q1.setNumber(1);
        q1.setType(QuestionTypeEnum.radiogroup);
        q1.setIsRequired(Boolean.TRUE);
        q1.setExplanation("x = 2 - 5 = -3");
        quiz2.getQuestionList().add(q1);
        em.persist(q1);

        q2 = new Question();
        q2.setTitle("Find x if 10x + 1 = 51");
        q2.setChoices(new ArrayList<>());
        q2.getChoices().add("5");
        q2.getChoices().add("4.9");
        q2.getChoices().add("5.1");
        q2.getChoices().add("4");
        q2.setCorrectAnswer("5");
        q2.setPoints(1.0);
        q2.setNumber(2);
        q2.setType(QuestionTypeEnum.radiogroup);
        q2.setIsRequired(Boolean.TRUE);
        q2.setExplanation("x = (51 - 1)/10");
        quiz2.getQuestionList().add(q2);
        em.persist(q2);

        q3 = new Question();
        q3.setTitle("y = 3x + 2. What is the value of y if x is 2");
        q3.setChoices(new ArrayList<>());
        q3.getChoices().add("2");
        q3.getChoices().add("3");
        q3.getChoices().add("4");
        q3.getChoices().add("8");
        q3.getChoices().add("11");
        q3.setCorrectAnswer("8");
        q3.setPoints(1.0);
        q3.setNumber(3);
        q3.setType(QuestionTypeEnum.radiogroup);
        q3.setIsRequired(Boolean.TRUE);
        q3.setExplanation("y = 3*2 + 2 = 8");
        quiz2.getQuestionList().add(q3);
        em.persist(q3);

        q4 = new Question();
        q4.setTitle("What is the distance from (1,1) to (2,1)");
        q4.setChoices(new ArrayList<>());
        q4.getChoices().add("1");
        q4.getChoices().add("2");
        q4.getChoices().add("3");
        q4.getChoices().add("0");
        q4.setCorrectAnswer("1");
        q4.setPoints(1.0);
        q4.setNumber(4);
        q4.setType(QuestionTypeEnum.radiogroup);
        q4.setIsRequired(Boolean.TRUE);
        q4.setExplanation("Distance is 1");
        quiz2.getQuestionList().add(q4);
        em.persist(q4);

        q5 = new Question();
        q5.setTitle("If x + y = 2 and x - y = 1. The value of x and y respectively is");
        q5.setChoices(new ArrayList<>());
        q5.getChoices().add("1.5, 3");
        q5.getChoices().add("1.5, 0.5");
        q5.getChoices().add("1, 0.5");
        q5.getChoices().add("1, 2");
        q5.setCorrectAnswer("1.5, 0.5");
        q5.setPoints(1.0);
        q5.setNumber(5);
        q5.setType(QuestionTypeEnum.radiogroup);
        q5.setIsRequired(Boolean.TRUE);
        q5.setExplanation("Our team works hard through night and day and deserves it.");
        quiz2.getQuestionList().add(q5);
        em.persist(q5);

        em.persist(quiz2);
        coursepack.getQuizList().add(quiz2);

        Quiz quiz3 = new Quiz();
        quiz3.setOpeningDate(new Date(2019 - 1900, 11, 10));
        quiz3.setClosingDate(new Date(2019 - 1900, 12, 5));
        quiz3.setTitle("Quiz 3");
        quiz3.setDescription("This quiz will test your life.");
        quiz3.setQuizType(QuizTypeEnum.normal);
        quiz3.setQuestionsOrder(QuestionOrderEnum.initial);
        quiz3.setQuestionList(new ArrayList<>());
        quiz3.setMaxMarks(5.0);

        q1 = new Question();
        q1.setTitle("Life is");
        q1.setChoices(new ArrayList<>());
        q1.getChoices().add("having fun");
        q1.getChoices().add("death");
        q1.getChoices().add("a joke");
        q1.getChoices().add("Very sad");
        q1.setCorrectAnswer("Very sad");
        q1.setPoints(1.0);
        q1.setNumber(1);
        q1.setType(QuestionTypeEnum.radiogroup);
        q1.setIsRequired(Boolean.TRUE);
        q1.setExplanation("Life is what you believe life is");
        quiz3.getQuestionList().add(q1);
        em.persist(q1);

        q2 = new Question();
        q2.setTitle("Life finds a way");
        q2.setChoices(new ArrayList<>());
        q2.getChoices().add("somehow");
        q2.getChoices().add("Nowhere");
        q2.getChoices().add("back");
        q2.getChoices().add("to the future");
        q2.setCorrectAnswer("Nowhere");
        q2.setPoints(1.0);
        q2.setNumber(2);
        q2.setType(QuestionTypeEnum.radiogroup);
        q2.setIsRequired(Boolean.TRUE);
        q2.setExplanation("Life finds a way somehow");
        quiz3.getQuestionList().add(q2);
        em.persist(q2);

        q3 = new Question();
        q3.setTitle("Death should be celebrated");
        q3.setChoices(new ArrayList<>());
        q3.getChoices().add("Strongly agree");
        q3.getChoices().add("agree");
        q3.getChoices().add("neutral");
        q3.getChoices().add("disagree");
        q3.getChoices().add("strongly disagree");
        q3.setCorrectAnswer("Strongly agree");
        q3.setPoints(1.0);
        q3.setNumber(3);
        q3.setType(QuestionTypeEnum.radiogroup);
        q3.setIsRequired(Boolean.TRUE);
        q3.setExplanation("Up to you");
        quiz3.getQuestionList().add(q3);
        em.persist(q3);

        q4 = new Question();
        q4.setTitle("The goal of life is to");
        q4.setChoices(new ArrayList<>());
        q4.getChoices().add("achieve ZEN");
        q4.getChoices().add("Create chaos");
        q4.getChoices().add("go to war");
        q4.getChoices().add("lalala");
        q4.setCorrectAnswer("Create chaos");
        q4.setPoints(1.0);
        q4.setNumber(4);
        q4.setType(QuestionTypeEnum.radiogroup);
        q4.setIsRequired(Boolean.TRUE);
        q4.setExplanation("achieve ZEN");
        quiz3.getQuestionList().add(q4);
        em.persist(q4);

        q5 = new Question();
        q5.setTitle("Thoughts on PMD ban on footpaths?");
        q5.setChoices(new ArrayList<>());
        q5.getChoices().add("why you do this");
        q5.getChoices().add("adapt, overcome");
        q5.getChoices().add("not using anymore");
        q5.getChoices().add("Blame garmen");
        q5.setCorrectAnswer("Blame garmen");
        q5.setPoints(1.0);
        q5.setNumber(5);
        q5.setType(QuestionTypeEnum.radiogroup);
        q5.setIsRequired(Boolean.TRUE);
        q5.setExplanation("great");
        quiz3.getQuestionList().add(q5);
        em.persist(q5);

        em.persist(quiz3);
        coursepack.getQuizList().add(quiz3);
    }

    public void createQuizAttempt(Quiz quiz) {
        Random random = new Random();
        for (User u : quiz.getModule().getStudentList()) { // For all students in the module
            QuizAttempt qa = new QuizAttempt();
            qa.setCreateTs(new Date());
            qa.setQuiz(quiz);
            qa.setQuizTaker(u);
            qa.setTotalMarks(0.0);
            qa.setQuestionAttemptList(new ArrayList<>());

            // For each questions
            for (Question que : quiz.getQuestionList()) {
                if (que.getType() == QuestionTypeEnum.radiogroup) {
                    QuestionAttempt queA = new QuestionAttempt();
                    // Randomly select options
                    queA.setAnswer(que.getChoices().get(random.nextInt(que.getChoices().size())));
                    queA.setMarks(0.0);
                    queA.setQuestion(que);

                    if (queA.getAnswer().equals(que.getCorrectAnswer())) { // If Correct
                        queA.setMarks(que.getPoints());
                        qa.setTotalMarks(qa.getTotalMarks() + que.getPoints());
                    }
                    em.persist(queA);
                    qa.getQuestionAttemptList().add(queA);
                }
            }
            em.persist(qa);
            u.getQuizAttemptList().add(qa);
            quiz.getQuizAttemptList().add(qa);
            em.flush();
        }
    }

    public void createGradeItem(Module module) {
        GradeItem gi = new GradeItem();
        gi.setTitle("Mid-Term Test");
        gi.setDescription("Mid-Term test on first half of this module.");
        gi.setModule(module);
        gi.setGradeEntries(new ArrayList<>());
        gi.setPublish(true);
        gi.setMaxMarks(100.0);
        em.persist(gi);

        Random random = new Random();
        for (User u : module.getStudentList()) {
            GradeEntry ge = new GradeEntry();
            ge.setMarks(random.nextDouble() * 100);
            ge.setStudent(u);
            ge.setGradeItem(gi);
            em.persist(ge);

            gi.getGradeEntries().add(ge);
        }

        module.getGradeItemList().add(gi);

        GradeItem gi2 = new GradeItem();
        gi2.setTitle("Final Test");
        gi2.setDescription("Test everything!");
        gi2.setModule(module);
        gi2.setGradeEntries(new ArrayList<>());
        gi2.setPublish(true);
        gi2.setMaxMarks(100.0);
        em.persist(gi2);

        for (User u : module.getStudentList()) {
            GradeEntry ge = new GradeEntry();
            ge.setMarks(random.nextDouble() * 100);
            ge.setStudent(u);
            ge.setGradeItem(gi2);
            em.persist(ge);

            gi2.getGradeEntries().add(ge);
        }

        module.getGradeItemList().add(gi2);
        em.flush();
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
