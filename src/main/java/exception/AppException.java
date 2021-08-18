package exception;

public class AppException extends BaseException {
    private static final long serialVersionUID = 1L;

    public AppException() {
        super();
    }

    public AppException(String msg) {
        super(msg);
    }

    public AppException(String msg, Throwable cause) {
        super(msg, cause);
    }

    public AppException(String field, String msg) {
        super(field, msg);
    }

    public AppException(String field, String msg, Throwable cause) {
        super(field, msg, cause);
    }

    public AppException(ErrorInfo errInfo) {
        super(errInfo);
    }

    public AppException(ErrorInfo errInfo, Throwable cause) {
        super(errInfo, cause);
    }
}
