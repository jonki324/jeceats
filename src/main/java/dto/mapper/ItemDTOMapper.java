package dto.mapper;

import dto.ItemDTO;
import entity.Item;
import resource.request.ItemAddRequest;
import resource.request.ItemEditRequest;
import resource.request.ItemRemoveRequest;

public class ItemDTOMapper {
    public ItemDTO mapToDTO(ItemAddRequest req) {
        return new ItemDTO().setName(req.getName()).setPrice(req.getPrice()).setDescription(req.getDescription())
                .setObjectName(req.getObjectName());
    }

    public ItemDTO mapToDTO(ItemEditRequest req) {
        return new ItemDTO().setId(req.getId()).setName(req.getName()).setPrice(req.getPrice())
                .setDescription(req.getDescription()).setObjectName(req.getObjectName()).setVersion(req.getVersion());
    }

    public ItemDTO mapToDTO(ItemRemoveRequest req) {
        return new ItemDTO().setId(req.getId()).setObjectName(req.getObjectName()).setVersion(req.getVersion());
    }

    public ItemDTO mapToDTO(Item entity) {
        return new ItemDTO().setId(entity.getId()).setName(entity.getName()).setPrice(entity.getPrice())
                .setDescription(entity.getDescription()).setObjectName(entity.getObjectName())
                .setVersion(entity.getVersion());
    }
}
