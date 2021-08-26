package entity.mapper;

import dto.OrderStatusLogDTO;
import entity.OrderStatusLog;

public class OrderStatusLogEntityMapper {
    public OrderStatusLog mapToEntity(OrderStatusLogDTO dto) {
        return OrderStatusLog.builder().id(dto.getId()).status(dto.getStatus()).build();
    }
}
