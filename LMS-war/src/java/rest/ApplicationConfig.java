package rest;

import java.util.Set;
import javax.ws.rs.core.Application;
import org.glassfish.jersey.media.multipart.MultiPartFeature;



@javax.ws.rs.ApplicationPath("webresources")

public class ApplicationConfig extends Application
{
    @Override
    public Set<Class<?>> getClasses()
    {
        Set<Class<?>> resources = new java.util.HashSet<>();
        addRestResourceClasses(resources);
        resources.add(MultiPartFeature.class);
        return resources;
    }

    private void addRestResourceClasses(Set<Class<?>> resources) {
        resources.add(rest.AnnoucementResource.class);
        resources.add(rest.AssessmentResource.class);
        resources.add(rest.AttendanceResource.class);
        resources.add(rest.ConsultationResource.class);
//        resources.add(rest.CorsFilter.class);
        resources.add(rest.CorsFilter.class);
        resources.add(rest.CoursepackEnrollmentResource.class);
        resources.add(rest.CoursepackResource.class);
        resources.add(rest.FeedbackResource.class);
        resources.add(rest.FileResource.class);
        resources.add(rest.ForumResource.class);
        resources.add(rest.GamificationResource.class);
        resources.add(rest.GroupManagementResource.class);
        resources.add(rest.ModuleMountingResource.class);
        resources.add(rest.ModuleResource.class);
        resources.add(rest.StudentEnrollmentResource.class);
        resources.add(rest.UserResource.class);

    }
}
