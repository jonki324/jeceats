package service;

import java.util.List;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import config.MessageConfig;
import dao.ItemDAO;
import dao.ObjectStorageDAO;
import dto.ItemDTO;
import dto.mapper.ItemDTOMapper;
import entity.Item;
import entity.mapper.ItemEntityMapper;

@RequestScoped
public class ItemService extends BaseService {
    @Inject
    protected ItemDAO itemDAO;

    @Inject
    protected ObjectStorageDAO objectStorageDAO;

    public ItemService() {
        super();
    }

    public ItemService(MessageConfig msgConfig) {
        super(msgConfig);
    }

    public List<ItemDTO> getAll() {
        List<ItemDTO> ItemDTOList = itemDAO.readAll().stream().sequential().map(e -> {
            var url = getImageUrl(e);
            var dto = new ItemDTOMapper().mapToDTO(e);
            dto.setImageSrc(url);
            return dto;
        }).collect(Collectors.toList());
        return ItemDTOList;
    }

    public ItemDTO get(Integer id) {
        ItemDTO itemDTO = itemDAO.read(id).map(e -> {
            var url = getImageUrl(e);
            var dto = new ItemDTOMapper().mapToDTO(e);
            dto.setImageSrc(url);
            return dto;
        }).orElseThrow(() -> createValidationException(msgConfig.NOT_EXIST));
        return itemDTO;
    }

    @Transactional
    public void add(ItemDTO itemDTO) {
        Item entity = new ItemEntityMapper().mapToEntity(itemDTO);
        itemDAO.create(entity);
        itemDAO.detach(entity);
    }

    @Transactional
    public void edit(ItemDTO itemDTO) {
        Item target = itemDAO.read(itemDTO.getId()).orElseThrow(() -> createValidationException(msgConfig.NOT_EXIST));
        target = new ItemEntityMapper().mapToEntity(itemDTO, target);
        Item entity = itemDAO.update(target);
        itemDAO.detach(entity);
    }

    @Transactional
    public void remove(ItemDTO itemDTO) {
        Item entity = new ItemEntityMapper().mapToEntity(itemDTO);
        itemDAO.delete(entity);
        objectStorageDAO.removeObject(itemDTO.getObjectName());
    }

    private String getImageUrl(Item entity) {
        Long count = itemDAO.countByIdAndObjectName(entity.getId(), entity.getObjectName());
        if (count != 1) {
            throw createAppException(msgConfig.GET_SIGNED_URL);
        }
        return objectStorageDAO.getPresignedObjectUrlMethodGet(entity.getObjectName());
    }
}
