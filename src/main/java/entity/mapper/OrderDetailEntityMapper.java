package entity.mapper;

import dto.OrderDetailDTO;
import entity.OrderDetail;

public class OrderDetailEntityMapper {
    public OrderDetail mapToEntity(OrderDetailDTO dto) {
        return OrderDetail.builder().id(dto.getId()).itemName(dto.getItemName()).price(dto.getPrice())
                .quantity(dto.getQuantity()).item(new ItemEntityMapper().mapToEntity(dto.getItem())).build();
    }
}
