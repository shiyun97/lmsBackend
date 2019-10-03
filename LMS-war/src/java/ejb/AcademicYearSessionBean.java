/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package ejb;

import java.util.Date;
import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.LocalBean;
import javax.ejb.Schedule;
import javax.ejb.Startup;

/**
 *
 * @author Asus
 */
@Singleton
@LocalBean
@Startup
public class AcademicYearSessionBean {

    private static String year;
    private static int semester;
    
    @Schedule(dayOfMonth="1", month="7")
    private static void changeToFirstSemester(){
        semester = 1;
        Date now = new Date();
        year = (now.getYear()+1900) + "/" + (now.getYear()+1901);
    }
    
    @Schedule(dayOfMonth="1", month="1")
    private static void changeToSecondSemester(){
        semester = 2;
    }
    
    @PostConstruct
    public static void postConstruct(){
        Date now = new Date();
        if(now.getMonth() > 6){
            changeToFirstSemester();
        } else {
            changeToSecondSemester();
            year = (now.getYear()+1899) + "/" + (now.getYear()+1900);
        }
        
        System.out.println("CURRENT YEAR: " + year + ", CURRENT SEM: " + semester);
    }
    
    public static String getYear() {
        return year;
    }

    public static int getSemester() {
        return semester;
    }
}
