package exception;

public class StorageException extends BaseException {
    private static final long serialVersionUID = 1L;

    public static enum ErrorType {
        BUCKET_CONNECT, GET_SIGNED_URL, REMOVE_OBJECT, OTHER
    }

    private ErrorType errorType;

    public StorageException() {
        super();
        this.errorType = ErrorType.OTHER;
    }

    public StorageException(String field, String msg, ErrorType errorType) {
        super(field, msg);
        this.errorType = errorType;
    }

    public StorageException(String field, String msg, Throwable cause, ErrorType errorType) {
        super(field, msg, cause);
        this.errorType = errorType;
    }

    public ErrorType getErrorType() {
        return errorType;
    }
}
