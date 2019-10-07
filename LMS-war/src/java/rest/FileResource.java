/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.ErrorRsp;
import datamodel.rest.RetrieveFilesRsp;
import datamodel.rest.CreateFolderRqst;
import entities.Folder;
import entities.Module;
import entities.User;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.sql.Timestamp;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.servlet.ServletContext;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.UriInfo;
import org.glassfish.jersey.media.multipart.BodyPart;
import org.glassfish.jersey.media.multipart.ContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataBodyPart;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;
import util.AccessRightEnum;

/**
 *
 * @author yaosh
 */
@Path("file")
public class FileResource {
    
    @Context
    private UriInfo context;
    @Context
    private ServletContext servletContext;
    
    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;
    
    public FileResource() 
    {
    }
    
    private void CreateNewFile(entities.File newFile, Long folderId) {
        if (folderId != null) {
            Folder folder = em.find(Folder.class, folderId);
            if (folder != null) {
                newFile.setFolder(folder);
                folder.getFile().add(newFile);
            }
        }

        em.persist(newFile);
        em.flush();
        return;
    }
    
    @POST
    @Path("upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response upload(@FormDataParam("file") InputStream uploadedFileInputStream,
                            @FormDataParam("file") FormDataContentDisposition uploadedFileDetails, @QueryParam("moduleId") Long moduleId, 
            @QueryParam("folderId") Long folderId, @QueryParam("userId") Long userId, @QueryParam("type") String type)
    {
        User user = em.find(User.class, userId);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User does not exist!")).build();
        }
        Module module = em.find(Module.class, moduleId);
        if (module == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module does not exist!")).build();
        }
        
        if (type.equals("document") && folderId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Require folderId!")).build();
        }
        
