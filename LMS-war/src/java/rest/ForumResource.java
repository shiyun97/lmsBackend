/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package rest;

import datamodel.rest.CreateThreadReq;
import datamodel.rest.ErrorRsp;
import datamodel.rest.GetForumPostRsp;
import datamodel.rest.GetForumTopicRsp;
import entities.ForumPost;
import entities.ForumTopic;
import entities.Module;
import entities.User;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
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
import util.AccessRightEnum;

/**
 *
 * @author madeline
 */
@Stateless
@Path("Forum")
public class ForumResource {

    @PersistenceContext(unitName = "LMS-warPU")
    private EntityManager em;

    public SimpleDateFormat dateFormatter = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss");

    @Path("viewAllForumTopics")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllForumTopics(@QueryParam("moduleId") Long moduleId) {
        try {
            Module mod = em.find(Module.class, moduleId);
            if (mod == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exists!")).build();
            } else {
                System.out.println("here");
                List<ForumTopic> forumTopics = mod.getForumTopicList();
                GetForumTopicRsp rsp = new GetForumTopicRsp(new ArrayList<>());

                if (forumTopics != null && !forumTopics.isEmpty()) {
                    for (ForumTopic ft : forumTopics) {
                        rsp.getForumTopics().add(new ForumTopic(ft.getForumTopicId(), ft.getTitle(), ft.getDescription(), null, null, null));
                    }
                    return Response.status(Response.Status.OK).entity(rsp).build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("No topic has been created!")).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("viewAllThreadsByTopic")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllThreadsByTopic(@QueryParam("forumTopicId") Long forumTopicId) {
        try {
            ForumTopic ft = em.find(ForumTopic.class, forumTopicId);

            if (ft == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Topic Not Exists!")).build();
            } else {
                List<ForumPost> threads = ft.getThreads();
                GetForumPostRsp rsp = new GetForumPostRsp(new ArrayList<>());

                if (threads != null && !threads.isEmpty()) {
                    for (ForumPost fp : threads) {
                        rsp.getPosts().add(new ForumPost(fp.getTitle(), fp.getMessage(),
                                fp.getCreateTs(), fp.getUpdateTs(), null, fp.getOwner(), null, null));
                    }
                    return Response.status(Response.Status.OK).entity(rsp).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("No thread for this topic").build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("viewAllRepliesToThread")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllRepliesToThread(@QueryParam("forumThreadId") Long forumThreadId) {
        try {
            ForumPost thread = em.find(ForumPost.class, forumThreadId);
            if (thread == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Thread Not Exists!")).build();
            } else {
                List<ForumPost> replies = thread.getReplies();
                GetForumPostRsp rsp = new GetForumPostRsp(new ArrayList<>());

                if (replies != null && !replies.isEmpty()) {
                    for (ForumPost fp : replies) {
                        rsp.getPosts().add(new ForumPost(fp.getForumPostId(), fp.getTitle(), fp.getMessage(),
                                fp.getCreateTs(), fp.getUpdateTs(), null, fp.getOwner(), null, null,
                                fp.getComments(), null));

                    }
                    return Response.status(Response.Status.OK).entity(rsp).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("No reply for this thread").build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("viewAllCommentToThread")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllCommentToThread(@QueryParam("forumThreadId") Long forumThreadId) {
        try {
            ForumPost thread = em.find(ForumPost.class, forumThreadId);
            if (thread == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Thread Not Exists!")).build();
            } else {
                List<ForumPost> comments = thread.getComments();
                GetForumPostRsp rsp = new GetForumPostRsp(new ArrayList<>());

                if (comments != null && !comments.isEmpty()) {
                    for (ForumPost fp : comments) {
                        rsp.getPosts().add(new ForumPost(fp.getForumPostId(), fp.getTitle(), fp.getMessage(),
                                fp.getCreateTs(), fp.getUpdateTs(), null, fp.getOwner(), null, null,
                                null, null));

                    }
                    return Response.status(Response.Status.OK).entity(rsp).build();
                } else {
                    return Response.status(Response.Status.NOT_FOUND).entity("No reply for this thread").build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("createTopic")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTopic(CreateThreadReq createTopic, @QueryParam("moduleId") Long moduleId, @QueryParam("userId") Long userId) {
        try {
            Module mod = em.find(Module.class, moduleId);
            User user = em.find(User.class, userId);
            if (mod == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exist!")).build();
            } else if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User Not Exist!")).build();
            } else {
                if (user.getAccessRight() != AccessRightEnum.Teacher) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You do not have rights to create topic!")).build();
                } else {
                    List<ForumTopic> currentTopics = mod.getForumTopicList();
                    Boolean sameTitle = false;

                    for (ForumTopic ft : currentTopics) {
                        if (ft.getTitle().equalsIgnoreCase(createTopic.getTitle())) {
                            sameTitle = true;
                        }
                    }
                    if (sameTitle) {
                        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Title Already Exist!")).build();
                    } else {
                        ForumTopic newTopic = new ForumTopic();
                        newTopic.setTitle(createTopic.getTitle());
                        newTopic.setDescription(createTopic.getMessage());
                        newTopic.setModule(mod);

                        mod.getForumTopicList().add(newTopic);
                        em.persist(newTopic);
                        em.flush();
                        return Response.status(Response.Status.OK).build();
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("createThread")
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response createThread(CreateThreadReq createThreadReq, @QueryParam("forumTopicId") Long forumTopicId, @QueryParam("userId") Long userId) {
        try {
            ForumTopic topic = em.find(ForumTopic.class, forumTopicId);
            User user = em.find(User.class, userId);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User Not Exist!")).build();
            } else if (topic == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Topic Not Exist!")).build();
            } else {
                List<ForumPost> threads = topic.getThreads();
                Boolean sameMessage = false;

                for (ForumPost fp : threads) {
                    if (fp.getMessage().equalsIgnoreCase(createThreadReq.getMessage()) && fp.getTitle().equalsIgnoreCase(createThreadReq.getTitle())) {
                        sameMessage = true;
                    }
                }
                if (sameMessage == false) {
                    ForumPost newThread = new ForumPost();
                    newThread.setMessage(createThreadReq.getMessage());
                    newThread.setTitle(createThreadReq.getTitle());
                    newThread.setCreateTs(LocalDateTime.now());
                    newThread.setUpdateTs(LocalDateTime.now());
                    newThread.setTopic(topic);
                    newThread.setOwner(user);
                    newThread.setThreadStarter(Boolean.TRUE);

                    topic.getThreads().add(newThread);
                    em.persist(newThread);
                    em.flush();
                    return Response.status(Response.Status.OK).build();
                } else {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Message has already been posted!")).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("createPost")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response createPost(CreateThreadReq createThreadReq, @QueryParam("userId") Long userId, @QueryParam("parentPostId") Long parentPostId) {
        try {
            User user = em.find(User.class, userId);
            ForumPost parentPost = em.find(ForumPost.class, parentPostId);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User Not Exist!")).build();
            } else if (parentPost == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Parent Post Not Exist!")).build();
            } else {
                if (parentPost.getThreadStarter().equals(Boolean.FALSE)) { // reply 
                    if (!parentPost.getReplies().isEmpty()) {
                        for (ForumPost fp : parentPost.getReplies()) {
                            if (fp.getMessage().equalsIgnoreCase(createThreadReq.getMessage())) {
                                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Message has already been posted!")).build();
                            }
                        }
                    }

                    ForumPost newPost = new ForumPost();
                    newPost.setCreateTs(LocalDateTime.now());
                    newPost.setUpdateTs(LocalDateTime.now());
                    newPost.setMessage(createThreadReq.getMessage());
                    newPost.setOwner(user);
                    newPost.setTitle(createThreadReq.getTitle());
                    newPost.setThreadStarter(Boolean.FALSE);
                    newPost.setType(createThreadReq.getType().toLowerCase());

                    parentPost.getComments().add(newPost);
                    em.persist(newPost);
                    em.flush();
                    return Response.status(Response.Status.OK).build();

                } else { //thread
                    if (!parentPost.getReplies().isEmpty()) {
                        for (ForumPost fp : parentPost.getReplies()) {
                            if (fp.getMessage().equalsIgnoreCase(createThreadReq.getMessage())) {
                                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Message has already been posted!")).build();
                            }
                        }
                    } else if (!parentPost.getComments().isEmpty()) {
                        for (ForumPost fp : parentPost.getComments()) {
                            if (fp.getMessage().equalsIgnoreCase(createThreadReq.getMessage())) {
                                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Message has already been posted!")).build();
                            }
                        }
                    }
                    ForumPost newPost = new ForumPost();
                    newPost.setCreateTs(LocalDateTime.now());
                    newPost.setUpdateTs(LocalDateTime.now());
                    newPost.setMessage(createThreadReq.getMessage());
                    newPost.setOwner(user);
                    newPost.setTitle(createThreadReq.getTitle());
                    newPost.setThreadStarter(Boolean.FALSE);
                    newPost.setType(createThreadReq.getType().toLowerCase());

                    if (createThreadReq.getType().equalsIgnoreCase("reply")) {
                        parentPost.getReplies().add(newPost);
                    } else {
                        parentPost.getComments().add(newPost);
                    }
                    em.persist(newPost);
                    em.flush();
                    return Response.status(Response.Status.OK).build();
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("deleteTopic")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTopic(@QueryParam("forumTopicId") Long forumTopicId, @QueryParam("userId") Long userId) {
        try {
            ForumTopic forumTopic = em.find(ForumTopic.class, forumTopicId);
            User user = em.find(User.class, userId);

            if (forumTopic == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Topic Not Exist!")).build();
            } else if (user.getAccessRight() != AccessRightEnum.Teacher) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You do not have the rights to delete topic!")).build();
            } else if (!forumTopic.getThreads().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You cannot delete the topic!")).build();
            } else {
                Module mod = forumTopic.getModule();
                if (mod == null) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exist!")).build();
                } else {
                    mod.getForumTopicList().remove(forumTopic);
                    em.remove(forumTopic);
                    return Response.status(Response.Status.OK).build();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("deletePost")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deletePost(@QueryParam("userId") Long userId, @QueryParam("postId") Long postId) {
        try {
            User user = em.find(User.class, userId);
            ForumPost post = em.find(ForumPost.class, postId);

            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User Not Exist!")).build();
            } else if (post == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Post Not Exist!")).build();
            } else {
                if (post.getThreadStarter().equals(Boolean.TRUE)) {//thread
                    if (post.getReplies().isEmpty() && post.getComments().isEmpty()) {
                        post.getTopic().getThreads().remove(post);
                        em.remove(post);
                        return Response.status(Response.Status.OK).build();
                    } else {
                        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You cannot delete the Thread!")).build();
                    }
                } else {//not threadstarter
                    if (post.getType().equalsIgnoreCase("reply")) {
                        Query q = em.createQuery("Select p FROM ForumPost p WHERE p.replies=:post");
                        q.setParameter("post", post);

                        ForumPost parentPost = (ForumPost) q.getResultList();
                        if (parentPost != null) {
                            parentPost.getReplies().remove(post);
                            em.remove(post);
                            return Response.status(Response.Status.OK).build();
                        } else {
                            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Thread does not exist!")).build();
                        }

                    } else { //comment 
                        Query q = em.createQuery("Select p FROM ForumPost p WHERE p.comments=:post");
                        q.setParameter("post", post);

                        ForumPost thread = (ForumPost) q.getResultList();
                        if (thread != null) {
                            thread.getComments().remove(post);
                            em.remove(post);
                            return Response.status(Response.Status.OK).build();
                        } else {
                            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Thread does not exist!")).build();
                        }
                    }
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("editTopic")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response editTopic(@QueryParam("forumTopicId") Long forumTopicId, @QueryParam("userId") Long userId,CreateThreadReq createThreadReq) {
        try {
            ForumTopic forumTopic = em.find(ForumTopic.class, forumTopicId);
            User user = em.find(User.class, userId);

            if (forumTopic == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Topic Not Exist!")).build();
            } else if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User Not Exist!")).build();
            } else if (user.getAccessRight() != AccessRightEnum.Teacher) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You have no rights to edit the topic!")).build();
            } else if (!forumTopic.getThreads().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You cannot edit this topic!")).build();
            } else {
                forumTopic.setDescription(createThreadReq.getMessage());
                forumTopic.setTitle(createThreadReq.getTitle());
                return Response.status(Response.Status.OK).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }

    @Path("editPost")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response editPost(@QueryParam("userId") Long userId, @QueryParam("forumPostId") Long forumPostId, @QueryParam("message") String message) {
        try {
            User user = em.find(User.class, userId);
            ForumPost forumPost = em.find(ForumPost.class, forumPostId);
            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User Not Exist!")).build();
            } else if (forumPost == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Post Not Exist!")).build();
            } else if (!forumPost.getReplies().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You cannot edit the post!")).build();
            } else if (!user.equals(forumPost.getOwner())) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You are not authorise to edit the post!")).build();
            } else {
                forumPost.setMessage(message);
                forumPost.setUpdateTs(LocalDateTime.now());
                return Response.status(Response.Status.OK).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
}
