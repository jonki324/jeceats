package service;

import java.util.ResourceBundle;

import common.AppException;
import common.Constants;
import common.Constants.ErrorType;
import common.ErrorInfo;

public abstract class BaseService {
    protected AppException createAppException(ErrorType errorType) {
        String msg = ResourceBundle.getBundle("messages").getString(errorType.toString());
        return new AppException(errorType, Constants.DEFAULT_FIELD_NAME, msg);
    }

    protected AppException createAppException(ErrorType errorType, Throwable cause) {
        String msg = ResourceBundle.getBundle("messages").getString(errorType.toString());
        return new AppException(errorType, Constants.DEFAULT_FIELD_NAME, msg, cause);
    }

    protected AppException createAppException(ErrorType errorType, ErrorInfo errInfo) {
        return new AppException(errorType, errInfo);
    }
}
