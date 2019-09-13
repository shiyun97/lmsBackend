/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unusedEntities;

import entities.Attendance;
import entities.Module;
import java.io.Serializable;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Vixson
 */
@Entity
@Table(name = "STUDENT")
@XmlRootElement
public class Student implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long studentId;
    @Column
    private String name;
    @Column
    private String gender;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Integer phone;
    @Column
    private Float finalGrade;
    @Column
    private String major;
    @Column
    private String minor;
    @Column
    private String matricNo;
    @ManyToMany//(mappedBy = "student")
    private List<Module> module;
    @ManyToOne
    private Attendance attendance;
    
    /**
    public student(String name, String gender, String email, String password, Integer phone, Float finalGrade, String major, String minor, String matricNo){
        this.name = name;
        this.gender = gender;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.finalGrade = finalGrade;
        this.major = major;
        this.minor = minor;
        this.matricNo
    } 
**/
    public Student(){
        
    }
    
    public Long getStudentId() {
        return studentId;
    }

    public void setStudentId(Long studentId) {
        this.studentId = studentId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (studentId != null ? studentId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Student)) {
            return false;
        }
        Student other = (Student) object;
        if ((this.studentId == null && other.studentId != null) || (this.studentId != null && !this.studentId.equals(other.studentId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.student[ id=" + studentId + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getPhone() {
        return phone;
    }

    public void setPhone(Integer phone) {
        this.phone = phone;
    }

    public Float getFinalGrade() {
        return finalGrade;
    }

    public void setFinalGrade(Float finalGrade) {
        this.finalGrade = finalGrade;
    }

    public String getMajor() {
        return major;
    }

    public void setMajor(String major) {
        this.major = major;
    }

    public String getMinor() {
        return minor;
    }

    public void setMinor(String minor) {
        this.minor = minor;
    }

    public String getMatricNo() {
        return matricNo;
    }

    public void setMatricNo(String matricNo) {
        this.matricNo = matricNo;
    }

    public List<Module> getModule() {
        return module;
    }

    public void setModule(List<Module> module) {
        this.module = module;
    }
    
}
