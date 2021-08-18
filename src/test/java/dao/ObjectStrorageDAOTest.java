package dao;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import config.MessageConfig;
import config.StorageConfig;
import exception.ErrorInfo;
import exception.StorageException;
import io.minio.ListObjectsArgs;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import io.minio.Result;
import io.minio.UploadObjectArgs;
import io.minio.errors.ErrorResponseException;
import io.minio.errors.XmlParserException;
import io.minio.messages.Item;
import util.ConfigUtil;

class ObjectStrorageDAOTest {
    private static final String DATA_PATH = "src/test/data/dao/ObjectStorageDAOTest/objnm1";
    private static StorageConfig STORAGE_CONFIG;
    private static MessageConfig MESSAGE_CONFIG;
    private static MinioClient client;
    private ObjectStorageDAO sut;

    @BeforeAll
    static void beforeAll() throws Exception {
        var configUtil = new ConfigUtil();
        STORAGE_CONFIG = configUtil.getStorageConfig();
        MESSAGE_CONFIG = configUtil.getMessageConfig();
        removeBucket();
    }

    @BeforeEach
    void beforeEach() {
        sut = new ObjectStorageDAOImpl(STORAGE_CONFIG, MESSAGE_CONFIG);
    }

    @Test
    void testBucketExists() {
        boolean actual = sut.bucketExists(STORAGE_CONFIG.BUCKET_NAME);
        assertTrue(actual);
    }

    @Test
    void testBucketExistsNotFound() {
        boolean actual = sut.bucketExists("notfoundbacket");
        assertFalse(actual);
    }

    @Test
    void testBucketExistsInvalid() {
        StorageException actual = Assertions.assertThrows(StorageException.class, () -> {
            sut.bucketExists(null);
        });
        String expected = MESSAGE_CONFIG.BUCKET_CONNECT;
        assertEquals(expected, actual.getErrorInfo().getErrors().get(ErrorInfo.DEFAULT_FIELD_NAME).get(0));
    }

    @Test
    void testGetPresigneObjectUrlMethodGet() {
        String actual = sut.getPresignedObjectUrlMethodGet("objnm1");
        assertNotEquals("", actual);
    }

    @Test
    void testGetPresigneObjectUrlMethodGetInvalid() {
        StorageException actual = Assertions.assertThrows(StorageException.class, () -> {
            sut.getPresignedObjectUrlMethodGet(null);
        });
        String expected = MESSAGE_CONFIG.GET_SIGNED_URL;
        assertEquals(expected, actual.getErrorInfo().getErrors().get(ErrorInfo.DEFAULT_FIELD_NAME).get(0));
    }

    @Test
    void testGetPresigneObjectUrlMethodPut() {
        String actual = sut.getPresignedObjectUrlMethodPut("objnm99");
        assertNotEquals("", actual);
    }

    @Test
    void testGetPresigneObjectUrlMethodPutInvalid() {
        StorageException actual = Assertions.assertThrows(StorageException.class, () -> {
            sut.getPresignedObjectUrlMethodPut(null);
        });
        String expected = MESSAGE_CONFIG.GET_SIGNED_URL;
        assertEquals(expected, actual.getErrorInfo().getErrors().get(ErrorInfo.DEFAULT_FIELD_NAME).get(0));
    }

    @Test
    void testRemoveObject() throws XmlParserException, Exception {
        uploadObject();
        Iterable<Result<Item>> list = client
                .listObjects(ListObjectsArgs.builder().bucket(STORAGE_CONFIG.BUCKET_NAME).build());
        var itr = list.iterator();
        while (itr.hasNext()) {
            Result<io.minio.messages.Item> result = (Result<io.minio.messages.Item>) itr.next();
            String actual = result.get().objectName();
            String expected = "objnm1";
            assertEquals(expected, actual);
        }
        sut.removeObject("objnm1");
        Iterable<Result<Item>> list2 = client
                .listObjects(ListObjectsArgs.builder().bucket(STORAGE_CONFIG.BUCKET_NAME).build());
        var itr2 = list2.iterator();
        assertFalse(itr2.hasNext());
    }

    @Test
    void testRemoveObjectInvalid() {
        StorageException actual = Assertions.assertThrows(StorageException.class, () -> {
            sut.removeObject(null);
        });
        String expected = MESSAGE_CONFIG.REMOVE_OBJECT;
        assertEquals(expected, actual.getErrorInfo().getErrors().get(ErrorInfo.DEFAULT_FIELD_NAME).get(0));
    }

    private static void removeBucket() throws Exception {
        client = MinioClient.builder().endpoint(STORAGE_CONFIG.ENDPOINT).region(STORAGE_CONFIG.REGION)
                .credentials(STORAGE_CONFIG.ACCESS_KEY, STORAGE_CONFIG.SECRET_KEY).build();
        try {
            client.removeBucket(RemoveBucketArgs.builder().bucket(STORAGE_CONFIG.BUCKET_NAME).build());
        } catch (ErrorResponseException e) {
            if (!e.errorResponse().code().equals("NoSuchBucket")) {
                throw e;
            }
        }
    }

    private void uploadObject() throws Exception {
        try {
            client.uploadObject(UploadObjectArgs.builder().bucket(STORAGE_CONFIG.BUCKET_NAME).object("objnm1")
                    .filename(DATA_PATH).build());
        } catch (ErrorResponseException e) {
            throw e;
        }
    }
}
