/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package datamodel.rest;

import entities.File;
import entities.Folder;
import java.util.List;

/**
 *
 * @author yaosh
 */
public class RetrieveFilesRsp {
    
    private List<File> files;
    private List<Folder> folders;
    private Folder folder;

    public RetrieveFilesRsp() {
    }
    
    public RetrieveFilesRsp(List<File> files, List<Folder> folders, Folder folder) {
        this.files = files;
        this.folders = folders;
        this.folder = folder;
    }

    public List<File> getFiles() {
        return files;
    }

    public void setFiles(List<File> files) {
        this.files = files;
    }

    public List<Folder> getFolders() {
        return folders;
    }

    public void setFolders(List<Folder> folders) {
        this.folders = folders;
    }

    public Folder getFolder() {
        return folder;
    }

    public void setFolder(Folder folder) {
        this.folder = folder;
    }
    
}
