package service;

import java.util.Objects;
import java.util.ResourceBundle;

import common.AppException;
import common.Constants.ErrorType;

public class BaseService {
    protected AppException createAppException(ErrorType errorType, Throwable cause) {
        String msg = ResourceBundle.getBundle("messages").getString(errorType.toString());
        return Objects.isNull(cause) ? new AppException(errorType, msg) : new AppException(errorType, msg, cause);
    }
}
