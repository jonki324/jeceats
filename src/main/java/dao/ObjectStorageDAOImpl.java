package dao;

import java.util.ResourceBundle;

import javax.annotation.PostConstruct;
import javax.enterprise.context.Dependent;
import javax.inject.Inject;

import org.eclipse.microprofile.config.inject.ConfigProperty;

import common.AppException;
import common.Constants;
import common.Constants.ErrorType;
import io.minio.BucketExistsArgs;
import io.minio.GetPresignedObjectUrlArgs;
import io.minio.MakeBucketArgs;
import io.minio.MinioClient;
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
            throw createAppException(ErrorType.BUCKET_CONNECT_ERROR, e);
        }
        return found;
    }

    @Override
    public void makeBucket(String bucketName) {
        try {
            client.makeBucket(MakeBucketArgs.builder().bucket(bucketName).build());
        } catch (Exception e) {
            throw createAppException(ErrorType.BUCKET_CONNECT_ERROR, e);
        }
    }

    @Override
    public String getPresignedObjectUrlMethodGet(Integer id, String objectName) {
        Long count = itemDAO.countByIdAndObjectName(id, objectName);
        if (count != 1) {
            throw createAppException(ErrorType.SIGNED_URL_GET_ERROR, new IllegalArgumentException());
        }
        String url = "";
        try {
            url = client.getPresignedObjectUrl(GetPresignedObjectUrlArgs.builder().method(Method.GET).bucket(bucketName)
                    .object(objectName).expiry(Integer.parseInt(expiry)).build());
        } catch (Exception e) {
            throw createAppException(ErrorType.SIGNED_URL_GET_ERROR, e);
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
            throw createAppException(ErrorType.SIGNED_URL_GET_ERROR, e);
        }
        return url;
    }

    @Override
    public String getPresignedObjectUrlMethodPut(Integer id, String objectName) {
        Long count = itemDAO.countByIdAndObjectName(id, objectName);
        if (count != 1) {
            throw createAppException(ErrorType.SIGNED_URL_GET_ERROR, new IllegalArgumentException());
        }
        return getPresignedObjectUrlMethodPut(objectName);
    }

    protected AppException createAppException(ErrorType errorType, Throwable cause) {
        String msg = ResourceBundle.getBundle("messages").getString(errorType.toString());
        return new AppException(errorType, Constants.DEFAULT_FIELD_NAME, msg, cause);
    }
}
