package exception;

public class ValidationException extends BaseException {
    private static final long serialVersionUID = 1L;

    public ValidationException() {
        super();
    }

    public ValidationException(String field, String msg) {
        super(field, msg);
    }

    public ValidationException(String field, String msg, Throwable cause) {
        super(field, msg, cause);
    }

    public ValidationException(ErrorInfo errInfo) {
        super(errInfo);
    }

    public ValidationException(ErrorInfo errInfo, Throwable cause) {
        super(errInfo, cause);
    }
}
