package config;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Dependent
public class MessageConfig {
    public final String OPTIMISTIC_LOCK;

    public final String ENTITY_EXISTS;

    public final String PERSISTENCE;

    public final String NOT_EXIST;

    public final String INVALID_LOGIN;

    public final String BUCKET_CONNECT;

    public final String GET_SIGNED_URL;

    public final String REMOVE_OBJECT;

    public final String OTHER;

    @Inject
    public MessageConfig(@ConfigProperty(name = "message.optimistic.lock") String OPTIMISTIC_LOCK,
            @ConfigProperty(name = "message.entity.exists") String ENTITY_EXISTS,
            @ConfigProperty(name = "message.persistence") String PERSISTENCE,
            @ConfigProperty(name = "message.not.exist") String NOT_EXIST,
            @ConfigProperty(name = "message.invalid.login") String INVALID_LOGIN,
            @ConfigProperty(name = "message.bucket.connect") String BUCKET_CONNECT,
            @ConfigProperty(name = "message.get.signed.url") String GET_SIGNED_URL,
            @ConfigProperty(name = "message.remove.object") String REMOVE_OBJECT,
            @ConfigProperty(name = "message.other") String OTHER) {
        this.OPTIMISTIC_LOCK = OPTIMISTIC_LOCK;
        this.ENTITY_EXISTS = ENTITY_EXISTS;
        this.PERSISTENCE = PERSISTENCE;
        this.NOT_EXIST = NOT_EXIST;
        this.INVALID_LOGIN = INVALID_LOGIN;
        this.BUCKET_CONNECT = BUCKET_CONNECT;
        this.GET_SIGNED_URL = GET_SIGNED_URL;
        this.REMOVE_OBJECT = REMOVE_OBJECT;
        this.OTHER = OTHER;
    }
}
