package dao;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.io.File;
import java.math.BigDecimal;
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

import entity.Item;

class ItemDAOTest {
    private static EntityManager em;
    private static String DRIVER;
    private static String URL;
    private static String USER;
    private static String PASSWORD;
    private static final String PROP_NAME_JDBC = "javax.persistence.jdbc.";
    private static final String TEST_DATA_DIR = "src/test/data/dao/ItemDAOTest/";
    private static final String DATA_BEFORE = TEST_DATA_DIR + "before.xml";
    private static final String DATA_AFTER_CREATE = TEST_DATA_DIR + "after_create.xml";
    private static final String DATA_AFTER_UPDATE = TEST_DATA_DIR + "after_update.xml";
    private static final String DATA_AFTER_DELETE = TEST_DATA_DIR + "after_delete.xml";
    private static final String TEST_DB_TABLE = "items";
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
    }

    @BeforeEach
    void beforeEach() throws Exception {
        databaseTester = new JdbcDatabaseTester(DRIVER, URL, USER, PASSWORD);
        IDataSet dataSet = new FlatXmlDataSetBuilder().build(new File(DATA_BEFORE));
        databaseTester.setDataSet(dataSet);
        databaseTester.setSetUpOperation(DatabaseOperation.CLEAN_INSERT);
        databaseTester.onSetup();
        sut = new ItemDAOImpl(em);
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
    void testCreate() throws Exception {
        Item item = new Item("name4", new BigDecimal("400"), "desc4", "objnm4");
        em.getTransaction().begin();
        sut.create(item);
        em.getTransaction().commit();

        ITable actualTbl = databaseTester.getConnection().createTable(TEST_DB_TABLE);
        ITable expectedTbl = new FlatXmlDataSetBuilder().build(new File(DATA_AFTER_CREATE)).getTable(TEST_DB_TABLE);

        String[] excludedCol = new String[] { "id", "created_at", "updated_at", "version" };
        ITable filteredActualTbl = DefaultColumnFilter.excludedColumnsTable(actualTbl, excludedCol);
        ITable filteredExpectedTbl = DefaultColumnFilter.excludedColumnsTable(expectedTbl, excludedCol);

        Assertion.assertEquals(filteredExpectedTbl, filteredActualTbl);
    }

    @Test
    void testRead() {
        Item actual = sut.read(1).get();
        assertEquals(1, actual.getId());
        assertEquals("name1", actual.getName());
        assertEquals(new BigDecimal("100"), actual.getPrice());
        assertEquals("desc1", actual.getDescription());
        assertEquals("objnm1", actual.getObjectName());
    }

    @Test
    void testUpdate() throws Exception {
        Item item = sut.read(2).get();
        item.setName("name22");
        item.setPrice(new BigDecimal("222"));
        item.setDescription("desc22");
        item.setObjectName("objnm22");
        em.getTransaction().begin();
        sut.update(item);
        em.getTransaction().commit();

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
    void testDelete() throws Exception {
        Item item = sut.read(2).get();
        em.getTransaction().begin();
        sut.delete(item);
        em.getTransaction().commit();

        ITable actualTbl = databaseTester.getConnection().createTable(TEST_DB_TABLE);
        ITable expectedTbl = new FlatXmlDataSetBuilder().build(new File(DATA_AFTER_DELETE)).getTable(TEST_DB_TABLE);

        String[] excludedCol = new String[] { "created_at", "updated_at", "version" };
        ITable filteredActualTbl = DefaultColumnFilter.excludedColumnsTable(actualTbl, excludedCol);
        ITable filteredExpectedTbl = DefaultColumnFilter.excludedColumnsTable(expectedTbl, excludedCol);

        Assertion.assertEquals(filteredExpectedTbl, filteredActualTbl);
    }

    @Test
    void testReadAll() {
        int actual = sut.readAll().size();
        int expected = 3;
        assertEquals(expected, actual);
    }

    @Test
    void testCountByIdAndObjectName() {
        Long actual = sut.countByIdAndObjectName(1, "objnm1");
        Long expected = 1L;
        assertEquals(expected, actual);

        actual = sut.countByIdAndObjectName(1, "objnm2");
        expected = 0L;
        assertEquals(expected, actual);
    }
}
