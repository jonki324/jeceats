package resource;

import javax.annotation.security.RolesAllowed;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dto.ItemInputDTO;
import dto.ItemListOutputDTO;
import dto.ItemOutputDTO;
import service.ItemService;

@RequestScoped
@Path("items")
public class ItemResource extends BaseResource {
    @Inject
    protected ItemService ItemService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAll() {
        ItemListOutputDTO output = ItemService.getAll();
        return Response.status(Status.OK).entity(output).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed("STAFF")
    public Response get(@PathParam("id") Integer id) {
        ItemOutputDTO output = ItemService.get(id);
        return Response.status(Status.OK).entity(output).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response add(ItemInputDTO item) {
        validate(item);
        ItemService.add(item);
        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response edit(ItemInputDTO item, @PathParam("id") Integer id) {
        item.setId(id);
        validate(item);
        ItemService.edit(item);
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response remove(ItemInputDTO item, @PathParam("id") Integer id) {
        item.setId(id);
        ItemService.remove(item);
        return Response.status(Status.NO_CONTENT).build();
    }
}
