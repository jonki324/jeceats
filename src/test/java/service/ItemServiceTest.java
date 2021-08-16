package service;

import static org.junit.Assert.assertTrue;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.mockitoSession;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoSession;

import config.MessageConfig;
import dao.ItemDAO;
import dao.ObjectStorageDAO;
import dto.ItemDTO;
import dto.ItemInputDTO;
import dto.ItemListOutputDTO;
import dto.ItemOutputDTO;
import entity.Item;
import exception.AppException;
import util.ConfigUtil;

class ItemServiceTest {
    private static MessageConfig msgConfig;

    private MockitoSession session;

    @Mock
    private ItemDAO itemDAO;

    @Mock
    private ObjectStorageDAO objectStorageDAO;

    @InjectMocks
    private ItemService itemService = new ItemService(msgConfig);

    @BeforeAll
    static void beforeAll() {
        msgConfig = new ConfigUtil().getMessageConfig();
    }

    @BeforeEach
    void beforeEach() {
        session = mockitoSession().initMocks(this).startMocking();
    }

    @AfterEach
    void afterEach() {
        session.finishMocking();
    }

    @Test
    void testGetAll() {
        when(itemDAO.readAll()).thenReturn(getMockItemList());
        when(itemDAO.countByIdAndObjectName(anyInt(), anyString())).thenReturn(1L);
        when(objectStorageDAO.getPresignedObjectUrlMethodGet(anyString())).thenReturn("objurl");
        ItemListOutputDTO tmp = itemService.getAll();
        int actual = tmp.getItems().size();
        int expected = 2;
        assertEquals(expected, actual);
    }

    @Test
    void testGetAllEmpty() {
        when(itemDAO.readAll()).thenReturn(new ArrayList<Item>());
        ItemListOutputDTO tmp = itemService.getAll();
        int actual = tmp.getItems().size();
        int expected = 0;
        assertEquals(expected, actual);
    }

    @Test
    void testGet() {
        when(itemDAO.read(anyInt())).thenReturn(Optional.of(getMockItem()));
        when(itemDAO.countByIdAndObjectName(anyInt(), anyString())).thenReturn(1L);
        when(objectStorageDAO.getPresignedObjectUrlMethodGet(anyString())).thenReturn("objurl");
        ItemOutputDTO tmp = itemService.get(1);
        ItemDTO actual = tmp.getItem();
        assertNotNull(actual);
    }

    @Test
    void testGetNull() {
        when(itemDAO.read(anyInt())).thenReturn(Optional.empty());
        AppException e = assertThrows(AppException.class, () -> {
            itemService.get(1);
        });
        String expected = msgConfig.NOT_EXIST;
        assertTrue(e.getErrorInfo().hasError());
        assertEquals(expected, e.getErrorInfo().getErrors().get(AppException.DEFAULT_FIELD_NAME).get(0));
    }

    @Test
    void testAdd() {
        doNothing().when(itemDAO).create(any(Item.class));
        itemService.add(getItemInputDTO());
        verify(itemDAO).create(any(Item.class));
    }

    @Test
    void testEdit() {
        when(itemDAO.read(anyInt())).thenReturn(Optional.of(getMockItem()));
        when(itemDAO.update(any(Item.class))).thenReturn(getMockItem());
        itemService.edit(getItemInputDTO());
        verify(itemDAO).read(anyInt());
        verify(itemDAO).update(any(Item.class));
    }

    @Test
    void testEditGetNull() {
        when(itemDAO.read(anyInt())).thenReturn(Optional.empty());
        AppException e = assertThrows(AppException.class, () -> {
            itemService.edit(getItemInputDTO());
        });
        String expected = msgConfig.NOT_EXIST;
        assertTrue(e.getErrorInfo().hasError());
        assertEquals(expected, e.getErrorInfo().getErrors().get(AppException.DEFAULT_FIELD_NAME).get(0));
    }

    @Test
    void testRemove() {
        doNothing().when(itemDAO).delete(any(Item.class));
        doNothing().when(objectStorageDAO).removeObject(anyString());
        itemService.remove(getItemInputDTO());
        verify(itemDAO).delete(any(Item.class));
        verify(objectStorageDAO).removeObject(anyString());
    }

    @Disabled
    private List<Item> getMockItemList() {
        Item item1 = new Item();
        item1.setId(1);
        item1.setName("name1");
        item1.setPrice(new BigDecimal("100"));
        item1.setDescription("desc1");
        item1.setObjectName("objnm1");
        item1.setCreatedAt(LocalDateTime.now());
        item1.setUpdatedAt(LocalDateTime.now());
        item1.setVersion(1);
        Item item2 = new Item();
        item2.setId(2);
        item2.setName("name2");
        item2.setPrice(new BigDecimal("200"));
        item2.setDescription("desc2");
        item2.setObjectName("objnm2");
        item2.setCreatedAt(LocalDateTime.now());
        item2.setUpdatedAt(LocalDateTime.now());
        item2.setVersion(1);
        return List.of(item1, item2);
    }

    @Disabled
    private Item getMockItem() {
        Item item = new Item();
        item.setId(1);
        item.setName("name1");
        item.setPrice(new BigDecimal("100"));
        item.setDescription("desc1");
        item.setObjectName("objnm1");
        item.setCreatedAt(LocalDateTime.now());
        item.setUpdatedAt(LocalDateTime.now());
        item.setVersion(1);
        return item;
    }

    @Disabled
    private ItemInputDTO getItemInputDTO() {
        ItemInputDTO item = new ItemInputDTO();
        item.setId(1);
        item.setName("name1");
        item.setPrice(new BigDecimal("100"));
        item.setDescription("desc1");
        item.setObjectName("objnm1");
        item.setVersion(1);
        return item;
    }
}
