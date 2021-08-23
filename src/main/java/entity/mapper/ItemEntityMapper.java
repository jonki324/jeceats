package entity.mapper;

import dto.ItemDTO;
import entity.Item;

public class ItemEntityMapper {
    public Item mapToEntity(ItemDTO dto) {
        return Item.builder().id(dto.getId()).name(dto.getName()).price(dto.getPrice())
                .description(dto.getDescription()).objectName(dto.getObjectName()).version(dto.getVersion()).build();
    }

    public Item mapToEntity(ItemDTO dto, Item target) {
        target.setName(dto.getName());
        target.setPrice(dto.getPrice());
        target.setDescription(dto.getDescription());
        target.setObjectName(dto.getObjectName());
        target.setVersion(dto.getVersion());
        return target;
    }
}
