package config;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "message")
@ApplicationScoped
public class MessageConfig {
    public String OPTIMISTIC_LOCK;

    public String ENTITY_EXISTS;

    public String PERSISTENCE;

    public String NOT_EXIST;

    public String INVALID_LOGIN;

    public String BUCKET_CONNECT;

    public String GET_SIGNED_URL;

    public String REMOVE_OBJECT;

    public String OTHER;
}
