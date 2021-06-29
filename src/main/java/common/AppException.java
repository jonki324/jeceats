package common;

import common.Constants.ErrorType;

public class AppException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private ErrorType errorType;

    private ErrorInfo errInfo;

    public AppException() {
        super();
    }

    public AppException(ErrorType errorType, String field, String msg) {
        super();
        this.errorType = errorType;
        errInfo = new ErrorInfo(field, msg);
    }

    public AppException(ErrorType errorType, String field, String msg, Throwable cause) {
        super(cause);
        this.errorType = errorType;
        errInfo = new ErrorInfo(field, msg);
    }

    public AppException(ErrorType errorType, ErrorInfo errInfo) {
        super();
        this.errorType = errorType;
        this.errInfo = errInfo;
    }

    public ErrorType getErrorType() {
        return errorType;
    }

    public void setErrorType(ErrorType errorType) {
        this.errorType = errorType;
    }

    public ErrorInfo getErrorInfo() {
        return errInfo;
    }

    public void setErrorInfo(ErrorInfo errInfo) {
        this.errInfo = errInfo;
    }
}
