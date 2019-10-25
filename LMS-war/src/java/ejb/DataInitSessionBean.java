/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Annoucement;
import entities.Attendance;
import entities.ConsultationTimeslot;
import entities.Coursepack;
import entities.ForumPost;
import entities.ForumTopic;
import entities.Module;
import entities.Question;
import entities.Schedule;
import entities.Survey;
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
        a1.setEndTs(new Timestamp(2020 - 1900, 4, 29, 13, 11, 0, 0));
        a1.setModule(m1);
        a1.setSemester(1);
        a1.setStartTs(new Timestamp(2020 - 1900, 4, 29, 13, 10, 0, 0));
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
        a2.setEndTs(new Timestamp(2020 - 1900, 3, 29, 13, 11, 0, 0));
        //a1.setModule(m1);
        //m1.getAttandanceList().add(a1);
        a2.setSemester(1);
        a2.setStartTs(new Timestamp(2020 - 1900, 3, 29, 13, 10, 0, 0));
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
        a3.setEndTs(new Timestamp(2020 - 1900, 11, 29, 13, 11, 0, 0));
        a3.setSemester(1);
        a3.setStartTs(new Timestamp(2020 - 1900, 11, 29, 13, 10, 0, 0));
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
        a4.setEndTs(new Timestamp(2020 - 1900, 10, 29, 13, 11, 0, 0));
        a4.setSemester(1);
        a4.setStartTs(new Timestamp(2020 - 1900, 10, 29, 13, 10, 0, 0));
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

        Coursepack cp1 = new Coursepack();
        cp1.setAssignedTeacher(teacher);
        cp1.setCategory("Computer Science");
        cp1.setCode("CS1000");
        cp1.setDescription("Learn C Programming and unlock doors to careers in computer engineering");
        cp1.setPrice(500.00);
        cp1.setRating(5.00);
        //cp1.setStartDate(new Timestamp(2019-1900, 11, 30, 9, 0, 0, 0));
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

        ForumTopic topic1 = new ForumTopic();
        topic1.setModule(m1);
        topic1.setDescription("Please post your group name and team members (no more than 5 per team).");
        topic1.setTitle("Group Formation");
        em.persist(topic1);
        em.flush();

        ForumTopic topic2 = new ForumTopic();
        topic2.setModule(m1);
        topic2.setDescription("This topic heading is only used for disseminating important information.");
        topic2.setTitle("Course Administration");
        em.persist(topic2);
        em.flush();

        ForumTopic topic3 = new ForumTopic();
        topic3.setCoursepack(cp1);
        topic3.setDescription("Please use this topic heading to ask any general questions about the module.");
        topic3.setTitle("General Enquiries");
        em.persist(topic3);
        em.flush();

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

        ForumPost comment1 = new ForumPost();
        comment1.setCreateTs(LocalDateTime.now());
        comment1.setUpdateTs(LocalDateTime.now());
        comment1.setMessage("No, you are doing to have to use a method.");
        comment1.setOwner(student2);
        comment1.setThreadStarter(Boolean.FALSE);
        comment1.setType("comment");
        em.persist(comment1);
        em.flush();

        ForumPost reply1 = new ForumPost();
        reply1.setTitle("Re:Accessing all elements in array");
        reply1.setCreateTs(LocalDateTime.now());
        reply1.setUpdateTs(LocalDateTime.now());
        reply1.setMessage("Probably the simplest way is to use arrays API.\n"
                + "If you can use ES6, it would look like so: parents.forEach(parent => parent.style.background = \"black\")");
        reply1.setOwner(teacher);
        reply1.setThreadStarter(Boolean.FALSE);
        reply1.setType("reply");
        em.persist(reply1);
        em.flush();

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
