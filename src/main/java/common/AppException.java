package common;

public class AppException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private ErrorInfo errInfo;

    public AppException() {
        super();
    }

    public AppException(String msg) {
        super();
        errInfo = new ErrorInfo(msg);
    }

    public AppException(String msg, Throwable cause) {
        super(cause);
        errInfo = new ErrorInfo(msg);
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
