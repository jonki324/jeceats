package common;

import common.Constants.ErrorType;

public class AppException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private ErrorInfo errInfo;

    public AppException() {
        super();
    }

    public AppException(ErrorType errorType, String msg) {
        super();
        errInfo = new ErrorInfo(errorType, msg);
    }

    public AppException(ErrorType errorType, String msg, Throwable cause) {
        super(cause);
        errInfo = new ErrorInfo(errorType, msg);
    }

    public AppException(ErrorInfo errInfo) {
        super();
        this.errInfo = errInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errInfo;
    }

    public void setErrorInfo(ErrorInfo errInfo) {
        this.errInfo = errInfo;
    }
}
