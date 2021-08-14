package dao;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import config.MessageConfig;
import config.StorageConfig;
import exception.StorageException;
import exception.StorageException.ErrorType;
import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
import io.minio.RemoveObjectArgs;
import io.minio.http.Method;

@Dependent
public class ObjectStorageDAOImpl implements ObjectStorageDAO {
    protected MinioClient client;

    @Inject
    protected StorageConfig config;

    @Inject
    protected MessageConfig msgConfig;

    public ObjectStorageDAOImpl() {
        super();
    }

    public ObjectStorageDAOImpl(StorageConfig config, MessageConfig msgConfig) {
        this.msgConfig = msgConfig;
        this.config = config;
        createClient();
    }

    @PostConstruct
    private void createClient() {
        client = MinioClient.builder().endpoint(config.ENDPOINT).region(config.REGION)
                .credentials(config.ACCESS_KEY, config.SECRET_KEY).build();
        if (!bucketExists(config.BUCKET_NAME)) {
            makeBucket(config.BUCKET_NAME);
        }
    }

    @Override
    public boolean bucketExists(String bucketName) {
        boolean found = false;
        try {
            found = client.bucketExists(BucketExistsArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw createStorageException(ErrorType.BUCKET_CONNECT, e);
        }
        return found;
    }

    @Override
    public void makeBucket(String bucketName) {
        try {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw createStorageException(ErrorType.BUCKET_CONNECT, e);
        }
    }

    @Override
    public String getPresignedObjectUrlMethodGet(String objectName) {
        String url = "";
        try {
            url = client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET)
                    .bucket(config.BUCKET_NAME).object(objectName).expiry(config.EXPIRY_SEC).build());
        } catch (Exception e) {
            throw createStorageException(ErrorType.GET_SIGNED_URL, e);
        }
        return url;
    }

    @Override
    public String getPresignedObjectUrlMethodPut(String objectName) {
        String url = "";
        try {
            url = client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.PUT)
                    .bucket(config.BUCKET_NAME).object(objectName).expiry(config.EXPIRY_SEC).build());
        } catch (Exception e) {
            throw createStorageException(ErrorType.GET_SIGNED_URL, e);
        }
        return url;
    }

    @Override
    public void removeObject(String objectName) {
        try {
            client.removeObject(RemoveObjectArgs.builder().bucket(config.BUCKET_NAME).object(objectName).build());
        } catch (Exception e) {
            throw createStorageException(ErrorType.REMOVE_OBJECT, e);
        }
    }

    protected StorageException createStorageException(ErrorType errorType, Throwable cause) {
        String msg;
        switch (errorType) {
            case BUCKET_CONNECT:
                msg = msgConfig.BUCKET_CONNECT;
                break;
            case GET_SIGNED_URL:
                msg = msgConfig.GET_SIGNED_URL;
                break;
            case REMOVE_OBJECT:
                msg = msgConfig.REMOVE_OBJECT;
                break;
            case OTHER:
            default:
                msg = msgConfig.OTHER;
                break;
        }
        return new StorageException(StorageException.DEFAULT_FIELD_NAME, msg, cause, errorType);
    }
}
