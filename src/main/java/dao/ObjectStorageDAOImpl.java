package dao;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import config.MessageConfig;
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
    @Inject
    @ConfigProperty(name = "default.storage.endpoint")
    private String endpoint;

    @Inject
    @ConfigProperty(name = "default.storage.region")
    private String region;

    @Inject
    @ConfigProperty(name = "default.storage.access.key")
    private String accessKey;

    @Inject
    @ConfigProperty(name = "default.storage.secret.key")
    private String secretKey;

    @Inject
    @ConfigProperty(name = "default.storage.bucket.name")
    private String bucketName;

    @Inject
    @ConfigProperty(name = "default.storage.expiry.sec")
    private String expiry;

    private MinioClient client;

    @Inject
    private ItemDAO itemDAO;

    @Inject
    private MessageConfig msgConfig;

    public ObjectStorageDAOImpl() {
    }

    public ObjectStorageDAOImpl(String endpoint, String region, String accessKey, String secretKey, String bucketName,
            String expiry) {
        this.endpoint = endpoint;
        this.region = region;
        this.accessKey = accessKey;
        this.secretKey = secretKey;
        this.bucketName = bucketName;
        this.expiry = expiry;
        createClient();
    }

    @PostConstruct
    private void createClient() {
        client = MinioClient.builder().endpoint(endpoint).region(region).credentials(accessKey, secretKey).build();
        if (!bucketExists(bucketName)) {
            makeBucket(bucketName);
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
    public String getPresignedObjectUrlMethodGet(Integer id, String objectName) {
        Long count = itemDAO.countByIdAndObjectName(id, objectName);
        if (count != 1) {
            throw createStorageException(ErrorType.GET_SIGNED_URL, new IllegalArgumentException());
        }
        String url = "";
        try {
            url = client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName)
                    .object(objectName).expiry(Integer.parseInt(expiry)).build());
        } catch (Exception e) {
            throw createStorageException(ErrorType.GET_SIGNED_URL, e);
        }
        return url;
    }

    @Override
    public String getPresignedObjectUrlMethodPut(String objectName) {
        String url = "";
        try {
            url = client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.PUT).bucket(bucketName)
                    .object(objectName).expiry(Integer.parseInt(expiry)).build());
        } catch (Exception e) {
            throw createStorageException(ErrorType.GET_SIGNED_URL, e);
        }
        return url;
    }

    @Override
    public String getPresignedObjectUrlMethodPut(Integer id, String objectName) {
        Long count = itemDAO.countByIdAndObjectName(id, objectName);
        if (count != 1) {
            throw createStorageException(ErrorType.GET_SIGNED_URL, new IllegalArgumentException());
        }
        return getPresignedObjectUrlMethodPut(objectName);
    }

    @Override
    public void removeObject(String objectName) {
        try {
            client.removeObject(RemoveObjectArgs.builder().bucket(bucketName).object(objectName).build());
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
