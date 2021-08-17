package config;

import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

@Dependent
public class StorageConfig {
    public final String ENDPOINT;

    public final String REGION;

    public final String ACCESS_KEY;

    public final String SECRET_KEY;

    public final String BUCKET_NAME;

    public final int EXPIRY_SEC;

    @Inject
    public StorageConfig(@ConfigProperty(name = "server.storage.endpoint") String ENDPOINT,
            @ConfigProperty(name = "server.storage.region") String REGION,
            @ConfigProperty(name = "server.storage.access.key") String ACCESS_KEY,
            @ConfigProperty(name = "server.storage.secret.key") String SECRET_KEY,
            @ConfigProperty(name = "server.storage.bucket.name") String BUCKET_NAME,
            @ConfigProperty(name = "server.storage.expiry.sec") int EXPIRY_SEC) {
        this.ENDPOINT = ENDPOINT;
        this.REGION = REGION;
        this.ACCESS_KEY = ACCESS_KEY;
        this.SECRET_KEY = SECRET_KEY;
        this.BUCKET_NAME = BUCKET_NAME;
        this.EXPIRY_SEC = EXPIRY_SEC;
    }
}
