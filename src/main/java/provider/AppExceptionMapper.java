package provider;

import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.ResponseBuilder;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import common.AppException;

@Provider
public class AppExceptionMapper implements ExceptionMapper<Throwable> {
    @Context
    private HttpHeaders headers;

    @Override
    public Response toResponse(Throwable exception) {
        Response res = null;
        ResponseBuilder rb = null;
        if (exception instanceof WebApplicationException) {
            res = ((WebApplicationException) exception).getResponse();
        } else if (exception instanceof AppException) {
            AppException e = (AppException) exception;
            Status status;
            switch (e.getErrorType()) {
                case ENTITY_EXISTS:
                case OPTIMISTIC_LOCK:
                    status = Status.CONFLICT;
                    break;
                case NOT_EXIST:
                case VALIDATION_ERROR:
                case LOGIN_ERROR:
                    status = Status.BAD_REQUEST;
                    break;
                case PERSISTENCE:
                default:
                    status = Status.INTERNAL_SERVER_ERROR;
                    break;
            }
            rb = Response.status(status).entity(e.getErrorInfo());
            res = rb.build();
        } else {
            rb = Response.status(Status.INTERNAL_SERVER_ERROR);
            rb.type(headers.getMediaType());
            res = rb.build();
        }
        return res;
    }
}
