package it;

import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasSize;

import java.io.File;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.Persistence;

import org.dbunit.Assertion;
import org.dbunit.IDatabaseTester;
import org.dbunit.JdbcDatabaseTester;
import org.dbunit.dataset.IDataSet;
import org.dbunit.dataset.ITable;
import org.dbunit.dataset.SortedTable;
import org.dbunit.dataset.filter.DefaultColumnFilter;
import org.dbunit.dataset.xml.FlatXmlDataSetBuilder;
import org.dbunit.operation.DatabaseOperation;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

class ItemEndpointIT {
    private static EntityManager em;
    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static final String PROP_NAME_JDBC = "javax.persistence.jdbc.";
    private static final String TEST_DATA_DIR = "src/test/data/it/ItemEndpointIT/";
    private static final String DATA_BEFORE = TEST_DATA_DIR + "before.xml";
    private static final String DATA_AFTER_CREATE = TEST_DATA_DIR + "after_create.xml";
    private static final String DATA_AFTER_UPDATE = TEST_DATA_DIR + "after_update.xml";
    private static final String DATA_AFTER_DELETE = TEST_DATA_DIR + "after_delete.xml";
    private static final String TEST_DB_TABLE = "items";
    private static final String BASE_API_URL = "/api/items";
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
    void testGetAll() {
        RestAssured.get().then().body("items", hasSize(3));
    }

    @Test
    void testGet() {
        RestAssured.given().pathParam("id", 2).when().get("/{id}").then().body("item.name", equalTo("name2"));
    }

    @Test
    void testGetBadRequest() {
        RestAssured.given().pathParam("id", 4).when().get("/{id}").then().statusCode(400).body("errors.system",
                hasSize(1));
    }

    @Test
    void testAdd() throws Exception {
        Map<String, Object> json = Map.of("name", "name4", "price", "400", "description", "desc4", "objectName",
                "objnm4");
        RestAssured.given().contentType(ContentType.JSON).body(json).when().post().then().statusCode(201);

        ITable actualTbl = databaseTester.getConnection().createTable(TEST_DB_TABLE);
        ITable expectedTbl = new FlatXmlDataSetBuilder().build(new File(DATA_AFTER_CREATE)).getTable(TEST_DB_TABLE);

        String[] excludedCol = new String[] { "id", "created_at", "updated_at", "version" };
        ITable filteredActualTbl = DefaultColumnFilter.excludedColumnsTable(actualTbl, excludedCol);
        ITable filteredExpectedTbl = DefaultColumnFilter.excludedColumnsTable(expectedTbl, excludedCol);

        Assertion.assertEquals(filteredExpectedTbl, filteredActualTbl);
    }

    @Test
    void testAddInvalid() {
        Map<String, Object> json = Map.of("name", "", "price", "400", "description", "desc4", "objectName", "objnm4");
        RestAssured.given().contentType(ContentType.JSON).body(json).when().post().then().statusCode(400);

        json = Map.of("name", "name4", "price", "-1", "description", "desc4", "objectName", "objnm4");
        RestAssured.given().contentType(ContentType.JSON).body(json).when().post().then().statusCode(400);
        json = Map.of("name", "name4", "price", "10000", "description", "desc4", "objectName", "objnm4");
        RestAssured.given().contentType(ContentType.JSON).body(json).when().post().then().statusCode(400);

        json = Map.of("name", "name4", "price", "400", "description", "", "objectName", "objnm4");
        RestAssured.given().contentType(ContentType.JSON).body(json).when().post().then().statusCode(400);

        json = Map.of("name", "name4", "price", "400", "description", "desc4", "objectName", "");
        RestAssured.given().contentType(ContentType.JSON).body(json).when().post().then().statusCode(400);
    }

    @Test
    void testEdit() throws Exception {
        Map<String, Object> json = Map.of("name", "name22", "price", "222", "description", "desc22", "objectName",
                "objnm22", "version", "1");
        RestAssured.given().contentType(ContentType.JSON).pathParam("id", 2).body(json).when().put("/{id}").then()
                .statusCode(204);

        ITable actualTbl = databaseTester.getConnection().createTable(TEST_DB_TABLE);
        ITable expectedTbl = new FlatXmlDataSetBuilder().build(new File(DATA_AFTER_UPDATE)).getTable(TEST_DB_TABLE);

        String[] sortedCol = new String[] { "id" };
        SortedTable sortedActualTbl = new SortedTable(actualTbl, sortedCol);
        sortedActualTbl.setUseComparable(true);
        SortedTable sortedExpectedTbl = new SortedTable(expectedTbl, sortedCol);
        sortedExpectedTbl.setUseComparable(true);

        String[] excludedCol = new String[] { "created_at", "updated_at", "version" };
        ITable filteredActualTbl = DefaultColumnFilter.excludedColumnsTable(sortedActualTbl, excludedCol);
        ITable filteredExpectedTbl = DefaultColumnFilter.excludedColumnsTable(sortedExpectedTbl, excludedCol);

        Assertion.assertEquals(filteredExpectedTbl, filteredActualTbl);
    }

    @Test
    void testEditBadRequest() {
        Map<String, Object> json = Map.of("name", "name4", "price", "400", "description", "desc4", "objectName",
                "objnm4", "version", "1");
        RestAssured.given().contentType(ContentType.JSON).pathParam("id", 4).body(json).when().put("/{id}").then()
                .statusCode(400);
    }

    @Test
    void testEditConflict() {
        Map<String, Object> json = Map.of("name", "name4", "price", "400", "description", "desc4", "objectName",
                "objnm4", "version", "2");
        RestAssured.given().contentType(ContentType.JSON).pathParam("id", 2).body(json).when().put("/{id}").then()
                .statusCode(409);
    }

    @Test
    void testRemove() throws Exception {
        Map<String, Object> json = Map.of("name", "name2", "price", "200", "description", "desc2", "objectName",
                "objnm4", "version", "1");
        RestAssured.given().contentType(ContentType.JSON).pathParam("id", 2).body(json).when().delete("/{id}").then()
                .statusCode(204);

        ITable actualTbl = databaseTester.getConnection().createTable(TEST_DB_TABLE);
        ITable expectedTbl = new FlatXmlDataSetBuilder().build(new File(DATA_AFTER_DELETE)).getTable(TEST_DB_TABLE);

        String[] excludedCol = new String[] { "created_at", "updated_at", "version" };
        ITable filteredActualTbl = DefaultColumnFilter.excludedColumnsTable(actualTbl, excludedCol);
        ITable filteredExpectedTbl = DefaultColumnFilter.excludedColumnsTable(expectedTbl, excludedCol);

        Assertion.assertEquals(filteredExpectedTbl, filteredActualTbl);
    }

    @Test
    void testRemoveBadRequest() {
        Map<String, Object> json = Map.of("name", "name2", "price", "200", "description", "desc2", "objectName",
                "objnm2", "version", "1");
        RestAssured.given().contentType(ContentType.JSON).pathParam("id", 4).body(json).when().delete("/{id}").then()
                .statusCode(400);
    }

    @Test
    void testRemoveConflict() {
        Map<String, Object> json = Map.of("name", "name2", "price", "200", "description", "desc2", "objectName",
                "objnm2", "version", "2");
        RestAssured.given().contentType(ContentType.JSON).pathParam("id", 2).body(json).when().delete("/{id}").then()
                .statusCode(409);
    }
}
