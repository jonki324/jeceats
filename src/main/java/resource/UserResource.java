package resource;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import dto.mapper.UserDTOMapper;
import resource.request.LoginRequest;
import resource.response.mapper.LoginResponseMapper;
import service.UserService;

@RequestScoped
@Path("users")
public class UserResource extends BaseResource {
    @Inject
    protected UserService UserService;

    @POST
    @Path("login")
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest input) {
        validate(input);
        var output = UserService.login(new UserDTOMapper().mapToDTO(input));
        var response = new LoginResponseMapper().mapToResponse(output);
        return Response.status(Status.OK).entity(response).build();
    }
}
