package config;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Dependent
public class MessageConfig {
    @Inject
    @ConfigProperty(name = "message.optimistic.lock")
    public String OPTIMISTIC_LOCK;

    @Inject
    @ConfigProperty(name = "message.entity.exists")
    public String ENTITY_EXISTS;

    @Inject
    @ConfigProperty(name = "message.persistence")
    public String PERSISTENCE;

    @Inject
    @ConfigProperty(name = "message.not.exist")
    public String NOT_EXIST;

    @Inject
    @ConfigProperty(name = "message.invalid.login")
    public String INVALID_LOGIN;

    @Inject
    @ConfigProperty(name = "message.bucket.connect")
    public String BUCKET_CONNECT;

    @Inject
    @ConfigProperty(name = "message.get.signed.url")
    public String GET_SIGNED_URL;

    @Inject
    @ConfigProperty(name = "message.remove.object")
    public String REMOVE_OBJECT;

    @Inject
    @ConfigProperty(name = "message.other")
    public String OTHER;
}
