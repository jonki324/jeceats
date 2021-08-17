package util;

import org.eclipse.microprofile.config.ConfigProvider;

import config.MessageConfig;
import config.StorageConfig;

public class ConfigUtil {
    public StorageConfig getStorageConfig() {
        var config = ConfigProvider.getConfig();
        var ENDPOINT = config.getValue("server.storage.endpoint", String.class);
        var REGION = config.getValue("server.storage.region", String.class);
        var ACCESS_KEY = config.getValue("server.storage.access.key", String.class);
        var SECRET_KEY = config.getValue("server.storage.secret.key", String.class);
        var BUCKET_NAME = config.getValue("server.storage.bucket.name", String.class);
        var EXPIRY_SEC = config.getValue("server.storage.expiry.sec", Integer.class);
        var storageConfig = new StorageConfig(ENDPOINT, REGION, ACCESS_KEY, SECRET_KEY, BUCKET_NAME, EXPIRY_SEC);
        return storageConfig;
    }

    public MessageConfig getMessageConfig() {
        var config = ConfigProvider.getConfig();
        var OPTIMISTIC_LOCK = config.getValue("message.optimistic.lock", String.class);
        var ENTITY_EXISTS = config.getValue("message.entity.exists", String.class);
        var PERSISTENCE = config.getValue("message.persistence", String.class);
        var NOT_EXIST = config.getValue("message.not.exist", String.class);
        var INVALID_LOGIN = config.getValue("message.invalid.login", String.class);
        var BUCKET_CONNECT = config.getValue("message.bucket.connect", String.class);
        var GET_SIGNED_URL = config.getValue("message.get.signed.url", String.class);
        var REMOVE_OBJECT = config.getValue("message.remove.object", String.class);
        var OTHER = config.getValue("message.other", String.class);
        var msgConfig = new MessageConfig(OPTIMISTIC_LOCK, ENTITY_EXISTS, PERSISTENCE, NOT_EXIST, INVALID_LOGIN,
                BUCKET_CONNECT, GET_SIGNED_URL, REMOVE_OBJECT, OTHER);
        return msgConfig;
    }
}
