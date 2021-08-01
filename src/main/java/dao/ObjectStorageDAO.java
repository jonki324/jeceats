package dao;

public interface ObjectStorageDAO {
    public abstract boolean bucketExists(String bucketName);

    public abstract void makeBucket(String bucketName);

    public abstract String getPresignedObjectUrlMethodGet(Integer id, String objectName);

    public abstract String getPresignedObjectUrlMethodPut(String objectName);

    public abstract String getPresignedObjectUrlMethodPut(Integer id, String objectName);
}
