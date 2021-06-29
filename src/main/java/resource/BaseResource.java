package resource;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

public abstract class BaseResource {
    @Context
    private HttpServletRequest request;

    @Context
    private HttpHeaders headers;

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }
}
