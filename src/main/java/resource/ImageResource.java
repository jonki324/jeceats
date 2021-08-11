package resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dto.ImageOutputDTO;
import service.ImageService;

@RequestScoped
@Path("images")
public class ImageResource extends BaseResource {
    @Inject
    protected ImageService imageService;

    @PUT
    @Produces(MediaType.APPLICATION_JSON)
    public Response create() {
        ImageOutputDTO output = imageService.getPresignedObjectUrlForPut();
        return Response.status(Status.OK).entity(output).build();
    }

    @GET
    @Path("{id}/{objectName}")
    public Response get(@PathParam("id") Integer id, @PathParam("objectName") String objectName) {
        ImageOutputDTO output = imageService.getPresignedObjectUrlForGet(id, objectName);
        return Response.status(Status.OK).entity(output).build();
    }

    @PUT
    @Path("{id}/{objectName}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response update(@PathParam("id") Integer id, @PathParam("objectName") String objectName) {
        ImageOutputDTO output = imageService.getPresignedObjectUrlForPut(id, objectName);
        return Response.status(Status.OK).entity(output).build();
    }
}
