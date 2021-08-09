package provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import exception.DBException;

@Provider
public class DBExceptionMapper implements ExceptionMapper<DBException> {
    @Override
    public Response toResponse(DBException exception) {
        exception.printStackTrace();
        Status status;
        switch (exception.getErrorType()) {
            case ENTITY_EXISTS:
            case OPTIMISTIC_LOCK:
                status = Status.CONFLICT;
                break;
            case NOT_EXIST:
                status = Status.BAD_REQUEST;
                break;
            case PERSISTENCE:
            case OTHER:
            default:
                status = Status.INTERNAL_SERVER_ERROR;
                break;
        }
        return Response.status(status).entity(exception.getErrorInfo()).build();
    }
}
