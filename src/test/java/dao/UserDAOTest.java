package dao;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;

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

import entity.Role;
import entity.User;

class UserDAOTest {
    private static EntityManager em;
    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static final String PROP_NAME_JDBC = "javax.persistence.jdbc.";
    private static final String TEST_DATA_DIR = "src/test/data/dao/UserDAOTest/";
    private static final String DATA_BEFORE = TEST_DATA_DIR + "before.xml";
    private IDatabaseTester databaseTester;
    private UserDAO sut;

    @BeforeAll
    static void beforeAll() throws Exception {
        em = Persistence.createEntityManagerFactory("jpa-unit").createEntityManager();
        Map<String, Object> prop = em.getProperties();
        DRIVER = (String) prop.get(PROP_NAME_JDBC + "driver");
        URL = (String) prop.get(PROP_NAME_JDBC + "url");
        USER = (String) prop.get(PROP_NAME_JDBC + "user");
        PASSWORD = (String) prop.get(PROP_NAME_JDBC + "password");
    }

    @BeforeEach
    void beforeEach() throws Exception {
        databaseTester = new JdbcDatabaseTester(DRIVER, URL, USER, PASSWORD);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File(DATA_BEFORE));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
        sut = new UserDAOImpl(em);
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
    void testFindByLoginIdAndPassword() throws Exception {
        User actual = sut.findByLoginIdAndPassword("admin", "pass2").get();
        assertEquals(2, actual.getId());
        assertEquals("name2", actual.getName());
        assertEquals(Role.ADMIN, actual.getRole());
    }

    @Test
    void testFindByLoginIdAndPasswordInvalid() throws Exception {
        boolean actual = sut.findByLoginIdAndPassword("admin", "pass").isEmpty();
        assertTrue(actual);
    }
}
