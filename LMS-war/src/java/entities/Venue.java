/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.OneToMany;

/**
 *
 * @author Vixson
 */
@Entity
public class Venue implements Serializable {

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long venueId;
    @Column
    private String name;
//    @OneToMany(mappedBy = "examVenue", orphanRemoval = true)
//    private List<Module> moduleList;
//    @OneToMany(mappedBy = "venue", orphanRemoval = true)
//    private List<Tutorial> tutorialList;

    public Venue() {
    }

    public Venue(Long venueId, String name) {
        this.venueId = venueId;
        this.name = name;
    }

    
    
    public Long getId() {
        return venueId;
    }

    public void setId(Long id) {
        this.venueId = id;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (venueId != null ? venueId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the id fields are not set
        if (!(object instanceof Venue)) {
            return false;
        }
        Venue other = (Venue) object;
        if ((this.venueId == null && other.venueId != null) || (this.venueId != null && !this.venueId.equals(other.venueId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Venue[ id=" + venueId + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

//    public List<Module> getModuleList() {
//        return moduleList;
//    }
//
//    public void setModuleList(List<Module> moduleList) {
//        this.moduleList = moduleList;
//    }
//
//    public List<Tutorial> getTutorialList() {
//        return tutorialList;
//    }
//
//    public void setTutorialList(List<Tutorial> tutorialList) {
//        this.tutorialList = tutorialList;
//    }
//    
}
