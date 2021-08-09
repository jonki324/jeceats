package provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import exception.ValidationException;

@Provider
public class ValidationExceptionMapper implements ExceptionMapper<ValidationException> {
    @Override
    public Response toResponse(ValidationException exception) {
        exception.printStackTrace();
        return Response.status(Status.BAD_REQUEST).entity(exception.getErrorInfo()).build();
    }
}
