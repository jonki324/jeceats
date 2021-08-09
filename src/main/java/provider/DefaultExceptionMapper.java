package provider;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

@Provider
public class DefaultExceptionMapper implements ExceptionMapper<Throwable> {
    @Context
    private HttpHeaders headers;

    @Override
    public Response toResponse(Throwable exception) {
        exception.printStackTrace();
        Response res = null;
        ResponseBuilder rb = null;
        if (exception instanceof WebApplicationException) {
            res = ((WebApplicationException) exception).getResponse();
        } else {
            rb = Response.status(Status.INTERNAL_SERVER_ERROR);
            rb.type(headers.getMediaType());
            res = rb.build();
        }
        return res;
    }
}
