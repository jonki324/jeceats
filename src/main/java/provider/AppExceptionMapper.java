package provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import exception.AppException;

@Provider
public class AppExceptionMapper implements ExceptionMapper<AppException> {
    @Override
    public Response toResponse(AppException exception) {
        exception.printStackTrace();
        return Response.status(Status.INTERNAL_SERVER_ERROR).entity(exception.getErrorInfo()).build();
    }
}
