package entity.mapper;

import java.util.stream.Collectors;

import dto.OrderDTO;
import entity.Order;

public class OrderEntityMapper {
    public Order mapToEntity(OrderDTO dto) {
        return Order.builder().id(dto.getId()).status(dto.getStatus()).details(dto.getDetails().stream().map(d -> {
            return new OrderDetailEntityMapper().mapToEntity(d);
        }).collect(Collectors.toList())).statusLogs(dto.getStatusLogs().stream().map(l -> {
            return new OrderStatusLogEntityMapper().mapToEntity(l);
        }).collect(Collectors.toList())).version(dto.getVersion()).build();
    }
}
