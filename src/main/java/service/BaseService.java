package service;

import java.util.Objects;
import java.util.ResourceBundle;

import common.AppException;
import common.Constants;
import common.Constants.ErrorType;

public abstract class BaseService {
    protected AppException createAppException(ErrorType errorType, Throwable cause) {
        String msg = ResourceBundle.getBundle("messages").getString(errorType.toString());
        return Objects.isNull(cause) ? new AppException(errorType, Constants.DEFAULT_FIELD_NAME, msg)
                : new AppException(errorType, Constants.DEFAULT_FIELD_NAME, msg, cause);
    }
}
