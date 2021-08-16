package config;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Dependent
public class StorageConfig {
    @Inject
    @ConfigProperty(name = "server.storage.endpoint")
    public String ENDPOINT;

    @Inject
    @ConfigProperty(name = "server.storage.region")
    public String REGION;

    @Inject
    @ConfigProperty(name = "server.storage.access.key")
    public String ACCESS_KEY;

    @Inject
    @ConfigProperty(name = "server.storage.secret.key")
    public String SECRET_KEY;

    @Inject
    @ConfigProperty(name = "server.storage.bucket.name")
    public String BUCKET_NAME;

    @Inject
    @ConfigProperty(name = "server.storage.expiry.sec")
    public int EXPIRY_SEC;
}
