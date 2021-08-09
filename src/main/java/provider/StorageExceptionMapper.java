package provider;

import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.ext.ExceptionMapper;
import javax.ws.rs.ext.Provider;

import exception.StorageException;

@Provider
public class StorageExceptionMapper implements ExceptionMapper<StorageException> {
    @Override
    public Response toResponse(StorageException exception) {
        exception.printStackTrace();
        Status status;
        switch (exception.getErrorType()) {
            case BUCKET_CONNECT:
            case GET_SIGNED_URL:
            case REMOVE_OBJECT:
            case OTHER:
            default:
                status = Status.INTERNAL_SERVER_ERROR;
                break;
        }
        return Response.status(status).entity(exception.getErrorInfo()).build();
    }
}
