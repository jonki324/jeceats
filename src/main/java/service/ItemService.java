package service;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import dao.ItemDAO;
import dao.ObjectStorageDAO;
import dto.ItemDTO;
import dto.ItemInputDTO;
import dto.ItemListOutputDTO;
import dto.ItemOutputDTO;
import entity.Item;

@RequestScoped
public class ItemService extends BaseService {
    @Inject
    protected ItemDAO itemDAO;

    @Inject
    protected ObjectStorageDAO objectStorageDAO;

    public ItemListOutputDTO getAll() {
        List<ItemDTO> ItemDTOList = itemDAO.readAll().stream().sequential().map(e -> {
            String url = getImageUrl(e);
            var dto = convert(e);
            dto.setImageSrc(url);
            return dto;
        }).collect(Collectors.toList());
        return new ItemListOutputDTO(ItemDTOList);
    }

    public ItemOutputDTO get(Integer id) {
        ItemDTO itemDTO = itemDAO.read(id).map(e -> {
            String url = getImageUrl(e);
            var dto = convert(e);
            dto.setImageSrc(url);
            return dto;
        }).orElseThrow(() -> createAppException(msgConfig.NOT_EXIST));
        return new ItemOutputDTO(itemDTO);
    }

    @Transactional
    public void add(ItemInputDTO itemDTO) {
        Item entity = convert(itemDTO);
        itemDAO.create(entity);
        itemDAO.detach(entity);
    }

    @Transactional
    public void edit(ItemInputDTO itemDTO) {
        Item target = itemDAO.read(itemDTO.getId()).orElseThrow(() -> createAppException(msgConfig.NOT_EXIST));
        target.setName(itemDTO.getName());
        target.setPrice(itemDTO.getPrice());
        target.setDescription(itemDTO.getDescription());
        target.setObjectName(itemDTO.getObjectName());
        target.setVersion(itemDTO.getVersion());
        Item entity = itemDAO.update(target);
        itemDAO.detach(entity);
    }

    @Transactional
    public void remove(ItemInputDTO itemDTO) {
        Item entity = new Item();
        entity.setId(itemDTO.getId());
        entity.setVersion(itemDTO.getVersion());
        itemDAO.delete(entity);
        objectStorageDAO.removeObject(itemDTO.getObjectName());
    }

    private ItemDTO convert(Item entity) {
        ItemDTO itemDTO = new ItemDTO();
        itemDTO.setId(entity.getId());
        itemDTO.setName(entity.getName());
        itemDTO.setPrice(entity.getPrice());
        itemDTO.setDescription(entity.getDescription());
        itemDTO.setObjectName(entity.getObjectName());
        itemDTO.setVersion(entity.getVersion());
        itemDAO.detach(entity);
        return itemDTO;
    }

    private Item convert(ItemInputDTO itemDTO) {
        Item entity = new Item();
        entity.setName(itemDTO.getName());
        entity.setPrice(itemDTO.getPrice());
        entity.setDescription(itemDTO.getDescription());
        entity.setObjectName(itemDTO.getObjectName());
        return entity;
    }

    private String getImageUrl(Item entity) {
        return objectStorageDAO.getPresignedObjectUrlMethodGet(entity.getId(), entity.getObjectName());
    }
}