        try
        {
            System.err.println("********** FileResource.upload()");
            String outputFilePath = servletContext.getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + uploadedFileDetails.getFileName();
            File file = new File(outputFilePath);        
            FileOutputStream fileOutputStream = new FileOutputStream(file);
            
            int a;
            int BUFFER_SIZE = 8192;
            byte[] buffer = new byte[BUFFER_SIZE];

            while (true)
            {
                a = uploadedFileInputStream.read(buffer);

                if (a < 0)
                {
                    break;
                }

                fileOutputStream.write(buffer, 0, a);
                fileOutputStream.flush();
            }

            fileOutputStream.close();
            uploadedFileInputStream.close();
            
            // create new file entity
                entities.File newFile = new entities.File();
                newFile.setName(uploadedFileDetails.getFileName());
                newFile.setType(type);
                newFile.setLocation(outputFilePath);
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                newFile.setCreatedDt(timestamp);
                newFile.setIsDelete(false);
                newFile.setUploader(user);
                //CreateNewFile(newFile, folderId);
                
                if (folderId != null) {
                    Folder folder = em.find(Folder.class, folderId);
                    if (folder == null || folder.getIsDelete() == true) {
                        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Folder does not exist!")).build();
                    }
                    if (folder != null && folder.getIsDelete() == false) {
                        newFile.setFolder(folder);
                        folder.getFile().add(newFile);
                    }
                }
                if (type.equals("multimedia")) {
                    newFile.setModule(module);
                    module.getMultimediaList().add(newFile);
                }

                em.persist(newFile);
                em.flush();
            
            return Response.status(Response.Status.OK).entity(outputFilePath).build();
        }
        catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp("file not found error")).build();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp("file IO exception error")).build();
        }
    }

    @POST
    @Path("uploadMultiple")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response uploadMultiple(@FormDataParam("file") FormDataBodyPart body, @QueryParam("moduleId") Long moduleId, 
            @QueryParam("folderId") Long folderId, @QueryParam("userId") Long userId, @QueryParam("type") String type) {
        User user = em.find(User.class, userId);
        if (user == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User does not exist!")).build();
        }
        Module module = em.find(Module.class, moduleId);
        if (module == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module does not exist!")).build();
        }
        
        if (type.equals("document") && folderId == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Require folderId!")).build();
        }
        
        try {
            System.out.println(body);
            for (BodyPart part : body.getParent().getBodyParts()) {
                InputStream inputStream = part.getEntityAs(InputStream.class);
                ContentDisposition meta = part.getContentDisposition();
                Timestamp timestamp = new Timestamp(System.currentTimeMillis());
                String outputFilePath = servletContext.getInitParameter("alternatedocroot_1") + System.getProperty("file.separator") + timestamp.getTime() + "_" + meta.getFileName();
                // do upload
                java.io.File file = new java.io.File(outputFilePath);
                FileOutputStream fileOutputStream = new FileOutputStream(file);

                int a;
                int BUFFER_SIZE = 8192;
                byte[] buffer = new byte[BUFFER_SIZE];

                while (true) {
                    a = inputStream.read(buffer);

                    if (a < 0) {
                        break;
                    }

                    fileOutputStream.write(buffer, 0, a);
                    fileOutputStream.flush();
                }
                fileOutputStream.close();
                inputStream.close();
                
                // create new file entity
                entities.File newFile = new entities.File();
                newFile.setName(meta.getFileName());
                newFile.setType(type);
                newFile.setLocation(outputFilePath);
                newFile.setCreatedDt(timestamp);
                newFile.setIsDelete(false);
                newFile.setUploader(user);
                //CreateNewFile(newFile, folderId);
                
                if (folderId != null) {
                    Folder folder = em.find(Folder.class, folderId);
                    if (folder == null || folder.getIsDelete() == true) {
                        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Folder does not exist!")).build();
                    }
                    if (folder != null && folder.getIsDelete() == false) {
                        newFile.setFolder(folder);
                        folder.getFile().add(newFile);
                    }
                }
                if (type.equals("multimedia")) {
                    newFile.setModule(module);
                }

                em.persist(newFile);
                em.flush();

            }
            return Response.status(Response.Status.OK).entity("ok").build();
        }
        catch(FileNotFoundException ex)
        {
            ex.printStackTrace();
            
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp("File not found error")).build();
        }
        catch(IOException ex)
        {
            ex.printStackTrace();
        
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp("File IO exception error")).build();
        }
    }
    
    @Path("retrieveFilesByFolderIdForModule")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllFilesByFolderIdForModule(@QueryParam("moduleId") Long moduleId, @QueryParam("folderId") Long folderId){
        Module module = em.find(Module.class, moduleId);
        if (module == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module does not exist!")).build();
        }
        // calling from subfolder
        if (folderId != null) {
            Folder folder = em.find(Folder.class, folderId);
            if (folder == null || folder.getIsDelete() == true) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Folder does not exist!")).build();
            }
            Folder folderInfo = new Folder();
            folderInfo.setSubmission(folder.getSubmission());
            folderInfo.setSubmissionOpenTs(folder.getSubmissionOpenTs());
            folderInfo.setSubmissionCloseTs(folder.getSubmissionCloseTs());
            try {
                List<entities.File> files = folder.getFile();
                List<entities.File> filesRsp = new ArrayList<>();
                if (files != null && !files.isEmpty()) {
                    for (entities.File f : files) {
                        if (f.getIsDelete() == false) {
                            User u = new User();
                            u.setUserId(f.getUploader().getUserId());
                            u.setFirstName(f.getUploader().getFirstName());
                            u.setLastName(f.getUploader().getLastName());
                            entities.File temp = new entities.File(f.getFileId(), f.getName(), f.getType(), f.getLocation(), f.getCreatedDt(), f.getIsDelete(), null, null, u);
                            filesRsp.add(temp);
                        }
                    }
                }
                //Query query = em.createQuery("select f from Folder f");
                List<Folder> allFolderList = module.getFolderList();
                List<Folder> folderRsp = new ArrayList<>();
                for (Folder f : allFolderList) {
                    if (f.getParentFolder() != null && f.getParentFolder().getFolderId().equals(folderId) && f.getIsDelete() == false) {
                        Folder temp = new Folder(f.getFolderId(), f.getName(), f.getSize(), f.getSubmission(), f.getSubmissionOpenTs(), f.getSubmissionCloseTs(), null, null, null, f.getIsDelete(), null);
                        folderRsp.add(temp);
                    }
                }

                return Response.status(Response.Status.OK).entity(new RetrieveFilesRsp(filesRsp, folderRsp, folderInfo)).build();
            } catch (Exception e) {
                e.printStackTrace();
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
            }
        }

        // call from root folder
        // retrieve folders
        try {
            List<Folder> allFolderList = module.getFolderList();
            List<Folder> folderRsp = new ArrayList<>();
            for (Folder f : allFolderList) {
                if (f.getParentFolder() == null && f.getIsDelete() == false) {
                    Folder temp = new Folder(f.getFolderId(), f.getName(), f.getSize(), f.getSubmission(), f.getSubmissionOpenTs(), f.getSubmissionCloseTs(), null, null, null, f.getIsDelete(), null);
                    folderRsp.add(temp);
                }
            }
            return Response.status(Response.Status.OK).entity(new RetrieveFilesRsp(new ArrayList<>(), folderRsp, new Folder())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
        
    }
    
    @Path("retrieveAllMultimediaForModule")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllMultimediaForModule(@QueryParam("moduleId") Long moduleId){
        Module module = em.find(Module.class, moduleId);
        if (module == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module does not exist!")).build();
        }
        try {
            List<entities.File> multimediaList = module.getMultimediaList();
            List<entities.File> multimediaRsp = new ArrayList<>();
            for (entities.File f : multimediaList) {
                if (f.getIsDelete() == false) {
                    User u = new User();
                    u.setUserId(f.getUploader().getUserId());
                    u.setFirstName(f.getUploader().getFirstName());
                    u.setLastName(f.getUploader().getLastName());
                    entities.File temp = new entities.File(f.getFileId(), f.getName(), f.getType(), f.getLocation(), f.getCreatedDt(), f.getIsDelete(), null, null, u);
                    multimediaRsp.add(temp);
                }
            }
            return Response.status(Response.Status.OK).entity(new RetrieveFilesRsp(multimediaRsp, new ArrayList<>(), new Folder())).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @Path("createFolder")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response createFolder(CreateFolderRqst createFolderRqst, @QueryParam("moduleId") Long moduleId, @QueryParam("folderId") Long folderId){
        Module module = em.find(Module.class, moduleId);
        if (module == null) {
            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module does not exist!")).build();
        }

        try {
            if (folderId == null) {
                // in the root folder
                Folder folder = new Folder(null, createFolderRqst.getName(), null, createFolderRqst.getSubmission(), createFolderRqst.getSubmissionOpenTs(), createFolderRqst.getSubmissionCloseTs(), createFolderRqst.getAccessRight(), null, module, false, null);
                if (createFolderRqst.getSubmission() == false) {
                    folder.setSubmissionCloseTs(null);
                    folder.setSubmissionOpenTs(null);
                }
                module.getFolderList().add(folder);

                em.persist(folder);
                em.flush();
                
                return Response.status(Response.Status.OK).entity("ok").build();
            }
            else {
                // create sub folder
                Folder parentFolder = em.find(Folder.class, folderId);
                if (parentFolder == null || parentFolder.getIsDelete() == true) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Folder does not exist!")).build();
                }
                Folder folder = new Folder(null, createFolderRqst.getName(), null, createFolderRqst.getSubmission(), createFolderRqst.getSubmissionOpenTs(), createFolderRqst.getSubmissionCloseTs(), createFolderRqst.getAccessRight(), null, module, false, parentFolder);
                module.getFolderList().add(folder);

                em.persist(folder);
                em.flush();
                
                return Response.status(Response.Status.OK).entity("ok").build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @Path(value = "deleteFolder")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteFolder(@QueryParam("folderId") Long folderId) {
        try {
            Folder folder = em.find(Folder.class, folderId);
            if (folder == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("Folder does not exist!")).build();
            }
            List<entities.File> files = folder.getFile();
            for (entities.File f : files) {
                f.setIsDelete(true);
                em.merge(f);
            }
            folder.setIsDelete(true);
            
            em.flush();

            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @Path(value = "deleteFile")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    @Transactional
    public Response deleteFile(@QueryParam("fileId") Long fileId) {
        try {
            entities.File file = em.find(entities.File.class, fileId);
            if (file == null) {
                return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("File does not exist!")).build();
            }
            em.detach(file);
            
            File actualFile = new File(file.getLocation());
            actualFile.delete();
            file.setIsDelete(true);
            em.merge(file);
            em.flush();

            return Response.status(Response.Status.OK).build();
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
    
    @Path(value = "downloadFile")
    @GET
    @Produces(MediaType.APPLICATION_OCTET_STREAM)
    public Response downloadFile(@QueryParam("fileId") Long fileId) {
        entities.File fileToRetrieve = em.find(entities.File.class, fileId);
        if (fileToRetrieve == null) {
            return Response.status(Response.Status.NOT_FOUND).entity(new ErrorRsp("File does not exist!")).build();
        }
        try {
            String path = fileToRetrieve.getLocation();
            File file = new File(path);
            ResponseBuilder response = Response.ok((Object) file);
            response.header("Content-Disposition", "attachment;filename=" + file);
            return response.build();
        }
        catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
}
