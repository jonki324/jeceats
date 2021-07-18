package it;

import static org.hamcrest.Matchers.blankOrNullString;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;
import static org.hamcrest.Matchers.not;

import java.io.File;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

class UserEndpointIT {
    private static EntityManager em;
    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static final String PROP_NAME_JDBC = "javax.persistence.jdbc.";
    private static final String TEST_DATA_DIR = "src/test/data/it/UserEndpointIT/";
    private static final String DATA_BEFORE = TEST_DATA_DIR + "before.xml";
    private static final String BASE_API_URL = "/api/users";
    private IDatabaseTester databaseTester;

    @BeforeAll
    static void beforeAll() throws Exception {
        em = Persistence.createEntityManagerFactory("jpa-unit").createEntityManager();
        Map<String, Object> prop = em.getProperties();
        DRIVER = (String) prop.get(PROP_NAME_JDBC + "driver");
        URL = (String) prop.get(PROP_NAME_JDBC + "url");
        USER = (String) prop.get(PROP_NAME_JDBC + "user");
        PASSWORD = (String) prop.get(PROP_NAME_JDBC + "password");
        RestAssured.port = Integer.getInteger("default.http.port");
        RestAssured.basePath = BASE_API_URL;
    }

    @BeforeEach
    void beforeEach() throws Exception {
        databaseTester = new JdbcDatabaseTester(DRIVER, URL, USER, PASSWORD);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File(DATA_BEFORE));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
    }

    @AfterEach
    void afterEach() throws Exception {
        databaseTester.setTearDownOperation(DatabaseOperation.NONE);
        databaseTester.onTearDown();
    }

    @AfterAll
    static void afterAll() {
        em.close();
    }

    @Test
    void testLogin() {
        Map<String, Object> json = Map.of("loginId", "admin", "password", "pass2");
        RestAssured.given().contentType(ContentType.JSON).body(json).when().post("/login").then().statusCode(200)
                .body("user.id", equalTo(2)).body("token", not(blankOrNullString()));
    }

    @Test
    void testLoginBadRequest() {
        Map<String, Object> json = Map.of("loginId", "admin", "password", "pass");
        RestAssured.given().contentType(ContentType.JSON).body(json).when().post("/login").then().statusCode(400)
                .body("errors.system", hasSize(1));

        json = Map.of("loginId", "", "password", "pass1");
        RestAssured.given().contentType(ContentType.JSON).body(json).when().post("/login").then().statusCode(400);

        json = Map.of("loginId", "staff", "password", "");
        RestAssured.given().contentType(ContentType.JSON).body(json).when().post("/login").then().statusCode(400);
    }
}
