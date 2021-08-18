package exception;

public class BaseException extends RuntimeException {
    private static final long serialVersionUID = 1L;

    private ErrorInfo errInfo;

    public BaseException() {
        super();
        errInfo = new ErrorInfo();
    }

    public BaseException(String msg) {
        super();
        errInfo = new ErrorInfo(msg);
    }

    public BaseException(String msg, Throwable cause) {
        super(cause);
        errInfo = new ErrorInfo(msg);
    }

    public BaseException(String field, String msg) {
        super();
        errInfo = new ErrorInfo(field, msg);
    }

    public BaseException(String field, String msg, Throwable cause) {
        super(cause);
        errInfo = new ErrorInfo(field, msg);
    }

    public BaseException(ErrorInfo errInfo) {
        super();
        this.errInfo = errInfo;
    }

    public BaseException(ErrorInfo errInfo, Throwable cause) {
        super(cause);
        this.errInfo = errInfo;
    }

    public ErrorInfo getErrorInfo() {
        return errInfo;
    }

    public void setErrorInfo(ErrorInfo errInfo) {
        this.errInfo = errInfo;
    }
}
