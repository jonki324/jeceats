package dao;

import static org.junit.Assert.assertFalse;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.util.ResourceBundle;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import common.AppException;
import common.Constants;
import common.Constants.ErrorType;
import io.minio.MinioClient;
import io.minio.RemoveBucketArgs;
import io.minio.errors.ErrorResponseException;

class ObjectStrorageDAOTest {
    private static final String ENDPOINT = "http://localhost:9000";
    private static final String REGION = "ap-northeast-1";
    private static final String ACCESS_KEY = "accesskey";
    private static final String SECRET_KEY = "secretkey";
    private static final String BUCKET_NAME = "jeceats";
    private static final String EXPIRY_SEC = "60";
    private ObjectStorageDAO sut;

    @BeforeAll
    static void beforeAll() throws Exception {
        removeBucket();
    }

    @BeforeEach
    void beforeEach() {
        sut = new ObjectStorageDAOImpl(ENDPOINT, REGION, ACCESS_KEY, SECRET_KEY, BUCKET_NAME, EXPIRY_SEC);
    }

    @Test
    void testBucketExists() {
        boolean actual = sut.bucketExists(BUCKET_NAME);
        assertTrue(actual);
    }

    @Test
    void testBucketExistsNotFound() {
        boolean actual = sut.bucketExists("notfoundbacket");
        assertFalse(actual);
    }

    @Test
    void testBucketExistsInvalid() {
        AppException actual = Assertions.assertThrows(AppException.class, () -> {
            sut.bucketExists(null);
        });
        String expected = ResourceBundle.getBundle("messages").getString(ErrorType.BUCKET_CONNECT_ERROR.toString());
        assertEquals(expected, actual.getErrorInfo().getErrors().get(Constants.DEFAULT_FIELD_NAME).get(0));
    }

    @Test
    void testGetPresigneObjectUrlMethodGet() {
        String actual = sut.getPresignedObjectUrlMethodGet("test");
        assertNotEquals("", actual);
    }

    @Test
    void testGetPresigneObjectUrlMethodGetInvalid() {
        AppException actual = Assertions.assertThrows(AppException.class, () -> {
            sut.getPresignedObjectUrlMethodGet(null);
        });
        String expected = ResourceBundle.getBundle("messages").getString(ErrorType.SIGNED_URL_GET_ERROR.toString());
        assertEquals(expected, actual.getErrorInfo().getErrors().get(Constants.DEFAULT_FIELD_NAME).get(0));
    }

    @Test
    void testGetPresigneObjectUrlMethodPut() {
        String actual = sut.getPresignedObjectUrlMethodPut("test");
        assertNotEquals("", actual);
    }

    @Test
    void testGetPresigneObjectUrlMethodPutInvalid() {
        AppException actual = Assertions.assertThrows(AppException.class, () -> {
            sut.getPresignedObjectUrlMethodPut(null);
        });
        String expected = ResourceBundle.getBundle("messages").getString(ErrorType.SIGNED_URL_GET_ERROR.toString());
        assertEquals(expected, actual.getErrorInfo().getErrors().get(Constants.DEFAULT_FIELD_NAME).get(0));
    }

    private static void removeBucket() throws Exception {
        MinioClient client = MinioClient.builder().endpoint(ENDPOINT).region(REGION).credentials(ACCESS_KEY, SECRET_KEY)
                .build();
        try {
            client.removeBucket(RemoveBucketArgs.builder().bucket(BUCKET_NAME).build());
        } catch (ErrorResponseException e) {
            if (!e.errorResponse().code().equals("NoSuchBucket")) {
                throw e;
            }
        }
    }
}
