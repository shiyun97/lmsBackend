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
import entities.Coursepack;
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

    @Path("viewAllForumTopics")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllForumTopics(@QueryParam("moduleId") Long moduleId) {
        try {
            Module mod = em.find(Module.class, moduleId);
            if (mod == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exists!")).build();
            } else {
                List<ForumTopic> forumTopics = mod.getForumTopicList();
                User owner = new User();
                owner.setUserId(mod.getAssignedTeacher().getUserId());
                GetForumTopicRsp rsp = new GetForumTopicRsp(new ArrayList<>(), owner);

                if (forumTopics != null && !forumTopics.isEmpty()) {
                    for (ForumTopic ft : forumTopics) {
                        List<ForumPost> threads = ft.getThreads();
                        List<ForumPost> rspThreads = new ArrayList<>();
                        for (ForumPost fp : threads) {
                            ForumPost temp = new ForumPost();
                            temp.setForumPostId(fp.getForumPostId());
                            rspThreads.add(temp);
                        }
                        rsp.getForumTopics().add(new ForumTopic(ft.getForumTopicId(), ft.getTitle(), ft.getDescription(), rspThreads, null, null));
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

    @Path("viewAllForumTopicsForCoursepack")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveAllForumTopicsForCoursepack(@QueryParam("coursepackId") Long coursepackId) {
        try {
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);

            if (coursepack == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Coursepack Not Exists!")).build();
            } else {
                List<ForumTopic> forumTopics = coursepack.getForumTopicList();
                User owner = new User();
                //owner.setUserId(coursepack.getAssignedTeacher().getUserId());
                GetForumTopicRsp rsp = new GetForumTopicRsp(new ArrayList<>(), owner);

                if (forumTopics != null && !forumTopics.isEmpty()) {
                    for (ForumTopic ft : forumTopics) {
                        List<ForumPost> threads = ft.getThreads();
                        List<ForumPost> rspThreads = new ArrayList<>();
                        for (ForumPost fp : threads) {
                            ForumPost temp = new ForumPost();
                            temp.setForumPostId(fp.getForumPostId());
                            rspThreads.add(temp);
                        }
                        rsp.getForumTopics().add(new ForumTopic(ft.getForumTopicId(), ft.getTitle(), ft.getDescription(), rspThreads, null, null));
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
                        User owner = new User();
                        owner.setUserId(fp.getOwner().getUserId());
                        owner.setFirstName(fp.getOwner().getFirstName());
                        owner.setLastName(fp.getOwner().getLastName());
                        List<ForumPost> replies = fp.getReplies();
                        List<ForumPost> repliesRsp = new ArrayList<>();
                        for (ForumPost reply : replies) {
                            ForumPost temp = new ForumPost();
                            temp.setForumPostId(reply.getForumPostId());
                            repliesRsp.add(temp);
                        }
                        rsp.getPosts().add(new ForumPost(fp.getForumPostId(), fp.getTitle(), fp.getMessage(),
                                fp.getCreateTs(), fp.getUpdateTs(), fp.getThreadStarter(), owner, null, repliesRsp, null, null));
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

    @Path("viewThreadDetails")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response retrieveThreadDetails(@QueryParam("forumThreadId") Long forumThreadId) {
        try {
            ForumPost thread = em.find(ForumPost.class, forumThreadId);
            if (thread == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Thread does not exist!")).build();
            } else {
                User owner = new User();
                owner.setFirstName(thread.getOwner().getFirstName());
                owner.setLastName(thread.getOwner().getLastName());
                owner.setUserId(thread.getOwner().getUserId());
                ForumPost rsp = new ForumPost(thread.getTitle(), thread.getMessage(), thread.getCreateTs(), thread.getUpdateTs(), thread.getThreadStarter(),
                        owner, null, thread.getType());
                rsp.setForumPostId(thread.getForumPostId());

                List<ForumPost> repliesRsp = new ArrayList<>();

                for (ForumPost reply : thread.getReplies()) {
                    User replyOwner = new User();
                    replyOwner.setFirstName(reply.getOwner().getFirstName());
                    replyOwner.setLastName(reply.getOwner().getLastName());
                    replyOwner.setUserId(reply.getOwner().getUserId());
                    ForumPost tempReply = new ForumPost(reply.getTitle(), reply.getMessage(), reply.getCreateTs(), reply.getUpdateTs(), reply.getThreadStarter(), replyOwner, null, reply.getType());
                    tempReply.setForumPostId(reply.getForumPostId());
                    List<ForumPost> tempReplyComments = new ArrayList<>();

                    for (ForumPost comment : reply.getComments()) {
                        User commentOwner = new User();
                        commentOwner.setFirstName(comment.getOwner().getFirstName());
                        commentOwner.setLastName(comment.getOwner().getLastName());
                        commentOwner.setUserId(comment.getOwner().getUserId());
                        ForumPost tempComment = new ForumPost(comment.getTitle(), comment.getMessage(), comment.getCreateTs(), comment.getUpdateTs(), comment.getThreadStarter(), commentOwner, null, comment.getType());
                        tempComment.setForumPostId(comment.getForumPostId());
                        tempReplyComments.add(tempComment);
                    }
                    tempReply.setComments(tempReplyComments);
                    repliesRsp.add(tempReply);
                }
                rsp.setReplies(repliesRsp);

                List<ForumPost> tempComments = new ArrayList<>();
                for (ForumPost comment : thread.getComments()) {
                    User commentOwner = new User();
                    commentOwner.setFirstName(comment.getOwner().getFirstName());
                    commentOwner.setLastName(comment.getOwner().getLastName());
                    commentOwner.setUserId(comment.getOwner().getUserId());
                    ForumPost tempComment = new ForumPost(comment.getTitle(), comment.getMessage(), comment.getCreateTs(), comment.getUpdateTs(), comment.getThreadStarter(), commentOwner, null, comment.getType());
                    tempComment.setForumPostId(comment.getForumPostId());
                    tempComments.add(tempComment);
                }
                rsp.setComments(tempComments);
                return Response.status(Response.Status.OK).entity(rsp).build();

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

    @Path("createTopicForCoursepack")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    public Response createTopicForCoursepack(CreateThreadReq createTopic, @QueryParam("coursepackId") Long coursepackId, @QueryParam("userId") Long userId) {
        try {
            Coursepack coursepack = em.find(Coursepack.class, coursepackId);
            User user = em.find(User.class, userId);

            if (coursepack == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exist!")).build();
            } else if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User Not Exist!")).build();
            } else {
                if (user.getAccessRight() != AccessRightEnum.Teacher) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You do not have rights to create topic!")).build();
                } else {
                    List<ForumTopic> currentTopics = coursepack.getForumTopicList();
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
                        newTopic.setCoursepack(coursepack);

                        coursepack.getForumTopicList().add(newTopic);
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
                if (createThreadReq.getType().equalsIgnoreCase("comment")) { // creating a comment 
                    /*if (!parentPost.getReplies().isEmpty()) {
                        for (ForumPost fp : parentPost.getReplies()) {
                            if (fp.getMessage().equalsIgnoreCase(createThreadReq.getMessage())) {
                                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Message has already been posted!")).build();
                            }
                        }
                    }*/

                    ForumPost newPost = new ForumPost();
                    newPost.setCreateTs(LocalDateTime.now());
                    newPost.setUpdateTs(LocalDateTime.now());
                    newPost.setMessage(createThreadReq.getMessage());
                    newPost.setOwner(user);
                    newPost.setTitle(createThreadReq.getTitle());
                    newPost.setThreadStarter(Boolean.FALSE);
                    newPost.setType(createThreadReq.getType().toLowerCase());
                    newPost.setParentOfComments(parentPost);

                    parentPost.getComments().add(newPost);
                    em.persist(newPost);
                    em.flush();
                    return Response.status(Response.Status.OK).build();

                } else { // creating a reply 
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
                    newPost.setComments(new ArrayList<>());
                    newPost.setReplies(new ArrayList<>());
                    newPost.setParentOfReply(parentPost);
                    parentPost.getReplies().add(newPost);

                    /*if (createThreadReq.getType().equalsIgnoreCase("reply")) {
                        parentPost.getReplies().add(newPost);
                    } else {
                        parentPost.getComments().add(newPost);
                    }*/
                    em.merge(parentPost);
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

    @Path("deleteTopicForCoursepack")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteTopicForCoursepack(@QueryParam("forumTopicId") Long forumTopicId, @QueryParam("userId") Long userId) {
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
                Coursepack coursepack = forumTopic.getCoursepack();
                if (coursepack == null) {
                    return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Module Not Exist!")).build();
                } else {
                    coursepack.getForumTopicList().remove(forumTopic);
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
                        ForumTopic topic = post.getTopic();
                        topic.getThreads().remove(post);
                        em.remove(post);
                        em.flush();
                        return Response.status(Response.Status.OK).build();
                    } else {
                        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You cannot delete the Thread!")).build();
                    }
                } else {//not threadstarter
                    if (post.getType().equalsIgnoreCase("reply")) { //reply
                        if (post.getComments().isEmpty()) {
                            ForumPost parentPost = post.getParentOfReply();
                            parentPost.getReplies().remove(post);
                            em.remove(post);
                            em.flush();
                            return Response.status(Response.Status.OK).build();
                        } else {
                            return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You cannot delete the Post!")).build();
                        }
                    } else { // comment
                        ForumPost parentPost = post.getParentOfComments();
                        parentPost.getComments().remove(post);
                        em.remove(post);
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

    @Path("editTopic")
    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response editTopic(@QueryParam("forumTopicId") Long forumTopicId, @QueryParam("userId") Long userId, CreateThreadReq createThreadReq) {
        try {
            ForumTopic forumTopic = em.find(ForumTopic.class, forumTopicId);
            User user = em.find(User.class, userId);

            if (forumTopic == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Topic Not Exist!")).build();
            } else if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User Not Exist!")).build();
            } else if (user.getAccessRight() != AccessRightEnum.Teacher) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You have no rights to edit the topic!")).build();
            } /*else if (!forumTopic.getThreads().isEmpty()) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You cannot edit this topic!")).build();
            }*/ else {
                forumTopic.setDescription(createThreadReq.getMessage());
                forumTopic.setTitle(createThreadReq.getTitle());
                em.merge(forumTopic);
                em.flush();
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
    public Response editPost(CreateThreadReq createThreadReq, @QueryParam("userId") Long userId, @QueryParam("forumPostId") Long forumPostId) {
        try {
            User user = em.find(User.class, userId);
            ForumPost forumPost = em.find(ForumPost.class, forumPostId);

            if (user == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("User Not Exist!")).build();
            } else if (forumPost == null) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("Post Not Exist!")).build();
            } /*else if(!forumPost.getComments().isEmpty() || !forumPost.getReplies().isEmpty()){
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You cannot edit this post!")).build();
            }*/else if (!user.equals(forumPost.getOwner())) {
                return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorRsp("You do not have rights to edit the post!")).build();
            } else {
                forumPost.setMessage(createThreadReq.getMessage());
                forumPost.setUpdateTs(LocalDateTime.now());
                
                em.merge(forumPost);
                em.flush();
                return Response.status(Response.Status.OK).build();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(new ErrorRsp(e.getMessage())).build();
        }
    }
}
