package dto;

import java.io.Serializable;
import java.util.List;

import entity.OrderStatus;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Accessors(chain = true)
@EqualsAndHashCode
@ToString
public class OrderDTO implements Serializable {
    private static final long serialVersionUID = 1L;

    private Integer id;

    private OrderStatus status;

    private List<OrderDetailDTO> details;

    private List<OrderStatusLogDTO> statusLogs;

    private Integer version;
}
