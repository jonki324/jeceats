package service;

import javax.inject.Inject;

import config.MessageConfig;
import exception.AppException;
import exception.ErrorInfo;
import exception.ValidationException;

public abstract class BaseService {
    @Inject
    protected MessageConfig msgConfig;

    public BaseService() {
        super();
    }

    public BaseService(MessageConfig msgConfig) {
        super();
        this.msgConfig = msgConfig;
    }

    protected AppException createAppException(String msg) {
        return new AppException(AppException.DEFAULT_FIELD_NAME, msg);
    }

    protected AppException createAppException(String msg, Throwable cause) {
        return new AppException(AppException.DEFAULT_FIELD_NAME, msg, cause);
    }

    protected AppException createAppException(ErrorInfo errInfo) {
        return new AppException(errInfo);
    }

    protected ValidationException createValidationException(String msg) {
        return new ValidationException(ValidationException.DEFAULT_FIELD_NAME, msg);
    }

    protected ValidationException createValidationException(String msg, Throwable cause) {
        return new ValidationException(ValidationException.DEFAULT_FIELD_NAME, msg, cause);
    }

    protected ValidationException creatValidationException(ErrorInfo errInfo) {
        return new ValidationException(errInfo);
    }
}
