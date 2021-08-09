package config;

import javax.enterprise.context.ApplicationScoped;

import org.eclipse.microprofile.config.inject.ConfigProperties;

@ConfigProperties(prefix = "server.storage")
@ApplicationScoped
public class StorageConfig {
    public String ENDPOINT;

    public String REGION;

    public String ACCESS_KEY;

    public String SECRET_KEY;

    public String BUCKET_NAME;

    public int EXPIRY_SEC;
}
