/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package entities;

import java.io.Serializable;
import java.sql.Timestamp;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;

/**
 *
 * @author Vixson
 */
@Entity
public class File implements Serializable {

    public File(String name, String type, String location, Folder folder, Module module, Boolean isDelete) {
        this.name = name;
        this.type = type;
        this.location = location;
        this.folder = folder;
        this.module = module;
        this.isDelete = isDelete;
    }
    
    public File(Long fileId, String name, String type, String location, Timestamp createdDt, Boolean isDelete, Folder folder, Module module, User uploader) {
        this.fileId = fileId;
        this.name = name;
        this.type = type;
        this.location = location;
        this.createdDt = createdDt;
        this.isDelete = isDelete;
        this.folder = folder;
        this.module = module;
        this.uploader = uploader;
    }

    public File() {
    }

    private static final long serialVersionUID = 1L;
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long fileId;
    @Column
    private String name;
    @Column
    private String type;
    @Column
    private String location;
    @Column
    private Timestamp createdDt;
    @Column
    private Boolean isDelete;
    @ManyToOne
    private Folder folder;
    @ManyToOne
    private Module module; // for multimedia files
    @ManyToOne
    private User uploader;

    public Long getFileId() {
        return fileId;
    }

    public void setFileId(Long fileId) {
        this.fileId = fileId;
    }

    @Override
    public int hashCode() {
        int hash = 0;
        hash += (fileId != null ? fileId.hashCode() : 0);
        return hash;
    }

    @Override
    public boolean equals(Object object) {
        // TODO: Warning - this method won't work in the case the fileId fields are not set
        if (!(object instanceof File)) {
            return false;
        }
        File other = (File) object;
        if ((this.fileId == null && other.fileId != null) || (this.fileId != null && !this.fileId.equals(other.fileId))) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return "entities.File[ Id=" + fileId + " ]";
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getLocation() {
        return location;
    }

    public void setLocation(String location) {
        this.location = location;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Boolean getIsDelete() {
        return isDelete;
    }

    public void setIsDelete(Boolean isDelete) {
        this.isDelete = isDelete;
    }

    public Timestamp getCreatedDt() {
        return createdDt;
    }

    public void setCreatedDt(Timestamp createdDt) {
        this.createdDt = createdDt;
    }

    public User getUploader() {
        return uploader;
    }

    public void setUploader(User uploader) {
        this.uploader = uploader;
    }
    
}
