/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import unusedEntities.Student;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Vixson
 */
@Entity
@Table(name = "MODULE")
@XmlRootElement
public class Module implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private String code;
    @Column
    private String title;
    @Column
    private String description;
    @Column
    private String feedback;
    @Column
    private Integer semesterOffered;
    @Column
    private Integer creditUnit;
    @Column
    private String grade;
    @Column
    private Integer maxEnrollment;
    @ManyToMany
    private List<User> studentList;
    @ManyToMany
    private List<User> publicUserList;
    @OneToMany(mappedBy = "module")
    private List<Folder> folderList;
    @OneToMany(mappedBy = "module")
    private List<Annoucement> annoucementList;
    @OneToMany(mappedBy = "module")
    private List<ForumPost> forumPostList;
    @OneToMany(mappedBy = "module")
    private List<Quiz> quizList;
    @OneToMany(mappedBy = "module")
    private List<GradeItem> gradeItemList;
    @OneToMany(mappedBy = "module")
    private List<Attendance> attandanceList;
    @OneToMany(mappedBy = "module")
    private List<Consultation> consultationList;
    @OneToMany(mappedBy = "module")
    private List<LessonPlan> lessonPlanList;
    @ManyToOne
    private User owner;
    
    /**
    public Module(String title, String description, String feedback, Integer semesterOffered, Integer creditUnit, String grade){
        this.title = title;
        this.description = description;
        this.feedback = feedback;
        this.semesterOffered = semesterOffered;
        this.creditUnit = creditUnit;
	this.grade = grade;
    }
**/
    public Module(){
        
    }
    
    public String getModuleId() {
        return code;
    }

    public void setModuleId(String code) {
        this.code = code;
    }

    /** * @Override
    public int hashCode() {
        int hash = 0;
        hash += (code != null ? code.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the code fields are not set
        if (!(object instanceof module)) {
            return false;
        }
        module other = (module) object;
        if ((this.code == null && other.code != null) || (this.code != null && !this.code.equals(other.code))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.module[ id=" + code + " ]";
    }**/

}
