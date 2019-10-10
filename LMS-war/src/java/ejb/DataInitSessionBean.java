/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import entities.Attendance;
import entities.ConsultationTimeslot;
import entities.Module;
import entities.Schedule;
import entities.Tutorial;
import entities.User;
import java.sql.Timestamp;
import java.time.LocalDate;
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
        teacher.getTeacherModuleList().add(m4);
        em.persist(m4);
        em.flush();

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
        c1.setBooker(student);
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
        
        Attendance a1 = new Attendance();
        a1.setDuration(5);
        a1.setEndTs(new Timestamp(2020-1900, 4, 29, 13,11, 0, 0));
        a1.setModule(m1);
        m1.getAttandanceList().add(a1);
        a1.setSemester(1);
        a1.setStartTs(new Timestamp(2020-1900, 4, 29, 13,10, 0, 0));
        a1.setTotal(20);
        a1.setTutorial(t1);
        t1.setAttendance(a1);
        a1.setAttendees(m1.getStudentList());
        a1.setAttendedNumber(1);
        em.persist(a1);
        em.flush();
    }

    public void persist(Object object) {
        em.persist(object);
    }
}
