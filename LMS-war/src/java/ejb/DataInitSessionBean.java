/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.ConsultationTimeslot;
import entities.Coursepack;
import entities.ForumPost;
import entities.ForumTopic;
import entities.Module;
import entities.Schedule;
import entities.Tutorial;
import entities.User;
import java.sql.Timestamp;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Startup;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import util.AccessRightEnum;
import util.GenderEnum;

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
        m1.setExamTime(new Timestamp(2019 - 1900, 11, 29, 9, 0, 0, 0));
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
        m2.setExamTime(new Timestamp(2019 - 1900, 11, 30, 9, 0, 0, 0));
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
        m3.setExamTime(new Timestamp(2020 - 1900, 4, 29, 13, 0, 0, 0));
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
        m4.setExamTime(new Timestamp(2020 - 1900, 12, 1, 9, 0, 0, 0));
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
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
