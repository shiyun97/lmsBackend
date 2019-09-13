/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package unusedEntities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Column;
import javax.persistence.Table;
import javax.xml.bind.annotation.XmlRootElement;
/**
 *
 * @author Vixson
 */
@Entity
@Table(name = "TEACHER")
@XmlRootElement
public class Teacher implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long teacherId;
    @Column
    private String name;
    @Column
    private String gender;
    @Column
    private String title;
    @Column
    private String email;
    @Column
    private String password;
    @Column
    private Integer phone;
    @Column
    private String faculty;
    
    
  /**  
    public teacher(String name, String gender, String title, String email, String password, Integer phone, String faculty){
        this.name = name;
        this.gender = gender;
        this.title = title;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.faculty;
    }
**/
    public Teacher(){
        
    }
    
    public Long getTeacherId() {
        return teacherId;
    }

    public void setTeacherId(Long teacherId) {
        this.teacherId = teacherId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (teacherId != null ? teacherId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Teacher)) {
            return false;
        }
        Teacher other = (Teacher) object;
        if ((this.teacherId == null && other.teacherId != null) || (this.teacherId != null && !this.teacherId.equals(other.teacherId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entity.teacher[ id=" + teacherId + " ]";
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

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
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

    public String getFaculty() {
        return faculty;
    }

    public void setFaculty(String faculty) {
        this.faculty = faculty;
    }
    
}
