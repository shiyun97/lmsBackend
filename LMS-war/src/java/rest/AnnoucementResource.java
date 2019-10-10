/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.CreateAnnoucement;
import datamodel.rest.ErrorRsp;
import datamodel.rest.GetAnnoucementRsp;
import entities.Annoucement;
import java.util.ArrayList;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author Vixson
 */
@Stateless
@Path("Annoucement")
public class AnnoucementResource {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;

    public AnnoucementResource() {
    }

    @POST
    @Path(value = "createAnnoucement")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createAnnoucement(CreateAnnoucement createAnnoucement) {
        try {
            Annoucement annoucement = new Annoucement();
            annoucement.setTitle(createAnnoucement.getTitle());
            annoucement.setDescription(createAnnoucement.getDescription());
            annoucement.setCreateTs(createAnnoucement.getCreateTs());
            //annoucement.setUpdateTs(createAnnoucement.getUpdateTs());
            annoucement.setSystemWide(createAnnoucement.getSystemWide());
            annoucement.setModule(createAnnoucement.getModule());
            annoucement.setOwner(createAnnoucement.getOwner());
            em.persist(annoucement);
            em.flush();
            Annoucement annoucementCopy = new Annoucement(annoucement.getAnnoucementId(), annoucement.getTitle(),
                    annoucement.getDescription(), annoucement.getCreateTs(), null, annoucement.getSystemWide(), null, null);
            return Response.status(Response.Status.OK).entity(annoucementCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAllAnnoucement")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAnnoucement() {
        try {
            Query query = em.createQuery("select a from Annoucement a");
            List<Annoucement> annoucementList = query.getResultList();
            if (annoucementList == null || annoucementList.isEmpty()) {
                return Response.status(Response.Status.NOT_FOUND).entity("No annoucement found").build();
            }
            GetAnnoucementRsp rsp = new GetAnnoucementRsp(new ArrayList<>());
            for (Annoucement a : annoucementList) {
                rsp.getAnnoucementList().add(
                        new Annoucement(a.getAnnoucementId(), a.getTitle(),
                                a.getDescription(), a.getCreateTs(), a.getUpdateTs(),
                                a.getSystemWide(), null, null));
            }
            return Response.status(Response.Status.OK).entity(rsp).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @GET
    @Path("getAnnoucement")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAnnoucement(@QueryParam("annoucementId") Long annoucementId) {
        try {
            Annoucement annoucement = em.find(Annoucement.class, annoucementId);
            if (annoucement == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No annoucement found").build();
            }
            Annoucement annoucementCopy = new Annoucement(annoucement.getAnnoucementId(), annoucement.getTitle(),
                    annoucement.getDescription(), annoucement.getCreateTs(), annoucement.getUpdateTs(),
                    annoucement.getSystemWide(), null, null);

            return Response.status(Response.Status.OK).entity(annoucementCopy).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }

    @Path(value = "deleteAnnoucement")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteAnnoucement(@QueryParam("annoucementId") Long annoucementId) {
        try {
            Annoucement annoucement = em.find(Annoucement.class, annoucementId);
            if (annoucement == null) {
                return Response.status(Response.Status.NOT_FOUND).entity("No annoucement found").build();
            }
            annoucement.getModule().getAnnoucementList().remove(annoucement);
            annoucement.setOwner(null);
            em.remove(annoucement);
            return Response.status(Response.Status.OK).build();
        } catch (Exception ex) {
            ex.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(ex.getMessage())).build();
        }
    }
}
