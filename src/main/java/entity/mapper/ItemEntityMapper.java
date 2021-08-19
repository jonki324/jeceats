package entity.mapper;

import dto.ItemDTO;
import entity.Item;

public class ItemEntityMapper {
    public Item mapToEntity(ItemDTO dto) {
        return Item.builder().id(dto.getId()).name(dto.getName()).price(dto.getPrice())
                .description(dto.getDescription()).objectName(dto.getObjectName()).version(dto.getVersion()).build();
    }
}
