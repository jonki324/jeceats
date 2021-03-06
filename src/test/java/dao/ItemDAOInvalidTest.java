package dao;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;
import java.util.Optional;

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

import config.MessageConfig;
import entity.Item;
import exception.DBException;
import exception.ErrorInfo;
import util.ConfigUtil;

class ItemDAOInvalidTest {
    private static EntityManager em;
    private static MessageConfig msgConfig;
    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static final String PROP_NAME_JDBC = "javax.persistence.jdbc.";
    private static final String TEST_DATA_DIR = "src/test/data/dao/ItemDAOTest/";
    private static final String DATA_BEFORE = TEST_DATA_DIR + "before.xml";
    private IDatabaseTester databaseTester;
    private ItemDAO sut;

    @BeforeAll
    static void beforeAll() throws Exception {
        em = Persistence.createEntityManagerFactory("jpa-unit").createEntityManager();
        Map<String, Object> prop = em.getProperties();
        DRIVER = (String) prop.get(PROP_NAME_JDBC + "driver");
        URL = (String) prop.get(PROP_NAME_JDBC + "url");
        USER = (String) prop.get(PROP_NAME_JDBC + "user");
        PASSWORD = (String) prop.get(PROP_NAME_JDBC + "password");
        msgConfig = new ConfigUtil().getMessageConfig();
    }

    @BeforeEach
    void beforeEach() throws Exception {
        databaseTester = new JdbcDatabaseTester(DRIVER, URL, USER, PASSWORD);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File(DATA_BEFORE));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
        sut = new ItemDAOImpl(em, msgConfig);
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
    void testInvalidCreate() {
        DBException e = assertThrows(DBException.class, () -> {
            Item item = new Item("name4", new BigDecimal("400"), "desc4", "objnm4");
            item.setId(1);
            try {
                em.getTransaction().begin();
                sut.create(item);
            } finally {
                em.getTransaction().rollback();
            }
        });
        String expected = msgConfig.ENTITY_EXISTS;
        assertTrue(e.getErrorInfo().hasError());
        assertEquals(expected, e.getErrorInfo().getErrors().get(ErrorInfo.DEFAULT_FIELD_NAME).get(0));
    }

    @Test
    void testReadNullVal() {
        Optional<Item> actual = sut.read(4);
        assertTrue(actual.isEmpty());
    }

    @Test
    void testInvalidUpdate() {
        DBException e1 = assertThrows(DBException.class, () -> {
            Item item = sut.read(2).get();
            item.setId(4);
            item.setName("name22");
            try {
                em.getTransaction().begin();
                sut.update(item);
            } finally {
                em.getTransaction().rollback();
            }
        });
        String expected = msgConfig.PERSISTENCE;
        assertTrue(e1.getErrorInfo().hasError());
        assertEquals(expected, e1.getErrorInfo().getErrors().get(ErrorInfo.DEFAULT_FIELD_NAME).get(0));

        DBException e2 = assertThrows(DBException.class, () -> {
            Item item = sut.read(2).get();
            item.setName("name22");
            item.setVersion(2);
            try {
                em.getTransaction().begin();
                sut.update(item);
            } finally {
                em.getTransaction().rollback();
            }
        });
        expected = msgConfig.OPTIMISTIC_LOCK;
        assertTrue(e2.getErrorInfo().hasError());
        assertEquals(expected, e2.getErrorInfo().getErrors().get(ErrorInfo.DEFAULT_FIELD_NAME).get(0));
    }

    @Test
    void testInvalidDelete() {
        DBException e1 = assertThrows(DBException.class, () -> {
            Item item = sut.read(2).get();
            item.setId(4);
            try {
                em.getTransaction().begin();
                sut.delete(item);
            } finally {
                em.getTransaction().rollback();
            }
        });
        String expected = msgConfig.NOT_EXIST;
        assertTrue(e1.getErrorInfo().hasError());
        assertEquals(expected, e1.getErrorInfo().getErrors().get(ErrorInfo.DEFAULT_FIELD_NAME).get(0));

        DBException e2 = assertThrows(DBException.class, () -> {
            Item item = sut.read(2).get();
            item.setVersion(2);
            try {
                em.getTransaction().begin();
                sut.delete(item);
            } finally {
                em.getTransaction().rollback();
            }
        });
        expected = msgConfig.OPTIMISTIC_LOCK;
        assertTrue(e2.getErrorInfo().hasError());
        assertEquals(expected, e2.getErrorInfo().getErrors().get(ErrorInfo.DEFAULT_FIELD_NAME).get(0));
    }

    @Test
    void testReadAllEmptyList() throws Exception {
        databaseTester.setSetUpOperation(DatabaseOperation.DELETE_ALL);
        databaseTester.onSetup();
        int actual = sut.readAll().size();
        int expected = 0;
        assertEquals(expected, actual);
    }
}
