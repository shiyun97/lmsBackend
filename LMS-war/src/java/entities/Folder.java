/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;

/**
 *
 * @author Vixson
 */
@Entity
public class Folder implements Serializable {

    public Folder(String name, Double size, Boolean submission, Timestamp submissionOpenTs, Timestamp submissionCloseTs, Enum accessRight, List<File> file, Module module) {
        this.name = name;
        this.size = size;
        this.submission = submission;
        this.submissionOpenTs = submissionOpenTs;
        this.submissionCloseTs = submissionCloseTs;
        this.accessRight = accessRight;
        this.file = file;
        this.module = module;
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long folderId;
    @Column
    private String name;
    @Column 
    private Double size;
    @Column
    private Boolean submission;
    @Column
    private Timestamp submissionOpenTs;
    @Column
    private Timestamp submissionCloseTs;
    @Column
    private Enum accessRight;
    @OneToMany(mappedBy = "folder")
    private List<File> file;
    @ManyToOne
    private Module module;
  

    public Long getFolderId() {
        return folderId;
    }

    public void setFolderId(Long folderId) {
        this.folderId = folderId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (folderId != null ? folderId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the folderId fields are not set
        if (!(object instanceof Folder)) {
            return false;
        }
        Folder other = (Folder) object;
        if ((this.folderId == null && other.folderId != null) || (this.folderId != null && !this.folderId.equals(other.folderId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.Folder[ Id=" + folderId + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Double getSize() {
        return size;
    }

    public void setSize(Double size) {
        this.size = size;
    }

    public Boolean getSubmission() {
        return submission;
    }

    public void setSubmission(Boolean submission) {
        this.submission = submission;
    }

    public Timestamp getSubmissionOpenTs() {
        return submissionOpenTs;
    }

    public void setSubmissionOpenTs(Timestamp submissionOpenTs) {
        this.submissionOpenTs = submissionOpenTs;
    }

    public Timestamp getSubmissionCloseTs() {
        return submissionCloseTs;
    }

    public void setSubmissionCloseTs(Timestamp submissionCloseTs) {
        this.submissionCloseTs = submissionCloseTs;
    }

    public Enum getAccessRight() {
        return accessRight;
    }

    public void setAccessRight(Enum accessRight) {
        this.accessRight = accessRight;
    }

    public List<File> getFile() {
        return file;
    }

    public void setFile(List<File> file) {
        this.file = file;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }
    
}
