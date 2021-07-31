package common;

public class Constants {
    public static final String DEFAULT_FIELD_NAME = "system";

    public enum ErrorType {
        OPTIMISTIC_LOCK, ENTITY_EXISTS, PERSISTENCE, NOT_EXIST, VALIDATION_ERROR, LOGIN_ERROR, BUCKET_CONNECT_ERROR,
        SIGNED_URL_GET_ERROR
    }

    public enum Role {
        STAFF, ADMIN
    }
}
