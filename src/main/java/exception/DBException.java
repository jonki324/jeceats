package exception;

public class DBException extends BaseException {
    private static final long serialVersionUID = 1L;

    public static enum ErrorType {
        OPTIMISTIC_LOCK, ENTITY_EXISTS, PERSISTENCE, NOT_EXIST, OTHER
    }

    private ErrorType errorType;

    public DBException() {
        super();
        this.errorType = ErrorType.OTHER;
    }

    public DBException(String msg, ErrorType errorType) {
        super(msg);
        this.errorType = errorType;
    }

    public DBException(String msg, Throwable cause, ErrorType errorType) {
        super(msg, cause);
        this.errorType = errorType;
    }

    public DBException(String field, String msg, ErrorType errorType) {
        super(field, msg);
        this.errorType = errorType;
    }

    public DBException(String field, String msg, Throwable cause, ErrorType errorType) {
        super(field, msg, cause);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
