package common;

public class Constants {
    public static final String DEFAULT_FIELD_NAME = "system";

    public enum ErrorType {
        OPTIMISTIC_LOCK, ENTITY_EXISTS, PERSISTENCE, NOT_EXIST, VALIDATION_ERROR, LOGIN_ERROR
    }

    public enum Role {
        STAFF, ADMIN
    }
}
