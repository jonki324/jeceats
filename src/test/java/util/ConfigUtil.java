package util;

import org.eclipse.microprofile.config.ConfigProvider;

import config.MessageConfig;
import config.StorageConfig;

public class ConfigUtil {
    public StorageConfig getStorageConfig() {
        var storageConfig = new StorageConfig();
        var config = ConfigProvider.getConfig();
        storageConfig.ENDPOINT = config.getValue("server.storage.endpoint", String.class);
        storageConfig.REGION = config.getValue("server.storage.region", String.class);
        storageConfig.ACCESS_KEY = config.getValue("server.storage.access.key", String.class);
        storageConfig.SECRET_KEY = config.getValue("server.storage.secret.key", String.class);
        storageConfig.BUCKET_NAME = config.getValue("server.storage.bucket.name", String.class);
        storageConfig.EXPIRY_SEC = config.getValue("server.storage.expiry.sec", Integer.class);
        return storageConfig;
    }

    public MessageConfig getMessageConfig() {
        var msgConfig = new MessageConfig();
        var config = ConfigProvider.getConfig();
        msgConfig.OPTIMISTIC_LOCK = config.getValue("message.optimistic.lock", String.class);
        msgConfig.ENTITY_EXISTS = config.getValue("message.entity.exists", String.class);
        msgConfig.PERSISTENCE = config.getValue("message.persistence", String.class);
        msgConfig.NOT_EXIST = config.getValue("message.not.exist", String.class);
        msgConfig.INVALID_LOGIN = config.getValue("message.invalid.login", String.class);
        msgConfig.BUCKET_CONNECT = config.getValue("message.bucket.connect", String.class);
        msgConfig.GET_SIGNED_URL = config.getValue("message.get.signed.url", String.class);
        msgConfig.REMOVE_OBJECT = config.getValue("message.remove.object", String.class);
        msgConfig.OTHER = config.getValue("message.other", String.class);
        return msgConfig;
    }
}
