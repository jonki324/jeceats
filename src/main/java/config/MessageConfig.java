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

    public String LOGIN_ERROR;

    public String BUCKET_CONNECT_ERROR;

    public String SIGNED_URL_GET_ERROR;
}
