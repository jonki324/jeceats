package resource;

import java.util.Set;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.HttpHeaders;

import config.MessageConfig;
import exception.ErrorInfo;
import exception.ValidationException;

public abstract class BaseResource {
    @Context
    private HttpServletRequest request;

    @Context
    private HttpHeaders headers;

    @Inject
    protected MessageConfig msgConfig;

    public HttpServletRequest getRequest() {
        return request;
    }

    public HttpHeaders getHeaders() {
        return headers;
    }

    protected <T> void validate(T bean) {
        ValidatorFactory factory = Validation.buildDefaultValidatorFactory();
        Validator validator = factory.getValidator();
        Set<ConstraintViolation<T>> result = validator.validate(bean);
        if (!result.isEmpty()) {
            ErrorInfo errInfo = new ErrorInfo();
            result.forEach(e -> {
                errInfo.addError(e.getPropertyPath().toString(), e.getMessage());
            });
            throw creatValidationException(errInfo);
        }
    }

    protected ValidationException creatValidationException(ErrorInfo errInfo) {
        return new ValidationException(errInfo);
    }
}
