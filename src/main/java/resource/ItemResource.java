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

import dto.mapper.ItemDTOMapper;
import resource.request.ItemAddRequest;
import resource.request.ItemEditRequest;
import resource.request.ItemRemoveRequest;
import resource.response.mapper.ItemGetListResponseMapper;
import resource.response.mapper.ItemGetResponseMapper;
import service.ItemService;

@RequestScoped
@Path("items")
public class ItemResource extends BaseResource {
    @Inject
    protected ItemService ItemService;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "STAFF", "ADMIN" })
    public Response getAll() {
        var output = ItemService.getAll();
        var response = new ItemGetListResponseMapper().mapToResponse(output);
        return Response.status(Status.OK).entity(response).build();
    }

    @GET
    @Path("{id}")
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "STAFF", "ADMIN" })
    public Response get(@PathParam("id") Integer id) {
        var output = ItemService.get(id);
        var response = new ItemGetResponseMapper().mapToResponse(output);
        return Response.status(Status.OK).entity(response).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "ADMIN" })
    public Response add(ItemAddRequest item) {
        validate(item);
        ItemService.add(new ItemDTOMapper().mapToDTO(item));
        return Response.status(Status.CREATED).build();
    }

    @PUT
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "ADMIN" })
    public Response edit(ItemEditRequest item, @PathParam("id") Integer id) {
        item.setId(id);
        validate(item);
        ItemService.edit(new ItemDTOMapper().mapToDTO(item));
        return Response.status(Status.NO_CONTENT).build();
    }

    @DELETE
    @Path("{id}")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    @RolesAllowed({ "ADMIN" })
    public Response remove(ItemRemoveRequest item, @PathParam("id") Integer id) {
        item.setId(id);
        ItemService.remove(new ItemDTOMapper().mapToDTO(item));
        return Response.status(Status.NO_CONTENT).build();
    }
}
